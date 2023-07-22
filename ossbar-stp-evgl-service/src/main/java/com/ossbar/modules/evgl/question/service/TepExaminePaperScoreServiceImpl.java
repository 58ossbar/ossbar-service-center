package com.ossbar.modules.evgl.question.service;

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
import com.ossbar.modules.evgl.question.api.TepExaminePaperScoreService;
import com.ossbar.modules.evgl.question.domain.TepExaminePaperScore;
import com.ossbar.modules.evgl.question.persistence.TepExaminePaperScoreMapper;

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
@RequestMapping("/question/tepexaminepaperscore")
public class TepExaminePaperScoreServiceImpl implements TepExaminePaperScoreService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TepExaminePaperScoreServiceImpl.class);
	@Autowired
	private TepExaminePaperScoreMapper tepExaminePaperScoreMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/question/tepexaminepaperscore/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TepExaminePaperScore> tepExaminePaperScoreList = tepExaminePaperScoreMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tepExaminePaperScoreList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/question/tepexaminepaperscore/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tepExaminePaperScoreList = tepExaminePaperScoreMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tepExaminePaperScoreList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tepExaminePaperScore
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/question/tepexaminepaperscore/save")
	public R save(@RequestBody(required = false) TepExaminePaperScore tepExaminePaperScore) throws OssbarException {
		tepExaminePaperScore.setScoreId(Identities.uuid());
		ValidatorUtils.check(tepExaminePaperScore);
		tepExaminePaperScoreMapper.insert(tepExaminePaperScore);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tepExaminePaperScore
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/question/tepexaminepaperscore/update")
	public R update(@RequestBody(required = false) TepExaminePaperScore tepExaminePaperScore) throws OssbarException {
	    ValidatorUtils.check(tepExaminePaperScore);
		tepExaminePaperScoreMapper.update(tepExaminePaperScore);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/question/tepexaminepaperscore/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tepExaminePaperScoreMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/question/tepexaminepaperscore/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tepExaminePaperScoreMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/question/tepexaminepaperscore/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tepExaminePaperScoreMapper.selectObjectById(id));
	}

	@Override
	public List<TepExaminePaperScore> selectListByMap(Map<String, Object> map) {
		return tepExaminePaperScoreMapper.selectListByMap(map);
	}

	@Override
	public List<Map<String, Object>> selectListMapLinkQuestionByMap(Map<String, Object> map) {
		return tepExaminePaperScoreMapper.selectListMapLinkQuestionByMap(map);
	}
}
