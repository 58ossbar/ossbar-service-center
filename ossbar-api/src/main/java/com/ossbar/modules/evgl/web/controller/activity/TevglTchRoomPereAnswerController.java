package com.ossbar.modules.evgl.web.controller.activity;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.activity.api.TevglTchRoomPereAnswerService;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@RestController
@RequestMapping("/activity/pere")
public class TevglTchRoomPereAnswerController {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglTchRoomPereAnswerController.class);
	@Reference(version = "1.0.0")
	private TevglTchRoomPereAnswerService tevglTchRoomPereAnswerService;
	
	/**
	 * 【教师端】新增课堂表现
	 * @author zhouyl加
	 * @date 2020年12月22日
	 * @param request
	 * @return
	 */
	@PostMapping("launchAnswer")
	@CheckSession
	public R launchAnswer(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTchRoomPereAnswerService.launchAnswer(jsonObject, traineeInfo.getTraineeId());
	}

	/**
	 * @author zhouyl加
	 * @date 2020年12月23日
	 * 手动选人以及随机选人
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("selectPeopleToAnswer")
	@CheckSession
	public R selectPeopleToAnswer(HttpServletRequest request,@RequestBody JSONObject jsonObject) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTchRoomPereAnswerService.selectPeopleToAnswer(jsonObject, traineeInfo.getTraineeId());
	}
	
	/**
	 * 查询该课堂下的所有成员不分页
	 * @author zhouyl加
	 * @date 2020年12月23日
	 * @param ctId 课堂id
	 * @return
	 */
	@GetMapping("selectClassroomTraineeInfo")
	@CheckSession
	public R selectClassroomTraineeInfo(HttpServletRequest request, String ctId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTchRoomPereAnswerService.selectClassroomTraineeInfo(ctId, traineeInfo.getTraineeId());
	}

	/**
	 * 【教师端】查询课堂下抢答活动的抢答人员
	 * @param ctId 课堂id
	 * @param activityId 活动id
	 * @return
	 */
	@GetMapping("answerActivityTraineeInfo")
	@CheckSession
	public R answerActivityTraineeInfo(HttpServletRequest request, String ctId, String activityId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTchRoomPereAnswerService.answerActivityTraineeInfo(ctId, activityId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 查询抢答活动的总体结果
	 * @author zhouyl加
	 * @date 2020年12月26日
	 * @param request
	 * @param ctId 课堂id
	 * @param activityId 活动id
	 * @return
	 */
	@GetMapping("answerSummaryResults")
	@CheckSession
	public R answerSummaryResults(HttpServletRequest request, String ctId, String activityId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTchRoomPereAnswerService.answerSummaryResults(ctId, activityId, traineeInfo.getTraineeId());
	}
}
