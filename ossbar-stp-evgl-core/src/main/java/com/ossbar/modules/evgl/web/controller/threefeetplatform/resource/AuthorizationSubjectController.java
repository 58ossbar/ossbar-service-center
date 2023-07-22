package com.ossbar.modules.evgl.web.controller.threefeetplatform.resource;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.book.api.TevglBookChapterService;
import com.ossbar.modules.evgl.book.api.TevglBookSubjectService;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.pkg.api.TevglBookpkgTeamDetailService;
import com.ossbar.modules.evgl.pkg.api.TevglBookpkgTeamService;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;

/**
 * 课程与章节的授权相关接口
 * @author huj
 *
 */
@RestController
@RequestMapping("/resourceCenter-api/subject")
public class AuthorizationSubjectController {

	@Reference(version = "1.0.0")
	private TevglBookpkgTeamDetailService tevglBookpkgTeamDetailService;
	@Reference(version = "1.0.0")
	private TevglBookpkgTeamService tevglBookpkgTeamService;
	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TevglBookSubjectService tevglBookSubjectService;
	@Reference(version = "1.0.0")
	private TevglBookChapterService tevglBookChapterService;
	
	/**
	 * 学员列表
	 * @param params
	 * @return
	 */
	@GetMapping("/listTrainee")
	public R listTrainee(@RequestParam Map<String, Object> params) {
		return tevglTraineeInfoService.listTrainee(params);
	}
	
	/**
	 * 根据单个或多个用户查询章节共同权限
	 * @param request
	 * @param jsonObject {'pkgId':'', 'subjectId':'', traineeIds:[]}
	 * @return
	 */
	@PostMapping("/queryAuthorization")
	@CheckSession
	public R queryAuthorization(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglBookpkgTeamService.queryAuthorization(jsonObject, traineeInfo.getTraineeId());
	}
	
	/**
	 * 保存授权信息
	 * @param request
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/saveAuthInfo")
	@CheckSession
	public R saveAuthInfo(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		//return tevglBookpkgTeamDetailService.authorizationAlone(jsonObject, traineeInfo.getTraineeId());
		return tevglBookpkgTeamService.authorization(jsonObject, traineeInfo.getTraineeId());
	}
	
	/**
	 * 标识章节赋予了谁
	 * @param request
	 * @param subjectId
	 * @return
	 */
	@PostMapping("/getChapterTreeWithTeacherName")
	@CheckSession
	public R getChapterTreeWithTeacherName(HttpServletRequest request, String subjectId, String pkgId) {
		TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
		if (traineeInfo == null) {
			return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
		}
		return tevglBookChapterService.getChapterTreeWithTeacherName(subjectId, pkgId, traineeInfo.getTraineeId());
	}
	
}
