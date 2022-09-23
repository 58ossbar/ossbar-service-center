package com.ossbar.modules.wx.activity.controller;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbUploadUtils;
import com.ossbar.modules.common.DictService;
import com.ossbar.modules.evgl.activity.api.TevglActivitySigninInfoService;
import com.ossbar.modules.evgl.activity.api.TevglActivitySigninTraineeService;
import com.ossbar.modules.evgl.activity.domain.TevglActivitySigninInfo;
import com.ossbar.modules.evgl.activity.domain.TevglActivitySigninTrainee;
import com.ossbar.modules.evgl.medu.api.TmeduApiTokenService;
import com.ossbar.modules.evgl.medu.domain.TmeduApiToken;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/wx/activity-api/signin")
public class ActivitySigninController {

	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TmeduApiTokenService tmeduApiTokenService;
	@Reference(version = "1.0.0")
	private TevglActivitySigninInfoService tevglActivitySigninInfoService;
	@Reference(version = "1.0.0")
	private TevglActivitySigninTraineeService tevglActivitySigninTraineeService;
	@Autowired
	private CbUploadUtils cbUploadUtils;
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
	 * 上传
	 * @param file
	 * @return
	 */
	@PostMapping("/uploads")
	public R uploads(@RequestPart(name="file", required = true)MultipartFile file) {
		return cbUploadUtils.uploads(file, "18", null, null);
	}
	
	/**
	 * 保存一个签到活动的基础数据
	 * @param tevglActivitySigninInfo
	 * @param pkgId
	 * @param token
	 * @return
	 */
	@PostMapping("/saveSigninInfo")
	public R saveSigninInfo(@RequestBody TevglActivitySigninInfo tevglActivitySigninInfo, String pkgId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		if (StrUtils.isEmpty(tevglActivitySigninInfo.getActivityId())) {
			return tevglActivitySigninInfoService.saveSigninInfo(tevglActivitySigninInfo, pkgId, traineeInfo.getTraineeId());	
		} else {
			return tevglActivitySigninInfoService.updateSigninInfo(tevglActivitySigninInfo, pkgId, traineeInfo.getTraineeId());
		}
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
	 * @param params
	 * @param token
	 * @return
	 */
	@GetMapping("/queryTraineeList")
	public R queryTraineeList(@RequestParam Map<String, Object> params, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglActivitySigninTraineeService.queryTraineeList(params, traineeInfo.getTraineeId());
	}
	
	/**
	 * 教师批量设置学员签到状态
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/setTraineeSignState")
	public R setTraineeSignState(@RequestBody JSONObject jsonObject) {
		String token = jsonObject.getString("token");
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglActivitySigninTraineeService.setTraineeSignState(jsonObject, traineeInfo.getTraineeId());
	}
	
	/**
	 * 签到（拍照时所用）
	 * @param file 拍照时，需传的媒体文件
	 * @param activityId 必传参数，签到活动主键
	 * @param signType 签到类型
	 * @param area 签到具体位置
	 * @param areaTitle 
	 * @param number 手势签到时的数字顺序
	 * @param token
	 * @return
	 */
	@RequestMapping("signIn")
	public R signIn(@RequestPart(value = "file", required = false) MultipartFile file,
			String activityId, String ctId, String signType, String area, String areaTitle, 
			String number, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		TevglActivitySigninTrainee tevglActivitySigninTrainee = new TevglActivitySigninTrainee();
		tevglActivitySigninTrainee.setCtId(ctId);
		tevglActivitySigninTrainee.setActivityId(activityId);
		tevglActivitySigninTrainee.setArea(area);
		tevglActivitySigninTrainee.setAreaTitle(areaTitle);
		tevglActivitySigninTrainee.setSignType(signType);
		tevglActivitySigninTrainee.setNumber(number);
		// 为普通签到时，需上传图片
		if ("1".equals(signType)) {
			if (file == null || file.isEmpty()) {
				return R.error("请拍照签到");
			}
			R r = cbUploadUtils.uploads(file, "18", null, null);
			if (!r.get("code").equals(0)) {
				return r;
			}
			@SuppressWarnings("unchecked")
			Map<String, Object> object = (Map<String, Object>) r.get(Constant.R_DATA);
			String fileName = (String) object.get("fileName");
			String attachId = (String) object.get("attachId");
			tevglActivitySigninTrainee.setFile(fileName);
			tevglActivitySigninTrainee.setAttachId(attachId);
		}
		return tevglActivitySigninTraineeService.signIn(tevglActivitySigninTrainee, traineeInfo.getTraineeId());
	}
	
	/**
	 * 学员签到（目前使用的这个）
	 * @param tevglActivitySigninTrainee
	 * @param token
	 * @return
	 */
	@RequestMapping("signIno")
	public R signIn2(@RequestBody TevglActivitySigninTrainee tevglActivitySigninTrainee, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglActivitySigninTraineeService.signIn(tevglActivitySigninTrainee, traineeInfo.getTraineeId());
	}
	
	
	/**
	 * 查看学员的签到信息
	 * @param ctId
	 * @param activityId
	 * @param token
	 * @return
	 */
	@GetMapping("/viewTraineeSignInfo")
	public R viewTraineeSignInfo(String ctId, String activityId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglActivitySigninTraineeService.viewTraineeSignInfo(ctId, activityId, traineeInfo.getTraineeId());
	}
}
