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
import com.ossbar.modules.evgl.site.api.TevglSiteVideoRelationService;
import com.ossbar.modules.evgl.site.domain.TevglSiteVideoRelation;
import com.ossbar.modules.evgl.site.persistence.TevglSiteVideoRelationMapper;

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
@RequestMapping("/site/tevglsitevideorelation")
public class TevglSiteVideoRelationServiceImpl implements TevglSiteVideoRelationService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglSiteVideoRelationServiceImpl.class);
	@Autowired
	private TevglSiteVideoRelationMapper tevglSiteVideoRelationMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/site/tevglsitevideorelation/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglSiteVideoRelation> tevglSiteVideoRelationList = tevglSiteVideoRelationMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglSiteVideoRelationList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/site/tevglsitevideorelation/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglSiteVideoRelationList = tevglSiteVideoRelationMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglSiteVideoRelationList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglSiteVideoRelation
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/site/tevglsitevideorelation/save")
	public R save(@RequestBody(required = false) TevglSiteVideoRelation tevglSiteVideoRelation) throws OssbarException {
		tevglSiteVideoRelation.setVrId(Identities.uuid());
		ValidatorUtils.check(tevglSiteVideoRelation);
		tevglSiteVideoRelationMapper.insert(tevglSiteVideoRelation);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglSiteVideoRelation
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/site/tevglsitevideorelation/update")
	public R update(@RequestBody(required = false) TevglSiteVideoRelation tevglSiteVideoRelation) throws OssbarException {
	    ValidatorUtils.check(tevglSiteVideoRelation);
		tevglSiteVideoRelationMapper.update(tevglSiteVideoRelation);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/site/tevglsitevideorelation/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglSiteVideoRelationMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/site/tevglsitevideorelation/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws OssbarException {
		tevglSiteVideoRelationMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/site/tevglsitevideorelation/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglSiteVideoRelationMapper.selectObjectById(id));
	}

	/**
	 * 批量新增
	 * @param list
	 * @throws OssbarException
	 */
	@Override
	public void insertBatch(List<TevglSiteVideoRelation> list) throws OssbarException {
		tevglSiteVideoRelationMapper.insertBatch(list);
	}

	@Override
	public List<TevglSiteVideoRelation> selectListByMap(Map<String, Object> map) {
		return tevglSiteVideoRelationMapper.selectListByMap(map);
	}
	
}
