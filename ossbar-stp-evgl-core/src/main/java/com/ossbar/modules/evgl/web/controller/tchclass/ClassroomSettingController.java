package com.ossbar.modules.evgl.web.controller.tchclass;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomSettingService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;

/**
 * 课堂相关设置
 * @author huj
 *
 */
@RestController
@RequestMapping("/classroom-api/setting")
public class ClassroomSettingController {
	
	@Reference(version = "1.0.0")
	private TevglTchClassroomSettingService tevglTchClassroomSettingService;
	
	/**
	 * 保存设置
	 * @param request
	 * @param radio1
	 * @param radio2
	 * @return
	 */
	@RequestMapping("saveSetting")
	public R saveSetting(HttpServletRequest request, String ctId, String radio1, String radio2) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTchClassroomSettingService.saveSetting(ctId, traineeInfo.getTraineeId(), radio1, radio2);
	}
	
	@RequestMapping("viewSetting")
	public R viewSetting(String ctId) {
		return tevglTchClassroomSettingService.viewSetting(ctId);
	}

}
