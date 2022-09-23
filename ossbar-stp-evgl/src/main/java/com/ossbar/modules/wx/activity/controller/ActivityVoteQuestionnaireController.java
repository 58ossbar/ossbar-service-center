package com.ossbar.modules.wx.activity.controller;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbUploadUtils;
import com.ossbar.modules.common.DictService;
import com.ossbar.modules.evgl.activity.api.TevglActivityVoteQuestionnaireService;
import com.ossbar.modules.evgl.activity.api.TevglActivityVoteQuestionnaireTraineeAnswerFileService;
import com.ossbar.modules.evgl.activity.api.TevglActivityVoteQuestionnaireTraineeAnswerService;
import com.ossbar.modules.evgl.activity.domain.TevglActivityVoteQuestionnaireTraineeAnswerFile;
import com.ossbar.modules.evgl.medu.api.TmeduApiTokenService;
import com.ossbar.modules.evgl.medu.domain.TmeduApiToken;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.ossbar.utils.tool.VideoUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 活动-投票/问卷
 * @author huj
 *
 */
@RestController
@RequestMapping("/wx/activity-api/voteQuestionnaire")
public class ActivityVoteQuestionnaireController {

	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TmeduApiTokenService tmeduApiTokenService;
	@Reference(version = "1.0.0")
	private TevglActivityVoteQuestionnaireService tevglActivityVoteQuestionnaireService;
	@Reference(version = "1.0.0")
	private DictService dictService;
	@Reference(version = "1.0.0")
	private TevglActivityVoteQuestionnaireTraineeAnswerService tevglActivityVoteQuestionnaireTraineeAnswerService;
	@Reference(version = "1.0.0")
	private TevglActivityVoteQuestionnaireTraineeAnswerFileService tevglActivityVoteQuestionnaireTraineeAnswerFileService;
	@Autowired
	private CbUploadUtils cbUploadUtils;
	@Autowired
	private UploadFileUtils uploadFileUtils;

	// 文件上传路径
	@Value("${com.creatorblue.file-upload-path}")
    private String uploadPath;
	// 文件显示路径
	@Value("${com.creatorblue.file-access-path}")
	private String accessPath;
	// 一些限制
	private final List<String> imgSuffixList = Arrays.asList(".JPEG", ".PNG", ".JPG", ".GIF", ".BMP");
	private final List<String> videoSuffixList = Arrays.asList(".MP4", ".FLV", ".RMVB", ".AVI", ".WMV", ".MOV", ".QUICKTIME");
	private final List<String> audioSuffixList = Arrays.asList(".MP3", ".WAV", ".WMA", ".OGG", ".FLAC", ".AAC", ".M4A");
	private final List<String> blackSuffixList = Arrays.asList(".EXE", ".SH", ".BAT");
	
	/**
	 * 附件上传
	 * @param file
	 * @return
	 */
	@PostMapping("/uploads")
	public R uploads(@RequestPart(name = "file", required = false) MultipartFile file) {
		return cbUploadUtils.uploads(file, "20", null, null);
	}
	
	@PostMapping("/upload")
	public R upload(String token, @RequestPart(name = "file", required = false) MultipartFile multipartFile) throws Exception {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		TevglActivityVoteQuestionnaireTraineeAnswerFile attach = new TevglActivityVoteQuestionnaireTraineeAnswerFile();
		String originalFilename = multipartFile.getOriginalFilename();
		int pos = originalFilename.lastIndexOf("."); // 源文件名
		String uuid = UUID.randomUUID().toString();
		String extension = originalFilename.substring(pos); // 拓展名
		if(blackSuffixList.contains(extension.toUpperCase())) {
			return R.error("文件格式不允许上传！");
		}
		String fileType = "file"; // 文件类型
		if (imgSuffixList.contains(extension.toUpperCase())) {
			fileType = "image";
			if (multipartFile.getSize() / 1024 / 1024 > 2) {
                return R.error("图片大小不能超过2MB 请自行调整");
            }
		} else if(videoSuffixList.contains(extension.toUpperCase())) {
			fileType = "video";
			if (multipartFile.getSize() / 1024 / 1024 > 10) {
                return R.error("视频大小不能超过10MB 请自行调整");
            }
		} else if(audioSuffixList.contains(extension.toUpperCase())) {
			fileType = "audio";
			if (multipartFile.getSize() / 1024 / 1024 > 10) {
                return R.error("音频大小不能超过10MB 请自行调整");
            }
		}
		File path = new File(uploadPath + uploadFileUtils.getPathByParaNo("20"));
		if(!path.exists()) {
			path.mkdirs();
		}
		String savePath = uploadPath + uploadFileUtils.getPathByParaNo("20") + "/" + uuid + extension;
		File file = new File(savePath);
		multipartFile.transferTo(file);
		if ("video".equals(fileType) || "voice".equals(fileType)) {
			//attach.setDurationTime(VideoUtils.getDuration(file));
		}
		if ("video".equals(fileType)) {
			String firstCapture = VideoUtils.getVideoImageByFrame(file, uploadPath + uploadFileUtils.getPathByParaNo("21"), fileType + "/" + uuid + ".jpg");
			attach.setFirstCaptureAccessUrl(accessPath + uploadFileUtils.getPathByParaNo("20") + "/" + firstCapture);
			attach.setFirstCaptureSavePath(uploadPath + uploadFileUtils.getPathByParaNo("20") + "/" + firstCapture);
		}
		attach.setFileId(Identities.uuid());
		attach.setUrl(uuid + extension);
		attach.setFileType(fileType);
		attach.setFileSuffix(extension);
		attach.setFileSize(multipartFile.getSize());
		attach.setOriginalFilename(originalFilename);
		attach.setState("Y");
		//attach.setSortNum(i);
		attach.setCreateTime(DateUtils.getNowTimeStamp());
		attach.setTraineeId(traineeInfo.getTraineeId());
		attach.setCreateUserId(traineeInfo.getTraineeId());
		tevglActivityVoteQuestionnaireTraineeAnswerFileService.save(attach);
		return R.ok().put("attachId", attach.getFileId());
	}
	
	/**
	 * 保存投票问卷
	 * @param request
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/saveVoteQuestionnaireInfo")
	public R saveVoteQuestionnaireInfo(HttpServletRequest request, @RequestBody JSONObject jsonObject, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		String activityId = jsonObject.getString("activityId");
		if (StrUtils.isEmpty(activityId)) {
			return tevglActivityVoteQuestionnaireService.saveVoteQuestionnaireInfo(jsonObject, traineeInfo.getTraineeId()); 
		} else {
			return tevglActivityVoteQuestionnaireService.updateVoteQuestionnaireInfo(jsonObject, traineeInfo.getTraineeId());
		}
	}
	
	/**
	 * 活动题目类型
	 * @return
	 */
	@GetMapping("/listQuestionType")
	public R listQuestionType() {
		return R.ok().put(Constant.R_DATA, dictService.getDictList("activityQuestionType"));
	}
	
	/**
	 * 查看学员针对投票/问卷的作答内容（个人结果）
	 * @param ctId 课堂
	 * @param activityId 活动
	 * @param traineeId 学员
	 * @param token
	 * @return
	 */
	@PostMapping("/viewTraineeAnswerData")
	public R viewTraineeAnswerData(String ctId, String activityId, String traineeId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglActivityVoteQuestionnaireService.viewTraineeAnswerData(ctId, activityId, traineeId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 查看学员针对投票/问卷的作答内容（总体结果）
	 * @param
	 * @param
	 * @param token
	 */
	@GetMapping("viewTraineeAnswerListData")
	public R viewTraineeAnswerListData(@RequestParam Map<String, Object> params, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglActivityVoteQuestionnaireService.viewTraineeAnswerListData(params, traineeInfo.getTraineeId());
	}
	
	/**
	 * 保存学员提交投票/问卷的作答内容
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/saveTraineeCommitAnswerContent")
	public R saveTraineeCommitAnswerContent(@RequestBody JSONObject jsonObject) {
		String token = jsonObject.getString("token");
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglActivityVoteQuestionnaireTraineeAnswerService.saveTraineeCommitAnswerContent(jsonObject, traineeInfo.getTraineeId());
	}
	
}
