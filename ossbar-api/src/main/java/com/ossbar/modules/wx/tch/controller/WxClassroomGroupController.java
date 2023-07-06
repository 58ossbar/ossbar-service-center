package com.ossbar.modules.wx.tch.controller;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.DictService;
import com.ossbar.modules.evgl.medu.api.TmeduApiTokenService;
import com.ossbar.modules.evgl.medu.domain.TmeduApiToken;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomGroupMemberService;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomGroupService;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomTraineeService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomGroup;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/classroom-group-api")
public class WxClassroomGroupController {

	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TmeduApiTokenService tmeduApiTokenService;
	
	@Reference(version = "1.0.0")
	private TevglTchClassroomGroupService tevglTchClassroomGroupService;
	@Reference(version = "1.0.0")
	private TevglTchClassroomGroupMemberService tevglTchClassroomGroupMemberService;
	@Reference(version = "1.0.0")
	private TevglTchClassroomTraineeService tevglTchClassroomTraineeService;
	@Reference(version = "1.0.0")
	private DictService dictService;
	
	/**
	 * 课堂小组列表
	 * @param ctId
	 * @param token
	 * @return
	 */
	@GetMapping("/listClassroomGroupSimpleInfo")
	public R listClassroomGroupSimpleInfo(String ctId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomGroupService.listClassroomGroupSimpleInfo(ctId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 保存课堂小组信息
	 * @param tevglTchClassroomGroup
	 * @param token
	 * @return
	 */
	@PostMapping("/saveClassroomGroupInfo")
	public R saveClassroomGroupInfo(@RequestBody TevglTchClassroomGroup tevglTchClassroomGroup, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomGroupService.saveClassroomGroupInfo(tevglTchClassroomGroup, traineeInfo.getTraineeId());
	}
	
	/**
	 * 查看课堂小组信息
	 * @param gpId
	 * @return
	 */
	@GetMapping("/viewClassroomGroupInfo")
	public R viewClassroomGroupInfo(String gpId, String traineeName, Integer pageNum, Integer pageSize, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginUserId", traineeInfo.getTraineeId());
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		if (StrUtils.isNotEmpty(traineeName)) {
			params.put("traineeName", traineeName);
		}
		return tevglTchClassroomGroupService.viewClassroomGroupInfo(gpId, 1, params, traineeInfo.getTraineeId());
	}
	
	/**
	 * 只会查询未加入课堂小组的成员
	 * @param ctId
	 * @param token
	 * @return
	 */
	@GetMapping("/listClassroomTraineeExcludeJoinedGroup")
	public R listClassroomTraineeExcludeJoinedGroup(String ctId, String traineeName, Integer pageNum, Integer pageSize, String token) {
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctId", ctId);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		if (StrUtils.isNotEmpty(traineeName) && !"null".equals(traineeName)) {
			params.put("traineeName", traineeName);
		}
		return tevglTchClassroomTraineeService.listClassroomTraineeExcludeJoinedGroup(params);
	}
	
	/**
	 * 将学员加入小组
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/setTraineeToGroup")
	public R setTraineeToGroup(@RequestBody JSONObject jsonObject) {
		String token = jsonObject.getString("token");
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomGroupService.setTraineeToGroup(jsonObject, traineeInfo.getTraineeId());
	}
	
	/**
	 * 将成员移出小组
	 * @param gmId
	 * @param token
	 * @return
	 */
	@GetMapping("/deleteClassroomGroupMember")
	public R deleteClassroomGroupMember(String gmId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomGroupMemberService.deleteClassroomGroupMember(gmId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 批量、单个移除小组
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/removeGroupMembers")
	public R removeGroupMembers(@RequestBody JSONObject jsonObject) {
		String token = jsonObject.getString("token");
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomGroupMemberService.removeGroupMembers(jsonObject, traineeInfo.getTraineeId());
	}
	
	/**
	 * 删除课堂小组
	 * @param gpId
	 * @param token
	 * @return
	 */
	@PostMapping("/deleteClassroomGroup")
	public R deleteClassroomGroup(String gpId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomGroupService.deleteClassroomGroup(gpId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 重命名小组
	 * @param gpId
	 * @return
	 */
	@PostMapping("/rename")
	public R rename(String gpId, String name, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomGroupService.rename(gpId, name, traineeInfo.getTraineeId());
	}
	

	/**
	 * 将学员设为组长
	 * @param request
	 * @param gpId
	 * @param traineeId
	 * @return
	 */
	@PostMapping("/setTraineeToLeader")
	public R setTraineeToLeader(HttpServletRequest request, String gpId, String traineeId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomGroupService.setTraineeToLeader(gpId, traineeId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 获取课堂小组成员身份列表
	 * @return
	 */
	@GetMapping("/getIdentityList")
	public R getIdentityList() {
		List<Map<String,Object>> dictList = dictService.getDictList("group_member_identity");
		return R.ok().put(Constant.R_DATA, dictList);
	}
	
	/**
	 * 设置小组成员身份
	 * @param request
	 * @param ctId
	 * @param gmId
	 * @param dictCode
	 * @return
	 */
	@PostMapping("/setIdentity")
	public R setIdentity(HttpServletRequest request, String ctId, String gmId, String dictCode, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomGroupMemberService.setIdentity(ctId, gmId, dictCode, traineeInfo.getTraineeId());
	}
	
	/**
	 * 设置小组成员身份
	 * @param request
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/setIdentityBatch")
	public R setIdentityBatch(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(jsonObject.getString("token"));
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomGroupMemberService.setIdentityBatch(jsonObject, traineeInfo.getTraineeId());
	}
	
}
