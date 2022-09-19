package com.ossbar.modules.evgl.pkg.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.DictService;
import com.ossbar.modules.common.GlobalEmpiricalValueGetType;
import com.ossbar.modules.evgl.pkg.api.TevglPkgCheckService;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgCheck;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgCheckMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.modules.sys.domain.TsysDict;
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
@RequestMapping("/pkg/tevglpkgcheck")
public class TevglPkgCheckServiceImpl implements TevglPkgCheckService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglPkgCheckServiceImpl.class);
	@Autowired
	private TevglPkgCheckMapper tevglPkgCheckMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private UploadFileUtils uploadPathUtils;
	@Autowired
	private TevglPkgInfoMapper tevglPkgInfoMapper;
	@Autowired
	private DictService dictService;

	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/pkg/tevglpkgcheck/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglPkgCheck> tevglPkgCheckList = tevglPkgCheckMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglPkgCheckList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tevglPkgCheckList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/pkg/tevglpkgcheck/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglPkgCheckList = tevglPkgCheckMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglPkgCheckList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglPkgCheckList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglPkgCheck
	 * @throws CreatorblueException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/pkg/tevglpkgcheck/save")
	public R save(@RequestBody(required = false) TevglPkgCheck tevglPkgCheck) throws CreatorblueException {
		tevglPkgCheck.setPcId(Identities.uuid());
		tevglPkgCheck.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglPkgCheck.setCreateTime(DateUtils.getNowTimeStamp());
		//ValidatorUtils.check(tevglPkgCheck);
		tevglPkgCheckMapper.insert(tevglPkgCheck);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglPkgCheck
	 * @throws CreatorblueException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/pkg/tevglpkgcheck/update")
	public R update(@RequestBody(required = false) TevglPkgCheck tevglPkgCheck) throws CreatorblueException {
	    //ValidatorUtils.check(tevglPkgCheck);
		tevglPkgCheckMapper.update(tevglPkgCheck);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/pkg/tevglpkgcheck/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tevglPkgCheckMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/pkg/tevglpkgcheck/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws CreatorblueException {
		tevglPkgCheckMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/pkg/tevglpkgcheck/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglPkgCheckMapper.selectObjectById(id));
	}

	/**
	 * 管理端，查询教学包列表
	 * @param params
	 * @return
	 */
	@Override
	@SentinelResource("/pkg/tevglpkgcheck/queryPkgListForMgr")
	public R queryPkgListForMgr(Map<String, Object> params) {
		// 默认查询待审核的
		if (StrUtils.isNull(params.get("releaseStatus"))) {
			params.put("releaseStatus", "N");
		}
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglPkgCheckList = tevglPkgCheckMapper.queryWaitCheckPkgList(query);
		if (tevglPkgCheckList != null && tevglPkgCheckList.size() > 0) {
			convertUtil.convertDict(tevglPkgCheckList, "checkState", "isPass");
			tevglPkgCheckList.stream().forEach(a -> {
				a.put("pkgLogo", uploadPathUtils.stitchingPath(a.get("pkgLogo"), "12"));
			});
		}
		PageUtils pageUtil = new PageUtils(tevglPkgCheckList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	/**
	 * 查询我的待审核的教学包
	 * @param params
	 * @param loginUserId
	 * @return
	 */
	@Override
	@SentinelResource("/pkg/tevglpkgcheck/querMyWaitCheckPkgList")
	public R querMyWaitCheckPkgList(Map<String, Object> params, String loginUserId) {
		if (StrUtils.isNull(params.get("pkgId")) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		params.put("refPkgId", params.get("pkgId"));
		params.remove("pkgId");
		params.put("loginUserId", loginUserId);
		List<Map<String,Object>> list = tevglPkgCheckMapper.querMyWaitCheckPkgList(params);
		if (list != null && list.size() > 0) {
			convertUtil.convertDict(list, "checkState", "isPass");
			list.stream().forEach(a -> {
				a.put("pkgLogo", uploadPathUtils.stitchingPath(a.get("pkgLogo"), "12"));
			});
			return R.ok().put(Constant.R_DATA, list.get(0));
		}
		return R.ok().put(Constant.R_DATA, null);
	}

	@Override
	public R updatePkgReleaseStatus(TevglPkgCheck tevglPkgCheck) {
		if (StrUtils.isEmpty(tevglPkgCheck.getPkgId()) || StrUtils.isEmpty(tevglPkgCheck.getCheckState())) {
			return R.error("必传参数为空");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pkgId", tevglPkgCheck.getPkgId());
		List<TevglPkgCheck> list = tevglPkgCheckMapper.selectListByMap(map);
		if (list == null || list.size() == 0) {
			tevglPkgCheck.setPcId(Identities.uuid());
			tevglPkgCheck.setCreateTime(DateUtils.getNowTimeStamp());
			tevglPkgCheck.setCreateUserId(serviceLoginUtil.getLoginUserId());
			tevglPkgCheckMapper.insert(tevglPkgCheck);
			if ("Y".equals(tevglPkgCheck.getCheckState())) {
				// 教学包教学包的发布状态
				TevglPkgInfo t = new TevglPkgInfo();
				t.setPkgId(tevglPkgCheck.getPkgId());
				t.setReleaseStatus("Y");
				tevglPkgInfoMapper.update(t);
				// 记录经验值
				TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(tevglPkgCheck.getPkgId());
				if (tevglPkgInfo != null) {				
					// TODO 记录老师获取的经验值
					map.clear();
					map.put("type", GlobalEmpiricalValueGetType.TEACHER_LIVING_TEXTBOOKS_17);
					map.put("params17", tevglPkgCheck.getPkgId());
				}
			}
		} else {
			// 判断是否有被使用，没有被使用的，则还允许修改
			map.clear();
			map.put("refPkgId", tevglPkgCheck.getPkgId());
			List<TevglPkgInfo> tevglPkgInfoList = tevglPkgInfoMapper.selectListByMap(map);
			if (tevglPkgInfoList != null && tevglPkgInfoList.size() > 0) {
				return R.error("教学包已被用户使用了，不允许再更改审核");
			}
			TevglPkgCheck check = list.get(0);
			check.setReason(tevglPkgCheck.getReason());
			TevglPkgInfo t = new TevglPkgInfo();
			t.setPkgId(tevglPkgCheck.getPkgId());
			if ("Y".equals(tevglPkgCheck.getCheckState())) {
				t.setReleaseStatus("Y");
				check.setCheckState("Y");
			} else {
				t.setReleaseStatus("N");
				check.setCheckState("N");
			}
			tevglPkgInfoMapper.update(t);
			tevglPkgCheckMapper.update(check);
		}
		return R.ok("操作成功");
	}
	
	/**
	 * 从字典 teach_exp_type 获取老师获取经验值的方式
	 * @return
	 */
	private Integer findEmpiricalValue() {
		List<TsysDict> list = dictService.getTsysDictListByType("teach_exp_type");
		if (list != null && list.size() > 0) {
			List<TsysDict> collect = list.stream().filter(a -> a.getDictCode().equals(GlobalEmpiricalValueGetType.TEACHER_LIVING_TEXTBOOKS_17)).collect(Collectors.toList());
			if (collect != null && collect.size() > 0) {
				return Integer.valueOf(collect.get(0).getDictValue());
			}
		}
		return 20;
	}

	/**
	 *  查看教学包对应教材的树形章节数据，（管理端专用）
	 * @param pkgId
	 * @return
	 */
	@Override
	public R getBookTreeDataForMgrCheck(String pkgId) {
		if (StrUtils.isEmpty(pkgId)) {
			return R.error("必传参数为空");
		}
		
		return null;
	}
}
