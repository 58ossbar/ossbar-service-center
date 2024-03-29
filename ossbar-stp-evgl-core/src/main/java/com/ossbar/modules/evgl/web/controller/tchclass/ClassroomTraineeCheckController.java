package com.ossbar.modules.evgl.web.controller.tchclass;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.medu.sys.api.TmeduApiTokenService;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomTraineeCheckService;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;

/**
 * 课堂-审核成员
 * @author huj
 *
 */
@RestController
@RequestMapping("teachingCenter-api/classroom-trainee-check-api")
public class ClassroomTraineeCheckController {

	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TmeduApiTokenService tmeduApiTokenService;
	@Reference(version = "1.0.0")
	private TevglTchClassroomTraineeCheckService tevglTchClassroomTraineeCheckService;
	
	/**
	 * 待审核的成员列表
	 * @param params {'ctId':'', 'traineeName':'', 'pageNum':1, 'pageSize':10}
	 * @param token
	 * @return
	 */
	@GetMapping("/queryTraineeList")
	@CheckSession
	public R queryTraineeList(HttpServletRequest request, @RequestParam Map<String, Object> params) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		params.put("isPass", "N");
		return tevglTchClassroomTraineeCheckService.queryTraineeList(params, traineeInfo.getTraineeId());
	}
	
	/**
	 * 将待审核的学员加入成课程成员
	 * @param jsonObject {'token':'', 'ctId':'', 'traineeIds':[]}
	 * @return
	 */
	@PostMapping("/setTraineeToClassroom")
	@CheckSession
	public R setTraineeToClassroom(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTchClassroomTraineeCheckService.setTraineeToClassroom(jsonObject, traineeInfo.getTraineeId());
	}
}
