package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysResourceService;
import com.ossbar.modules.sys.domain.TsysResource;
import com.ossbar.modules.sys.persistence.TsysResourceMapper;
import com.ossbar.modules.sys.persistence.TsysUserinfoMapper;
import com.ossbar.utils.constants.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
	@GetMapping("/getUserMenuList")
	@SentinelResource("/sys/resource/getUserMenuList")
	@Cacheable(value = "menu_list_cache", key = "'getUserMenuList_'+#userId+'_' +#systemId")
	public List<TsysResource> getUserMenuList(String userId, String systemId) {
		// 系统管理员，拥有最高权限
		if (userId.equalsIgnoreCase(Constant.SUPER_ADMIN)) {
			return getAllMenuList(null, systemId);
		}
		// 用户菜单列表
		List<String> menuIdList = tsysUserinfoMapper.selectAllMenuId(userId);
		return getAllMenuList(menuIdList, systemId);
	}

	/**
	 * 获取所有菜单列表
	 */
	public List<TsysResource> getAllMenuList(List<String> menuIdList, String systemId) {
		// 查询根菜单列表
		List<TsysResource> menuList = selectListParentId(systemId, menuIdList);
		// 递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		return menuList;
	}

	private List<TsysResource> selectListParentId(String parentId, List<String> menuIdList) {
		List<TsysResource> menuList = tsysResourceMapper.selectListParentId(parentId);
		if (menuIdList == null) {
			return menuList;
		}
		List<TsysResource> userMenuList = new ArrayList<>();
		for (TsysResource menu : menuList) {
			if (menuIdList.contains(menu.getMenuId())) {
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	/**
	 * 递归
	 */
	private List<TsysResource> getMenuTreeList(List<TsysResource> menuList, List<String> menuIdList) {
		List<TsysResource> subMenuList = new ArrayList<TsysResource>();
		for (TsysResource entity : menuList) {
			// 目录
			if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
				entity.setList(getMenuTreeList(selectListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		return subMenuList;
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

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @return
	 */
	@Override
	@GetMapping("/queryParentResources")
	@SentinelResource("/sys/resource/queryParentResources")
	@Cacheable(value = "menu_list_cache", key = "'selectListParentId_' +#parentId")
	public R selectListParentId(String parentId) {
		return R.ok().put(Constant.R_DATA, tsysResourceMapper.selectListParentId(parentId));
	}

	/**
	 * 根据主键id查询资源
	 * @param menuId
	 * @return
	 */
	@Override
	@GetMapping("/get")
	@SentinelResource("/sys/resource/get")
	public R selectObjectById(String menuId) {
		return R.ok().put(Constant.R_DATA, tsysResourceMapper.selectObjectById(menuId));
	}

	/**
	 * 根据条件查询资源
	 * @param map
	 * @return
	 */
	@Override
	@GetMapping("/selectListByMap")
	@SentinelResource("/sys/resource/selectListByMap")
	public List<TsysResource> selectListByMap(Map<String, Object> map) {
		Query query = new Query(map);
		return tsysResourceMapper.selectListByMap(query);
	}

	/**
	 * 获取用户权限id
	 * @param userId
	 * @return
	 */
	@Override
	@Cacheable(value = "menu_list_cache", key = "'getUserPermissions_'+#userId")
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
