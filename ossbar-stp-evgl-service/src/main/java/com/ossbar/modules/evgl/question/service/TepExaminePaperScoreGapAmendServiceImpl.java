package com.ossbar.modules.evgl.question.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ossbar.modules.evgl.question.persistence.TepExaminePaperScoreGapfillingMapper;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.ossbar.modules.evgl.question.api.TepExaminePaperScoreGapAmendService;
import com.ossbar.modules.evgl.question.domain.TepExamineHistoryPaper;
import com.ossbar.modules.evgl.question.domain.TepExaminePaperInfo;
import com.ossbar.modules.evgl.question.domain.TepExaminePaperScore;
import com.ossbar.modules.evgl.question.domain.TepExaminePaperScoreGapAmend;
import com.ossbar.modules.evgl.question.domain.TevglQuestionsInfo;
import com.ossbar.modules.evgl.question.dto.SaveScoreGapAmendDTO;
import com.ossbar.modules.evgl.question.persistence.TepExamineHistoryPaperMapper;
import com.ossbar.modules.evgl.question.persistence.TepExaminePaperScoreGapAmendMapper;
import com.ossbar.modules.evgl.question.persistence.TepExaminePaperScoreMapper;
import com.ossbar.modules.evgl.question.persistence.TevglQuestionChoseMapper;
import com.ossbar.modules.evgl.question.persistence.TevglQuestionsInfoMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.BeanUtils;
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
@RequestMapping("/question/tepexaminepaperscoregapamend")
public class TepExaminePaperScoreGapAmendServiceImpl implements TepExaminePaperScoreGapAmendService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TepExaminePaperScoreGapAmendServiceImpl.class);
	@Autowired
	private TepExaminePaperScoreGapAmendMapper tepExaminePaperScoreGapAmendMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private TepExaminePaperScoreMapper tepExaminePaperScoreMapper;
	@Autowired
	private TepExamineHistoryPaperMapper tepExamineHistoryPaperMapper;
	@Autowired
	private TevglQuestionsInfoMapper tevglQuestionsInfoMapper;
	@Autowired
	private TevglQuestionChoseMapper tevglQuestionChoseMapper;
	@Autowired
	private TepExaminePaperScoreGapfillingMapper tepExaminePaperScoreGapfillingMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/question/tepexaminepaperscoregapamend/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TepExaminePaperScoreGapAmend> tepExaminePaperScoreGapAmendList = tepExaminePaperScoreGapAmendMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tepExaminePaperScoreGapAmendList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tepExaminePaperScoreGapAmendList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/question/tepexaminepaperscoregapamend/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tepExaminePaperScoreGapAmendList = tepExaminePaperScoreGapAmendMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tepExaminePaperScoreGapAmendList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tepExaminePaperScoreGapAmendList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tepExaminePaperScoreGapAmend
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/question/tepexaminepaperscoregapamend/save")
	public R save(@RequestBody(required = false) TepExaminePaperScoreGapAmend tepExaminePaperScoreGapAmend) throws OssbarException {
		tepExaminePaperScoreGapAmend.setAmId(Identities.uuid());
		tepExaminePaperScoreGapAmend.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tepExaminePaperScoreGapAmend.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tepExaminePaperScoreGapAmend);
		tepExaminePaperScoreGapAmendMapper.insert(tepExaminePaperScoreGapAmend);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tepExaminePaperScoreGapAmend
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/question/tepexaminepaperscoregapamend/update")
	public R update(@RequestBody(required = false) TepExaminePaperScoreGapAmend tepExaminePaperScoreGapAmend) throws OssbarException {
	    ValidatorUtils.check(tepExaminePaperScoreGapAmend);
		tepExaminePaperScoreGapAmendMapper.update(tepExaminePaperScoreGapAmend);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/question/tepexaminepaperscoregapamend/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tepExaminePaperScoreGapAmendMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/question/tepexaminepaperscoregapamend/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws OssbarException {
		tepExaminePaperScoreGapAmendMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/question/tepexaminepaperscoregapamend/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tepExaminePaperScoreGapAmendMapper.selectObjectById(id));
	}

	@Override
	public R findScoreGapAmend(String scoreId, String traineeId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R saveScoreGapAmend(SaveScoreGapAmendDTO dto, String traineeId) {
		// 找到已评分的分值
		TepExaminePaperScore tepExaminePaperScore = tepExaminePaperScoreMapper.selectObjectById(dto.getScoreId());
		if (tepExaminePaperScore == null) {
			return R.error("参数异常");
		}
		if ("Y".equals(tepExaminePaperScore.getIsCorrect())) {
			return R.error("当前填空题回答已回答正确，不允许操作！");
		}
		TevglQuestionsInfo tevglQuestionsInfo = tevglQuestionsInfoMapper.selectObjectById(tepExaminePaperScore.getQuestionsId());
		if (tevglQuestionsInfo == null) {
			return R.error("题目已丢失！");
		}
		TepExaminePaperInfo tepExaminePaperInfo = tepExaminePaperScoreGapAmendMapper.findPaperByScoreId(dto.getScoreId());
		if (tepExaminePaperInfo == null) {
			return R.error("参数异常，试卷未找到！");
		}
		// 重新更改试卷总分
		TepExamineHistoryPaper tepExamineHistoryPaper = null;
		String historyId = tepExaminePaperScoreGapAmendMapper.findHistoryIdByScoreId(dto.getScoreId());
		if (StrUtils.isNotEmpty(historyId)) {
			tepExamineHistoryPaper = tepExamineHistoryPaperMapper.selectObjectById(historyId);
			if (tepExamineHistoryPaper == null) {
				return R.error("参数异常！");
			}
		} else {
			return R.error("参数异常！");
		}
		// 填充信息
		TepExaminePaperScoreGapAmend tepExaminePaperScoreGapAmend = new TepExaminePaperScoreGapAmend();
		BeanUtils.copyProperties(tepExaminePaperScoreGapAmend, dto);
		tepExaminePaperScoreGapAmend.setAmId(Identities.uuid());
		tepExaminePaperScoreGapAmend.setCreateUserId(traineeId);
		tepExaminePaperScoreGapAmend.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tepExaminePaperScoreGapAmend);
		// TODO 计算最新总分
		BigDecimal val = new BigDecimal("0");
		// 该题是否为复合题的子题目
		boolean flag = StrUtils.isNotEmpty(tevglQuestionsInfo.getParentId());
		if (flag) {
			if (new BigDecimal(dto.getScore()).compareTo(new BigDecimal(tepExaminePaperInfo.getCompositeScore())) > 1) {
				return R.error("给出的分数不能超过复合题限定的小题最高分！");
			}
			String latestScore = tepExaminePaperScoreGapAmendMapper.findLatestScoreByScoreId(dto.getScoreId());
			if (StrUtils.isEmpty(latestScore)) {
				val = new BigDecimal(tepExamineHistoryPaper.getPaperFinalScore()).add(new BigDecimal(tepExaminePaperInfo.getCompositeScore()));
			} else {
				val = new BigDecimal(tepExamineHistoryPaper.getPaperFinalScore()).subtract(new BigDecimal(latestScore)).add(new BigDecimal(tepExaminePaperInfo.getCompositeScore()));
			}
		} else {
			Map<String, Object> params = new HashMap<>();
			params.put("questionsIds", Arrays.asList(tepExaminePaperScore.getQuestionsId()));
			params.put("sidx", "sort_num");
			params.put("order", "asc");
			List<Map<String, Object>> optionList = tevglQuestionChoseMapper.selectSimpleListByMap(params);
			BigDecimal multiplyVal = new BigDecimal(tepExaminePaperInfo.getGapFilling()).multiply(new BigDecimal(optionList.size()));
			if (new BigDecimal(dto.getScore()).compareTo(multiplyVal) > 1) {
				return R.error("给出的分数不能超过最高" + multiplyVal + "分！");
			}
			// 取出本题填空的标准答案
			List<Object> modelAnswerList = optionList.stream().filter(a -> a.get("questionsId").equals(tepExaminePaperScore.getQuestionsId())).map(a -> a.get("content")).collect(Collectors.toList());
			// 1.回答错误 2.每空得分规则  同时满足两种情况才去计算
			if ("N".equals(tepExaminePaperScore.getIsCorrect()) && "1".equals(tepExaminePaperInfo.getGapFillingScoreStandard())) {
				int inCorrectNum = 0;
				params.clear();
				params.put("historyId", tepExamineHistoryPaper.getHistoryId());
				params.put("scoreId", dto.getScoreId());
				params.put("traineeId", tepExaminePaperScore.getTraineeId());
				params.put("sidx", "sort_num");
				params.put("order", "asc");
				List<Map<String, Object>> userAnswerList = tepExaminePaperScoreGapfillingMapper.selectSimpleListMapByMap(params);
				// TODO 算出该题本来得了多少分
				for (int i = 0; i < modelAnswerList.size(); i++) {
					if (modelAnswerList.get(i).equals(userAnswerList.get(i))) {
						inCorrectNum++;
					}
				}
				BigDecimal multiply = new BigDecimal(inCorrectNum).multiply(new BigDecimal(tepExaminePaperInfo.getGapFilling()));
				val = new BigDecimal(tepExamineHistoryPaper.getPaperFinalScore()).subtract(multiply).add(new BigDecimal(tepExaminePaperInfo.getCompositeScore()));
			}
		}
		tepExaminePaperScoreGapAmendMapper.insert(tepExaminePaperScoreGapAmend);
		TepExamineHistoryPaper t = new TepExamineHistoryPaper();
		t.setHistoryId(historyId);
		t.setPaperFinalScore(String.valueOf(val));
		tepExamineHistoryPaperMapper.update(t);
		return R.ok("操作成功 接口实现中！");
	}

}
