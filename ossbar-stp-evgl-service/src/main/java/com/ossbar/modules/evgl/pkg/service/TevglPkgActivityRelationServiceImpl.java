package com.ossbar.modules.evgl.pkg.service;

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
import com.ossbar.modules.evgl.pkg.api.TevglPkgActivityRelationService;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgActivityRelation;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgActivityRelationMapper;

/**
 * <p> Title: 教学包与活动关系表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/pkg/tevglpkgactivityrelation")
public class TevglPkgActivityRelationServiceImpl implements TevglPkgActivityRelationService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglPkgActivityRelationServiceImpl.class);
	@Autowired
	private TevglPkgActivityRelationMapper tevglPkgActivityRelationMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/pkg/tevglpkgactivityrelation/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglPkgActivityRelation> tevglPkgActivityRelationList = tevglPkgActivityRelationMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglPkgActivityRelationList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/pkg/tevglpkgactivityrelation/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglPkgActivityRelationList = tevglPkgActivityRelationMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglPkgActivityRelationList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglPkgActivityRelation
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/pkg/tevglpkgactivityrelation/save")
	public R save(@RequestBody(required = false) TevglPkgActivityRelation tevglPkgActivityRelation) throws OssbarException {
		tevglPkgActivityRelation.setPaId(Identities.uuid());
		ValidatorUtils.check(tevglPkgActivityRelation);
		tevglPkgActivityRelationMapper.insert(tevglPkgActivityRelation);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglPkgActivityRelation
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/pkg/tevglpkgactivityrelation/update")
	public R update(@RequestBody(required = false) TevglPkgActivityRelation tevglPkgActivityRelation) throws OssbarException {
	    ValidatorUtils.check(tevglPkgActivityRelation);
		tevglPkgActivityRelationMapper.update(tevglPkgActivityRelation);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/pkg/tevglpkgactivityrelation/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglPkgActivityRelationMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/pkg/tevglpkgactivityrelation/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglPkgActivityRelationMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/pkg/tevglpkgactivityrelation/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglPkgActivityRelationMapper.selectObjectById(id));
	}
	
	/**
	 * 根据条件查询活动
	 * @param params
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectSimpleListMap(Map<String, Object> params) {
		return tevglPkgActivityRelationMapper.selectSimpleListMap(params);
	}

	@Override
	public List<TevglPkgActivityRelation> selectListByMap(Map<String, Object> params) {
		return tevglPkgActivityRelationMapper.selectListByMap(params);
	}
}
