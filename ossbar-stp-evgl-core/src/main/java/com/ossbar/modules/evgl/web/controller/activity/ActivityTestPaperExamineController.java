package com.ossbar.modules.evgl.web.controller.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.activity.api.TevglActivityTestPaperTraineeAnswerService;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.question.api.TepExamineDynamicPaperService;
import com.ossbar.modules.evgl.question.api.TepExamineDynamicQuestionsOptionsService;
import com.ossbar.modules.evgl.question.api.TepExamineHistoryPaperService;
import com.ossbar.modules.evgl.question.api.TepExaminePaperInfoService;
import com.ossbar.modules.evgl.question.api.TepExaminePaperQuestionsDetailService;
import com.ossbar.modules.evgl.question.api.TepExaminePaperScoreService;
import com.ossbar.modules.evgl.question.api.TevglQuestionChoseService;
import com.ossbar.modules.evgl.question.api.TevglQuestionsInfoService;
import com.ossbar.modules.evgl.question.domain.TepExamineDynamicPaper;
import com.ossbar.modules.evgl.question.domain.TepExamineHistoryPaper;
import com.ossbar.modules.evgl.question.domain.TepExaminePaperInfo;
import com.ossbar.modules.evgl.question.domain.TepExaminePaperScore;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;

/**
 * 4测试活动（试卷考核）
 * 
 * @author huj
 *
 */
@RestController
@RequestMapping("/activity/testPaper/examine")
public class ActivityTestPaperExamineController {

	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TevglActivityTestPaperTraineeAnswerService tevglActivityTestPaperTraineeAnswerService;
	@Reference(version = "1.0.0")
	private TepExamineDynamicPaperService tepExamineDynamicPaperService;
	@Reference(version = "1.0.0")
	private TepExaminePaperInfoService tepExaminePaperInfoService;
	@Reference(version = "1.0.0")
	private TepExamineHistoryPaperService tepExamineHistoryPaperService;
	@Reference(version = "1.0.0")
	private TepExaminePaperScoreService tepExaminePaperScoreService;
	@Reference(version = "1.0.0")
	private TepExamineDynamicQuestionsOptionsService tepExamineDynamicQuestionsOptionsService;
	@Reference(version = "1.0.0")
	private TevglQuestionChoseService tevglQuestionChoseService;
	@Reference(version = "1.0.0")
	private TepExaminePaperQuestionsDetailService tepExaminePaperQuestionsDetailService;
	@Reference(version = "1.0.0")
	private TevglQuestionsInfoService tevglQuestionsInfoService;

	/**
	 * 进入试卷考核页码触发此请求
	 * 
	 * @param request
	 * @param isDynamic
	 * @param paperId
	 * @param paperType
	 * @return
	 */
	@PostMapping("list")
	@CheckSession
	public R list(HttpServletRequest request, String isDynamic, String paperId, String paperType, String pkgId, String ctId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		isDynamic = "N";
		paperType = "1";
		return tevglActivityTestPaperTraineeAnswerService.list(isDynamic, paperId, paperType, traineeInfo.getTraineeId(), pkgId, traineeInfo.getTraineeType(), ctId);
	}

	/**
	 * 
	 * @desc //TODO 加载试卷时间 用于前台控制用户的答卷时间
	 * @author huangwb
	 * 
	 * @data 2019年3月4日 下午5:36:25
	 */
	@PostMapping("loadPaperTime/{dyId}")
	@CheckSession
	public R loadPaperTime(@PathVariable("dyId") String dyId, HttpServletRequest request) throws ParseException {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		TepExamineDynamicPaper dynamicPaper = tepExamineDynamicPaperService.selectObjectById(dyId);
		if (dynamicPaper == null) {
			return R.error("无法查看他人试卷时间");
		}
		if (dynamicPaper.getPaperIsFinished().equals("Y")) {
			return R.error("试卷已经作答完毕");
		}
		TepExaminePaperInfo paper = tepExaminePaperInfoService.selectObjectById(dynamicPaper.getPaperId());
		Map<String, Object> map = new HashMap<>();
		map.put("traineeId", traineeInfo.getTraineeId());
		map.put("dyId", dyId);
		TepExamineHistoryPaper history = tepExamineHistoryPaperService.selectListByMap(map).get(0);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simpleDateFormat.parse(history.getPaperBeginTime());
		Calendar nowTime = Calendar.getInstance();
		nowTime.setTime(date);
		nowTime.add(Calendar.MINUTE, Integer.valueOf(paper.getPaperConfinementTime()));
		long currenTime = nowTime.getTimeInMillis() - System.currentTimeMillis();
		return R.ok().put("endTimeFlag", currenTime > 0 ? false : true).put("endTime", currenTime > 0 ? currenTime : 0);
	}

	/**
	 * 每隔30秒提交一次题目答案 需要传递题目号
	 * 
	 * @param request
	 * @param paperArrays
	 * @param dyId
	 * @return
	 */
	@PostMapping("examinePreCommit/{dyId}")
	@CheckSession
	public R examinePreCommit(HttpServletRequest request, @RequestBody List<TepExaminePaperScore> paperArrays,
			@PathVariable("dyId") String dyId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivityTestPaperTraineeAnswerService.examinePreCommit(request, paperArrays, dyId,
				traineeInfo.getTraineeId(), traineeInfo.getTraineeType());
	}

	/**
	 * 最终提交试卷作答结果
	 * 
	 * @param request
	 * @param paperArrays
	 * @param dyId
	 * @return
	 */
	@PostMapping("examineCommit/{dyId}")
	@CheckSession
	@Transactional
	public R finishExamineCommit(HttpServletRequest request, @RequestBody List<TepExaminePaperScore> paperArrays,
			@PathVariable("dyId") String dyId, String ctId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivityTestPaperTraineeAnswerService.saveTraineeCommitAnswerContent(ctId, dyId, paperArrays, traineeInfo.getTraineeId(), traineeInfo.getTraineeType());
	}
	
	
	/**
	 * 查询本测试活动的总体结果
	 * @param ctId 课堂id
	 * @param pkgId 课堂对应的教学包id
	 * @param activityId 测试活动id（试卷id）
	 * @param loginUserId 当前登录用户
	 * @return
	 */
	@PostMapping("queryTraineeAnswerInfo")
	@CheckSession
	public R queryTraineeAnswerInfo(HttpServletRequest request, String ctId, String pkgId, String activityId, String traineeName, String mobile, String jobNumber) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivityTestPaperTraineeAnswerService.queryTraineeAnswerInfo(ctId, pkgId, activityId, traineeInfo.getTraineeId(), traineeName, mobile, jobNumber);
	}
	
	/**
	 * 查看某一次试卷考核结果
	 * @param request
	 * @param ctId
	 * @param dyId
	 * @return
	 */
	@PostMapping("viewExamineResult")
	@CheckSession
	public R viewExamineResult(HttpServletRequest request, String ctId, String dyId, String traineeId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivityTestPaperTraineeAnswerService.viewExamineResult(ctId, dyId, traineeInfo.getTraineeId(), traineeId);
	}

	/**
	 * 给简答题评分
	 * @param request
	 * @param ctId
	 * @param pkgId
	 * @param activityId
	 * @param scoreId
	 * @param questionsScore
	 * @return
	 */
	@PostMapping("giveScore")
	@CheckSession
	public R giveScore(HttpServletRequest request, String ctId, String pkgId, String activityId, String scoreId, String questionsScore) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivityTestPaperTraineeAnswerService.giveScore(ctId, pkgId, activityId, scoreId, questionsScore, traineeInfo.getTraineeId());
	}
}
