package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.dataprivilege.annotation.DataFilter;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysUserinfoService;
import com.ossbar.modules.sys.domain.TsysUserinfo;
import com.ossbar.modules.sys.persistence.TsysUserinfoMapper;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/userinfo")
public class TsysUserinfoServiceImpl implements TsysUserinfoService {

	@Autowired
	private TsysUserinfoMapper tsysUserinfoMapper;

	@Autowired
	private ConvertUtil convertUtil;

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

	@Override
	public R save(TsysUserinfo user, String attachId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R update(TsysUserinfo user, String attachId) {
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
		// TODO Auto-generated method stub
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
