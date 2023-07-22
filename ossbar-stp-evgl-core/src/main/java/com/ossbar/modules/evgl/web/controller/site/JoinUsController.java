package com.ossbar.modules.evgl.web.controller.site;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.DictService;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.site.api.TevglSiteJoinUsService;
import com.ossbar.modules.evgl.site.domain.TevglSiteJoinUs;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.constants.Constant;

@RestController
@RequestMapping("/site/join-us")
public class JoinUsController {

	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TevglSiteJoinUsService tevglSiteJoinUsService;
	
	@Reference(version = "1.0.0")
	private DictService dictService;
	
	/**
	 * 从字典获取合作模式
	 * @return
	 */
	@RequestMapping("/queryCooperationModelList")
	public R queryCooperationModelList() {
		List<Map<String,Object>> dictList = dictService.getDictList("cooperation_model");
		return R.ok().put(Constant.R_DATA, dictList);
	}
	
	/**
	 * 加入我们
	 * @param request
	 * @param tevglSiteJoinUs
	 * @return
	 */
	@PostMapping("/commit")
	@CheckSession
	public R commit(HttpServletRequest request, @RequestBody TevglSiteJoinUs tevglSiteJoinUs) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglSiteJoinUsService.joinUs(tevglSiteJoinUs, traineeInfo.getTraineeId());
	}
	
}
