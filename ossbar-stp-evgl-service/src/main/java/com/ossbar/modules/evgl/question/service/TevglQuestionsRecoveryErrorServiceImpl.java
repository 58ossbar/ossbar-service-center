package com.ossbar.modules.evgl.question.service;

import java.util.HashMap;
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
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.question.api.TevglQuestionsRecoveryErrorService;
import com.ossbar.modules.evgl.question.domain.TevglQuestionChose;
import com.ossbar.modules.evgl.question.domain.TevglQuestionsInfo;
import com.ossbar.modules.evgl.question.domain.TevglQuestionsRecoveryError;
import com.ossbar.modules.evgl.question.persistence.TevglQuestionChoseMapper;
import com.ossbar.modules.evgl.question.persistence.TevglQuestionsInfoMapper;
import com.ossbar.modules.evgl.question.persistence.TevglQuestionsRecoveryErrorMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
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
@RequestMapping("/question/tevglquestionsrecoveryerror")
public class TevglQuestionsRecoveryErrorServiceImpl implements TevglQuestionsRecoveryErrorService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglQuestionsRecoveryErrorServiceImpl.class);
	@Autowired
	private TevglQuestionsRecoveryErrorMapper tevglQuestionsRecoveryErrorMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private TevglQuestionsInfoMapper tevglQuestionsInfoMapper;
	@Autowired
	private TevglQuestionChoseMapper tevglQuestionChoseMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/question/tevglquestionsrecoveryerror/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglQuestionsRecoveryError> tevglQuestionsRecoveryErrorList = tevglQuestionsRecoveryErrorMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglQuestionsRecoveryErrorList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tevglQuestionsRecoveryErrorList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/question/tevglquestionsrecoveryerror/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglQuestionsRecoveryErrorList = tevglQuestionsRecoveryErrorMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglQuestionsRecoveryErrorList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglQuestionsRecoveryErrorList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglQuestionsRecoveryError
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/question/tevglquestionsrecoveryerror/save")
	@Override
	public R save(TevglQuestionsRecoveryError tevglQuestionsRecoveryError) throws OssbarException {
		if (tevglQuestionsRecoveryError.getContent() == null) {
			return R.error("纠错的理由不能为空");
		}
		tevglQuestionsRecoveryError.setId(Identities.uuid());
		tevglQuestionsRecoveryError.setCreateTime(DateUtils.getNowTimeStamp());
		tevglQuestionsRecoveryError.setQuestionId(tevglQuestionsRecoveryError.getQuestionId());  //纠错的题目id
		tevglQuestionsRecoveryError.setContent(tevglQuestionsRecoveryError.getContent());  //纠错的理由
		tevglQuestionsRecoveryError.setState("N"); //默认未处理
		ValidatorUtils.check(tevglQuestionsRecoveryError);
		tevglQuestionsRecoveryErrorMapper.insert(tevglQuestionsRecoveryError);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglQuestionsRecoveryError
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/question/tevglquestionsrecoveryerror/update")
	public R update(@RequestBody(required = false) TevglQuestionsRecoveryError tevglQuestionsRecoveryError) throws OssbarException {
	    ValidatorUtils.check(tevglQuestionsRecoveryError);
		tevglQuestionsRecoveryErrorMapper.update(tevglQuestionsRecoveryError);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/question/tevglquestionsrecoveryerror/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglQuestionsRecoveryErrorMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/question/tevglquestionsrecoveryerror/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglQuestionsRecoveryErrorMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/question/tevglquestionsrecoveryerror/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglQuestionsRecoveryErrorMapper.selectObjectById(id));
	}
	
	/**
	 * 纠错题目
	 * @data 2020年11月20日
	 * @author zyl改
	 */
	@SysLog(value="纠错题目")
	@GetMapping("/selectByCollectionMap")
	@SentinelResource("/question/tevglquestionsrecoveryerror/selectByCollectionMap")
	@Override
	public R selectByCollectionMap(String questionsId) {
		if (StrUtils.isEmpty(questionsId) || "null".equals(questionsId)) {
			return R.error("参数questionsId为空");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("questionsId", questionsId);
		System.out.println("questionsId:" + questionsId);
		//根据前端传过来的题目id查询纠错信息
		TevglQuestionsInfo questionsInfo = tevglQuestionsInfoMapper.selectObjectById(questionsId);
		System.out.println("题目信息:" + questionsInfo);
		if (questionsInfo == null) {
			return R.error("该题目已失效，请刷新重试");
		}
		params.put("sidx", "code");
		params.put("order", "desc");
		//根据题目id查询选项集合
		List<TevglQuestionChose> optionList = tevglQuestionChoseMapper.selectListByMap(params);
		//保存到题目列表
		questionsInfo.setOptionList(optionList);
		return R.ok().put(Constant.R_DATA, questionsInfo);
	}
}
