package com.ossbar.modules.wx;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.GlobalRoomPermission;
import com.ossbar.modules.evgl.book.api.TevglBookChapterService;
import com.ossbar.modules.evgl.book.api.TevglBookSubjectService;
import com.ossbar.modules.evgl.book.domain.TevglBookChapter;
import com.ossbar.modules.evgl.medu.api.TmeduApiTokenService;
import com.ossbar.modules.evgl.medu.domain.TmeduApiToken;
import com.ossbar.modules.evgl.pkg.api.TevglPkgResService;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomRoleprivilegeService;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomService;
import com.ossbar.modules.evgl.tch.api.TevglTchTeacherService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 课堂--资源
 * @author water
 *
 */
@RestController
@RequestMapping("/wx/resource-api")
public class WxResourceController {

	Logger log = LoggerFactory.getLogger(this.getClass());
	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TmeduApiTokenService tmeduApiTokenService;
	@Reference(version = "1.0.0")
	private TevglBookChapterService tevglBookChapterService;
	@Reference(version = "1.0.0")
	private TevglPkgResService tevglPkgResService;
	@Reference(version = "1.0.0")
	private TevglBookSubjectService tevglBookSubjectService;
	@Reference(version = "1.0.0")
    private TevglTchTeacherService tevglTchTeacherService;
	@Reference(version = "1.0.0")
	private TevglTchClassroomRoleprivilegeService tevglTchClassroomRoleprivilegeService;
	@Reference(version = "1.0.0")
	private TevglTchClassroomService tevglTchClassroomService;

	/**
	 * 获取课程树形数据
	 * @param subjectId
	 * @param chapterName
	 * @param token
	 * @return
	 */
	@GetMapping("/getBookTreeDataNew")
	public R getBookTreeDataNew(String ctId, String pkgId, String subjectId, String chapterName, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		if (StrUtils.isNotEmpty(ctId)) {
			TevglTchClassroom tevglTchClassroom = tevglTchClassroomService.selectObjectById(ctId);
            if (tevglTchClassroom == null) {
                return R.error("课堂不存在了！");
            }
            boolean isCreator = StrUtils.isEmpty(tevglTchClassroom.getReceiverUserId()) && tevglTchClassroom.getCreateUserId().equals(traineeInfo.getTraineeId());
            boolean isTeachingAssistant = StrUtils.isNotEmpty(tevglTchClassroom.getTraineeId()) && traineeInfo.getTraineeId().equals(tevglTchClassroom.getTraineeId());
            boolean isReceiver = StrUtils.isNotEmpty(tevglTchClassroom.getReceiverUserId()) && tevglTchClassroom.getReceiverUserId().equals(traineeInfo.getTraineeId());
            // 如果是助教，校验下是否有设置学员是否可见的权限
            boolean hasSetVisiblePermission = false;
            if (isTeachingAssistant) {
                List<String> permissionList = tevglTchClassroomRoleprivilegeService.queryPermissionListByCtId(ctId);
                if (permissionList != null && permissionList.size() > 0) {
                    hasSetVisiblePermission = permissionList.stream().anyMatch(a -> a.equals(GlobalRoomPermission.ROOM_PERM_SUBJECT_CHAPTERVISIBLE));
                }
            }
            String identity = (isCreator || hasSetVisiblePermission || isReceiver) ? "teacher" : "trainee";
		    return tevglBookSubjectService.getBookForRoomPage(ctId, pkgId, subjectId, chapterName, traineeInfo.getTraineeId(), true, identity);
		} else {
			return R.error("非法操作");
		}
		/*TevglTchTeacher tevglTchTeacher = tevglTchTeacherService.selectObjectByTraineeId(traineeInfo.getTraineeId());
		String identity = tevglTchTeacher == null ? "trainee" : "teacher";
		return tevglBookSubjectService.getBookForRoomPage(ctId, pkgId, subjectId, chapterName, traineeInfo.getTraineeId(), true, identity);*/
	}
	
	/**
	 * 左右滑动查看章节
	 * @param pkgId
	 * @param chapterId
	 * @param token
	 * @return
	 */
	@GetMapping("slide")
	public R slide(String pkgId, String chapterId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglBookChapterService.slide(pkgId, chapterId, traineeInfo.getTraineeId());
	}
	
	/**
	 * <p>通过章节id查看章节内容，获取tab选项</p>  
	 * @author znn
	 * @data 2020年6月15日	
	 * @param chapterId 
	 * @param token
	 * @return
	 */
	@GetMapping("/getChapterInfo")
	public R getChapterInfo(String chapterId,String token, String pkgId, String type) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglBookChapterService.viewChapterInfo(chapterId, traineeInfo.getTraineeId(), pkgId, type); 
		
	}
	
	/**
	 * <p>通过资源resgroupId 获取章节内容</p>  
	 * @author znn
	 * @data 2020年6月16日	
	 * @param chapterId 
	 * @param token
	 * @return
	 */
	@GetMapping("/getChapterContent")
	public R getChapterContent(@RequestParam Map<String, Object> map,String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		return R.ok(tevglPkgResService.viewResInfo(map));
	}
	
	/**
	 * <p>新增章节</p>  
	 * @author znn
	 * @data 2020年6月12日	
	 * @param tevglBookChapter
	 * @param token
	 * @return
	 */
	@PostMapping("/saveChapter")
	public R saveChapter(@RequestBody(required = false) TevglBookChapter tevglBookChapter, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		
		if (StrUtils.isEmpty(tevglBookChapter.getChapterId())) { // 新增
			tevglBookChapter.setCreateUserId(traineeInfo.getTraineeId());
			return tevglBookChapterService.saveChapterInfo(tevglBookChapter, traineeInfo.getTraineeId());
		} else { // 修改
			tevglBookChapter.setUpdateUserId(traineeInfo.getTraineeId());
			//return tevglBookChapterService.update(tevglBookChapter);
			return tevglBookChapterService.updateChapterInfo(tevglBookChapter, traineeInfo.getTraineeId());
		}
	}
	
	/**
	 * 删除章节
	 * @param chapterId
	 * @param token
	 * @return
	 */
	@PostMapping("/deleteChapter")
	public R deleteChapter(String pkgId, String chapterId, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglBookChapterService.removeV2(pkgId, chapterId, traineeInfo.getTraineeId());
	}
	
	/**
	 * 批量设置章节学员是否可见
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/setTraineesVisible")
	public R setTraineesVisible(@RequestBody JSONObject jsonObject) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(jsonObject.getString("token"));
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglBookChapterService.batchSetChapterIsTraineesVisible(jsonObject, traineeInfo.getTraineeId());
	}
	
	/**
	 * 设置学员是否可见标签
	 * @param request
	 * @param ctId
	 * @param pkgId
	 * @param resgroupId
	 * @param isTraineesVisible
	 * @return
	 */
	@PostMapping("/setTraineesVisibleResgroup")
	public R setTraineesVisibleResgroup(HttpServletRequest request, String ctId, String pkgId, String resgroupId, String isTraineesVisible, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglBookChapterService.setTraineesVisibleResgroup(ctId, pkgId, resgroupId, isTraineesVisible, traineeInfo.getTraineeId());
	}

	/**
	 * 批量设置
	 * @param request
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/setTraineesVisibleResgroupBatch")
	public R setTraineesVisibleResgroup(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(jsonObject.getString("token"));
		if(te == null){
    		return R.error(401, "Invalid token");
    	}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		return tevglBookChapterService.setTraineesVisibleResgroupBatch(jsonObject, traineeInfo.getTraineeId());
	}
	
}
