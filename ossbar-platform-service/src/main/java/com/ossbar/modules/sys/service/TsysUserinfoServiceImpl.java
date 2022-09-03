package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.dataprivilege.annotation.DataFilter;
import com.ossbar.common.constants.ExecStatus;
import com.ossbar.common.utils.*;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.*;
import com.ossbar.modules.sys.domain.TsysParameter;
import com.ossbar.modules.sys.domain.TsysUserinfo;
import com.ossbar.modules.sys.dto.user.SaveUserDTO;
import com.ossbar.modules.sys.persistence.TsysParameterMapper;
import com.ossbar.modules.sys.persistence.TsysUserinfoMapper;
import com.ossbar.modules.sys.persistence.TsysUserprivilegeMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/userinfo")
public class TsysUserinfoServiceImpl implements TsysUserinfoService {

	@Autowired
	private TsysUserinfoMapper tsysUserinfoMapper;
	@Autowired
	private TsysParameterMapper tsysParameterMapper;

	@Autowired
	private TsysAttachService tsysAttachService;
	@Autowired
	private TuserRoleService tuserRoleService;
	@Autowired
	private TorgUserService torgUserService;
	@Autowired
	private TuserPostService tuserPostService;
	@Autowired
	private TsysUserprivilegeMapper tsysUserprivilegeMapper;

	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private UploadUtils uploadUtils;

	/**
	 * 从参数中获取默认密码
	 *
	 * @return
	 */
	@Override
	public String getDefaultPasswordFormParameters() {
		String password = "123456";
		Map<String, Object> map = new HashMap<>();
		map.put("paraname", "系统用户默认密码");
		//map.put("paraType", "password");
		List<TsysParameter> list = tsysParameterMapper.selectListByMap(map);
		if (list.size() > 0 && list != null) {
			if (list.get(0).getParano() != null && !"".equals(list.get(0).getParano()) && list.get(0).getParano().length() >= 6) {
				password = list.get(0).getParano();
			}
		}
		return password;
	}

	/**
	 * 根据条件分页查询用户
	 * @param params 查询条件
	 * @return
	 */
	@Override
	@GetMapping("/query")
	@SentinelResource("/sys/userinfo/query")
	@DataFilter(tableAlias = "oo", customDataAuth = "", selfUser = false)
	public R query(Map<String, Object> params) {
		// 处理前端传递的机构id
		handleQueryParams(params);
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<TsysUserinfo> userList = tsysUserinfoMapper.selectListByMap(query);
		userList.stream().forEach(item -> {
			item.setUserimg(uploadUtils.stitchingPath(item.getUserimg(), 2));
		});
		Map<String, String> m = new HashMap<>();
		m.put("sex", "sex");
		m.put("userType", "userType");
		m.put("status", "status");
		convertUtil.convertParam(userList, m);
		PageUtils pageUtil = new PageUtils(userList, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	@Override
	public List<TsysUserinfo> queryList(String[] orgIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R add(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R edit(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	@Override
	@PostMapping("/save")
	@SentinelResource("/sys/userinfo/save")
	@Transactional(rollbackFor = Exception.class)
	public R save(@RequestBody SaveUserDTO user) {
		TsysUserinfo tsysUserinfo = new TsysUserinfo();
		BeanUtils.copyProperties(tsysUserinfo, user);
		// 验证登陆名是否唯一
		TsysUserinfo existedUser = tsysUserinfoMapper.selectObjectByUserName(user.getUsername());
		if (existedUser != null) {
			return R.error("登陆账号已经存在，需唯一，请重新输入！");
		}
		// 验证手机号是否唯一
		TsysUserinfo existedUser2 = tsysUserinfoMapper.selectObjectByMobile(user.getMobile());
		if (existedUser2 != null) {
			return R.error("该手机号码已被绑定，请重新输入");
		}
		// 主机构在集合的第一个位置
		if (user.getOrgId() != null) {
			user.getOrgIdList().add(0, user.getOrgId());
		}
		// 默认密码-从参数表中获取
		String defautPwd = getDefaultPasswordFormParameters();
		// 加密
		String pwd = TicketDesUtil.encryptWithMd5(defautPwd, null);
		// 组装数据
		String uuid = Identities.uuid();
		tsysUserinfo.setUserId(uuid);
		tsysUserinfo.setPassword(pwd);
		tsysUserinfo.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tsysUserinfo.setCreateTime(DateUtils.getNowTimeStamp());
		tsysUserinfo.setUpdateTime(tsysUserinfo.getCreateTime());
		// 保存用户信息
		tsysUserinfoMapper.insert(tsysUserinfo);
		// 如果上传了资源文件
		tsysAttachService.updateAttachForAdd(user.getUserimgAttachId(), uuid,  "2");
		// 保存用户与角色的关系
		tuserRoleService.saveOrUpdate(user.getRoleIdList(), Arrays.asList(tsysUserinfo.getUserId()));
		// 保存用户与机构的关系
		torgUserService.saveOrUpdate(tsysUserinfo.getUserId(), user.getOrgIdList());
		// 保存用户与岗位的关系
		if (StrUtils.isNotEmpty(user.getPostId())) {
			tuserPostService.saveOrUpdate(tsysUserinfo.getUserId(), Arrays.asList(user.getPostId()));
		}
		return R.ok("用户新增成功");
	}

	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@Override
	@PostMapping("/update")
	@SentinelResource("/sys/userinfo/update")
	@Transactional(rollbackFor = Exception.class)
	public R update(SaveUserDTO user) {
		TsysUserinfo obj = tsysUserinfoMapper.selectObjectById(user.getUserId());
		if (obj == null) {
			return R.error("该用户不存在");
		}
		TsysUserinfo tsysUserinfo = new TsysUserinfo();
		BeanUtils.copyProperties(tsysUserinfo, user);
		// 验证登陆名是否唯一
		TsysUserinfo existedUser = tsysUserinfoMapper.selectObjectByUserName(user.getUsername());
		if (existedUser != null && !existedUser.getUserId().equals(user.getUserId())) {
			return R.error("登陆账号已经存在，需唯一，请重新输入！");
		}
		// 验证手机号是否唯一
		TsysUserinfo existedUser2 = tsysUserinfoMapper.selectObjectByMobile(user.getMobile());
		if (existedUser2 != null && !existedUser2.getUserId().equals(user.getUserId())) {
			return R.error("该手机号码已被绑定，请重新输入");
		}
		// 主机构在集合的第一个位置
		if (user.getOrgId() != null) {
			user.getOrgIdList().add(0, user.getOrgId());
		}
		// 更新用户信息
		tsysUserinfo.setUpdateUserId(serviceLoginUtil.getLoginUserId());
		tsysUserinfo.setUpdateTime(DateUtils.getNowTimeStamp());
		tsysUserinfoMapper.update(tsysUserinfo);
		// 如果上传了资源文件
		tsysAttachService.updateAttachForEdit(user.getUserimgAttachId(), tsysUserinfo.getUserId(),  "2");
		// 保存用户与角色的关系
		// 如果没有选择，则表示是清空
		if (user.getRoleIdList() == null || user.getRoleIdList().isEmpty()) {
			tuserRoleService.delete(user.getUserId());
		}  else {
			tuserRoleService.saveOrUpdate(user.getRoleIdList(), Arrays.asList(tsysUserinfo.getUserId()));
		}
		// 保存用户与机构的关系
		torgUserService.saveOrUpdate(tsysUserinfo.getUserId(), user.getOrgIdList());
		// 保存用户与岗位的关系
		if (StrUtils.isNotEmpty(user.getPostId())) {
			tuserPostService.saveOrUpdate(tsysUserinfo.getUserId(), Arrays.asList(user.getPostId()));
		}
		return R.ok("用户修改成功");
	}

	@Override
	public R delete(String id, String loginUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R deleteBatch(String[] userIds) {
		if (ArrayUtils.contains(userIds, Constant.SUPER_ADMIN)) {
			return R.error("系统管理员不能删除");
		}
		if (ArrayUtils.contains(userIds, serviceLoginUtil.getLoginUserId())) {
			return R.error("当前登录用户不能删除");
		}
		// 删除用户与角色关系
		tuserRoleService.deleteBatch(userIds);
		// 删除机构用户
		torgUserService.deleteBatch(userIds);
		// 删除岗位用户
		tuserPostService.deleteBatch(userIds);
		// 删除用户信息
		tsysUserinfoMapper.deleteBatch(userIds);
		return R.ok("删除成功");
	}

	/**
	 * 明细
	 * @param id
	 * @return
	 */
	@Override
	@RequestMapping("/view/{id}")
	@SentinelResource("sys/userinfo/view")
	public R view(@PathVariable("id") String id) {
		TsysUserinfo user = tsysUserinfoMapper.selectObjectById(id);
		if (user == null) {
			return R.error(ExecStatus.INVALID_PARAM.getCode(), ExecStatus.INVALID_PARAM.getMsg());
		}
		// 处理图片路径
		user.setUserimg(uploadUtils.stitchingPath(user.getUserimg(), 2));
		// 获取用户拥有的角色
		List<String> roleIds = tuserRoleService.selectRoleIdListByUserId(user.getUserId());
		user.setRoleIdList(roleIds);
		// 获取用户的岗位
		List<String> postIds = tuserPostService.selectPostIdListByUserId(user.getUserId());
		user.setPostIdList(postIds == null ? new ArrayList<>() : postIds);
		// 获取用户机构信息
		List<String> orgIds = torgUserService.selectOrgIdListByUserId(user.getUserId());
		user.setOrgIdList(orgIds == null ? new ArrayList<>() : orgIds);
		// 设置主机构
		if (orgIds != null && !orgIds.isEmpty()) {
			user.setOrgId(orgIds.get(0));
		}
		return R.ok().put(Constant.R_DATA, user);
	}

	@Override
	public R grantRole(String[] userIds, String[] roleIds, String loginUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R toGrantRole(String[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R updatePassword(String userId, String password, String newPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 重置密码
	 * @param userIdList
	 * @return
	 */
	@Override
	@RequestMapping("/resetPassword")
	@SentinelResource("sys/userinfo/resetPassword")
	@Transactional(rollbackFor = Exception.class)
	public R resetPassword(List<String> userIdList) {
		if (userIdList == null || userIdList.isEmpty()) {
			return R.error("没有要重置密码的用户");
		}
		// 超级管理员不能被非法修改密码
		if (!Constant.SUPER_ADMIN.equals(serviceLoginUtil.getLoginUserId()) && userIdList.contains(Constant.SUPER_ADMIN)) {
			userIdList.remove(Constant.SUPER_ADMIN);
		}
		if (userIdList.size() > 20) {
			return R.error("一次性不能重置超过20个用户的密码！");
		}
		// 删除当前登录用户，当前登录用户不能重置密码
		userIdList.remove(serviceLoginUtil.getLoginUserId());
		int count = doResetPassword(userIdList);
		if (count < userIdList.size()) {
			return R.error("部分用户密码重置失败");
		}
		return R.ok("密码重置成功");
	}

	@Override
	@CacheEvict(value = "menu_list_cache", allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public R clearPermissions(List<String> userIds) {
		if (userIds == null || userIds.isEmpty()) {
			return R.error("没有要清空权限的用户");
		}
		// 移出当前登录用户，当前登录用户不能清除权限
		userIds.remove(serviceLoginUtil.getLoginUserId());
		// 清除用户与角色关系
		tuserRoleService.deleteBatch(userIds.toArray(new String[userIds.size()]));
		// 清除用户菜单权限
		tsysUserprivilegeMapper.deleteBatch(userIds.toArray(new String[userIds.size()]));
		return R.ok("成功清除权限");
	}

	@Override
	public R grantPerms(String[] userIds, String[] menuIds, String loginUserId) {

		return null;
	}

	@Override
	public List<String> selectAllPerms(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> selectAllMenuId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据唯一用户名查询记录
	 * @param username
	 * @return
	 */
	@Override
	public TsysUserinfo selectObjectByUserName(String username) {
		TsysUserinfo tsysUserinfo = tsysUserinfoMapper.selectObjectByUserName(username);
		return tsysUserinfo;
	}

	@Override
	public TsysUserinfo selectObjectByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TsysUserinfo> getAllUserinfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R updataStatus(TsysUserinfo user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R updateSort(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxSort() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 处理一些查询条件
	 * @param params
	 */
	private void handleQueryParams(Map<String, Object> params) {
		if (params != null) {
			if (params.get("orgIds") != null && !"".equals(params.get("orgIds"))) {
				String ids = params.get("orgIds").toString();
				List<String> list = new ArrayList<>();
				String[] orgIds = ids.split(",");
				for (String string : orgIds) {
					list.add(string);
				}
				params.put("orgIds", list);
			}
		}
	}

	/**
	 * 重置密码(如果参数表中没有配置密码，则密码会被重置为123456)
	 * @author huj
	 * @data 2019年5月8日
	 * @param userIds 用户ID 多个以逗号隔开
	 * @return
	 */
	private int doResetPassword(List<String> userIds) {
		Map<String, Object> map = new HashMap<>();
		map.put("userIds", userIds);
		List<TsysUserinfo> userList = tsysUserinfoMapper.selectListByMap(map);
		// 默认密码-从参数表中获取
		int count = 0;
		String newPwd = TicketDesUtil.encryptWithMd5(getDefaultPasswordFormParameters(), null);
		for (TsysUserinfo user : userList) {
			count += doUpdatePassword(user.getUserId(), user.getPassword(), newPwd);
		}
		return count;
	}

	/**
	 * 实际修改密码的方法，暂勿删除
	 * @author huj
	 * @data 2019年5月8日
	 * @param userId      用户ID
	 * @param password    旧密码
	 * @param newPassword 新密码
	 * @return int
	 */
	private int doUpdatePassword(String userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return tsysUserinfoMapper.updatePassword(map);
	}
}
