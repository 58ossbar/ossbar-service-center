package com.ossbar.modules.wx.tch.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.book.api.TevglBookChapterService;
import com.ossbar.modules.evgl.book.api.TevglBookMajorService;
import com.ossbar.modules.evgl.book.api.TevglBookSubjectService;
import com.ossbar.modules.evgl.medu.api.TmeduApiTokenService;
import com.ossbar.modules.evgl.medu.domain.TmeduApiToken;
import com.ossbar.modules.evgl.tch.api.TevglTchClassService;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomRoleprivilegeService;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomService;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomTraineeService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 课堂
 * @author water
 *
 */
@RestController
@RequestMapping("/wx/classroom-api")
public class WxClassroomController {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Reference(version = "1.0.0")
	private TevglTchClassroomService tevglTchClassroomService;
	@Reference(version = "1.0.0")
	private TevglTchClassroomTraineeService tevglTchClassroomTraineeService;
	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TmeduApiTokenService tmeduApiTokenService;
	@Reference(version = "1.0.0")
	private TevglBookChapterService tevglBookChapterService;
	@Reference(version = "1.0.0")
	private TevglBookMajorService tevglBookMajorService;
	@Reference(version = "1.0.0")
	private TevglTchClassService tevglTchClassService;
	@Reference(version = "1.0.0")
	private TevglBookSubjectService tevglBookSubjectService;
	@Autowired
	private UploadFileUtils uploadFileUtils;
	@Autowired
	private TevglTchClassroomRoleprivilegeService tevglTchClassroomRoleprivilegeService;
	
	/**
	 * <p>保存课堂</p>  
	 * @author znn
	 * @data 2020年6月10日	
	 * @param tevglTchClassroom
	 * @param token
	 * @return
	 */
	@PostMapping("/saveOrUpdateClassroom")
	public R saveOrUpdateClassroom(TevglTchClassroom tevglTchClassroom, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
			return R.error(401, "Invalid token");
		}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		// 新增
		if (StrUtils.isEmpty(tevglTchClassroom.getCtId())) {
			return tevglTchClassroomService.saveClassroomInfoNew(tevglTchClassroom, traineeInfo.getTraineeId());
		} else { // 修改
			return tevglTchClassroomService.updateClassroomInfo(tevglTchClassroom, traineeInfo.getTraineeId());
		}
	}
	
	/**
	 * <p>通过邀请码搜索课堂</p>  
	 * @author znn
	 * @data 2020年6月11日	
	 * @param map
	 * @param token
	 * @return
	 */
	@GetMapping("/byInvitationCodeSearchClassroom")
	public R byInvitationCodeSearchClassroom(@RequestParam Map<String, Object> map,String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		String invitationCode = (String)map.get("invitationCode");
		if (!StrUtils.isEmpty(invitationCode.trim())) {
			return tevglTchClassroomService.listClassroom(map, traineeInfo.getTraineeId(), null);
		}else{
			return R.ok();
		}
	}
	
	/**
	 * <p>加入课堂</p>  
	 * @author znn
	 * @data 2020年6月10日	
	 * @param ctId 必传参数
	 * @param token
	 * @return
	 */
	@PostMapping("/joinTheClassroom")
	public R joinTheClassroom(String ctId, String token, String invitationCode, String createTime) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("ctId", ctId);
		map.put("invitationCode", invitationCode);
		map.put("createTime", createTime);
		return tevglTchClassroomTraineeService.joinTheClassroom(map, traineeInfo.getTraineeId());
		
	}
	
	/**
	 * <p>获取所有课堂类型列表</p>  
	 * @author znn
	 * @data 2020年6月13日	
	 * @param param
	 * @param token
	 * @return
	 */
	@GetMapping("/getAllBookMajorList")
	public R getAllBookMajorList(@RequestParam Map<String, Object> param,String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		param.put("state", "Y");
		return R.ok().put(Constant.R_DATA, tevglBookMajorService.selectListMapByMapForWeb(param));
		
	}
	/**
	 * <p>根据职业路径 获取所有课程列表</p>  
	 * @author znn
	 * @data 2020年6月16日	
	 * @param param
	 * @param token
	 * @return
	 */
	@GetMapping("/getAllBookSubjectList")
	public R getAllBookSubjectList(@RequestParam Map<String, Object> param,String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		param.put("state", "Y");
		return tevglBookSubjectService.listSelectSubject(param); 
		
	}
	/**
	 * <p>获取所有班级列表</p>  
	 * @author znn
	 * @data 2020年6月13日	
	 * @param params
	 * @param token
	 * @return
	 */
	@GetMapping("/getAllClassList")
	public R getAllClassList(@RequestParam Map<String, Object> params, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassService.queryClassListData(params, traineeInfo.getTraineeId());
		
	}
	
	/**
	 * <p>图片上传</p>  
	 * @author huj
	 * @data 2019年8月20日	
	 * @param picture
	 * @return
	 */
	@PostMapping("/uploadPic")
	public R uploadPic(@RequestPart(value = "file", required = false) MultipartFile picture) {
		String type = "14"; // 课堂封面
		return uploadFileUtils.uploadImage(picture, null, type, 0, 0);
	}
	
	/**
	 * 查看课堂信息
	 * @param ctId 课堂主键
	 * @return
	 */
	@GetMapping("/viewClassroomInfo")
	public R viewClassroomInfo(String ctId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomService.viewClassroomBaseInfo(ctId, traineeInfo.getTraineeId());
	}

	/**
	 * 将某学员设为此课堂助教
	 * @param token
	 * @param ctId 课堂id
	 * @param traineeId 学员id
	 * @return
	 */
	@PostMapping("/setTraineeToTeachingAssistant")
	public R setTraineeToTeachingAssistant(String token, String ctId, String traineeId) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ctId", ctId);
		map.put("traineeId", traineeId);
		map.put("loginUserId", traineeInfo.getTraineeId());
		return tevglTchClassroomService.setTraineeToTeachingAssistant(map);
	}
	
	/**
	 * 收藏课堂
	 * @param ctId
	 * @param token
	 * @return
	 */
	@GetMapping("/collect")
	public R collect(String ctId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomService.collect(ctId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 取消收藏
	 * @param ctId
	 * @param token
	 * @return
	 */
	@GetMapping("/cancelCollect")
	public R cancelCollect(String ctId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomService.cancelCollect(ctId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 课堂置顶
	 * @param ctId
	 * @param value
	 * @param token
	 * @return
	 */
	@PostMapping("/setTop")
	public R setTop(String ctId, String value, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		if ("Y".equals(value)) {
			return R.error("接口未实现");
		} else {
			//return tevglTchClassroomTopService.cancelTop(ctId, traineeInfo.getTraineeId());
			return R.error("接口未实现");
		}
	}
	
	/**
	 * 删除课堂
	 * @param ctId
	 * @param token
	 * @return
	 */
	@PostMapping("/deleteClassroom")
	public R deleteClassroom(String ctId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomService.deleteClassroom(ctId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 开始课堂
	 * @param ctId
	 * @param token
	 * @return
	 */
	@RequestMapping("/start")
	public R start(String ctId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomService.start(ctId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 结束课堂
	 * @param ctId
	 * @param token
	 * @return
	 */
	@RequestMapping("/end")
	public R end(String ctId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomService.end(ctId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 查询权限
	 * @param ctId
	 * @param token
	 * @return
	 */
	@PostMapping("/queryPermissionList")
	public R queryPermissionList(String ctId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTchClassroomRoleprivilegeService.queryPermissionList(ctId, traineeInfo.getTraineeId());
	}
	
}
