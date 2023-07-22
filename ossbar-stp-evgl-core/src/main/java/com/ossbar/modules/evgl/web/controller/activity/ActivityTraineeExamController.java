package com.ossbar.modules.evgl.web.controller.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.eao.api.TeaoTraineeExamineService;
import com.ossbar.modules.evgl.eao.api.TeaoTraineeExamroomService;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomTraineeService;
import com.ossbar.modules.evgl.tch.api.TevglTchTeacherService;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;

/**
 * 9 实践考核
 * @author huj
 *
 */
@RestController
@RequestMapping("/activity/traineeExam")
public class ActivityTraineeExamController {

	@Reference(version = "1.0.0")
	private TeaoTraineeExamroomService teaoTraineeExamroomService;
	@Reference(version = "1.0.0")
	private TeaoTraineeExamineService teaoTraineeExamineService;
	@Reference(version = "1.0.0")
	private TevglTchClassroomTraineeService tevglTchClassroomTraineeService;
	@Reference(version = "1.0.0")
	private TevglTchTeacherService tevglTchTeacherService;
	
	/**
	 * 查询课堂成员（不包含待审核的）（无分页）
	 * @param ctId
	 * @param traineeName
	 * @return
	 */
	@PostMapping("/queryTraineeList")
	@CheckSession
	public R queryTraineeList(HttpServletRequest request, String ctId, String traineeName) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		params.put("traineeName", traineeName);
		params.put("state", "Y");
		List<Map<String, Object>> list = tevglTchClassroomTraineeService.listClassroomTrainee(params);
		return R.ok().put(Constant.R_DATA, list);
	}
	
	/**
	 * 查询课堂成员（不包含待审核的）
	 * @param ctId
	 * @param traineeName
	 * @return
	 */
	@PostMapping("/queryTeacherList")
	@CheckSession
	public R queryTeacherList(HttpServletRequest request, String teacherName) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("teacherName", teacherName);
		params.put("state", "Y");
		List<TevglTchTeacher> list = tevglTchTeacherService.selectListByMapInnerJoinTraineeTable(params);
		return R.ok().put(Constant.R_DATA, list);
	}
	
	/**
	 * 新增修改
	 * @param request
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/saveActivityExamroomInfo")
	@CheckSession
	public R saveActivityExamroomInfo(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return teaoTraineeExamroomService.saveActivityExamroomInfo(jsonObject, traineeInfo.getTraineeId());
	}
	
	/**
	 * 查看基本信息
	 * @param request
	 * @param activityId
	 * @return
	 */
	@GetMapping("viewExamroomInfo")
	@CheckSession
	public R viewExamroomInfo(HttpServletRequest request, String activityId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return teaoTraineeExamroomService.viewExamroomInfo(activityId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 进入考核填表界面
	 * @param request
	 * @param ctId
	 * @param activityId
	 * @return
	 */
	@RequestMapping("list")
	@CheckSession
	public R list(HttpServletRequest request, String ctId, String activityId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return teaoTraineeExamroomService.list(ctId, activityId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 定时自动保存考核成绩
	 * @param request
	 * @param ctId
	 * @param activityId (erId)
	 * @param traineeIds
	 * @param regularIds
	 * @param regularSjnums
	 * @return
	 */
	@RequestMapping("precommit")
	@CheckSession
	public R preExam(HttpServletRequest request, String ctId, String activityId, String traineeIds, String regularIds, String regularSjnums) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		String traineeName = StrUtils.isEmpty(traineeInfo.getTraineeName()) ? traineeInfo.getNickName() : traineeInfo.getTraineeName();
		return teaoTraineeExamineService.preExam(ctId, activityId, traineeIds, regularIds, regularSjnums, traineeInfo.getTraineeId(), traineeName);
	}
	
	/**
	 * 最终提交考核成绩
	 * @param request
	 * @param ctId
	 * @param activityId (erId)
	 * @param traineeIds
	 * @param regularIds
	 * @param regularSjnums
	 * @return
	 */
	@RequestMapping("commit")
	@CheckSession
	public R commit(HttpServletRequest request, String ctId, String activityId, String traineeIds, String regularIds, String regularSjnums) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		String traineeName = StrUtils.isEmpty(traineeInfo.getTraineeName()) ? traineeInfo.getNickName() : traineeInfo.getTraineeName();
		return teaoTraineeExamineService.commit(ctId, activityId, traineeIds, regularIds, regularSjnums, traineeInfo.getTraineeId(), traineeName);
	}

	/**
	 * 查看评分排名信息
	 * @param request
	 * @param activityId
	 * @param traineeName
	 * @return
	 */
	@GetMapping("viewExamResultInfo")
	@CheckSession
	public R viewExamResultInfo(HttpServletRequest request, String activityId, String traineeName, String mobile, String jobNumber) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return teaoTraineeExamroomService.viewExamResultInfo(activityId, traineeName, mobile, jobNumber, traineeInfo.getTraineeId());
	}
	
	/**
	 * 查看考核规则成绩
	 * @param request
	 * @param activityId
	 * @param traineeId
	 * @param 1查看某人给别人评的规则分2查看某人的规则得分
	 * @return
	 */
	@GetMapping("viewExamDetailInfo")
	@CheckSession
	public R viewExamDetailInfo(HttpServletRequest request, String activityId, String traineeId, String type) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return teaoTraineeExamroomService.viewExamDetailInfo(traineeId, activityId, traineeInfo.getTraineeId(), type);
	}
	
}
