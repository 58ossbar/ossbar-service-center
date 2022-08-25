package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.dataprivilege.annotation.DataFilter;
import com.ossbar.common.constants.ExecStatus;
import com.ossbar.common.utils.*;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.*;
import com.ossbar.modules.sys.domain.TsysParameter;
import com.ossbar.modules.sys.domain.TsysRole;
import com.ossbar.modules.sys.domain.TsysUserinfo;
import com.ossbar.modules.sys.dto.user.SaveUserDTO;
import com.ossbar.modules.sys.persistence.TsysParameterMapper;
import com.ossbar.modules.sys.persistence.TsysRoleMapper;
import com.ossbar.modules.sys.persistence.TsysUserinfoMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.BeanUtils;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.TicketDesUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/userinfo")
public class TsysUserinfoServiceImpl implements TsysUserinfoService {

	@Autowired
	private TsysUserinfoMapper tsysUserinfoMapper;
	@Autowired
	private TsysParameterMapper tsysParameterMapper;
	@Autowired
	private TsysRoleMapper tsysRoleMapper;

	@Autowired
	private TsysAttachService tsysAttachService;
	@Autowired
	private TuserRoleService tuserRoleService;
	@Autowired
	private TorgUserService torgUserService;
	@Autowired
	private TuserPostService tuserPostService;

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
			// TODO 处理头像路径
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
		tsysUserinfo.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tsysUserinfo.setCreateTime(DateUtils.getNowTimeStamp());
		tsysUserinfo.setUpdateTime(tsysUserinfo.getCreateTime());
		// 保存用户信息
		tsysUserinfoMapper.insert(tsysUserinfo);
		// 如果上传了资源文件
		tsysAttachService.updateAttachForAdd(tsysUserinfo.getUserimg(), uuid,  "2");
		// TODO 保存用户与角色的关系
		//tuserRoleService.saveOrUpdate(Arrays.asList())
		// 保存用户与机构的关系
		torgUserService.saveOrUpdate(tsysUserinfo.getUserId(), user.getOrgIdList());
		// 保存用户与岗位的关系
		tuserPostService.saveOrUpdate(tsysUserinfo.getUserId(), Arrays.asList(user.getPostId()));
		return R.ok("用户新增成功");
	}

	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@Override
	public R update(SaveUserDTO user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R delete(String id, String loginUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R deleteBatch(String[] ids, String loginUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R view(String id) {
		TsysUserinfo user = tsysUserinfoMapper.selectObjectById(id);
		if (user == null) {
			return R.error(ExecStatus.INVALID_PARAM.getCode(), ExecStatus.INVALID_PARAM.getMsg());
		}
		Map<String, Object> map = new HashMap<>();
		// 获取用户所属的角色列表
		map.put("userId", user.getUserId());
		List<TsysRole> roleList = tsysRoleMapper.selectListByMap(map);
		List<String> roleIds = roleList.stream().map(a -> a.getRoleId()).collect(Collectors.toList());
		List<String> roleNames = roleList.stream().map(a -> a.getRoleName()).collect(Collectors.toList());
		// 获取岗位信息
		//List<TsysPost> postList = tsysPostService.selectListByMap(m);
		return null;
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

	@Override
	public R resetPassword(String[] userIds, String loginUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R clearPermissions(String[] userIds, String loginUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R grantPerms(String[] userIds, String[] menuIds, String loginUserId) {
		// TODO Auto-generated method stub
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
}
