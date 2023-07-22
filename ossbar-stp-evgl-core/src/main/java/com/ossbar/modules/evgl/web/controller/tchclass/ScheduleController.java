package com.ossbar.modules.evgl.web.controller.tchclass;

import java.util.HashMap;
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
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.tch.api.TevglTchScheduleClassService;
import com.ossbar.modules.evgl.tch.api.TevglTchScheduleService;
import com.ossbar.modules.evgl.tch.params.TevglTchScheduleParams;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.Identities;

/**
 * 授课安排
 * @author huj
 *
 */
@RestController
@RequestMapping("/site/schedule")
public class ScheduleController {

	@Reference(version = "1.0.0")
	private TevglTchScheduleService tevglTchScheduleService;
	@Reference(version = "1.0.0")
	private DictService dictService;
	@Reference(version = "1.0.0")
	private TevglTchScheduleClassService tevglTchScheduleClassService;
	
	/**
	 * 根据条件查询课表
	 * @param request
	 * @param params
	 * @return
	 */
	@PostMapping("/queryScheduleDataForWeb")
	@CheckSession
	public R queryScheduleDataForWeb(HttpServletRequest request, @RequestBody TevglTchScheduleParams params) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTchScheduleService.queryScheduleDataForWeb(params, traineeInfo);
	}
	
	/**
	 * 获取字典
	 * @return
	 */
	@RequestMapping("/getDictScheduleState")
	public R getDictScheduleState() {
		List<Map<String,Object>> dictList = dictService.getDictList("scheduleState");
		Map<String, Object> defaultMap = new HashMap<String, Object>();
		defaultMap.put("dictId", Identities.uuid());
		defaultMap.put("dictCode", "");
		defaultMap.put("dictValue", "全部类型");
		dictList.add(0, defaultMap);
		return R.ok().put(Constant.R_DATA, dictList);
	}
	
	/**
	 * 查询该用户所教，上课的班级
	 * @return
	 */
	@PostMapping("/findMyClassList")
	@CheckSession
	public R findMyClassList(HttpServletRequest request) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTchScheduleClassService.findMyClassList(traineeInfo.getTraineeId());
	}
	
	/**
	 * 查询我已经加入的班级
	 * @return
	 */
	@PostMapping("/findMyJoinedClassList")
	@CheckSession
	public R findMyJoinedClassList(HttpServletRequest request) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTchScheduleClassService.findMyJoinedClassList(traineeInfo.getTraineeId());
	}
	
	/**
	 * 查询该用户上课的老师
	 * @return
	 */
	@PostMapping("/findMyTeacherList")
	@CheckSession
	public R findMyTeacherList(HttpServletRequest request) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTchScheduleClassService.findMyTeacherList(traineeInfo.getTraineeId());
	}
	
	/**
	 * 查询教室列表
	 * @param params
	 * @return R
	 */
	@GetMapping("/queryTrainingRoomList")
	public R queryTrainingRoomList(@RequestParam Map<String, Object> params) {
		return tevglTchScheduleService.queryTrainingRoomList(params);
	}
}
