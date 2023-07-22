package com.ossbar.modules.evgl.web.controller.tchclass;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.tch.api.TevglTchClassService;
import com.ossbar.modules.evgl.tch.api.TevglTchTeacherService;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeScoreService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.constants.Constant;

/**
 * 学生成绩
 * @author huj
 *
 */
@RestController
@RequestMapping("teachingCenter-api/score-api")
public class TraineeScoreController {

	@Reference(version = "1.0.0")
	private TevglTraineeScoreService tevglTraineeScoreService;
	@Reference(version = "1.0.0")
	private TevglTchTeacherService tevglTchTeacherService;
	@Reference(version = "1.0.0")
	private TevglTchClassService tevglTchClassService;
	
	/**
	 * 根据条件查询课表
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping("/findTraineeScoreList")
	@CheckSession
	public R findTraineeScoreList(HttpServletRequest request, @RequestParam Map<String, Object> params) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		// 如果是老师
		TevglTchTeacher tevglTchTeacher = tevglTchTeacherService.selectObjectByTraineeId(traineeInfo.getTraineeId());
		// 否则是学员,则查看自己的成绩 （注意：教师账号无效的，视为学员）
		if (tevglTchTeacher == null || !"Y".equals(tevglTchTeacher.getState())) {
			params.put("traineeId", traineeInfo.getTraineeId());
		}
		return tevglTraineeScoreService.findTraineeScoreList(params);
	}
	
	@RequestMapping("/getClassList")
	public R getClassList(@RequestParam Map<String, Object> params) {
		params.put("sidx", "t1.create_time");
		params.put("order", "desc");
		List<Map<String, Object>> list = tevglTchClassService.selectSimpleListMap(params);
		return R.ok().put(Constant.R_DATA, list);
	}
	
	@RequestMapping("/findStudentInfo")
	@CheckSession
	public R findStudentInfo(HttpServletRequest request) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTraineeScoreService.findStudentInfo(traineeInfo.getTraineeId());
	}
}
