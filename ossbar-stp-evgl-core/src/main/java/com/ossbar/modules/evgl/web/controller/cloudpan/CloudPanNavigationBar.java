package com.ossbar.modules.evgl.web.controller.cloudpan;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.cloudpan.api.TcloudPanNavigationBarService;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;

/**
 * 云盘快捷面板
 * @author huj
 *
 */
@RestController
@RequestMapping("/cloud-api/navbar")
public class CloudPanNavigationBar {
	
	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TcloudPanNavigationBarService tcloudPanNavigationBarService;
	
	/**
	 * 快捷导航列表
	 * @param ctId
	 * @param name
	 * @return
	 */
	@PostMapping("/querNavBarList")
	@CheckSession
	public R querNavBarList(HttpServletRequest request, String ctId, String name) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tcloudPanNavigationBarService.querNavBarList(ctId, name, traineeInfo.getTraineeId());
	}
	
	/**
	 * 查询可设置选择的目录
	 * @return
	 */
	@PostMapping("/queryRightList")
	@CheckSession
	public R queryRightList(HttpServletRequest request, String ctId, String name) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tcloudPanNavigationBarService.queryRightList(ctId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 保存
	 * @return
	 */
	@PostMapping("/saveNavBarBatch")
	@CheckSession
	public R saveNavBarBatch(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tcloudPanNavigationBarService.saveNavBarBatch(jsonObject, traineeInfo.getTraineeId());
	}
}
