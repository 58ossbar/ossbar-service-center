package com.ossbar.modules.wx.trainee.controller;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbUploadUtils;
import com.ossbar.modules.common.DictService;
import com.ossbar.modules.evgl.medu.api.TmeduApiTokenService;
import com.ossbar.modules.evgl.medu.domain.TmeduApiToken;
import com.ossbar.modules.evgl.tch.api.TevglTchTeacherService;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/api/trainee")
@Api(value="学员相关服务接口",tags= {"学员相关服务接口"})
public class ApiTraineeController {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TmeduApiTokenService tmeduApiTokenService;
	@Autowired
	private CbUploadUtils cbUploadUtils;
	@Reference(version = "1.0.0")
	private DictService dictService;
	@Reference(version = "1.0.0")
	private TevglTchTeacherService tevglTchTeacherService;
	

	/**
	 * 图片上传
	 * @param file
	 * @return
	 */
	@PostMapping("/uploadPic")
	public R uploadPic(@RequestPart(value = "file", required = false) MultipartFile file) {
		String type = "16"; // 学员头像上传目录
		return cbUploadUtils.uploads(file, type, null, null);
	}
	
	/**
	 * 从字典获取用户类型（身份），供用户自己下拉选择身份
	 * @return
	 */
	@GetMapping("/listIdentity")
	public R listIdentity() {
		List<Map<String,Object>> dictList = dictService.getDictList("identity");
		return R.ok().put(Constant.R_DATA, dictList);
	}
	
	/**
	 * 性别
	 * @return
	 */
	@GetMapping("/listSex")
	public R listSex() {
		List<Map<String,Object>> dictList = dictService.getDictList("sex");
		return R.ok().put(Constant.R_DATA, dictList);
	}
	
	/**
	 * 查询学员信息
	 * @param token
	 * @return
	 */
	@PostMapping("/getTraineeInfo")
	public R getTraineeInfo(String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		boolean isTeacher = false;
		// 是否拥有教师账号
		//TevglTchTeacher teacher = tevglTchTeacherService.selectObjectById(traineeInfo.getTraineeId());
		TevglTchTeacher teacher = tevglTchTeacherService.selectObjectByTraineeId(traineeInfo.getTraineeId());
		if (teacher != null) {
			if ("Y".equals(teacher.getState())) {
				isTeacher = true;
			} else {
				log.debug("教师账号已无效[" + traineeInfo.getTraineeId() + "]");
			}
		}
		log.debug("当前用户["+traineeInfo.getTraineeId()+"]是否为教师：" + isTeacher);
		if (isTeacher) {
			return tevglTraineeInfoService.viewTraineeInfoForManagementPanel(traineeInfo.getTraineeId()).put("isTeacher", isTeacher);
		} else {
			return tevglTraineeInfoService.viewTraineeInfo(traineeInfo.getTraineeId()).put("isTeacher", isTeacher);
		}
	}
	
	/**
	 * 教师查看自己的信息
	 * @param token
	 * @return
	 */
	/*@GetMapping("/viewTchInfo")
	public R viewTchInfo(String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
			return R.error(401, "Invalid token");
		}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglTraineeInfoService.viewTraineeInfoForManagementPanel(traineeInfo.getTraineeId());
	}*/
	
	/**
	 * 保存信息
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/saveTraineeInfo")
	public R saveTraineeInfo(@RequestBody JSONObject jsonObject) {
		String token = jsonObject.getString("token");
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		// 学员可修改的数据有哪些
		String traineeName = jsonObject.getString("traineeName"); // 名称
		String nickName = jsonObject.getString("nickName"); // 名称
		String traineeSex = jsonObject.getString("traineeSex"); // 性别
		String traineeSchool = jsonObject.getString("traineeSchool"); // 就读院校
		String jobNumber = jsonObject.getString("jobNumber"); // 学号
		String email = jsonObject.getString("email"); // 邮箱
		String attachId = jsonObject.getString("attachId"); // 附件id
		String fileName = jsonObject.getString("fileName"); // 头像
		// 填充信息
		TevglTraineeInfo t = new TevglTraineeInfo();
		t.setTraineeId(traineeInfo.getTraineeId());
		t.setTraineeName(traineeName);
		t.setNickName(nickName);
		t.setTraineeSex(traineeSex);
		t.setTraineeSchool(traineeSchool);
		t.setJobNumber(jobNumber);
		t.setEmail(email);
		t.setAttachId(StrUtils.isEmpty(attachId) ? null : attachId);
		t.setTraineePic(StrUtils.isEmpty(fileName) ? null : fileName);
		tevglTraineeInfoService.update(t);
		// 修改教师账号信息
		//TevglTchTeacher tch = new TevglTchTeacher();
		
		return R.ok("保存成功");
	}
	
	/**
	 * 修改信息-为教师所用
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/saveInfo")
	public R saveInfo(@RequestBody JSONObject jsonObject) {
		String token = jsonObject.getString("token");
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		String traineeName = jsonObject.getString("traineeName"); // 名称
		String nickName = jsonObject.getString("nickName"); // 名称
		String traineeSex = jsonObject.getString("traineeSex"); // 性别
		String jobNumber = jsonObject.getString("jobNumber"); // 工号
		String teacherErtificateNumber = jsonObject.getString("teacherErtificateNumber"); // 教师资格证
		String email = jsonObject.getString("email"); // 邮箱
		String attachId = jsonObject.getString("attachId"); // 附件id
		String fileName = jsonObject.getString("fileName"); // 头像
		// 填充信息
		TevglTraineeInfo t = new TevglTraineeInfo();
		t.setTraineeId(traineeInfo.getTraineeId());
		t.setTraineeName(traineeName);
		t.setNickName(nickName);
		t.setTraineeSex(traineeSex);
		t.setJobNumber(jobNumber);
		t.setEmail(email);
		t.setAttachId(StrUtils.isEmpty(attachId) ? null : attachId);
		t.setTraineePic(StrUtils.isEmpty(fileName) ? null : fileName);
		t.setUpdateUserId(traineeInfo.getTraineeId());
		tevglTraineeInfoService.update(t);
		// 更新对应的教师账号信息
		TevglTchTeacher tevglTchTeacher = new TevglTchTeacher();
		tevglTchTeacher.setTeacherId(traineeInfo.getTraineeId());
		tevglTchTeacher.setTeacherName(traineeName);
		tevglTchTeacher.setJobNumber(jobNumber);
		tevglTchTeacher.setTeacherErtificateNumber(teacherErtificateNumber);
		tevglTchTeacher.setSex(traineeSex);
		tevglTchTeacherService.update(tevglTchTeacher);
		return tevglTraineeInfoService.update(t);
	}
	
}
