package com.ossbar.modules.evgl.web.controller.empirical;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeEmpiricalValueLogService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;

/**
 * 经验值、点赞数、等，图表
 * @author huj
 *
 */
@RestController
@RequestMapping("/classroom-api/icon")
public class TraineeIconDatasController {

	@Reference(version = "1.0.0")
	private TevglTraineeEmpiricalValueLogService tevglTraineeEmpiricalValueLogService;
	
	/**
	 * 查看某学员的当前课堂的经验值、热心解答次数、获取点赞数、课堂表现次数、视频学习个数
	 * @param request
	 * @param ctId
	 * @param traineeId
	 * @return
	 */
	@PostMapping("/viewNums")
	@CheckSession
	public R viewNums(HttpServletRequest request, String ctId, String traineeId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTraineeEmpiricalValueLogService.viewNums(ctId, traineeId);
	}
	
}
