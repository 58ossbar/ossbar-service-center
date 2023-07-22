package com.ossbar.modules.evgl.web.controller.activity;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.activity.api.TevglActivityLiveService;
import com.ossbar.modules.evgl.activity.domain.TevglActivityLive;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.tool.StrUtils;

/**
 * 7轻直播
 * @author huj
 *
 */
@RestController
@RequestMapping("/activity/live")
public class ActivityLiveController {

	@Reference(version = "1.0.0")
	private TevglActivityLiveService tevglActivityLiveService;
	
	@PostMapping("save")
	@CheckSession
	public R saveLive(HttpServletRequest request, @RequestBody TevglActivityLive tevglActivityLive) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (StrUtils.isEmpty(tevglActivityLive.getActivityId())) {
			return tevglActivityLiveService.saveLive(tevglActivityLive, traineeInfo.getTraineeId());
		} else {
			return tevglActivityLiveService.updateLive(tevglActivityLive, traineeInfo.getTraineeId());
		}
	}
	
}
