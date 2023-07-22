package com.ossbar.modules.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.core.common.utils.UploadFileUtils;
import com.ossbar.utils.tool.StrUtils;

/**
 * 创蓝图片上传组件文件上传处理类
 * @author zhuq
 *
 */
@RestController
@RequestMapping("/api/cbupload")
public class UploaderController {

	@Autowired
	private UploadFileUtils uploadFileUtils;
	/**
	 * <p>上传图片</p>
	 * @author huj
	 * @data 2019年7月18日
	 * @param picture
	 * @return
	 */
	@PostMapping("/uploadPic")
	public R uploadPic(@RequestPart(value = "file", required = false) MultipartFile picture, @RequestParam("type") String type) {
		if(StrUtils.isEmpty(type)) {
			type = "0";
		}
		return uploadFileUtils.uploadImage(picture, null, type, 0, 0);
	}
	
	/**
	 * <p>上传音频</p>
	 * @param file
	 * @param type
	 * @return
	 */
	@PostMapping("/uploadAudioFile")
	public R uploadAudioFileTest(@RequestPart MultipartFile file, HttpServletRequest request) {
		String type = request.getParameter("type");
		if(StrUtils.isEmpty(type)) {
			type = "0";
		}
		return uploadFileUtils.uploadAudioFile(file, null, type, null);
	}
}
