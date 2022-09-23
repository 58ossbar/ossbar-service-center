package com.ossbar.modules.wx.tch.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.medu.api.TmeduApiTokenService;
import com.ossbar.modules.evgl.medu.domain.TmeduApiToken;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomService;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomTraineeService;
import com.ossbar.modules.evgl.tch.api.TevglTchClasstraineeService;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课堂--成员
 * @author water
 *
 */
@RestController
@RequestMapping("/wx/classroom-trainee-api")
public class WxClassroomTraineeController {

	Logger log = LoggerFactory.getLogger(this.getClass());
	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TmeduApiTokenService tmeduApiTokenService;
	@Reference(version = "1.0.0")
	private TevglTchClassroomTraineeService tevglTchClassroomTraineeService;
	@Reference(version = "1.0.0")
	private TevglTchClasstraineeService tevglTchClasstraineeService;
	@Reference(version = "1.0.0")
	private TevglTchClassroomService tevglTchClassroomService;
	
	/**
	 * 课堂成员列表
	 * @param params
	 * @param token
	 * @return
	 */
	@GetMapping("listClassroomTrainee")
	public R listClassroomTrainee(@RequestParam Map<String, Object> params, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
			return R.error(401, "Invalid token");
		}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		params.put("queryPermission", "Y");
		return tevglTchClassroomTraineeService.selectListMapForWeb(params, traineeInfo.getTraineeId());
	}
	
	/**
	 * 班级成员列表，只会查询未加入课堂的
	 * @param ctId 课堂主键
	 * @param classId 班级主键
	 * @param traineeName 学员名称
	 * @param pageNum 当前页
	 * @param pageSize 每页显示数
	 * @param token
	 * @return
	 */
	@GetMapping("listClassTrainee")
	public R listClassTrainee(String ctId, String classId, String traineeName, 
			Integer pageNum, Integer pageSize, String token) {
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctId", ctId);
		params.put("classId", classId);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		if (StrUtils.isNotEmpty(traineeName)) {
			params.put("traineeName", traineeName);
		}
		return tevglTchClasstraineeService.listClassTraineeExcludeJoinedClassroom(params);
	}
	
	/**
	 * 删除课堂成员
	 * @param token
	 * @param id 课堂成员表主键
	 * @param ctId 课堂id
	 * @param traineeId 被删除的学员id
	 * @return
	 */
	//@PostMapping("/deleteClassTrainee")
	public R deleteClassroomTrainee(String token, String id, String ctId, String traineeId) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("ctId", ctId);
		map.put("traineeId", traineeId);
		map.put("loginUserId", traineeInfo.getTraineeId());
		return tevglTchClassroomTraineeService.deleteClassroomTrainee(map);
	}
	
	/**
	 * 批量删除课堂成员
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/deleteClassroomTraineeBatch")
	public R deleteClassroomTraineeBatch(@RequestBody JSONObject jsonObject) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(jsonObject.getString("token"));
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomTraineeService.deleteClassroomTraineeBatch(jsonObject, traineeInfo.getTraineeId());
	}
	
	/**
	 * 将班级学员导入成课堂成员
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/importTraineeBatch")
	public R importTraineeBatch(@RequestBody JSONObject jsonObject) {
		String ctId = jsonObject.getString("ctId"); 
		String classId = jsonObject.getString("classId");
		JSONArray jsonArray = jsonObject.getJSONArray("traineeIds");
		List<String> traineeIds = new ArrayList<>();
		for (int i = 0; i < jsonArray.size(); i++) {
			traineeIds.add((String)jsonArray.get(i));	
		}
		return tevglTchClassroomTraineeService.importTraineeBatch(ctId, classId, traineeIds);
	}
	
	/**
	 * 设为助教
	 * @param request
	 * @param ctId
	 * @param traineeId
	 * @param token
	 * @return
	 */
	@PostMapping("/setTraineeToTeachingAssistant")
	public R setTraineeToTeachingAssistant(HttpServletRequest request, String ctId, String traineeId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		params.put("traineeId", traineeId);
		params.put("loginUserId", traineeInfo.getTraineeId());
		return tevglTchClassroomService.setTraineeToTeachingAssistant(params);
	}
	
	/**
	 * 取消助教
	 * @param ctId
	 * @param token
	 * @return
	 */
	@PostMapping("/cancelTeachingAssistant")
	public R cancelTeachingAssistant(String ctId, String traineeId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomService.cancelTeachingAssistant(ctId, traineeId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 查看课堂成员基本信息
	 * @param ctId
	 * @param traineeId
	 * @return
	 */
	@GetMapping("/viewTraineeBaseInfo")
	public R viewTraineeBaseInfo(String ctId, String traineeId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomTraineeService.viewTraineeBaseInfo(ctId, traineeId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 图形信息
	 * @param ctId
	 * @param traineeId
	 * @return
	 */
	@GetMapping("/viewTraineeDetailInfo")
	public R viewTraineeDetailInfo(String ctId, String traineeId) {
		//return tevglTchClassroomTraineeService.viewTraineeDetailInfo(ctId, traineeId);
		return tevglTchClassroomTraineeService.viewTraineeDetailInfoV2(ctId, traineeId);
	}
	
	/**
	 * 教师修改学员信息
	 * @param request
	 * @param ctId
	 * @param traineeId
	 * @param traineePic
	 * @param traineeName
	 * @param traineeSex
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/updateTraineeInfo")
	public R updateTraineeInfo(HttpServletRequest request, String token, String ctId, String traineeId, String traineePic, String traineeName, String nickName, String traineeSex) throws Exception {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomTraineeService.updateTraineeInfo(request, ctId, traineeId, traineePic, traineeName, nickName, traineeSex, traineeInfo.getTraineeId());
	}
	
	@PostMapping("/updateTraineeInfoNoFile")
	public R updateTraineeInfoNoFile(HttpServletRequest request, String token, String ctId, String traineeId, String traineePic, String traineeName, String nickName, String traineeSex) throws Exception {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		TevglTraineeInfo trainee = new TevglTraineeInfo();
		trainee.setTraineeId(traineeId);
		trainee.setTraineeName(traineeName); 
		trainee.setNickName(nickName);
		trainee.setTraineeSex(traineeSex);
		return tevglTraineeInfoService.update(trainee);
	}
	
	/**
	 * 查看经验值明细
	 * @param ctId
	 * @param traineeId
	 * @return
	 */
	@GetMapping("/viewEmpiricalValueLogs")
	public R viewEmpiricalValueLogs(HttpServletRequest request, String ctId, String traineeId, Integer pageNum, Integer pageSize, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return R.error("暂未实现");
	}
}
