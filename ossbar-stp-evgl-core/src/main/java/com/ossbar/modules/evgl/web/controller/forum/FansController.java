package com.ossbar.modules.evgl.web.controller.forum;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.forum.api.TevglForumAttentionService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;

/**
 * 粉丝控制类，关注，取关
 * @author admin
 *
 */
@RestController
@RequestMapping("/site/forum/fans")
public class FansController {
	
	@Reference(version = "1.0.0")
	private TevglForumAttentionService tevglForumAttentionService;

	/**
	 * 关注/取消关注博主
	 * @author zhouyl加
	 * @date 2021年3月3日
	 * @param request
	 * @param traineeId
	 * @return
	 */
	@PostMapping("follow")
	@CheckSession
	public R follow(HttpServletRequest request, String traineeId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglForumAttentionService.follow(traineeId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 展示我的关注列表
	 * @author zhouyl加
	 * @date 2021年3月3日
	 * @param request
	 * @param params
	 * @return
	 */
	@GetMapping("queryMyFollowList")
	@CheckSession
	public R queryMyFollowList(HttpServletRequest request, @RequestParam Map<String, Object> params) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglForumAttentionService.queryMyFollowList(params, traineeInfo.getTraineeId());
	}
}
