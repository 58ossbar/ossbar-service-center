package com.ossbar.modules.wx.tch.controller;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.medu.api.TmeduApiTokenService;
import com.ossbar.modules.evgl.medu.domain.TmeduApiToken;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomTraineeCheckService;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 课堂-审核成员
 * @author huj
 *
 */
@RestController
@RequestMapping("/wx/classroom-trainee-check-api")
public class WxClassroomTraineeCheckController {

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
	public R queryTraineeList(@RequestParam Map<String, Object> params, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomTraineeCheckService.queryTraineeList(params, traineeInfo.getTraineeId());
	}
	
	/**
	 * 将待审核的学员加入成课程成员
	 * @param jsonObject {'token':'', 'ctId':'', 'traineeIds':[], 'isPass': 'Y/N'}
	 * @return
	 */
	@PostMapping("/setTraineeToClassroom")
	public R setTraineeToClassroom(@RequestBody JSONObject jsonObject) {
		String token = jsonObject.getString("token");
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomTraineeCheckService.setTraineeToClassroom(jsonObject, traineeInfo.getTraineeId());
	}
}
