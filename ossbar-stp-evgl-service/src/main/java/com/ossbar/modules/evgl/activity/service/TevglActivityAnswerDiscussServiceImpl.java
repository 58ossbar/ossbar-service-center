package com.ossbar.modules.evgl.activity.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ossbar.modules.evgl.medu.me.persistence.TmeduMeLikeMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgActivityRelationMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomTraineeMapper;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSONObject;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbNumsUtils;
import com.ossbar.modules.common.CbRoomUtils;
import com.ossbar.modules.common.GlobalActivity;
import com.ossbar.modules.common.GlobalActivityEmpiricalValue;
import com.ossbar.modules.common.GlobalLike;
import com.ossbar.modules.common.GlobalRoomPermission;
import com.ossbar.modules.common.PkgActivityUtils;
import com.ossbar.modules.common.PkgPermissionUtils;
import com.ossbar.modules.common.PkgUtils;
import com.ossbar.modules.evgl.activity.api.TevglActivityAnswerDiscussService;
import com.ossbar.modules.evgl.activity.domain.TevglActivityAnswerDiscuss;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityAnswerDiscussMapper;
import com.ossbar.modules.evgl.medu.me.domain.TmeduMeLike;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgActivityRelation;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomTrainee;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.github.pagehelper.PageHelper;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/activity/tevglactivityanswerdiscuss")
public class TevglActivityAnswerDiscussServiceImpl implements TevglActivityAnswerDiscussService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglActivityAnswerDiscussServiceImpl.class);
	@Autowired
	private TevglActivityAnswerDiscussMapper tevglActivityAnswerDiscussMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private TevglPkgActivityRelationMapper tevglPkgActivityRelationMapper;
	@Autowired
	private PkgPermissionUtils pkgPermissionUtils;
	@Autowired
	private PkgUtils pkgUtils;
	@Autowired
	private PkgActivityUtils pkgActivityUtils;

	@Autowired
	private TevglTchClassroomTraineeMapper tevglTchClassroomTraineeMapper;

	@Autowired
	private TmeduMeLikeMapper tmeduMeLikeMapper;

	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private CbNumsUtils cbNumsUtils;
	@Autowired
	private TevglPkgInfoMapper tevglPkgInfoMapper;
	@Autowired
	private CbRoomUtils cbRoomUtils;
	
	
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/activity/tevglactivityanswerdiscuss/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglActivityAnswerDiscuss> tevglActivityAnswerDiscussList = tevglActivityAnswerDiscussMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglActivityAnswerDiscussList, "createUserId", "updateUserId");
		convertUtil.convertUserId2RealName(tevglActivityAnswerDiscussList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tevglActivityAnswerDiscussList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/activity/tevglactivityanswerdiscuss/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglActivityAnswerDiscussList = tevglActivityAnswerDiscussMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglActivityAnswerDiscussList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglActivityAnswerDiscussList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglActivityAnswerDiscuss
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/activity/tevglactivityanswerdiscuss/save")
	public R save(@RequestBody(required = false) TevglActivityAnswerDiscuss tevglActivityAnswerDiscuss) throws OssbarException {
		tevglActivityAnswerDiscuss.setActivityId(Identities.uuid());
		tevglActivityAnswerDiscuss.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglActivityAnswerDiscuss.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglActivityAnswerDiscuss);
		tevglActivityAnswerDiscussMapper.insert(tevglActivityAnswerDiscuss);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglActivityAnswerDiscuss
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/activity/tevglactivityanswerdiscuss/update")
	public R update(@RequestBody(required = false) TevglActivityAnswerDiscuss tevglActivityAnswerDiscuss) throws OssbarException {
	    tevglActivityAnswerDiscuss.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglActivityAnswerDiscuss.setUpdateTime(DateUtils.getNowTimeStamp());
	    ValidatorUtils.check(tevglActivityAnswerDiscuss);
		tevglActivityAnswerDiscussMapper.update(tevglActivityAnswerDiscuss);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/activity/tevglactivityanswerdiscuss/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglActivityAnswerDiscussMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/activity/tevglactivityanswerdiscuss/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglActivityAnswerDiscussMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/activity/tevglactivityanswerdiscuss/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglActivityAnswerDiscussMapper.selectObjectById(id));
	}
	
	/**
	 * 新增一个 答疑讨论（群组）
	 * @param tevglActivityAnswerDiscuss
	 * @param pkgId
	 * @param loginUserId
	 * @return
	 */
	@Override
	@PostMapping("/saveAnswerDiscussInfo")
	public R saveAnswerDiscussInfo(@RequestBody TevglActivityAnswerDiscuss tevglActivityAnswerDiscuss, String loginUserId) throws OssbarException {
		String pkgId = tevglActivityAnswerDiscuss.getPkgId();
		String chapterId = tevglActivityAnswerDiscuss.getChapterId();
		if (StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectByPkgId(pkgId);
		if (tevglTchClassroom != null) {
			if ("3".equals(tevglTchClassroom.getClassroomState())) {
				return R.error("课堂已结束，无法添加活动");
			}
			// 权限判断，用于课堂创建者与课堂助教
			boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_ADD);
			if (!hasOperationBtnPermission) {
				return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
			}
		} else {
			// 权限校验，用于教学包创建者与章节共建者
			if (!pkgPermissionUtils.hasPermissionAv2(pkgId, loginUserId, chapterId)) {
				return R.error("暂无权限，操作失败");
			}
		}
		// 判断此章节是否已经生成了[活动]分组,如果没有,则生成
		TevglPkgInfo pkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
		if (pkgInfo == null) {
			return R.error("无效的教学包记录");
		}
		String refPkgId = pkgInfo.getPkgId();
		if (StrUtils.isNotEmpty(pkgInfo.getRefPkgId())) {
			refPkgId = pkgInfo.getRefPkgId();
		}
		pkgUtils.createDefaultActivityTab(refPkgId, chapterId, loginUserId);
		// 填充答疑讨论部分信息
		tevglActivityAnswerDiscuss.setActivityId(Identities.uuid());
		tevglActivityAnswerDiscuss.setActivityState("0"); // 0未开始1进行中2已结束
		tevglActivityAnswerDiscuss.setState("Y"); // Y有效N无效
		tevglActivityAnswerDiscuss.setActivityType(GlobalActivity.ACTIVITY_3_ANSWER_DISCUSS);
		tevglActivityAnswerDiscuss.setCreateTime(DateUtils.getNowTimeStamp());
		tevglActivityAnswerDiscuss.setCreateUserId(loginUserId);
		tevglActivityAnswerDiscuss.setAnswerNumber(0);
		tevglActivityAnswerDiscuss.setActivityPic("iconfont_svg icondayitaolun");
		tevglActivityAnswerDiscuss.setEmpiricalValue(GlobalActivityEmpiricalValue.ACTIVITY_3_ANSWER_DISCUSS);
		tevglActivityAnswerDiscuss.setIsAllowPic(StrUtils.isEmpty(tevglActivityAnswerDiscuss.getIsAllowPic()) ? "N" : tevglActivityAnswerDiscuss.getIsAllowPic());
		tevglActivityAnswerDiscuss.setIsAllowVoice(StrUtils.isEmpty(tevglActivityAnswerDiscuss.getIsAllowVoice()) ? "N" : tevglActivityAnswerDiscuss.getIsAllowVoice());
		tevglActivityAnswerDiscuss.setIsAllowVideo(StrUtils.isEmpty(tevglActivityAnswerDiscuss.getIsAllowVideo()) ? "N" : tevglActivityAnswerDiscuss.getIsAllowVideo());
		// 创建答疑讨论活动时固定分组
		tevglActivityAnswerDiscuss.setResgroupId(GlobalActivity.ACTIVITY_3_ANSWER_DISCUSS);
		Map<String, Object> ps = new HashMap<>();
		ps.put("pkgId", pkgId);
		ps.put("resgroupId", tevglActivityAnswerDiscuss.getResgroupId());
		Integer sortNum = tevglActivityAnswerDiscussMapper.getMaxSortNum(ps);
		tevglActivityAnswerDiscuss.setSortNum(sortNum);
		// 验证
		ValidatorUtils.check(tevglActivityAnswerDiscuss);
		// 保存至数据库
		tevglActivityAnswerDiscussMapper.insert(tevglActivityAnswerDiscuss);
		// 保存答疑讨论与教学包的关系
		pkgUtils.buildRelation(pkgId, tevglActivityAnswerDiscuss.getActivityId(), GlobalActivity.ACTIVITY_3_ANSWER_DISCUSS);
		// 更新数量
		pkgUtils.plusPkgActivityNum(pkgId);
		return R.ok("[答疑/讨论] 创建成功");
	}

	/**
	 * 修改答疑讨论
	 * @param tevglActivityAnswerDiscuss
	 * @param loginUserId
	 * @return
	 */
	@Override
	@PostMapping("/updateAnswerDiscussInfo")
	public R updateAnswerDiscussInfo(@RequestBody TevglActivityAnswerDiscuss tevglActivityAnswerDiscuss
			, String loginUserId) throws OssbarException {
		String activityId = tevglActivityAnswerDiscuss.getActivityId();
		String pkgId = tevglActivityAnswerDiscuss.getPkgId();
		String chapterId = tevglActivityAnswerDiscuss.getChapterId();
		if (StrUtils.isEmpty(activityId) || StrUtils.isEmpty(loginUserId) || StrUtils.isEmpty(pkgId)) {
			return R.error("必传参数为空");
		}
		TevglActivityAnswerDiscuss activityInfo = tevglActivityAnswerDiscussMapper.selectObjectById(activityId);
		if (activityInfo == null) {
			return R.error("无效的记录");
		}
		if ("1".equals(activityInfo.getActivityState())) {
			return R.error("活动已开始，无法修改");
		}
		if ("2".equals(activityInfo.getActivityState())) {
			return R.error("活动已结束，无法修改");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectByPkgId(pkgId);
		if (tevglTchClassroom != null) {
			if ("3".equals(tevglTchClassroom.getClassroomState())) {
				return R.error("课堂已结束，无法添加活动");
			}
			// 权限判断，用于课堂创建者与课堂助教
			boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_ADD);
			if (!hasOperationBtnPermission) {
				return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
			}
		} else {
			// 权限校验，用于教学包创建者与章节共建者
			if (!pkgPermissionUtils.hasPermissionAv2(pkgId, loginUserId, chapterId)) {
				return R.error("暂无权限，操作失败");
			}
		}
		// 判断此章节是否已经生成了[活动]分组,如果没有,则生成
		TevglPkgInfo pkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
		if (pkgInfo == null) {
			return R.error("无效的教学包记录");
		}
		String refPkgId = pkgInfo.getPkgId();
		if (StrUtils.isNotEmpty(pkgInfo.getRefPkgId())) {
			refPkgId = pkgInfo.getRefPkgId();
		}
		pkgUtils.createDefaultActivityTab(refPkgId, chapterId, loginUserId);
		// 填充信息
		tevglActivityAnswerDiscuss.setUpdateTime(DateUtils.getNowTimeStamp());
		tevglActivityAnswerDiscuss.setUpdateUserId(loginUserId);
		tevglActivityAnswerDiscuss.setState("Y");
		tevglActivityAnswerDiscuss.setActivityType(GlobalActivity.ACTIVITY_3_ANSWER_DISCUSS);
		tevglActivityAnswerDiscuss.setResgroupId(GlobalActivity.ACTIVITY_3_ANSWER_DISCUSS);
		// 验证
		ValidatorUtils.check(tevglActivityAnswerDiscuss);
		tevglActivityAnswerDiscussMapper.update(tevglActivityAnswerDiscuss);
		return R.ok("[答疑/讨论] 修改成功");
	}

	/**
	 * 查看答疑讨论
	 * @param activityId（注意，这个里值，具体可能会是t_evgl_pkg_activity_relation表的activity_id或group_id的值）
	 * @return
	 */
	@Override
	@GetMapping("/viewAnswerDiscussInfo")
	public R viewAnswerDiscussInfo(String activityId) {
		if (StrUtils.isEmpty(activityId)) {
			return R.error("必传参数为空");
		}
		Map<String, Object> answerDiscussInfo = tevglActivityAnswerDiscussMapper.selectObjectMapById(activityId);
		if (answerDiscussInfo == null) {
			TevglActivityAnswerDiscuss tevglActivityAnswerDiscuss = tevglActivityAnswerDiscussMapper.selectObjectByGroupId(activityId);
			if (tevglActivityAnswerDiscuss != null) {
				answerDiscussInfo = new HashMap<>();
				answerDiscussInfo.put("activityState", tevglActivityAnswerDiscuss.getActivityStateActual());
				answerDiscussInfo.put("activityId", tevglActivityAnswerDiscuss.getActivityId());
				answerDiscussInfo.put("groupId", tevglActivityAnswerDiscuss.getGroupId());
				answerDiscussInfo.put("activityTitle", tevglActivityAnswerDiscuss.getActivityTitle());
				answerDiscussInfo.put("content", tevglActivityAnswerDiscuss.getContent());
				answerDiscussInfo.put("isAllowPic", tevglActivityAnswerDiscuss.getIsAllowPic());
				answerDiscussInfo.put("isAllowVoice", tevglActivityAnswerDiscuss.getIsAllowVoice());
				answerDiscussInfo.put("isAllowVideo", tevglActivityAnswerDiscuss.getIsAllowVideo());
				answerDiscussInfo.put("purpose", tevglActivityAnswerDiscuss.getPurpose());
				answerDiscussInfo.put("chapterId", tevglActivityAnswerDiscuss.getChapterId());
			}
		}
		return R.ok().put(Constant.R_DATA, answerDiscussInfo);
	}
	
	/**
	 * 聊天内容
	 * @param params
	 * @param loginUserId
	 * @return
	 */
	@Override
	@GetMapping("/viewActInfo")
	public R viewActInfo(@RequestParam Map<String, Object> params, String loginUserId) {
		Object pkgId = params.get("pkgId");
		Object groupId = params.get("activityId");
		if (StrUtils.isNull(groupId)) {
			return R.error("必传参数为空");
		}
		return null;
		}

	/**
	 * 删除答疑讨论
	 * @param activityId 活动id
	 * @param pkgId 所属教学包
	 * @param loginUserId 当前登录用户
	 * @return
	 */
	@Override
	@PostMapping("/deleteAnswerDiscussInfo")
	public R deleteAnswerDiscussInfo(String activityId, String pkgId, String loginUserId) throws OssbarException {
		if (StrUtils.isEmpty(activityId) || StrUtils.isEmpty(loginUserId) || StrUtils.isEmpty(pkgId)) {
			return R.error("必传参数为空");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pkgId", pkgId);
		params.put("activityId", activityId);
		TevglActivityAnswerDiscuss answerDiscussInfo = tevglActivityAnswerDiscussMapper.selectObjectByIdAndPkgId(params);
		if (answerDiscussInfo == null) {
			return R.error("无效的记录");
		}
		// 权限校验
		if (!pkgPermissionUtils.hasPermissionDeleteActivity(pkgId, answerDiscussInfo.getCreateUserId(), loginUserId, answerDiscussInfo.getChapterId())) {
			return R.error("暂无权限，操作失败");
		}
		// 如果活动已经开始或结束,控制不能删除
		if (!"0".equals(answerDiscussInfo.getActivityStateActual())) {
			return R.error("当前活动已被使用,无法删除");
		}
		// 删除活动与教学包的关系
		tevglPkgActivityRelationMapper.deleteByActivityId(activityId);
		// 删除活动
		tevglActivityAnswerDiscussMapper.delete(activityId);
		// 教学包活动数量-1
		pkgUtils.plusPkgActivityReduceNum(pkgId);
		return R.ok("删除成功");
	}

	/**
	 * 开始答疑讨论
	 * @param ctId 必传参数，课堂主键
	 * @param activityId 必传参数，活动主键
	 * @param pkgId 活动对应的教学包id
	 * @param loginUserId 必传参数，当前登录用户
	 * @return
	 */
	@Override
	public R startAnswerDiscussInfo(String ctId, String activityId, String pkgId, String loginUserId, String activityEndTime) {
		if (StrUtils.isEmpty(activityId) || StrUtils.isEmpty(loginUserId) 
				|| StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(ctId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroom == null) {
			return R.error("无效的记录");
		}
		if ("1".equals(classroom.getClassroomState())) {
			return R.error("课堂尚未开始，无法开始活动");
		}
		if ("3".equals(classroom.getClassroomState())) {
			return R.error("课堂已经结束，无法开始活动");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		//params.put("pkgId", classroom.getPkgId()); // 不要用这个值
		params.put("pkgId", pkgId);
		params.put("activityId", activityId);
		TevglActivityAnswerDiscuss activityInfo = tevglActivityAnswerDiscussMapper.selectObjectByIdAndPkgId(params);
		if (activityInfo == null) {
			return R.error("无效的活动，请刷新后重试");
		}
		if ("1".equals(activityInfo.getActivityStateActual())) {
			return R.error("活动已开始");
		}
		if ("2".equals(activityInfo.getActivityStateActual())) {
			return R.error("活动已结束");
		}
		Map<String, Object> ps = new HashMap<String, Object>();
		ps.put("pkgId", pkgId);
		ps.put("activityId", activityId);
		List<TevglPkgActivityRelation> list = tevglPkgActivityRelationMapper.selectListByMap(ps);
		if (list == null || list.size() == 0) {
			log.debug("t_evgl_pkg_activity_relation没有数据,直接认为没有权限");
			return R.error("没有权限");
		}
		TevglPkgActivityRelation relation = list.get(0);
		// 权限判断
		/*if (!pkgActivityUtils.hasStartActPermission(activityId, relation, loginUserId, activityInfo.getCreateUserId())) {
			return R.error("暂无权限，操作失败");
		}*/
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(classroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_START);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		// 调整为修改关联表的状态
		relation.setActivityState("1");
		relation.setActivityBeginTime(DateUtils.getNowTimeStamp());
		relation.setActivityEndTime(StrUtils.isEmpty(activityEndTime) ? null : activityEndTime);
		tevglPkgActivityRelationMapper.update(relation);
		// 返回数据
		Map<String, Object> data = new HashMap<>();
		data.put("empiricalValue", activityInfo.getEmpiricalValue());
		data.put("activityType", GlobalActivity.ACTIVITY_3_ANSWER_DISCUSS);
		data.put("activityId", activityId);
		data.put("activityTitle", activityInfo.getActivityTitle());
		data.put("content", activityInfo.getContent());
		// ================================================== 即时通讯相关处理 begin ==================================================
		 
		// 第一步、把答疑讨论当成一个群组即可,所以需要创建一个群组
		//String groupId = createTimGroup(activityId, loginUserId, activityInfo);
		String groupId = createTimGroup(activityInfo.getGroupId(), loginUserId, activityInfo, pkgId);
		// 第二步、活动创建者（也是课堂创建者）成为[群主]
		createTimGroupUserAdmin(groupId, loginUserId);
			// 第四步、先找到本课堂所有有效成员
		List<TevglTchClassroomTrainee> classroomTraineeList = getClassroomTraineeList(ctId);
		log.debug("找到本课堂所有有效成员:" + classroomTraineeList.size());
		// 第五步、所有有效课堂成员成为此活动群的群成员(注：记得更新群聊列表记录)
		createTimGroupUser(groupId, classroomTraineeList);
		// 组装返回前端的数据
		String tips = "发起了活动【" + activityInfo.getActivityTitle() + "】";
		JSONObject msg = new JSONObject();
		// 3标识答疑讨论活动
		msg.put("activity_type", GlobalActivity.ACTIVITY_3_ANSWER_DISCUSS); 
		JSONObject busiMsg = new JSONObject();
		busiMsg.put("send_id", loginUserId);
		busiMsg.put("send_name", null);
		busiMsg.put("tips", tips);
		busiMsg.put("activity_id", activityInfo.getActivityId());
		busiMsg.put("activity_type", GlobalActivity.ACTIVITY_3_ANSWER_DISCUSS);
		busiMsg.put("activity_title", activityInfo.getActivityTitle());
		busiMsg.put("activity_pic", activityInfo.getActivityPic());
		busiMsg.put("activity_state", "1"); // 活动状态0未开始1进行中2已结束
		busiMsg.put("activityState", "1");
		busiMsg.put("content", activityInfo.getContent());
		busiMsg.put("ct_id", ctId);
		// 返回满足PC端的数据
		busiMsg.put("ctId", ctId);
		busiMsg.put("name", classroom.getName());
		busiMsg.put("classId", classroom.getClassId());
		busiMsg.put("pkgId", classroom.getPkgId());
		TevglPkgInfo pkgInfo = tevglPkgInfoMapper.selectObjectById(classroom.getPkgId());
		if (pkgInfo != null) {
			busiMsg.put("subjectId", pkgInfo.getSubjectId());	
		}
		msg.put("msg", busiMsg);

		// ================================================== 即时通讯相关处理 end ==================================================
		return R.ok("活动时间设置成功").put(Constant.R_DATA, data);
	}
	
	/**
	 * 第一步、创建一个群组
	 * @param activityId
	 * @return 返回群组主键
	 */
	private String createTimGroup(String activityId, String loginUserId, TevglActivityAnswerDiscuss activityInfo, String ctPkgId) {
		String groupId = null;
		R r = null;
		if (r.get(Constant.R_DATA) != null) {
			groupId = r.get(Constant.R_DATA).toString();
		}
		return groupId;
	}

	/**
	 * 第二步、将自己加入群主并成为群主
	 * @param groupId
	 * @param loginUserId
	 */
	private void createTimGroupUserAdmin(String groupId, String loginUserId) {

	}
	
	/**
	 * 第四步、获取课堂所有有效成员
	 * @param ctId
	 * @return
	 */
	private List<TevglTchClassroomTrainee> getClassroomTraineeList(String ctId) {
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		params.put("state", "Y");
		List<TevglTchClassroomTrainee> classroomTraineeList = tevglTchClassroomTraineeMapper.selectListByMap(params);
		return classroomTraineeList;
	}

	/**
	 * 第五步、将课堂成员加进群组,成为群组用户
	 * @param groupId 群组主键
	 * @param pkgId 教学包主键，用来获取课堂主键
	 * @param loginUserId
	 */
	private List createTimGroupUser(String groupId, List<TevglTchClassroomTrainee> classroomTraineeList) {
		if (StrUtils.isEmpty(groupId) || classroomTraineeList == null || classroomTraineeList.size() == 0) {
			return null;
		}
		List list =null;
		return list;
	}
	

	/**
	 * 结束活动
	 * @param activityId
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R endAnswerDiscussInfo(String ctId, String activityId, String loginUserId) {
		if (StrUtils.isEmpty(activityId) || StrUtils.isEmpty(loginUserId)
				|| StrUtils.isEmpty(ctId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroom == null) {
			return R.error("无效的记录");
		}
		if ("1".equals(classroom.getClassroomState())) {
			return R.error("课堂尚未开始，无法结束活动");
		}
		if ("3".equals(classroom.getClassroomState())) {
			return R.error("课堂已经结束，无法结束活动");
		}
		TevglActivityAnswerDiscuss activityInfo = tevglActivityAnswerDiscussMapper.selectObjectById(activityId);
		if (activityInfo == null) {
			return R.error("无效的活动，请刷新后重试");
		}
		if ("0".equals(activityInfo.getActivityStateActual())) {
			return R.error("活动未开始");
		}
		if ("2".equals(activityInfo.getActivityStateActual())) {
			return R.error("活动已结束");
		}
		Map<String, Object> ps = new HashMap<String, Object>();
		ps.put("pkgId", classroom.getPkgId());
		ps.put("activityId", activityId);
		List<TevglPkgActivityRelation> list = tevglPkgActivityRelationMapper.selectListByMap(ps);
		if (list == null || list.size() == 0) {
			log.debug("t_evgl_pkg_activity_relation没有数据,直接认为没有权限");
			return R.error("没有权限");
		}
		TevglPkgActivityRelation relation = list.get(0);
		// 权限判断
		/*if (!pkgActivityUtils.hasStartActPermission(activityId, relation, loginUserId, activityInfo.getCreateUserId())) {
			return R.error("暂无权限，操作失败");
		}*/
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(classroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_END);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		// 调整为修改关联表的状态
		relation.setActivityState("2"); // 0未开始1进行中2已结束
		relation.setActivityEndTime(DateUtils.getNowTimeStamp());
		tevglPkgActivityRelationMapper.update(relation);
		// ============================== 即时通讯相关处理 begin ==============================
		// 结束答疑讨论之后，将答疑讨论活动群状态设为无效
		activityId = list.get(0).getGroupId();
				// 找到本群的聊天列表数据
		Map<String, Object> params = new HashMap<>();
		params.put("friendType", "2");
		params.put("friendId", activityId);

		// 结束活动更新未读消息为已读
		params.clear();
		params.put("groupId", activityId);
		// ============================== 即时通讯相关处理 end ==============================
		// 返回数据
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("activityId", activityId);
		data.put("activityTitle", activityInfo.getActivityTitle());
		return R.ok("成功结束活动").put(Constant.R_DATA, data);
	}

	/**
	 * 复制一个新的活动
	 * @param targetActivityId 目标活动
	 * @param newPkgId 新教学包id
	 * @param loginUserId
	 * @param chapterId
	 */
	@Override
	public void copy(String targetActivityId, String newPkgId, String loginUserId, String chapterId, int sortNum) {
		if (StrUtils.isEmpty(targetActivityId) || StrUtils.isEmpty(newPkgId) || StrUtils.isEmpty(loginUserId)) {
			log.debug("必传参数为空");
			return;
		}
		// 找到目标活动
		TevglActivityAnswerDiscuss activityInfo = tevglActivityAnswerDiscussMapper.selectObjectById(targetActivityId);
		if (activityInfo == null) {
			return ;
		}
		// 找到目标活动的活动分组(自定义活动分组时记得控制分组名称唯一)
		String resgroupId = "0";
		resgroupId = activityInfo.getResgroupId();
		/*TevglPkgResgroup resgroup = tevglPkgResgroupMapper.selectObjectById(activityInfo.getResgroupId());
		// 不为空则表示是自己定义的活动分组
		if (resgroup != null) {
			Map<String, Object> ps = new HashMap<>();
			ps.put("pkgId", newPkgId);
			ps.put("groupType", GlobalActivity.ACTIVITY_GROUP_TYPE_2);
			List<TevglPkgResgroup> tevglPkgResgroupList = tevglPkgResgroupMapper.selectListByMap(ps);
			List<String> resgroupNameList = tevglPkgResgroupList.stream().map(a -> a.getResgroupName()).collect(Collectors.toList());
			// 若不存在这个分组才添加至数据库
			if (!resgroupNameList.contains(resgroup.getResgroupName())) {
				TevglPkgResgroup rg = new TevglPkgResgroup();
				rg = resgroup;
				rg.setPkgId(newPkgId);
				rg.setResgroupId(Identities.uuid());
				rg.setCreateTime(DateUtils.getNowTimeStamp());
				tevglPkgResgroupMapper.insert(rg);
				resgroupId = rg.getResgroupId();
				log.debug("新的活动分组:" + resgroupId);
			}
		}*/
		// 创建并填充一个新的活动
		TevglActivityAnswerDiscuss t = new TevglActivityAnswerDiscuss();
		t = activityInfo;
		t.setActivityId(Identities.uuid());
		t.setChapterId(StrUtils.isEmpty(chapterId) ? null : chapterId);
		t.setActivityTitle(activityInfo.getActivityTitle());
		t.setCreateUserId(loginUserId);
		t.setSortNum(sortNum);
		t.setActivityBeginTime(null);
		t.setActivityEndTime(null);
		t.setState("Y");
		t.setCreateTime(DateUtils.getNowTimeStamp());
		t.setResgroupId(resgroupId);
		t.setActivityState("0"); // 状态统一改为未开始
		// 保存活动至数据库
		tevglActivityAnswerDiscussMapper.insert(t);
		String newActivityId = t.getActivityId();
		// 保存活动与教学包的关系
		pkgActivityUtils.buildRelation(newPkgId, newActivityId, GlobalActivity.ACTIVITY_3_ANSWER_DISCUSS);
		// 更新教学包活动数量
		pkgUtils.plusPkgActivityNum(newPkgId);
	}
	

	/**
	 * 点赞
	 * @param taId
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R clickLike(String msgId, String loginUserId) {
		if (StrUtils.isEmpty(msgId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}

		List<TmeduMeLike> list = getMeLikeListData(msgId, loginUserId);
		if (list.size() > 0) {
			return R.error(GlobalLike.ALREADY_LIKE_MSG);
		}
		TmeduMeLike t = new TmeduMeLike();
		t.setLikeId(Identities.uuid());
		t.setLikeType(GlobalLike.LIKE_15_ACTIVITY_ANSWER_DISCUSS_TRAINEE_ANSWER);
		t.setLikeTime(DateUtils.getNowTimeStamp());
		t.setTraineeId(loginUserId);
		t.setTargetId(msgId);
		t.setReadState("N");
		tmeduMeLikeMapper.insert(t);
		return R.ok("点赞成功");
	}

	/**
	 * 取消点赞
	 * @param taId
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R cancelLike(String msgId, String loginUserId) {
		if (StrUtils.isEmpty(msgId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		List<TmeduMeLike> list = getMeLikeListData(msgId, loginUserId);
		if (list.size() == 0) {
			return R.error(GlobalLike.NO_LIKE_MSG);
		}
		for (TmeduMeLike tmeduMeLike : list) {
			tmeduMeLikeMapper.delete(tmeduMeLike.getLikeId());
		}
		return R.ok("取消点赞");
	}
	

	/**
	 * 获取当前登录用户是否对此作答点赞了
	 * @param anId
	 * @param loginUserId
	 * @return
	 */
	private List<TmeduMeLike> getMeLikeListData(String anId, String loginUserId){
		Map<String, Object> params = new HashMap<>();
		params.put("targetId", anId);
		params.put("likeType", GlobalLike.LIKE_15_ACTIVITY_ANSWER_DISCUSS_TRAINEE_ANSWER);
		params.put("traineeId", loginUserId);
		return tmeduMeLikeMapper.selectListByMap(params);
	}

	@Override
	public TevglActivityAnswerDiscuss selectObjectByGroupId(Object groupId) {
		return tevglActivityAnswerDiscussMapper.selectObjectByGroupId(groupId);
	}

}
