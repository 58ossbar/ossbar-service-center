package com.ossbar.modules.evgl.web.controller.empirical;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.empirical.api.TevglEmpiricalLogAudioService;
import com.ossbar.modules.evgl.empirical.api.TevglEmpiricalLogChapterService;
import com.ossbar.modules.evgl.empirical.api.TevglEmpiricalLogVideoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;

@RestController
@RequestMapping("/classroom-api/empirical/log")
public class EmpiricalLogController {
	
	@Reference(version = "1.0.0")
	private TevglEmpiricalLogChapterService tevglEmpiricalLogChapterService;
	@Reference(version = "1.0.0")
	private TevglEmpiricalLogVideoService tevglEmpiricalLogVideoService;
	@Reference(version = "1.0.0")
	private TevglEmpiricalLogAudioService tevglEmpiricalLogAudioService;
	
	/**
	 * 查看课程内容，滑动到最下面时，触发此接口，记录经验值
	 * @param request
	 * @param ctId
	 * @param pkgId
	 * @param subjectId
	 * @param chapterId
	 * @param resId
	 * @return
	 */
	@PostMapping("/viewChapter")
	@CheckSession
	public R viewChapter(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglEmpiricalLogChapterService.viewChapter(jsonObject, traineeInfo.getTraineeId());
	}

	/**
	 * 查看内容中的视频，触发此接口，记录经验值
	 * @param request
	 * @param ctId
	 * @param pkgId
	 * @param subjectId
	 * @param chapterId
	 * @param videoId
	 * @return
	 */
	@PostMapping("/viewVideo")
	@CheckSession
	public R viewVideo(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		String ctId = jsonObject.getString("ctId");
		String pkgId = jsonObject.getString("pkgId");
		String subjectId = jsonObject.getString("subjectId");
		String chapterId = jsonObject.getString("chapterId");
		String videoId = jsonObject.getString("videoId");
		return tevglEmpiricalLogVideoService.viewVideo(ctId, pkgId, subjectId, chapterId, videoId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 
	 * @param request
	 * @param ctId
	 * @param pkgId
	 * @param subjectId
	 * @param chapterId
	 * @param audioId
	 * @return
	 */
	@PostMapping("/viewAudio")
	@CheckSession
	public R viewAudio(HttpServletRequest request, @RequestBody JSONObject jsonObject/*String ctId, String pkgId, String subjectId, String chapterId, String audioId*/) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		String ctId = jsonObject.getString("ctId");
		String pkgId = jsonObject.getString("pkgId");
		String subjectId = jsonObject.getString("subjectId");
		String chapterId = jsonObject.getString("chapterId");
		String audioId = jsonObject.getString("audioId");
		return tevglEmpiricalLogAudioService.viewAudio(ctId, pkgId, subjectId, chapterId, audioId, traineeInfo.getTraineeId());
	}
}
