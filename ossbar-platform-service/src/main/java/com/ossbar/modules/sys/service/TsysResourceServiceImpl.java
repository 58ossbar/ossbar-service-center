package com.ossbar.modules.sys.service;

import java.util.*;

import com.ossbar.modules.sys.persistence.TsysResourceMapper;
import com.ossbar.modules.sys.persistence.TsysUserinfoMapper;
import com.ossbar.utils.constants.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysResourceService;
import com.ossbar.modules.sys.domain.TsysResource;

@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/resource")
public class TsysResourceServiceImpl implements TsysResourceService {

	@Autowired
	private TsysResourceMapper tsysResourceMapper;
	@Autowired
	private TsysUserinfoMapper tsysUserinfoMapper;

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
	//@Cacheable(value = "menu_list_cache", key = "'getUserPermissions_'+#userId")
	public Set<String> getUserPermissions(String userId) {
		List<String> permsList;
		// 系统管理员，拥有最高权限
		if (userId.equalsIgnoreCase(Constant.SUPER_ADMIN)) {
			List<TsysResource> menuList = tsysResourceMapper.selectListByMap(new HashMap<>());
			permsList = new ArrayList<>(menuList.size());
			for (TsysResource menu : menuList) {
				permsList.add(menu.getPerms());
			}
		} else {
			permsList = tsysUserinfoMapper.selectAllPerms(userId);
		}
		// 用户权限列表
		Set<String> permsSet = new HashSet<>();
		for (String perms : permsList) {
			if (StringUtils.isBlank(perms)) {
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}
		return permsSet;
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
