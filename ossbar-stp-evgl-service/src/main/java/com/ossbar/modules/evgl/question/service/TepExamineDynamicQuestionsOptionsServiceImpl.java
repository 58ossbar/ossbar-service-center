package com.ossbar.modules.evgl.question.service;

import java.util.List;
import java.util.Map;

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
import com.ossbar.modules.evgl.question.api.TepExamineDynamicQuestionsOptionsService;
import com.ossbar.modules.evgl.question.domain.TepExamineDynamicQuestionsOptions;
import com.ossbar.modules.evgl.question.persistence.TepExamineDynamicQuestionsOptionsMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.Identities;
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
@RequestMapping("/tch/tepexaminedynamicquestionsoptions")
public class TepExamineDynamicQuestionsOptionsServiceImpl implements TepExamineDynamicQuestionsOptionsService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TepExamineDynamicQuestionsOptionsServiceImpl.class);
	@Autowired
	private TepExamineDynamicQuestionsOptionsMapper tepExamineDynamicQuestionsOptionsMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/tch/tepexaminedynamicquestionsoptions/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TepExamineDynamicQuestionsOptions> tepExamineDynamicQuestionsOptionsList = tepExamineDynamicQuestionsOptionsMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tepExamineDynamicQuestionsOptionsList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/tch/tepexaminedynamicquestionsoptions/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tepExamineDynamicQuestionsOptionsList = tepExamineDynamicQuestionsOptionsMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tepExamineDynamicQuestionsOptionsList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tepExamineDynamicQuestionsOptions
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/tch/tepexaminedynamicquestionsoptions/save")
	public R save(@RequestBody(required = false) TepExamineDynamicQuestionsOptions tepExamineDynamicQuestionsOptions) throws OssbarException {
		tepExamineDynamicQuestionsOptions.setDqoId(Identities.uuid());
		ValidatorUtils.check(tepExamineDynamicQuestionsOptions);
		tepExamineDynamicQuestionsOptionsMapper.insert(tepExamineDynamicQuestionsOptions);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tepExamineDynamicQuestionsOptions
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/tch/tepexaminedynamicquestionsoptions/update")
	public R update(@RequestBody(required = false) TepExamineDynamicQuestionsOptions tepExamineDynamicQuestionsOptions) throws OssbarException {
	    ValidatorUtils.check(tepExamineDynamicQuestionsOptions);
		tepExamineDynamicQuestionsOptionsMapper.update(tepExamineDynamicQuestionsOptions);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/tch/tepexaminedynamicquestionsoptions/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tepExamineDynamicQuestionsOptionsMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/tch/tepexaminedynamicquestionsoptions/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tepExamineDynamicQuestionsOptionsMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/tch/tepexaminedynamicquestionsoptions/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tepExamineDynamicQuestionsOptionsMapper.selectObjectById(id));
	}

	@Override
	public List<Map<String, Object>> selectListMapWithQuestionInfoByDyId(String dyId) {
		return tepExamineDynamicQuestionsOptionsMapper.selectListMapWithQuestionInfoByDyId(dyId);
	}
}
