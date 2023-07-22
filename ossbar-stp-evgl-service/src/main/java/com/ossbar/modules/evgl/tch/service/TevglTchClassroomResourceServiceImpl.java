package com.ossbar.modules.evgl.tch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbRoomUtils;
import com.ossbar.modules.common.GlobalRoomPermission;
import com.ossbar.modules.common.RecursionUtils;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomResourceService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomResource;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomRoleprivilege;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomResourceMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomRoleprivilegeMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.github.pagehelper.PageHelper;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/tch/tevgltchclassroomresource")
public class TevglTchClassroomResourceServiceImpl implements TevglTchClassroomResourceService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglTchClassroomResourceServiceImpl.class);
	@Autowired
	private TevglTchClassroomResourceMapper tevglTchClassroomResourceMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private TevglTchClassroomRoleprivilegeMapper tevglTchClassroomRoleprivilegeMapper;
	@Autowired
	private RecursionUtils recursionUtils;
	@Autowired
	private CbRoomUtils roomUtils;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/tch/tevgltchclassroomresource/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglTchClassroomResource> tevglTchClassroomResourceList = tevglTchClassroomResourceMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglTchClassroomResourceList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/tch/tevgltchclassroomresource/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglTchClassroomResourceList = tevglTchClassroomResourceMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglTchClassroomResourceList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglTchClassroomResource
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/tch/tevgltchclassroomresource/save")
	public R save(@RequestBody(required = false) TevglTchClassroomResource tevglTchClassroomResource) throws OssbarException {
		tevglTchClassroomResource.setMenuId(Identities.uuid());
		ValidatorUtils.check(tevglTchClassroomResource);
		tevglTchClassroomResourceMapper.insert(tevglTchClassroomResource);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglTchClassroomResource
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/tch/tevgltchclassroomresource/update")
	public R update(@RequestBody(required = false) TevglTchClassroomResource tevglTchClassroomResource) throws OssbarException {
	    ValidatorUtils.check(tevglTchClassroomResource);
		tevglTchClassroomResourceMapper.update(tevglTchClassroomResource);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/tch/tevgltchclassroomresource/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglTchClassroomResourceMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/tch/tevgltchclassroomresource/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglTchClassroomResourceMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/tch/tevgltchclassroomresource/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglTchClassroomResourceMapper.selectObjectById(id));
	}

	/**
	 * 获取树形数据
	 * @param ctId
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R getTreeData(String ctId, String loginUserId) {
		if (StrUtils.isEmpty(ctId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return R.ok().put(Constant.R_DATA, new HashMap<>());
		}
		boolean hasAuth = roomUtils.hasOperatingAuthorization(tevglTchClassroom, loginUserId);
		if (!hasAuth) {
			return R.ok().put(Constant.R_DATA, new HashMap<>());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> allList = tevglTchClassroomResourceMapper.selectSimpleListByMap(map);
		//List<String> list = Arrays.asList("2a3", "2a4", "2a5");
		allList.stream().forEach(a -> {
			/*if (list.contains(a.get("menuId"))) {
				a.put("disabled", true);
			}*/
		});
		List<Map<String, Object>> treeData = recursionUtils.getTreeData("classroom", allList, "menuId", "parentId");
		map.put("ctId", ctId);
		map.put("roleId", "1");
		List<TevglTchClassroomRoleprivilege> roleprivilegeList = tevglTchClassroomRoleprivilegeMapper.selectListByMap(map);
		// 返回数据
		map.put("treeData", treeData);
		map.put("selectedList", roleprivilegeList.stream().map(a -> a.getMenuId()).distinct().collect(Collectors.toList()));
		List<Object> defaultExpandedKeys = allList.stream().filter(a -> a.get("parentId").equals(GlobalRoomPermission.MENU_ID)).map(a -> a.get("menuId")).collect(Collectors.toList());
		map.put("defaultExpandedKeys", defaultExpandedKeys);
		return R.ok().put(Constant.R_DATA, map);
	}
}
