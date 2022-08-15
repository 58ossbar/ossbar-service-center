package com.ossbar.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.ossbar.modules.sys.persistence.TsysUserinfoMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysUserinfoService;
import com.ossbar.modules.sys.domain.TsysUserinfo;

@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/userinfo")
public class TsysUserinfoServiceImpl implements TsysUserinfoService {

	@Autowired
	private TsysUserinfoMapper tsysUserinfoMapper;

	@Override
	public R query(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
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

}
