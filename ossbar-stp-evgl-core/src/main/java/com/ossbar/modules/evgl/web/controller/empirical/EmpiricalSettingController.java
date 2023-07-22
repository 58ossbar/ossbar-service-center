package com.ossbar.modules.evgl.web.controller.empirical;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.empirical.api.TevglEmpiricalSettingService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;

/**
 * 课堂经验值设置
 * @author huj
 *
 */
@RestController
@RequestMapping("/classroom-api/empirical")
public class EmpiricalSettingController {
 
	@Reference(version = "1.0.0")
	private TevglEmpiricalSettingService tevglEmpiricalSettingService;
	
	/**
	 * 经验值设置页面
	 * @param request
	 * @param ctId
	 * @return
	 */
	@PostMapping("/viewEmpiricalSetting")
	@CheckSession
	public R viewEmpiricalSetting(HttpServletRequest request, String ctId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglEmpiricalSettingService.viewEmpiricalSetting(ctId, traineeInfo.getTraineeId());
	}
	
	@PostMapping("saveSetting")
	@CheckSession
	public R saveSetting(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglEmpiricalSettingService.saveSessting(jsonObject, traineeInfo.getTraineeId());
	}
}
