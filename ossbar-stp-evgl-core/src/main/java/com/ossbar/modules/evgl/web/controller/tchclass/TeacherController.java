package com.ossbar.modules.evgl.web.controller.tchclass;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.tch.api.TevglTchTeacherService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;

@RestController
@RequestMapping("teachingCenter-api/teacher-api")
public class TeacherController {

	@Reference(version = "1.0.0.")
	private TevglTchTeacherService tevglTchTeacherService;
	
	/**
	 * 教师列表
	 * @param params
	 * @return
	 */
	@GetMapping("/queryTeacherList")
	@CheckSession
	public R queryTeacherList(HttpServletRequest request, @RequestParam Map<String, Object> params, String type) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		// 只查询在职的教师
		params.put("state", "Y");
		params.put("loginUserId", traineeInfo.getTraineeId());
		return tevglTchTeacherService.querySimpleListMapByMap(params, type);
	}
	
}
