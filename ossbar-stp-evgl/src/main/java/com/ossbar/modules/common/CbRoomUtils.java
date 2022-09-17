package com.ossbar.modules.common;

import com.ossbar.modules.evgl.activity.persistence.TevglActivitySigninInfoMapper;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityVoteQuestionnaireMapper;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityVoteQuestionnaireQuestionMapper;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityVoteQuestionnaireQuestionOptionMapper;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgActivityRelation;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgActivityRelationMapper;
import com.ossbar.modules.evgl.tch.domain.*;
import com.ossbar.modules.evgl.tch.persistence.*;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 课堂的可用的一些公共方法
 * @author huj
 *
 */
@Component
@RefreshScope
public class CbRoomUtils {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private TevglTchClassroomTraineeMapper tevglTchClassroomTraineeMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private TevglTchClasstraineeMapper tevglTchClasstraineeMapper;
	@Autowired
	private TevglTchClassroomGroupMapper tevglTchClassroomGroupMapper;
	@Autowired
	private TevglTchClassroomGroupMemberMapper tevglTchClassroomGroupMemberMapper;
	@Autowired
	private TevglTchClassroomTraineeCheckMapper tevglTchClassroomTraineeCheckMapper;
	@Autowired
	private TevglPkgActivityRelationMapper tevglPkgActivityRelationMapper;
	@Autowired
	private TevglActivityVoteQuestionnaireMapper tevglActivityVoteQuestionnaireMapper;
	@Autowired
	private TevglActivityVoteQuestionnaireQuestionMapper tevglActivityVoteQuestionnaireQuestionMapper;
	@Autowired
	private TevglActivityVoteQuestionnaireQuestionOptionMapper tevglActivityVoteQuestionnaireQuestionOptionMapper;
	@Autowired
	private TevglTchRoomPereAnswerMapper tevglTchRoomPereAnswerMapper;
	@Autowired
	private TevglTchRoomPereTraineeAnswerMapper tevglTchRoomPereTraineeAnswerMapper;
	@Autowired
	private TevglActivitySigninInfoMapper tevglActivitySigninInfoMapper;
	@Autowired
	private TevglTchClassroomRoleprivilegeMapper tevglTchClassroomRoleprivilegeMapper;

	
	/**
	 * 更新此课堂的学习人数（默认+1）
	 * @param ctId
	 */
	public void plusStudyNum(String ctId) {
		TevglTchClassroom tevglTchClassroom = new TevglTchClassroom();
		tevglTchClassroom.setCtId(ctId);
		tevglTchClassroom.setStudyNum(1);
		tevglTchClassroomMapper.plusNum(tevglTchClassroom);
	}
	
	/**
	 * 更新此课堂的学习人数
	 * @param ctId
	 * @param num
	 */
	public void plusStudyNum(String ctId, int num) {
		TevglTchClassroom tevglTchClassroom = new TevglTchClassroom();
		tevglTchClassroom.setCtId(ctId);
		tevglTchClassroom.setStudyNum(num);
		tevglTchClassroomMapper.plusNum(tevglTchClassroom);
	}
	
	/**
	 * 将此人，与课堂对应的班级，建议关系
	 * @param traineeId 当前登录用户id
	 * @param classId 当前课堂对应的班级id
	 * @param params 查询条件
	 */
	public void doHandleClassTraineeTable(String traineeId, String classId, Map<String, Object> params) {
		if (StrUtils.isEmpty(traineeId) || StrUtils.isEmpty(classId)) {
			return;
		}
		// 先判断是否存值
		params.clear();
		params.put("classId", classId);
		params.put("traineeId", traineeId);
		List<TevglTchClasstrainee> list = tevglTchClasstraineeMapper.selectListByMap(params);
		if (list != null && list.size() > 0) {
			return;
		}
		// 不存在才去添加
		TevglTchClasstrainee t = new TevglTchClasstrainee();
		t.setCtId(Identities.uuid());
		t.setClassId(classId);
		t.setTraineeId(traineeId);
		tevglTchClasstraineeMapper.insert(t);
	}
	
	/**
	 * （物理删除）删除课堂小组与课堂小组成员数据
	 * @param ctId
	 * @param params
	 */
	public void deleteClassroomGroupDatas(String ctId, Map<String, Object> params) {
		if (StrUtils.isEmpty(ctId)) {
			return;
		}
		params.clear();
		params.put("ctId", ctId);
		List<TevglTchClassroomGroup> classroomGroupList = tevglTchClassroomGroupMapper.selectListByMap(params);
		if (classroomGroupList != null && classroomGroupList.size() > 0) {
			// 查找课堂小组成员
			List<String> gpIds = classroomGroupList.stream().map(a -> a.getGpId()).collect(Collectors.toList());
			params.clear();
			params.put("ctId", ctId);
			params.put("gpIds", gpIds);
			List<TevglTchClassroomGroupMember> classroomGroupMemberList = tevglTchClassroomGroupMemberMapper.selectListByMap(params);
			if (classroomGroupMemberList != null && classroomGroupMemberList.size() > 0) {
				List<String> gmIds = classroomGroupMemberList.stream().map(a -> a.getGmId()).collect(Collectors.toList());
				// 先删除课堂成员
				String[] needDeleteGmIdArray = gmIds.stream().toArray(String[]::new);
				if (needDeleteGmIdArray != null && needDeleteGmIdArray.length > 0) {
					tevglTchClassroomGroupMemberMapper.deleteBatch(needDeleteGmIdArray);
				}
			}
			// 再删除课堂小组
			tevglTchClassroomGroupMapper.deleteBatch(gpIds.stream().toArray(String[]::new));
		}
	}
	
	/**
	 * （物理删除）删除课堂成员数据，课堂成员审核数据，以及课堂置顶数据
	 * @param ctId
	 * @param params
	 */
	public void deleteClassroomTraineeDatas(String ctId, Map<String, Object> params) {
		if (StrUtils.isEmpty(ctId)) {
			return;
		}
		params.clear();
		params.put("ctId", ctId);
		/*List<TevglTchClassroomTop> classroomTopList = tevglTchClassroomTopMapper.selectListByMap(params);
		if (classroomTopList != null && classroomTopList.size() > 0) {
			List<String> topIds = classroomTopList.stream().map(a -> a.getTopId()).collect(Collectors.toList());
			tevglTchClassroomTopMapper.deleteBatch(topIds.stream().toArray(String[]::new));
		}*/
		// 删除审核记录
		List<TevglTchClassroomTraineeCheck> classroomTraineeCheckList = tevglTchClassroomTraineeCheckMapper.selectListByMap(params);
		if (classroomTraineeCheckList != null && classroomTraineeCheckList.size() > 0) {
			List<String> tcIds = classroomTraineeCheckList.stream().map(a -> a.getTcId()).collect(Collectors.toList());
			tevglTchClassroomTraineeCheckMapper.deleteBatch(tcIds.stream().toArray(String[]::new));
		}
		// 删除课堂成员记录
		List<TevglTchClassroomTrainee> classroomTraineeList = tevglTchClassroomTraineeMapper.selectListByMap(params);
		if (classroomTraineeList != null && classroomTraineeList.size() > 0) {
			List<String> ids = classroomTraineeList.stream().map(a -> a.getId()).collect(Collectors.toList());
			tevglTchClassroomTraineeMapper.deleteBatch(ids.stream().toArray(String[]::new));
		}
	}
	
	/**
	 * （物理删除）请在调用该方法前，做创建者才能删除的权限判断，删除课堂的所有活动，注意，不能删除课堂教学包对应的来源教学包中的活动
	 * @param classroomPkgId 课堂对应的教学包，表t_evgl_tch_classroom的pkg_id字段值
	 * @param refPkgId 课堂对应的教学包的来源教学包，表t_evgl_tch_classroom的pkg_id字段值，表t_evgl_pkg_info对应的ref_pkg_id的值
	 */
	public void deleteClassroomActivityDatas(String classroomPkgId, String refPkgId) {
		doDeleteClassroomActivityDatas(classroomPkgId, refPkgId, new HashMap<String, Object>());
	}
	
	/**
	 * （物理删除）删除课堂的所有活动，注意，不能删除课堂教学包对应的来源教学包中的活动
	 * @param classroomPkgId 课堂对应的教学包，表t_evgl_tch_classroom的pkg_id字段值
	 * @param refPkgId 课堂对应的教学包的来源教学包，表t_evgl_tch_classroom的pkg_id字段值，表t_evgl_pkg_info对应的ref_pkg_id的值
	 * @param params 查询条件而已
	 */
	public void deleteClassroomActivityDatas(String classroomPkgId, String refPkgId, Map<String, Object> params) {
		doDeleteClassroomActivityDatas(classroomPkgId, refPkgId, params);
	}
	
	/**
	 * （物理删除）删除课堂的所有活动，注意，不能删除课堂教学包对应的来源教学包中的活动
	 * @param classroomPkgId 课堂对应的教学包，表t_evgl_tch_classroom的pkg_id字段值
	 * @param refPkgId 课堂对应的教学包的来源教学包，表t_evgl_tch_classroom的pkg_id字段值，表t_evgl_pkg_info对应的ref_pkg_id的值
	 * @param params 查询条件而已
	 */
	private void doDeleteClassroomActivityDatas(String classroomPkgId, String refPkgId, Map<String, Object> params) {
		if (StrUtils.isEmpty(classroomPkgId) || StrUtils.isEmpty(refPkgId)) {
			return;
		}
		params.clear();
		params.put("pkgId", classroomPkgId);
		List<TevglPkgActivityRelation> pkgActivityRelationList = tevglPkgActivityRelationMapper.selectListByMap(params);
		if (pkgActivityRelationList == null || pkgActivityRelationList.size() == 0) {
			return;
		}
		// 先直接删除活动的关系
		List<String> ids = pkgActivityRelationList.stream().map(a -> a.getPaId()).collect(Collectors.toList());
		tevglPkgActivityRelationMapper.deleteBatch(ids.stream().toArray(String[]::new));
		// 2.查询来源教学包的活动
		params.clear();
		params.put("pkgId", refPkgId);
		List<TevglPkgActivityRelation> fromPkgActivityRelationListFrom = tevglPkgActivityRelationMapper.selectListByMap(params);
		// 3.去重，得到需要物理删除的活动
		List<String> list1 = pkgActivityRelationList.stream().map(a -> a.getActivityId()).collect(Collectors.toList());
		if (fromPkgActivityRelationListFrom != null && fromPkgActivityRelationListFrom.size() > 0) {
			List<String> list2 = fromPkgActivityRelationListFrom.stream().map(a -> a.getActivityId()).collect(Collectors.toList());
			list1.removeAll(list2);
		}
		log.debug("去重后的活动：" + list1);
		if (list1 == null || list1.size() == 0) {
			log.debug("没有需要删除的数据，直接返回");
			return;
		}
		// 得到去重后的需要被删除活动
		List<Map<String, Object>> actMapList = new ArrayList<Map<String,Object>>();
		for (String activityId : list1) {
			Map<String, Object> m = new HashMap<>();
			m.put("activityId", activityId);
			for (TevglPkgActivityRelation relation : pkgActivityRelationList) {
				if (activityId.equals(relation.getActivityId())) {
					m.put("activityType", relation.getActivityType());
				}
			}
			actMapList.add(m);
		}
		if (actMapList == null || actMapList.size() == 0) {
			return;
		}
		log.debug("等待被删除的活动:" + actMapList.size());
		log.debug("" + actMapList);
		// 投票问卷
		handleDeleteAct1(actMapList);
		// 头脑风暴
		//handleDeleteAct2(actMapList);
		// 答疑讨论
		//handleDeleteAct3(actMapList);
		// 测试活动
		//handleDeleteAct4(actMapList);
		// 作业小组任务
		handleDeleteAct5(actMapList);
		// 课堂表现
		handleDeleteAct6(actMapList);
		// 轻直播
		handleDeleteAct7(actMapList);
		// 签到活动
		handleDeleteAct8(actMapList);
		// 实践考核
		//handleDeleteAct9(actMapList);
	}
	
	/**
	 * 投票问卷
	 * @param actMapList
	 */
	private void handleDeleteAct1(List<Map<String, Object>> actMapList) {
		List<Object> activityIdList = actMapList.stream()
				.filter(a -> GlobalActivity.ACTIVITY_1_VOTE_QUESTIONNAIRE.equals(a.get("activityType")))
				.map(a -> a.get("activityId"))
				.collect(Collectors.toList());
		if (activityIdList == null || activityIdList.size() == 0) {
			return;
		}
		// 找到对应活动的题目和选项
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activityIds", activityIdList);
		List<Map<String, Object>> questionList = tevglActivityVoteQuestionnaireQuestionMapper.selectSimpleListMapByMap(params);
		if (questionList != null && questionList.size() > 0) {
			String[] questionIds = questionList.stream().map(a -> (String)a.get("questionId")).toArray(String[]::new);
			if (questionIds != null && questionIds.length > 0) {
				// 先删除选项
				tevglActivityVoteQuestionnaireQuestionOptionMapper.deleteBatchByQuestionId(questionIds);
				// 再删除题目
				tevglActivityVoteQuestionnaireQuestionMapper.deleteBatch(questionIds);	
			}
		}
		// 再删除活动
		tevglActivityVoteQuestionnaireMapper.deleteBatch(activityIdList.stream().toArray(String[]::new));
	}

	/**
	 * 作业小组任务
	 * @param actMapList
	 */
	private void handleDeleteAct5(List<Map<String, Object>> actMapList) {
		List<Object> activityIdList = actMapList.stream()
				.filter(a -> GlobalActivity.ACTIVITY_5_TASK_GROUP.equals(a.get("activityType")))
				.map(a -> a.get("activityId"))
				.collect(Collectors.toList());
		if (activityIdList == null || activityIdList.size() == 0) {
			return;
		}
	}
	
	/**
	 * 课堂表现
	 * @param actMapList
	 */
	private void handleDeleteAct6(List<Map<String, Object>> actMapList) {
		List<Object> activityIdList = actMapList.stream()
				.filter(a -> GlobalActivity.ACTIVITY_6_CLASSROOM_PERFORMANCE.equals(a.get("activityType")))
				.map(a -> a.get("activityId"))
				.collect(Collectors.toList());
		if (activityIdList == null || activityIdList.size() == 0) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activityIds", activityIdList);
		List<TevglTchRoomPereTraineeAnswer> list = tevglTchRoomPereTraineeAnswerMapper.selectListByMap(params);
		if (list != null && list.size() > 0) {
			List<String> ids = list.stream().map(a -> a.getTraineeAnswerId()).collect(Collectors.toList());
			tevglTchRoomPereTraineeAnswerMapper.deleteBatch(ids.stream().toArray(String[]::new));
		}
		tevglTchRoomPereAnswerMapper.deleteBatch(activityIdList.stream().toArray(String[]::new));
	}
	
	/**
	 * 轻直播
	 * @param actMapList
	 */
	private void handleDeleteAct7(List<Map<String, Object>> actMapList) {
		List<Object> activityIdList = actMapList.stream()
				.filter(a -> GlobalActivity.ACTIVITY_7_LIGHT_LIVE.equals(a.get("activityType")))
				.map(a -> a.get("activityId"))
				.collect(Collectors.toList());
		if (activityIdList == null || activityIdList.size() == 0) {
			return;
		}
	}
	
	/**
	 * 签到活动
	 * @param actMapList
	 */
	private void handleDeleteAct8(List<Map<String, Object>> actMapList) {
		List<Object> activityIdList = actMapList.stream()
				.filter(a -> GlobalActivity.ACTIVITY_8_SIGININ_INFO.equals(a.get("activityType")))
				.map(a -> a.get("activityId"))
				.collect(Collectors.toList());
		if (activityIdList == null || activityIdList.size() == 0) {
			return;
		}
		tevglActivitySigninInfoMapper.deleteBatch(activityIdList.stream().toArray(String[]::new));
	}


	/**
	 * 课堂页面中，检验是否有操作按钮的权限,，如【设置章节对学员是否可见】、【开始活动】、【结束活动】等
	 * @param tevglTchClassroom 当前课堂
	 * @param loginUserId 当前登录用户
	 * @param perms 当前校验的按钮权限 详见com.creatorblue.modules.common.GlobalRoomPermission
	 * @return
	 */
	public boolean hasOperationBtnPermission(TevglTchClassroom tevglTchClassroom, String loginUserId, String perms) {
		if (tevglTchClassroom == null) {
			return false;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("ctId", tevglTchClassroom.getCtId());
		List<String> permissionList = tevglTchClassroomRoleprivilegeMapper.queryPermissionList(map);
		return doCheckHasOperationBtnPermission(tevglTchClassroom, loginUserId, perms, permissionList);
	}
	
	/**
	 * 课堂页面中，检验是否有操作按钮的权限,，如【设置章节对学员是否可见】、【开始活动】、【结束活动】等
	 * @param tevglTchClassroom 当前课堂
	 * @param loginUserId 当前登录用户
	 * @param perms 当前校验的按钮权限 详见com.creatorblue.modules.common.GlobalRoomPermission
	 * @param permissionList 当前课堂拥有的操作权限
	 * @return 返回布尔值，true则表示有，false没有
	 */
	public boolean hasOperationBtnPermission(TevglTchClassroom tevglTchClassroom, String loginUserId, String perms, List<String> permissionList) {
		return doCheckHasOperationBtnPermission(tevglTchClassroom, loginUserId, perms, permissionList);
	}
	
	/**
	 * 课堂页面中，检验是否有操作按钮的权限,，如【设置章节对学员是否可见】、【开始活动】、【结束活动】等
	 * @param ctId 当前课堂id
	 * @param loginUserId 当前登录用户
	 * @param perms 当前校验的按钮权限 详见com.creatorblue.modules.common.GlobalRoomPermission
	 * @param permissionList
	 * @return 返回布尔值，true则表示有，false没有
	 */
	public boolean hasOperationBtnPermission(String ctId, String loginUserId, String perms, List<String> permissionList) {
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return false;
		}
		return doCheckHasOperationBtnPermission(tevglTchClassroom, loginUserId, perms, permissionList);
	}
	
	/**
	 * 课堂页面中，检验是否有操作按钮的权限,，如【设置章节对学员是否可见】、【开始活动】、【结束活动】等
	 * @param ctId 当前课堂id
	 * @param loginUserId 当前登录用户
	 * @param perms 当前校验的按钮权限 详见com.creatorblue.modules.common.GlobalRoomPermission
	 * @return 返回布尔值，true则表示有，false没有
	 */
	public boolean hasOperationBtnPermission(String ctId, String loginUserId, String perms) {
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return false;
		}
		return hasOperationBtnPermission(tevglTchClassroom, loginUserId, perms);
	}
	
	/**
	 * 检验是否有操作按钮的权限
	 * @param tevglTchClassroom 当前课堂
	 * @param loginUserId 当前登录用户
	 * @param perms 当前校验的按钮权限 详见com.creatorblue.modules.common.GlobalRoomPermission
	 * @param permissionList 当前课堂拥有的操作权限
	 * @return 返回布尔值，true则表示有，false没有
	 */
	private boolean doCheckHasOperationBtnPermission(TevglTchClassroom tevglTchClassroom, String loginUserId, String perms, List<String> permissionList) {
		if (tevglTchClassroom == null || StrUtils.isEmpty(loginUserId)) {
			return false;
		}
        // 当前登录用户是否为课堂的助教
        boolean isTeachingAssistant = StrUtils.isNotEmpty(tevglTchClassroom.getTraineeId()) && loginUserId.equals(tevglTchClassroom.getTraineeId());
		/*log.debug("当前登录用户身份：【课堂创建者】" + isRoomCreator + "\t【课堂助教者】" + isTeachingAssistant);
		log.debug("当前课堂【"+tevglTchClassroom.getName()+"】【"+tevglTchClassroom.getCtId()+"】");
		log.debug("设置的操作按钮权限有：" + permissionList);
		log.debug("当前校验的权限按钮：" + perms);*/
		// 如果是助教
		if (isTeachingAssistant) {
			if (StrUtils.isEmpty(perms) || permissionList == null || permissionList.size() == 0) {
				return false;
			}
			// 且助教角色有操作对应按钮的权限
			boolean anyMatch = permissionList.stream().anyMatch(a -> a.equals(perms));
			return anyMatch;
		}
		// 当前用户是否为此课堂的创建者
        boolean isRoomCreator = loginUserId.equals(tevglTchClassroom.getCreateUserId());
		// 如果课堂没有被移交
		if (StrUtils.isEmpty(tevglTchClassroom.getReceiverUserId())) {
			if (isRoomCreator) {
				return true;
			}
		}
		// 如果课堂被移交了，那么接收者才有操作权限，原创建者，丧失操作权限
		if (StrUtils.isNotEmpty(tevglTchClassroom.getReceiverUserId())) {
			if (loginUserId.equals(tevglTchClassroom.getCreateUserId())) {
				return false;
			}
			if (loginUserId.equals(tevglTchClassroom.getReceiverUserId())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检验是否有操作按钮的权限（旧版本，没有兼容课堂被移交后，接收者的业务处理，详情请搜索doCheckHasOperationBtnPermission）
	 * @param tevglTchClassroom 当前课堂
	 * @param loginUserId 当前登录用户
	 * @param perms 当前校验的按钮权限 详见com.creatorblue.modules.common.GlobalRoomPermission
	 * @param permissionList 当前课堂拥有的操作权限
	 * @return 返回布尔值，true则表示有，false没有
	 */
	private boolean doCheckHasOperationBtnPermissionOlderVersion(TevglTchClassroom tevglTchClassroom, String loginUserId, String perms, List<String> permissionList) {
		if (tevglTchClassroom == null || StrUtils.isEmpty(loginUserId)) {
			return false;
		}
		if (loginUserId.equals(tevglTchClassroom.getCreateUserId())) {
			return true;
		}
		if (StrUtils.isEmpty(perms) || permissionList == null || permissionList.size() == 0) {
			return false;
		}
		// 当前用户是否为此课堂的创建者
		boolean isRoomCreator = loginUserId.equals(tevglTchClassroom.getCreateUserId());
		// 当前登录用户是否为课堂的助教
		boolean isTeachingAssistant = StrUtils.isNotEmpty(tevglTchClassroom.getTraineeId()) && loginUserId.equals(tevglTchClassroom.getTraineeId());
		// 如果是课堂创建者
		if (isRoomCreator) {
			return true;
		}
		// 如果是助教
		if (isTeachingAssistant) {
			// 且助教角色有操作对应按钮的权限
			boolean anyMatch = permissionList.stream().anyMatch(a -> a.equals(perms));
			log.debug("有权限：" + anyMatch);
			return anyMatch;
		}
		return false;
	}
	
	/**
	 * 判断登录用户是否有操作课堂的权限（仅区分创建者和接收者）
	 * @param tevglTchClassroom
	 * @param loginUserId
	 * @return 返回true才表示有
	 */
	public boolean hasOperatingAuthorization(TevglTchClassroom tevglTchClassroom, String loginUserId) {
		if (tevglTchClassroom == null || StrUtils.isEmpty(loginUserId)) {
			return false;
		}
		// 如果课堂没有移交
		if (StrUtils.isEmpty(tevglTchClassroom.getReceiverUserId())) {
			// 如果登录用户是课堂创建者
			if (loginUserId.equals(tevglTchClassroom.getCreateUserId())) {
				return true;
			}
		}
		// 如果课堂移交了
		if (StrUtils.isNotEmpty(tevglTchClassroom.getReceiverUserId())) {
			if (loginUserId.equals(tevglTchClassroom.getCreateUserId())) {
				return false;
			}
			if (loginUserId.equals(tevglTchClassroom.getReceiverUserId())) {
				return true;
			}
		}
		return false;
	}
	
}
