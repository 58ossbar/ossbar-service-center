package com.ossbar.modules.evgl.web.controller.site;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.DictService;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.site.api.TevglSiteFeedbackService;
import com.ossbar.modules.evgl.site.domain.TevglSiteFeedback;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.constants.Constant;

@RestController
@RequestMapping("/site")
public class FeedbackController {

	@Reference(version = "1.0.0")
	private TevglSiteFeedbackService tevglSiteFeedbackService;
	@Reference
	private DictService dictService;
	
	/**
	 * 从字典获取意见反馈类型
	 * @return
	 */
	@GetMapping("listFeedbackType")
	public R listFeedbackType() {
		List<Map<String, Object>> dictList = dictService.getDictList("feedbackType");
		return R.ok().put(Constant.R_DATA, dictList);
	}
	
	/**
	 * 所有意见反馈
	 * @param feedbackType
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/queryAllFeedback")
	public R queryAllFeedback(@RequestParam Map<String, Object> params) {
		params.put("sidx", "t1.create_time");
		params.put("order", "desc");
		return tevglSiteFeedbackService.queryFeedbackInfo(params);
	}
	
	/**
	 * 我的意见反馈
	 * @param request
	 * @param params {'feedbackType':'', 'pageNum':'', 'pageSize':''}
	 * @return
	 */
	@GetMapping("/queryMyFeedback")
	public R queryMyFeedback(HttpServletRequest request, @RequestParam Map<String, Object> params) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		params.put("sidx", "t1.create_time");
		params.put("order", "desc");
		params.put("createUserId", traineeInfo.getTraineeId());
		return tevglSiteFeedbackService.queryFeedbackInfo(params);
	}
	
	/**
	 * 保存我的意见反馈内容
	 * @param tevglSiteFeedback
	 * @param token
	 * @return
	 */
	@PostMapping("/saveFeedbackInfo")
	public R saveFeedbackInfo(HttpServletRequest request, @RequestBody TevglSiteFeedback tevglSiteFeedback) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		tevglSiteFeedback.setType("1"); // 1网站2小程序3app
		tevglSiteFeedback.setCreateUserId(traineeInfo.getTraineeId());
		return tevglSiteFeedbackService.save(tevglSiteFeedback);
	}
	
}
