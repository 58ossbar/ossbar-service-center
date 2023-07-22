package com.ossbar.modules.evgl.web.controller.activity;

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
import com.ossbar.modules.common.DictService;
import com.ossbar.modules.evgl.activity.api.TevglActivitySigninInfoService;
import com.ossbar.modules.evgl.activity.api.TevglActivitySigninTraineeService;
import com.ossbar.modules.evgl.activity.domain.TevglActivitySigninInfo;
import com.ossbar.modules.evgl.activity.domain.TevglActivitySigninTrainee;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.constants.Constant;

/**
 * 8签到活动
 * @author huj
 *
 */
@RestController
@RequestMapping("/activity/signin")
public class ActivitySigninInfoController {

	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TevglActivitySigninInfoService tevglActivitySigninInfoService;
	@Reference(version = "1.0.0")
	private TevglActivitySigninTraineeService tevglActivitySigninTraineeService;
	@Reference(version = "1.0.0")
	private DictService dictService;
	
	/**
	 * 签到状态
	 * @return
	 */
	@GetMapping("/getDictList")
	public R getDictList() {
		return R.ok().put(Constant.R_DATA, dictService.getDictList("signType"));
	}
	
	/**
	 * 保存一个签到活动的基础数据
	 * @param request
	 * @param pkgId
	 * @param tevglActivitySigninInfo
	 * @return
	 */
	@PostMapping("/saveSigninInfo")
	@CheckSession
	public R saveSigninInfo(HttpServletRequest request, String pkgId,
			@RequestBody TevglActivitySigninInfo tevglActivitySigninInfo) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivitySigninInfoService.saveSigninInfo(tevglActivitySigninInfo, pkgId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 查看签到信息
	 * @param activityId
	 * @return
	 */
	@GetMapping("/viewSigninInfo")
	public R viewSigninInfo(String activityId) {
		return tevglActivitySigninInfoService.viewSigninInfo(activityId);
	}
	
	/**
	 * 根据条件查询签到状态列表成员
	 * @param request
	 * @param params
	 * @return
	 */
	@GetMapping("/queryTraineeList")
	@CheckSession
	public R queryTraineeList(HttpServletRequest request, @RequestParam Map<String, Object> params) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivitySigninTraineeService.queryTraineeList(params, traineeInfo.getTraineeId());
	}
	
	/**
	 * 教师批量设置学员签到状态
	 * @param request
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/setTraineeSignState")
	@CheckSession
	public R setTraineeSignState(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivitySigninTraineeService.setTraineeSignState(jsonObject, traineeInfo.getTraineeId());
	}
	
	/**
	 * 签到
	 * @param request
	 * @param tevglActivitySigninTrainee
	 * @param token
	 * @return
	 */
	@PostMapping("signIn")
	@CheckSession
	public R signIn(HttpServletRequest request, @RequestBody TevglActivitySigninTrainee tevglActivitySigninTrainee, String token) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivitySigninTraineeService.signIn(tevglActivitySigninTrainee, traineeInfo.getTraineeId());
	}
	
	/**
	 * 查看签到活动历史记录
	 * @param ctId
	 * @return
	 */
	@GetMapping("/queryHistorySigninInfo")
	@CheckSession
	public R queryHistorySigninInfo(HttpServletRequest request, @RequestParam Map<String, Object> params) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivitySigninInfoService.queryHistorySigninInfo(params);
	}
	
	/**
	 * 删除
	 * @param request
	 * @param activityId
	 * @return
	 */
	@PostMapping("/deleteHistorySigninInfo")
	@CheckSession
	public R deleteHistorySigninInfo(HttpServletRequest request, String activityId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivitySigninInfoService.deleteHistorySigninInfo(activityId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 手势签到
	 * @param tevglActivitySigninTrainee
	 * @param token
	 * @return
	 */
	@RequestMapping("signIno")
	@CheckSession
	public R signIno(HttpServletRequest request, @RequestBody TevglActivitySigninTrainee tevglActivitySigninTrainee) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivitySigninTraineeService.signIn(tevglActivitySigninTrainee, traineeInfo.getTraineeId());
	}
	
	/**
	 * 出勤统计图
	 * @param request
	 * @param ctId
	 * @return
	 */
	@RequestMapping("viewAttendanceStatisticsChart")
	@CheckSession
	public R viewAttendanceStatisticsChart(HttpServletRequest request, String ctId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivitySigninInfoService.viewAttendanceStatisticsChart(ctId);
	}
	
}
