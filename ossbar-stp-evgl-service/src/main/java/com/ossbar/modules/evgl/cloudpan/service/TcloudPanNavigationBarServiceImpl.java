package com.ossbar.modules.evgl.cloudpan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbRoomUtils;
import com.ossbar.modules.evgl.cloudpan.api.TcloudPanNavigationBarService;
import com.ossbar.modules.evgl.cloudpan.domain.TcloudPanNavigationBar;
import com.ossbar.modules.evgl.cloudpan.persistence.TcloudPanDirectoryMapper;
import com.ossbar.modules.evgl.cloudpan.persistence.TcloudPanNavigationBarMapper;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
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
@RequestMapping("/cloudpan/tcloudpannavigationbar")
public class TcloudPanNavigationBarServiceImpl implements TcloudPanNavigationBarService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TcloudPanNavigationBarServiceImpl.class);
	@Autowired
	private TcloudPanNavigationBarMapper tcloudPanNavigationBarMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private TevglPkgInfoMapper tevglPkgInfoMapper;
	@Autowired
	private TcloudPanDirectoryMapper tcloudPanDirectoryMapper;
	@Autowired
	private CbRoomUtils roomUtils;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/cloudpan/tcloudpannavigationbar/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TcloudPanNavigationBar> tcloudPanNavigationBarList = tcloudPanNavigationBarMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tcloudPanNavigationBarList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/cloudpan/tcloudpannavigationbar/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tcloudPanNavigationBarList = tcloudPanNavigationBarMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tcloudPanNavigationBarList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tcloudPanNavigationBar
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/cloudpan/tcloudpannavigationbar/save")
	public R save(@RequestBody(required = false) TcloudPanNavigationBar tcloudPanNavigationBar) throws OssbarException {
		ValidatorUtils.check(tcloudPanNavigationBar);
		tcloudPanNavigationBarMapper.insert(tcloudPanNavigationBar);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tcloudPanNavigationBar
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/cloudpan/tcloudpannavigationbar/update")
	public R update(@RequestBody(required = false) TcloudPanNavigationBar tcloudPanNavigationBar) throws OssbarException {
	    ValidatorUtils.check(tcloudPanNavigationBar);
		tcloudPanNavigationBarMapper.update(tcloudPanNavigationBar);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/cloudpan/tcloudpannavigationbar/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tcloudPanNavigationBarMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/cloudpan/tcloudpannavigationbar/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tcloudPanNavigationBarMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/cloudpan/tcloudpannavigationbar/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tcloudPanNavigationBarMapper.selectObjectById(id));
	}

	/**
	 * 快捷导航列表
	 * @param ctId
	 * @param name
	 * @return
	 */
	@Override
	@GetMapping("querNavBarList")
	public R querNavBarList(String ctId, String name, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return R.error("数据异常，请重试");
		}
		// 不是课堂创建者，直接返回
		boolean hasAuth = roomUtils.hasOperatingAuthorization(tevglTchClassroom, loginUserId);
		if (!hasAuth) {
			return R.ok().put(Constant.R_DATA, new ArrayList<>());
		}
		// 最终返回数据
		Map<String, Object> data = new HashMap<>();
		// 查询用户的顶级目录
		Map<String, Object> params = new HashMap<>();
		params.put("pkgId", tevglTchClassroom.getPkgId());
		List<Map<String, Object>> directoryList = tcloudPanDirectoryMapper.selectTopDirectoryList(params);
		List<TevglPkgInfo> refPkgList = tevglPkgInfoMapper.selectListByMap(params);
		List<Map<String, Object>> refPkgDirList = new ArrayList<>();
		if (refPkgList != null && refPkgList.size() > 0) {
			params.clear();
			params.put("pkgId", refPkgList.get(0).getRefPkgId());
			System.out.println("params: " + params);
			refPkgDirList = tcloudPanDirectoryMapper.selectTopDirectoryList(params);
		}
		
		refPkgDirList.addAll(directoryList);
		
		// 去除重复元素
		for (int i = 0; i < refPkgDirList.size(); i++) {// 循环list
			for (int j = i + 1; j < refPkgDirList.size(); j++) {
				if (refPkgDirList.get(i).get("name").equals(refPkgDirList.get(j).get("name"))) {
					refPkgDirList.remove(i);// 删除一样的元素
					i--;
					break;
				}
			}
		}
		
		params.clear();
		params.put("pkgId", tevglTchClassroom.getPkgId());
		params.put("ctId", ctId);
		params.put("name", name);
		List<Map<String,Object>> list = tcloudPanNavigationBarMapper.querNavBarList(params);
		data.put("directoryList", directoryList);
		data.put("fastList", list.stream().map(a -> a.get("dirId")).distinct().collect(Collectors.toList()));
		if (refPkgDirList != null && refPkgDirList.size() > 0) {
			data.put("refPkgDirList", refPkgDirList);
		}
		return R.ok().put(Constant.R_DATA, data);
	}
	
	
	@Override
	public R queryRightList(String ctId, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return R.error("数据异常，请重试");
		}
		// 不是课堂创建者，直接返回
		boolean hasAuth = roomUtils.hasOperatingAuthorization(tevglTchClassroom, loginUserId);
		if (!hasAuth) {
			return R.error("非法操作，没有权限");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		List<Map<String,Object>> list = tcloudPanNavigationBarMapper.querNavBarList(params);
		return R.ok().put(Constant.R_DATA, list);
	}

	/**
	 * 查询可设置选择的目录
	 * @param ctId
	 * @param name
	 * @return
	 */
	@Override
	public R querySelectList(String ctId, String name, String loginUserId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctId", ctId);
		return null;
	}

	/**
	 * 保存
	 * @param jsonObject
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R saveSetting(JSONObject jsonObject, String loginUserId) {
		String ctId = jsonObject.getString("ctId");
		String pkgId = jsonObject.getString("pkgId");
		String dirId = jsonObject.getString("dirId");
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(pkgId) 
				|| StrUtils.isEmpty(dirId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return R.error("数据异常，请重试");
		}
		boolean hasAuth = roomUtils.hasOperatingAuthorization(tevglTchClassroom, loginUserId);
		if (!hasAuth) {
			return R.error("非法操作，没有权限");
		}
		if (!pkgId.equals(tevglTchClassroom.getPkgId())) {
			return R.error("非法操作");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		List<TcloudPanNavigationBar> list = tcloudPanNavigationBarMapper.selectListByMap(params);
		Integer maxSortNum = tcloudPanNavigationBarMapper.getMaxSortNum(params);
		List<String> collect = list.stream().map(a -> a.getDirId()).collect(Collectors.toList());
		if (!collect.contains(dirId)) {
			TcloudPanNavigationBar t = new TcloudPanNavigationBar();
			t.setId(Identities.uuid());
			t.setDirId(dirId);
			t.setSortNum(maxSortNum);
			t.setCtId(ctId);
			t.setPkgId(pkgId);
			tcloudPanNavigationBarMapper.insert(t);
		}
		return R.ok("保存成功");
	}
	
	/**
	 * 批量设置
	 * @param jsonObject
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R saveNavBarBatch(JSONObject jsonObject, String loginUserId) {
		String ctId = jsonObject.getString("ctId");
		String pkgId = jsonObject.getString("pkgId");
		JSONArray jsonArray = jsonObject.getJSONArray("dirIds");
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return R.error("数据异常，请重试");
		}
		boolean hasAuth = roomUtils.hasOperatingAuthorization(tevglTchClassroom, loginUserId);
		if (!hasAuth) {
			return R.error("非法操作，没有权限");
		}
		if (!pkgId.equals(tevglTchClassroom.getPkgId())) {
			return R.error("非法操作");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		List<TcloudPanNavigationBar> list = tcloudPanNavigationBarMapper.selectListByMap(params);
		// 如果为空，则清空
		/*if (jsonArray == null || jsonArray.size() == 0) {
			
			return R.ok();
		}*/
		// 先删除
		if (list != null && list.size() > 0) {
			List<String> collect = list.stream().map(a -> a.getId()).collect(Collectors.toList());
			tcloudPanNavigationBarMapper.deleteBatch(collect.stream().toArray(String[]::new));
		}
		// 再重新生成
		List<TcloudPanNavigationBar> insertList = new ArrayList<TcloudPanNavigationBar>();
		for (int i = 0; i < jsonArray.size(); i++) {
			String dirId = jsonArray.getString(i);
			TcloudPanNavigationBar t = new TcloudPanNavigationBar();
			t.setId(Identities.uuid());
			t.setDirId(dirId);
			t.setSortNum(i);
			t.setPkgId(pkgId);
			t.setCtId(ctId);
			insertList.add(t);
		}
		if (insertList != null && insertList.size() > 0) {
			tcloudPanNavigationBarMapper.insertBatch(insertList);
		}
		return R.ok("保存成功");
	}
}
