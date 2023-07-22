package com.ossbar.modules.evgl.web.controller.site;

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
import com.ossbar.modules.evgl.site.api.TevglSiteSysMsgService;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;

@RestController
@RequestMapping("/site/sys-msg")
public class SystemMsgController {

	@Reference(version = "1.0.0")
	private TevglSiteSysMsgService tevglSiteSysMsgService;
	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	
	@GetMapping("/query")
	@CheckSession
	public R query(HttpServletRequest request, @RequestParam Map<String, Object> params) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglSiteSysMsgService.queryMyMsg(params, traineeInfo.getTraineeId());
	}
	
	/**
	 * 发送通知
	 * @param token
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/sendText")
	@CheckSession
	public R sendText(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglSiteSysMsgService.sendText(jsonObject, traineeInfo.getTraineeId());
	}
}
