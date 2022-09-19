package com.ossbar.modules.evgl.web.controller.activity;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.activity.api.TevglActivityService;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动相关控制类
 * @author huj
 *
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TevglActivityService tevglActivityService;
	
	/**
	 * 从字典获取活动状态(未开始、进行中、已结束)
	 * @return
	 */
	@GetMapping("/listActivityState")
	@CheckSession
	public R listActivityState(HttpServletRequest request, String pkgId, String ctId, String chapterId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivityService.listActivityState(pkgId, ctId, chapterId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 获取活动分组(包含字典默认、自己针对教学包的活动创建的分组（注：没有细分到某个分组）)
	 * @param request
	 * @return
	 */
	@GetMapping("/listActivityGroup")
	@CheckSession
	public R listActivityGroup(HttpServletRequest request, String pkgId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivityService.listActivityGroup(pkgId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 活动列表
	 * @param request
	 * @param params
	 * activityState 活动状态0未开始1进行中2已结束
	 * @return
	 */
	@GetMapping("/listActivity")
	@CheckSession
	public R listActivity(HttpServletRequest request, @RequestParam Map<String, Object> params) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		if (StrUtils.isNull(params.get("pkgId"))) {
			return R.error("必传参数为空");
		}
		// 教学包详情页页面查询活动列表
		if (StrUtils.isNull(params.get("ctId")) || "null".equals(params.get("ctId"))) {
			log.debug("教学包详情页页面查询活动列表");
			return tevglActivityService.listActivityForPackage(params, traineeInfo.getTraineeId());
		} else {
			// 课堂详情页面查询活动列表
			log.debug("课堂详情页面查询活动列表");
			params.put("loginUserId", traineeInfo.getTraineeId());
			return tevglActivityService.listActivityForRoom(params, traineeInfo.getTraineeId());	
		}
	}
	

	/**
	 * 保存分组
	 * @param request
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/saveResgroup")
	@CheckSession
	public R saveResgroup(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivityService.saveResgroup(jsonObject, traineeInfo.getTraineeId());
	}
	
	/**
	 * 开始活动
	 * @param request
	 * @param ctId 课堂id
	 * @param activityId 活动id
	 * @param pkgId 所属教学包
	 * @param activityType 活动类型1投票/问卷2头脑风暴3答疑/讨论等等
	 * @param activityEndTime 活动自动结束的时间
	 * @return
	 */
	@PostMapping("/startActivity")
	@CheckSession
	public R startActivity(HttpServletRequest request, String ctId, String activityId, String pkgId, String activityType, String activityEndTime) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivityService.startActivity(ctId, activityId, pkgId, activityType, traineeInfo.getTraineeId(), activityEndTime);
	}
	
	/**
	 * 结束活动
	 * @param request
	 * @param activityId
	 * @param activityType
	 * @return
	 */
	@PostMapping("/endActivity")
	@CheckSession
	public R endActivity(HttpServletRequest request, String ctId, String activityId, String activityType) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivityService.endActivity(ctId, activityId, activityType, traineeInfo.getTraineeId());
	}
	
	/**
	 * 删除活动
	 * @param request
	 * @param activityId
	 * @param activityType
	 * @return
	 */
	@PostMapping("/deleteActivity")
	@CheckSession
	public R deleteActivity(HttpServletRequest request, String activityId, String activityType, String pkgId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglActivityService.deleteActivity(activityId, activityType, traineeInfo.getTraineeId(), pkgId);
	}
	
	/**
	 * 查看活动相关信息
	 * @param request
	 * @param activityId
	 * @param activityType
	 * @return
	 */
	@GetMapping("/viewActivityInfo")
	public R viewActivityInfo(HttpServletRequest request, String activityId, String activityType) {
		return tevglActivityService.viewActivityInfo(activityId, activityType);
	}
	
	/**
	 * 发布教学包中，点击课程节点获取全部没有绑定章节的活动
	 * @param request
	 * @param subjectId
	 * @param pkgId
	 * @return
	 */
	@PostMapping("/queryActivityList")
	@CheckSession
	public R queryActivityList(HttpServletRequest request, String subjectId, String pkgId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("pkgId", pkgId);
		List<Map<String,Object>> list = tevglActivityService.selectSimpleListMapForRelease(params);
		return R.ok().put(Constant.R_DATA, list);
	}
	
}
