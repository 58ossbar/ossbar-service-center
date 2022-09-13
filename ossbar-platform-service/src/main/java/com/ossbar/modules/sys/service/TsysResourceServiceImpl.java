package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.google.common.base.Strings;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysResourceService;
import com.ossbar.modules.sys.domain.TsysResource;
import com.ossbar.modules.sys.domain.TsysUserinfo;
import com.ossbar.modules.sys.persistence.TsysOrgMapper;
import com.ossbar.modules.sys.persistence.TsysResourceMapper;
import com.ossbar.modules.sys.persistence.TsysUserinfoMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/resource")
public class TsysResourceServiceImpl implements TsysResourceService {

	@Autowired
	private TsysResourceMapper tsysResourceMapper;
	@Autowired
	private TsysUserinfoMapper tsysUserinfoMapper;
	@Autowired
	private TsysOrgMapper tsysOrgMapper;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;

	@Override
	@PostMapping("/saveOrUpdate")
	@SentinelResource("/sys/resource/saveOrUpdate")
	@Transactional
	@CacheEvict(value = "menu_list_cache", allEntries = true)
	public R saveOrUpdate(TsysResource tsysResource) {
		try {
			if (Strings.isNullOrEmpty(tsysResource.getMenuId())) {
				// 数据校验
				verifyForm(tsysResource);
				tsysResource
						.setOrderNum(tsysResourceMapper.selectMaxOrderNumByParentId(tsysResource.getParentId()) + 1);
				insert(tsysResource);
			} else {
				update(tsysResource);
			}
			if (!Strings.isNullOrEmpty(tsysResource.getOrgId())) {
				tsysResource.setOrgName(tsysOrgMapper.selectObjectById(tsysResource.getOrgId()).getOrgName());
			}
			return R.ok().put("data", tsysResource);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(e.getMessage());
		}
	}

	private void insert(TsysResource tsysResource) {
		tsysResource.setMenuId(Identities.uuid());
		tsysResource.setCreateTime(DateUtils.getNowTimeStamp());
		tsysResource.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tsysResourceMapper.insert(tsysResource);
	}

	private void update(TsysResource tsysResource) {
		tsysResource.setUpdateUserId(serviceLoginUtil.getLoginUserId());
		tsysResource.setUpdateTime(DateUtils.getNowTimeStamp());
		tsysResourceMapper.update(tsysResource);
	}

	/**
	 * 删除操作
	 *
	 * @param menuId
	 * @return R
	 * @author huangwb
	 * @date 2019-05-06 15:18
	 */
	@Override
	@Transactional
	@DeleteMapping("/delete")
	@SentinelResource("/sys/resource/delete")
	@CacheEvict(value = "menu_list_cache", allEntries = true)
	public R delete(String menuId) {
		// 判断是否有子菜单或按钮
		List<TsysResource> menuList = tsysResourceMapper.selectListParentId(menuId);
		if (menuList != null && !menuList.isEmpty()) {
			return R.error("请先删除子菜单或按钮");
		}
		tsysResourceMapper.deleteBatchRes(new String[] { menuId });
		return R.ok();
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

	/**
	 * 角色授权菜单
	 * @return
	 */
	@Override
	public List<TsysResource> perms() {
		String userId = serviceLoginUtil.getLoginUserId();
		// 查询列表数据
		List<TsysResource> menuList = new ArrayList<>();
		// 只有超级管理员，才能查看所有管理员列表
		if (Constant.SUPER_ADMIN.equalsIgnoreCase(userId)) {
			menuList = tsysResourceMapper.selectAllListByMap(new HashMap<String, Object>());
		} else {
			// 方法有问题
			menuList = tsysResourceMapper.selectUserList(userId);
		}
		return menuList;
	}

	/**
	 * 用户菜单列表
	 *
	 * @param userId
	 * @return R
	 * @author huangwb
	 * @date 2019-05-06 15:18
	 */
	@Override
	@GetMapping("/user")
	@SentinelResource("/sys/resource/user")
	@Cacheable(value = "menu_list_cache", key = "'user_'+#userId+ '_' +#systemId")
	public R user(String userId, String systemId) {
		if (Strings.isNullOrEmpty(systemId)) {
			systemId = "19c786f2bfbf46398e3b495f6c7014b1";
		}
		List<TsysResource> menuList = getUserMenuList(userId, systemId);
		Set<String> permissions = getUserPermissions(userId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("menuList", menuList);
		dataMap.put("permissions", permissions);
		return R.ok().put(Constant.R_DATA, dataMap);
	}

	/**
	 * 根据获取菜单列表
	 *
	 * @param menuId 判断需要查询的是子菜单还是父级菜单
	 * @return R
	 * @author huangwb
	 * @date 2019-05-06 15:18
	 */
	@Override
	@GetMapping("/query")
	@SentinelResource("/sys/resource/query")
	public R query(String menuId) {
		List<TsysResource> menuList = null;
		if (menuId == null || StringUtils.isEmpty(menuId)) {
			menuList = tsysResourceMapper.selectSubSysList();
		} else {
			menuList = tsysResourceMapper.selectListParentId(menuId);
		}
		// 查询父名称
		menuList.stream().forEach(menu -> {
			if (menu.getParentId() != null && StringUtils.isNotEmpty(menu.getParentId())) {
				menu.setParentName(tsysResourceMapper.selectObjectById(menu.getParentId()).getName());
			}
			menu.setLeaf(tsysResourceMapper.selectCountParentId(menu.getMenuId()) > 0 ? false : true);
		});
		return R.ok().put(Constant.R_DATA, menuList);
	}

	/**
	 * 通过用户名获取用户菜单列表
	 *
	 * @param userId
	 * @return R
	 * @author huangwb
	 * @date 2019-05-06 15:18
	 */

	@Override
	@GetMapping("/getUserAllPermission")
	@SentinelResource("/sys/resource/getUserAllPermission")
	@Cacheable(value = "menu_list_cache", key = "'getUserAllPermission_'+#username+'_' +#parentId")
	public R getUserAllPermission(String username, String systemId) {
		if (Strings.isNullOrEmpty(systemId)) {
			systemId = "19c786f2bfbf46398e3b495f6c7014b1";
		}
		TsysUserinfo user = tsysUserinfoMapper.selectObjectByUserName(username);
		if (user == null) {
			return R.error("用户不存在");
		}
		List<TsysResource> menuList = getUserMenuList(user.getUserId(), systemId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("menuList", menuList);
		dataMap.put("user", user);
		return R.ok().put(Constant.R_DATA, dataMap);
	}

	/**
	 * 获取用户的资源菜单
	 *
	 * @param userId
	 * @param systemId
	 * @author huangwb
	 * @date 2019-05-06 15:18
	 */
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

	/**
	 * 查出所有子系统
	 *
	 * @return R
	 * @author huangwb
	 * @date 2019-05-06 15:18
	 */
	@Override
	@GetMapping("/querySubSys")
	@SentinelResource("/sys/resource/querySubSys")
	public R selectSubSysList() {
		return R.ok().put(Constant.R_DATA, tsysResourceMapper.selectSubSysList());
	}

	/**
	 * 获取不包含按钮的菜单列表
	 *
	 * @return R
	 * @author huangwb
	 * @date 2019-05-06 15:18
	 */
	@Override
	@GetMapping("/queryNotButton")
	@SentinelResource("/sys/resource/queryNotButton")
	public R selectNotButtonList() {
		return R.ok().put(Constant.R_DATA, tsysResourceMapper.selectNotButtonList());
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
		if (Constant.SUPER_ADMIN.equalsIgnoreCase(userId)) {
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

	/**
	 * 根据前端map参数获取菜单列表
	 *
	 * @param parentId 判断需要查询的是子菜单还是父级菜单 type判断是否是展开树还是模糊查询的操作 name模糊查询参数
	 * @return R
	 * @author huangwb
	 * @date 2019-05-20 15:18
	 */
	@Override
	@GetMapping("/queryByMap")
	@SentinelResource("/sys/resource/queryByMap")
	public R queryByMap(Map<String, Object> map) {
		List<TsysResource> menuList = null;
		// 如果为1代表只需要点击 如果type为2代表树形菜单模糊查询
		if (map.get("type") != null && map.get("type").equals("1")) {
			Map<String, Object> params = new HashMap<String, Object>();
			if (map.get("parentId") == null || StringUtils.isEmpty(map.get("parentId").toString())) {
				menuList = tsysResourceMapper.selectSubSysList();
			} else {
				menuList = tsysResourceMapper.selectListParentId(map.get("parentId").toString());
			}
			// 查询父名称
			menuList.stream().forEach(menu -> {
				if (menu.getParentId() != null && StringUtils.isNotEmpty(menu.getParentId())
						&& !menu.getParentId().equals("-1")) {
					menu.setParentName(tsysResourceMapper.selectObjectById(menu.getParentId()).getName());
				}
				menu.setLeaf(tsysResourceMapper.selectCountParentId(menu.getMenuId()) > 0 ? false : true);
			});
		} else {
			menuList = tsysResourceMapper.selectAllListByMap(map);
			// 递归存储资源菜单集合
			List<TsysResource> list = new ArrayList<TsysResource>();
			// 缓存操作 保存已经查询过的menu_id 防止重复查询同一个父菜单
			Map<String, Object> params = new HashMap<String, Object>();
			// 遍历查询的数据
			menuList.stream().forEach(menu -> {
				menu.setLeaf(tsysResourceMapper.selectCountParentId(menu.getMenuId()) > 0 ? false : true);
				recursionMenuTree(list, menu, params);
			});
			menuList.addAll(list);
			// 清空map方便GC回收
			params.clear();
		}
		for (TsysResource tsysResource : menuList) {
			if (tsysResource.getOrgId() != null && StringUtils.isNotBlank(tsysResource.getOrgId())) {
				tsysResource.setOrgName(tsysOrgMapper.selectObjectById(tsysResource.getOrgId()).getOrgName());
			}
		}
		return R.ok().put(Constant.R_DATA, menuList);
	}

	/**
	 * 根据前端map参数获取display显示的菜单列表
	 *
	 * @param parentId 判断需要查询的是子菜单还是父级菜单 type判断是否是展开树还是模糊查询的操作 name模糊查询参数
	 * @return R
	 * @author huangwb
	 * @date 2019-06-1 15:18
	 */
	@Override
	@GetMapping("/queryByMapLinkDisplay")
	@SentinelResource("/sys/resource/queryByMapLinkDisplay")
	public R queryByMapLinkState(Map<String, Object> map) {
		List<TsysResource> menuList = null;
		if (map.get("parentId") == null || StringUtils.isEmpty(map.get("parentId").toString())) {
			menuList = tsysResourceMapper.selectSubSysListByDisplay("1");
		} else {
			menuList = tsysResourceMapper.selectListParentIdAndDisplay(map.get("parentId").toString(), "1");
		}
		// 查询父名称
		menuList.stream().forEach(menu -> {
			if (menu.getParentId() != null && StringUtils.isNotEmpty(menu.getParentId())
					&& !menu.getParentId().equals("-1")) {
				menu.setParentName(tsysResourceMapper.selectObjectById(menu.getParentId()).getName());
			}
			menu.setLeaf(tsysResourceMapper.selectCountParentIdAndDisplay(menu.getMenuId(), "1") > 0 ? false : true);
		});
		return R.ok().put(Constant.R_DATA, menuList);
	}

	/**
	 * 递归资源菜单 查询出数据的所有父菜单
	 *
	 * @param datas    递归存储资源菜单集合
	 * @param resource 递归判断因素
	 * @param params   缓存操作 保存已经查询过的menu_id 防止重复查询同一个父菜单
	 * @author huangwb
	 * @date 2019-05-20 15:18
	 */
	public void recursionMenuTree(List<TsysResource> datas, TsysResource resource, Map<String, Object> params) {
		// 判断数据是否有父节点
		if (!resource.getType().equals("-1") && resource.getParentId() != null
				&& StringUtils.isNotEmpty(resource.getParentId()) && !resource.getParentId().equals("1")) {
			// 判断缓存map中是否存在已经查询过的父菜单id
			if (params != null && !params.containsKey(resource.getParentId())) {
				TsysResource selectObjectById = tsysResourceMapper.selectObjectById(resource.getParentId());
				// 从下往上遍历 所以查询结点必然存在子结点
				selectObjectById.setLeaf(false);
				datas.add(selectObjectById);
				params.put(resource.getParentId(), selectObjectById);
				recursionMenuTree(datas, selectObjectById, params);
			}
		}
	}

	/**
	 * 移动资源菜单操作
	 *
	 *
	 * @param moveId   移动对象的id
	 * @param targetId 目标对象的id
	 * @param moveType 移动的类型
	 * @return
	 * @author huangwb
	 * @date 2019-06-03 17:18
	 */
	@Override
	@GetMapping("/drag")
	@SentinelResource("/sys/resource/drag")
	@CacheEvict(value = "menu_list_cache", allEntries = true)
	public R drag(String moveId, String targetId, String moveType) {
		TsysResource move = tsysResourceMapper.selectObjectById(moveId);
		TsysResource target = tsysResourceMapper.selectObjectById(targetId);
		// 如果是移动到内部
		if (moveType == "inner") {
			// 获取最大的排序号
			move.setOrderNum(tsysResourceMapper.selectMaxOrderNumByParentId(target.getMenuId()) + 1);
			move.setParentId(target.getMenuId());
		} else if (moveType.equals("before")) {
			// 获取目标元素的所有同辈元素 如果目标元素和移动元素同级则移除移动元素
			List<TsysResource> targetParentResources = tsysResourceMapper.selectListParentId(target.getParentId())
					.stream().filter(resource -> !resource.getMenuId().equals(move.getMenuId()))
					.collect(Collectors.toList());
			// 计算排序号
			int k = 0;
			// 遍历目标元素所有同辈元素
			for (int i = 0; i < targetParentResources.size(); i++) {
				TsysResource tsysResource = targetParentResources.get(i);
				// 排序号从1开始
				k++;
				// 由于是before 所以找到元素之后要在找到的元素将他的排序号再次+1 并将未+1的排序号给move对象
				// 留出一个空闲的排序号位置给move对象
				if (tsysResource.getOrgId().equals(target.getOrgId())) {
					move.setOrderNum(k);
					tsysResource.setOrderNum(k + 1);
				} else {
					// 否则k就是自然排序号
					tsysResource.setOrderNum(k);
				}
				if (tsysResource.getOrderNum() == k) {
					continue;
				}
				update(tsysResource);
			}
			// 讲move的父id设置成target目标对象的父id
			move.setParentId(target.getParentId());
		} else {
			// 获取目标元素的所有同辈元素 如果目标元素和移动元素同级则移除移动元素
			List<TsysResource> targetParentResources = tsysResourceMapper.selectListParentId(target.getParentId())
					.stream().filter(resource -> !resource.getMenuId().equals(move.getMenuId()))
					.collect(Collectors.toList());
			// 计算排序号
			int k = 0;
			// 遍历目标元素所有同辈元素
			for (int i = 0; i < targetParentResources.size(); i++) {
				TsysResource tsysResource = targetParentResources.get(i);
				// 排序号从1开始
				k++;
				// 由于我们是after操作 所以先自然排序
				tsysResource.setOrderNum(k);
				// 当找到目标元素的时候 此时k再+1 留出一个空闲的排序号位置给move对象
				if (tsysResource.getOrgId().equals(target.getOrgId())) {
					k++;
					move.setOrderNum(k);
				}
				if (tsysResource.getOrderNum() == k) {
					continue;
				}
				update(tsysResource);
			}
			move.setParentId(target.getParentId());
		}
		update(move);
		return R.ok().put(Constant.R_DATA, move);
	}

	@Override
	public R clickDrag(String moveId, String targetId) {
		// 思路：交换两节点的OrgSN和OrgCode便可行
		TsysResource move = tsysResourceMapper.selectObjectById(moveId);
		TsysResource target = tsysResourceMapper.selectObjectById(targetId);
		Integer targetOrderNum = target.getOrderNum();
		target.setOrderNum(move.getOrderNum());
		target.setUpdateUserId(serviceLoginUtil.getLoginUserId());
		target.setUpdateTime(DateUtils.getNow());
		move.setOrderNum(targetOrderNum);
		move.setUpdateUserId(serviceLoginUtil.getLoginUserId());
		move.setUpdateTime(DateUtils.getNow());
		tsysResourceMapper.update(target);
		tsysResourceMapper.update(move);
		return R.ok();
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(TsysResource menu) {
		if (StringUtils.isBlank(menu.getName())) {
			throw new CreatorblueException("菜单名称不能为空");
		}

		if (menu.getParentId() == null) {
			throw new CreatorblueException("上级菜单不能为空");
		}

		// 菜单
		if (menu.getType() == Constant.MenuType.MENU.getValue()) {
			if (StringUtils.isBlank(menu.getUrl())) {
				throw new CreatorblueException("菜单URL不能为空");
			}
		}

		// 上级菜单类型
		int parentType = Constant.MenuType.SYS.getValue();
		if (menu.getParentId().length() > 0 && !menu.getParentId().equals("-1")) {
			TsysResource parentMenu = tsysResourceMapper.selectObjectById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		// 子系统
		if (menu.getType() == Constant.MenuType.SYS.getValue()) {
			if (menu.getParentId().length() > 0 && !menu.getParentId().equals("-1")) {
				throw new CreatorblueException("子系统不能选择上级菜单");
			}
			return;
		}
		// 目录
		if (menu.getType() == Constant.MenuType.CATALOG.getValue()) {
			if (parentType != Constant.MenuType.CATALOG.getValue() && parentType != Constant.MenuType.SYS.getValue()) {
				throw new CreatorblueException("上级菜单只能为目录类型或者子系统类型");
			}
			return;
		}
		// 菜单
		if (menu.getType() == Constant.MenuType.MENU.getValue()) {
			if (parentType != Constant.MenuType.CATALOG.getValue()) {
				throw new CreatorblueException("上级菜单只能为目录类型");
			}
			return;
		}
		// 按钮
		if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
			if (parentType != Constant.MenuType.MENU.getValue()) {
				throw new CreatorblueException("上级菜单只能为菜单类型");
			}
			return;
		}
	}

}
