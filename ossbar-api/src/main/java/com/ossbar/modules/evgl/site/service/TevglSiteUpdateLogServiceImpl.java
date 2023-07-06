package com.ossbar.modules.evgl.site.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.api.TevglSiteUpdateLogService;
import com.ossbar.modules.evgl.site.domain.TevglSiteUpdateLog;
import com.ossbar.modules.evgl.site.persistence.TevglSiteUpdateLogMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
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
@RequestMapping("/site/tevglsiteupdatelog")
public class TevglSiteUpdateLogServiceImpl implements TevglSiteUpdateLogService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglSiteUpdateLogServiceImpl.class);
	@Autowired
	private TevglSiteUpdateLogMapper tevglSiteUpdateLogMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/site/tevglsiteupdatelog/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglSiteUpdateLog> tevglSiteUpdateLogList = tevglSiteUpdateLogMapper.selectListByMap(query);
		convertUtil.convertDict(tevglSiteUpdateLogList, "type", "feedbackBigType");
		convertUtil.convertUserId2RealName(tevglSiteUpdateLogList, "createUserId", "updateUserId");
		convertUtil.convertUserId2RealName(tevglSiteUpdateLogList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tevglSiteUpdateLogList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/site/tevglsiteupdatelog/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglSiteUpdateLogList = tevglSiteUpdateLogMapper.selectListMapByMap(query);
		convertUtil.convertDict(tevglSiteUpdateLogList, "type", "feedbackBigType");
		convertUtil.convertUserId2RealName(tevglSiteUpdateLogList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglSiteUpdateLogList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglSiteUpdateLog
	 * @throws CreatorblueException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/site/tevglsiteupdatelog/save")
	public R save(@RequestBody(required = false) TevglSiteUpdateLog tevglSiteUpdateLog) throws CreatorblueException {
		tevglSiteUpdateLog.setLogId(Identities.uuid());
		tevglSiteUpdateLog.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglSiteUpdateLog.setCreateTime(DateUtils.getNowTimeStamp());
		//ValidatorUtils.check(tevglSiteUpdateLog);
		tevglSiteUpdateLogMapper.insert(tevglSiteUpdateLog);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglSiteUpdateLog
	 * @throws CreatorblueException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/site/tevglsiteupdatelog/update")
	public R update(@RequestBody(required = false) TevglSiteUpdateLog tevglSiteUpdateLog) throws CreatorblueException {
	    tevglSiteUpdateLog.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglSiteUpdateLog.setUpdateTime(DateUtils.getNowTimeStamp());
	    //ValidatorUtils.check(tevglSiteUpdateLog);
		tevglSiteUpdateLogMapper.update(tevglSiteUpdateLog);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/site/tevglsiteupdatelog/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tevglSiteUpdateLogMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/site/tevglsiteupdatelog/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws CreatorblueException {
		tevglSiteUpdateLogMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/site/tevglsiteupdatelog/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglSiteUpdateLogMapper.selectObjectById(id));
	}

	/**
	 * 查询更新日志
	 * @param params
	 * @return
	 */
	@Override
	@GetMapping("/queryUpdateLog")
	public R queryUpdateLog(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglSiteUpdateLog> tevglSiteUpdateLogList = tevglSiteUpdateLogMapper.selectListByMap(query);
		List<Map<String, Object>> list = tevglSiteUpdateLogList.stream().map(a -> {
			Map<String, Object> info = new HashMap<>();
			info.put("logId", a.getLogId());
			info.put("version", a.getVersion());
			info.put("content", a.getContent());
			info.put("createTime", a.getCreateTime().substring(0, 10));
			return info;
		}).collect(Collectors.toList());
		PageUtils pageUtil = new PageUtils(list,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
}
