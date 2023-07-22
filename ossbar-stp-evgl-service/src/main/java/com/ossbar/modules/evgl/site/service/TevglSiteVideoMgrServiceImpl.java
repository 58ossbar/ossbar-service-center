package com.ossbar.modules.evgl.site.service;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.utils.tool.Identities;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.api.TevglSiteVideoMgrService;
import com.ossbar.modules.evgl.site.domain.TevglSiteVideoMgr;
import com.ossbar.modules.evgl.site.persistence.TevglSiteVideoMgrMapper;

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
@RequestMapping("/site/tevglsitevideomgr")
public class TevglSiteVideoMgrServiceImpl implements TevglSiteVideoMgrService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglSiteVideoMgrServiceImpl.class);
	@Autowired
	private TevglSiteVideoMgrMapper tevglSiteVideoMgrMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/site/tevglsitevideomgr/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglSiteVideoMgr> tevglSiteVideoMgrList = tevglSiteVideoMgrMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglSiteVideoMgrList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/site/tevglsitevideomgr/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglSiteVideoMgrList = tevglSiteVideoMgrMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglSiteVideoMgrList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglSiteVideoMgr
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/site/tevglsitevideomgr/save")
	public R save(@RequestBody(required = false) TevglSiteVideoMgr tevglSiteVideoMgr) throws OssbarException {
		tevglSiteVideoMgr.setVmId(Identities.uuid());
		ValidatorUtils.check(tevglSiteVideoMgr);
		tevglSiteVideoMgrMapper.insert(tevglSiteVideoMgr);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglSiteVideoMgr
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/site/tevglsitevideomgr/update")
	public R update(@RequestBody(required = false) TevglSiteVideoMgr tevglSiteVideoMgr) throws OssbarException {
	    ValidatorUtils.check(tevglSiteVideoMgr);
		tevglSiteVideoMgrMapper.update(tevglSiteVideoMgr);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/site/tevglsitevideomgr/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglSiteVideoMgrMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/site/tevglsitevideomgr/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws OssbarException {
		tevglSiteVideoMgrMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/site/tevglsitevideomgr/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglSiteVideoMgrMapper.selectObjectById(id));
	}

	/**
	 * 批量新增
	 *
	 * @param list
	 */
	@Override
	public void insertBatch(List<TevglSiteVideoMgr> list) {
		tevglSiteVideoMgrMapper.insertBatch(list);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@Override
	public void deleteBatch(Object[] ids) {
		tevglSiteVideoMgrMapper.deleteBatch(ids);
	}

	/**
	 * 根据条件查询记录
	 * @param map
	 * @return
	 */
	@Override
	public List<TevglSiteVideoMgr> selectListByMap(Map<String, Object> map) {
		return tevglSiteVideoMgrMapper.selectListByMap(map);
	}

}
