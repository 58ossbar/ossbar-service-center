package com.ossbar.modules.evgl.web.controller.my;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.CbUploadUtils;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.utils.tool.StrUtils;

/**
 * <p>个人中心</p>
 * <p>Title: NewsController.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: 湖南创蓝信息科技有限公司</p> 
 * @author huj
 * @date 2019年11月16日
 */

@RestController
@RequestMapping("personalcenter-api")
public class PersonalCenterController {

	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Autowired
	private CbUploadUtils uploadUtils;
	@Reference(version = "1.0.0")
	private TsysAttachService tsysAttachService;
	
	/**
	 * 个人信息
	 * @param request
	 * @return
	 */
	@GetMapping("/viewTraineeInfo")
	@CheckSession
	public R viewTraineeInfo(HttpServletRequest request) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglTraineeInfoService.viewTraineeInfo(traineeInfo.getTraineeId());
	}
	
	/**
	 * 保存个人信息
	 * @param request
	 * @param file
	 * @param map
	 * @return
	 */
	@PostMapping("/saveInfo")
	@CheckSession
	public R saveInfo(HttpServletRequest request, @RequestPart(name="file", required = false) MultipartFile file, 
			@RequestParam Map<String, Object> map) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		try {
			String teacherName = (String) map.get("teacherName");
			String traineeId = traineeInfo.getTraineeId();
			String attachId = "";
			String fileName = "";
			if (file != null && !file.isEmpty()) {
				// 头像上传
				R r = uploadUtils.uploads(file, "16", null, "images");
				Integer code = (Integer) r.get("code");
				if (code != 0) {
					return r;
				}
				@SuppressWarnings("unchecked")
				Map<String, Object> obj = (Map<String, Object>) r.get("data");
				attachId = (String) obj.get("attachId");
				fileName = (String) obj.get("fileName");
			}
			// 实例化对象并填充信息
			Map<String, Object> traineeMapInfo = new HashMap<String, Object>();
			traineeMapInfo.put("traineeId", traineeId);
			traineeMapInfo.put("traineeName", teacherName);
			if (StrUtils.isNotEmpty(fileName)) {
				traineeMapInfo.put("traineePic", fileName);
			}
			//tevglTchTeacherService.updateTeacherInfo(teacherInfo);
			// 如果上传了资源文件
			if (StrUtils.isNotEmpty(attachId)) {
				tsysAttachService.updateAttach(attachId, traineeId, "0", "7");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("保存失败");
		}
		return R.ok("保存成功");
	}
	
}
