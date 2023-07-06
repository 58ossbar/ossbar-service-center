package com.ossbar.modules.evgl.forum.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.RecursionUtils;
import com.ossbar.modules.evgl.forum.api.TevglForumFriendTypeService;
import com.ossbar.modules.evgl.forum.domain.TevglForumFriend;
import com.ossbar.modules.evgl.forum.domain.TevglForumFriendType;
import com.ossbar.modules.evgl.forum.persistence.TevglForumFriendMapper;
import com.ossbar.modules.evgl.forum.persistence.TevglForumFriendTypeMapper;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/forum/tevglforumfriendtype")
public class TevglForumFriendTypeServiceImpl implements TevglForumFriendTypeService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglForumFriendTypeServiceImpl.class);
	@Autowired
	private TevglForumFriendTypeMapper tevglForumFriendTypeMapper;
	@Autowired
	private TevglForumFriendMapper tevglForumFriendMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private UploadFileUtils uploadPathUtils;
	@Autowired
	private RecursionUtils recursionUtils;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/forum/tevglforumfriendtype/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglForumFriendType> tevglForumFriendTypeList = tevglForumFriendTypeMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglForumFriendTypeList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tevglForumFriendTypeList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/forum/tevglforumfriendtype/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglForumFriendTypeList = tevglForumFriendTypeMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglForumFriendTypeList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglForumFriendTypeList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglForumFriendType
	 * @throws CreatorblueException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/forum/tevglforumfriendtype/save")
	public R save(@RequestBody(required = false) TevglForumFriendType tevglForumFriendType) throws CreatorblueException {
		tevglForumFriendType.setParentId(StrUtils.isEmpty(tevglForumFriendType.getParentId()) ? "0" : tevglForumFriendType.getParentId());
		tevglForumFriendType.setTypeId(Identities.uuid());
		tevglForumFriendType.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglForumFriendType.setCreateTime(DateUtils.getNowTimeStamp());
		//ValidatorUtils.check(tevglForumFriendType);
		tevglForumFriendTypeMapper.insert(tevglForumFriendType);
		return R.ok().put(Constant.R_DATA, tevglForumFriendType.getTypeId());
	}
	/**
	 * 修改
	 * @param tevglForumFriendType
	 * @throws CreatorblueException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/forum/tevglforumfriendtype/update")
	public R update(@RequestBody(required = false) TevglForumFriendType tevglForumFriendType) throws CreatorblueException {
	    //ValidatorUtils.check(tevglForumFriendType);
		tevglForumFriendTypeMapper.update(tevglForumFriendType);
		return R.ok().put(Constant.R_DATA, tevglForumFriendType.getTypeId());
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/forum/tevglforumfriendtype/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tevglForumFriendTypeMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/forum/tevglforumfriendtype/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws CreatorblueException {
		if (ids == null || ids.length == 0) {
			return R.ok();
		}
		// 删除友情链接
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("friendTypes", Stream.of(ids).collect(Collectors.toList()));
		List<TevglForumFriend> list = tevglForumFriendMapper.selectListByMap(map);
		if (list != null && list.size() > 0) {
			List<String> collect = list.stream().map(a -> a.getFriendId()).collect(Collectors.toList());
			tevglForumFriendMapper.deleteBatch(collect.stream().toArray(String[]::new));
		}
		// 删除分类
		tevglForumFriendTypeMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/forum/tevglforumfriendtype/view")
	public R view(@PathVariable("id") String id) {
		TevglForumFriendType tevglForumFriendType = tevglForumFriendTypeMapper.selectObjectById(id);
		if (tevglForumFriendType == null) {
			return R.ok().put(Constant.R_DATA, new TevglForumFriendType());
		}
		return R.ok().put(Constant.R_DATA, tevglForumFriendType);
	}

	/**
	 * 分类列表
	 * @param params
	 * @return
	 */
	@Override
	public R queryTypeList(Map<String, Object> params) {
		params.put("sidx", "sort_num");
		params.put("order", "asc");
		List<Map<String, Object>> allList = tevglForumFriendTypeMapper.selectSimpleListMapByMap(params);
		// 取出一级菜单
		List<Map<String, Object>> oneLevlList = allList.stream().filter(a -> "0".equals(a.get("parentId"))).collect(Collectors.toList());
		// 查出所有链接
		params.clear();
		params.put("state", "Y");
		params.put("sidx", "sort_num");
		params.put("order", "asc");
		List<Map<String, Object>> linkList = getAllList(params);
		// 遍历一级菜单
		for (int i = 0; i < oneLevlList.size(); i++) {
			Map<String, Object> node = oneLevlList.get(i);
			List<Map<String, Object>> webList = linkList.stream().filter(a -> a.get("parentId").equals(node.get("typeId"))).collect(Collectors.toList());
			node.put("web", webList);
			// 取出子菜单（暂定只有两级）（管理端录入记得控制一级目录下有链接，就不能新建子菜单了）
			List<Map<String,Object>> children = allList.stream().filter(a -> a.get("parentId").equals(node.get("typeId"))).collect(Collectors.toList());
			for (Map<String, Object> c : children) {
				c.put("web", linkList.stream().filter(a -> a.get("parentId").equals(c.get("typeId"))).collect(Collectors.toList()));
			}
			node.put("children", children);
			/*if (webList != null && webList.size() > 0) {
				node.put("web", webList);
			} else {
				// 取出子菜单（暂定只有两级）（管理端录入记得控制一级目录下有链接，就不能新建子菜单了）
				List<Map<String,Object>> children = allList.stream().filter(a -> a.get("parentId").equals(node.get("typeId"))).collect(Collectors.toList());
				for (Map<String, Object> c : children) {
					c.put("web", linkList.stream().filter(a -> a.get("parentId").equals(c.get("typeId"))).collect(Collectors.toList()));
				}
				node.put("children", children);
			}*/
		}
		return R.ok().put(Constant.R_DATA, oneLevlList);
		/*
		params.clear();
		List<TevglForumFriend> tevglForumFriendList = tevglForumFriendMapper.selectListByMap(params);
		List<Map<String, Object>> list = tevglForumFriendList.stream().map(a -> {
			Map<String, Object> info = new HashMap<>();
			info.put("typeId", a.getFriendId());
			info.put("parentId", a.getFriendType());
			info.put("name", a.getFriendName());
			info.put("icon", a.getFriendLogo());
			info.put("friendId", a.getFriendId());
			info.put("friendSummary", a.getFriendSummary());
			return info;
		}).collect(Collectors.toList());
		allList.addAll(list);
		List<Map<String, Object>> treeData = recursionUtils.getTreeData("0", allList, "typeId", "parentId");
		return R.ok().put(Constant.R_DATA, treeData);*/
	}
	
	private List<Map<String, Object>> getAllList(Map<String, Object> params){
		List<TevglForumFriend> tevglForumFriendList = tevglForumFriendMapper.selectListByMap(params);
		List<Map<String, Object>> list = tevglForumFriendList.stream().map(a -> {
			Map<String, Object> info = new HashMap<>();
			info.put("typeId", a.getFriendId());
			info.put("parentId", a.getFriendType());
			info.put("name", a.getFriendName());
			info.put("icon", uploadPathUtils.stitchingPath(a.getFriendLogo(), "24"));
			info.put("friendId", a.getFriendId());
			info.put("friendSummary", a.getFriendSummary());
			info.put("friendUrl", a.getFriendUrl());
			return info;
		}).collect(Collectors.toList());
		return list;
	}
	
	@Override
	public R queryTypeListForMgr(Map<String, Object> params) {
		params.put("sidx", "sort_num");
		params.put("order", "asc");
		List<Map<String, Object>> allList = tevglForumFriendTypeMapper.selectSimpleListMapByMap(params);
		// 取出一级菜单
		List<Map<String, Object>> oneLevlList = allList.stream().filter(a -> "0".equals(a.get("parentId"))).collect(Collectors.toList());
		// 查出所有链接
		params.clear();
		params.put("sidx", "sort_num");
		params.put("order", "asc");
		List<Map<String, Object>> linkList = getAllList(params);
		// 遍历一级菜单
		for (int i = 0; i < oneLevlList.size(); i++) {
			Map<String, Object> node = oneLevlList.get(i);
			List<Map<String, Object>> webList = linkList.stream().filter(a -> a.get("parentId").equals(node.get("typeId"))).collect(Collectors.toList());
			if (webList != null && webList.size() > 0) {
				//node.put("web", webList);
				node.put("canAddNode", "N"); // 是否能添加子节点(Y/N)
				node.put("canAddLink", "Y");
			} else {
				// 取出子菜单（暂定只有两级）（管理端录入记得控制一级目录下有链接，就不能新建子菜单了）
				List<Map<String,Object>> children = allList.stream().filter(a -> a.get("parentId").equals(node.get("typeId"))).collect(Collectors.toList());
				for (Map<String, Object> c : children) {
					c.put("canAddNode", "N");
					//c.put("web", linkList.stream().filter(a -> a.get("parentId").equals(c.get("typeId"))).collect(Collectors.toList()));
				}
				node.put("canAddNode", "Y");
				if (children != null && children.size() > 0) {
					node.put("canAddLink", "N");
					node.put("children", children);
				} else {
					node.put("canAddLink", "Y");
				}
			}
		}
		return R.ok().put(Constant.R_DATA, oneLevlList);
	}

	/**
	 * 获取最大排序号
	 * @param params
	 * @return
	 */
	@Override
	public R getMaxSortNum(Map<String, Object> params) {
		Integer maxSortNum = tevglForumFriendTypeMapper.getMaxSortNum(params);
		return R.ok().put(Constant.R_DATA, maxSortNum);
	}

	@Override
	public R getTree(Map<String, Object> map) {
		map.put("sidx", "sort_num");
		map.put("order", "asc");
		List<Map<String, Object>> allList = tevglForumFriendTypeMapper.selectSimpleListMapByMap(map);
		if (!StrUtils.isNull(map.get("flag"))) {
			List<Map<String,Object>> treeData = recursionUtils.getTreeData("0", allList, "typeId", "parentId");
			return R.ok().put(Constant.R_DATA, treeData);
		}
		return R.ok().put(Constant.R_DATA, allList);
	}
}
