package com.ossbar.modules.sys.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysResourceService;
import com.ossbar.modules.sys.domain.TsysResource;

@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/resource")
public class TsysResourceServiceImpl implements TsysResourceService {

	@Override
	public R saveOrUpdate(TsysResource tsysResource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R init(String menuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TsysResource> perms(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R user(String userId, String systemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R query(String menuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R getUserAllPermission(String username, String systemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TsysResource> getUserMenuList(String userId, String systemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R selectSubSysList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R selectNotButtonList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R selectListParentId(String parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R selectObjectById(String menuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TsysResource> selectListByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getUserPermissions(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R queryByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R drag(String moveId, String targetId, String moveType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R queryByMapLinkState(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R clickDrag(String moveId, String targetId) {
		// TODO Auto-generated method stub
		return null;
	}

}
