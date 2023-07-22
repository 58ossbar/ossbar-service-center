package com.ossbar.modules.evgl.web.controller.cloudpan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.DictService;
import com.ossbar.modules.evgl.cloudpan.api.TcloudPanFileService;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;


/**
 * 云盘文件
 * @author huj
 *
 */
@RestController
@RequestMapping("/cloud-api/file")
public class CloudPanFileController {

	@Reference(version = "1.0.0")
	private DictService dictService;
	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TcloudPanFileService tcloudPanFileService;
	
	/**
	 * 上传
	 * @param request
	 * @param dirId
	 * @return
	 */
	@PostMapping("/uploadFile")
	@CheckSession
	public R uploadFile(HttpServletRequest request, String dirId, String pkgId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tcloudPanFileService.uploadFiles(request, dirId, traineeInfo.getTraineeId(), pkgId);
	}
	
	/**
	 * 删除
	 * @param request
	 * @param dirId
	 * @return
	 */
	/*@PostMapping("/deleteFile")
	@CheckSession
	public R deleteFile(HttpServletRequest request, @RequestBody List<String> list) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tcloudPanFileService.deleteFileBatch(request.getParameter("pkgId"), list, traineeInfo.getTraineeId());
	}*/
	
	/**
	 * 下载
	 * @param request
	 * @param response
	 * @param fileId
	 * @return
	 */
	@GetMapping("/downloadFile")
	//@CheckSession
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String fileId, String pkgId) {
		tcloudPanFileService.downloadFile(response, fileId, pkgId);
	}
}
