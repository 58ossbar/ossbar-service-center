package com.ossbar.modules.evgl.activity.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ossbar.modules.common.CbRoomUtils;
import com.ossbar.modules.common.GlobalActivity;
import com.ossbar.modules.common.GlobalRoomPermission;
import com.ossbar.modules.common.PkgUtils;
import com.ossbar.modules.evgl.activity.api.TevglActivityLiveService;
import com.ossbar.modules.evgl.activity.domain.TevglActivityLive;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityLiveMapper;
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
@RequestMapping("/activity/tevglactivitylive")
public class TevglActivityLiveServiceImpl implements TevglActivityLiveService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglActivityLiveServiceImpl.class);
	@Autowired
	private TevglActivityLiveMapper tevglActivityLiveMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private CbRoomUtils cbRoomUtils;
	@Autowired
	private PkgUtils pkgUtils;
	@Autowired
	private TevglPkgActivityRelationMapper tevglPkgActivityRelationMapper;
	@Autowired	
	private TevglTchClassroomTraineeMapper tevglTchClassroomTraineeMapper;
	@Autowired
	private TevglPkgInfoMapper tevglPkgInfoMapper;

	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/activity/tevglactivitylive/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglActivityLive> tevglActivityLiveList = tevglActivityLiveMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglActivityLiveList, "createUserId", "updateUserId");
		convertUtil.convertUserId2RealName(tevglActivityLiveList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tevglActivityLiveList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/activity/tevglactivitylive/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglActivityLiveList = tevglActivityLiveMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglActivityLiveList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglActivityLiveList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglActivityLive
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/activity/tevglactivitylive/save")
	public R save(@RequestBody(required = false) TevglActivityLive tevglActivityLive) throws OssbarException {
		tevglActivityLive.setActivityId(Identities.uuid());
		tevglActivityLive.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglActivityLive.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglActivityLive);
		tevglActivityLiveMapper.insert(tevglActivityLive);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglActivityLive
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/activity/tevglactivitylive/update")
	public R update(@RequestBody(required = false) TevglActivityLive tevglActivityLive) throws OssbarException {
	    tevglActivityLive.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglActivityLive.setUpdateTime(DateUtils.getNowTimeStamp());
	    ValidatorUtils.check(tevglActivityLive);
		tevglActivityLiveMapper.update(tevglActivityLive);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/activity/tevglactivitylive/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglActivityLiveMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/activity/tevglactivitylive/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws OssbarException {
		tevglActivityLiveMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/activity/tevglactivitylive/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglActivityLiveMapper.selectObjectById(id));
	}

	@Override
	public R saveLive(TevglActivityLive tevglActivityLive, String loginUserId) {
		R r = checkIsPass(tevglActivityLive, loginUserId);
		if (!r.get("code").equals(0)) {
			return r;
		}
		String pkgId = tevglActivityLive.getPkgId();
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectByPkgId(pkgId);
		if (tevglTchClassroom == null) {
			return R.error("参数异常，添加失败");
		}
		if ("3".equals(tevglTchClassroom.getClassroomState())) {
			return R.error("课堂已结束，无法添加活动");
		}
		// 权限判断，用于课堂创建者与课堂助教
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_ADD);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		tevglActivityLive.setActivityId(Identities.uuid());
		tevglActivityLive.setCreateTime(DateUtils.getNowTimeStamp());
		tevglActivityLive.setCreateUserId(loginUserId);
		tevglActivityLive.setResgroupId(GlobalActivity.ACTIVITY_7_LIGHT_LIVE);
		// 0未开始1进行中2已结束
		tevglActivityLive.setActivityState("0");
		// 排序号处理
		Map<String, Object> ps = new HashMap<>();
		ps.put("pkgId", pkgId);
		Integer sortNum = tevglActivityLiveMapper.getMaxSortNum(ps);
		tevglActivityLive.setSortNum(sortNum);
		// 入库
		tevglActivityLiveMapper.insert(tevglActivityLive);
		// 保存教学包与活动之间的关系
		pkgUtils.buildRelation(pkgId, tevglActivityLive.getActivityId(), GlobalActivity.ACTIVITY_7_LIGHT_LIVE);
		// 更新教学包活动数量
		pkgUtils.plusPkgActivityNum(pkgId);
		return R.ok("新增成功");
	}

	@Override
	public R updateLive(TevglActivityLive tevglActivityLive, String loginUserId) {
		// 合法性校验
		R r = checkIsPass(tevglActivityLive, loginUserId);
		if ((Integer)r.get("code") != 0) {
			return r;
		}
		String pkgId = tevglActivityLive.getPkgId();
		String activityId = tevglActivityLive.getActivityId();
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectByPkgId(pkgId);
		if (tevglTchClassroom == null) {
			return R.error("参数异常，无法修改");
		}
		if ("3".equals(tevglTchClassroom.getClassroomState())) {
			return R.error("课堂已结束，无法修改活动");
		}
		// 权限判断，用于课堂创建者与课堂助教
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_ADD);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		TevglActivityLive activityInfo = tevglActivityLiveMapper.selectObjectById(activityId);
		if (activityInfo == null) {
			return R.error("无效的记录");
		}
		if ("1".equals(activityInfo.getActivityState())) {
			return R.error("活动已开始，无法修改");
		}
		if ("2".equals(activityInfo.getActivityState())) {
			return R.error("活动已结束，无法修改");
		}
		// 填充信息
		tevglActivityLive.setUpdateUserId(loginUserId);
		tevglActivityLive.setUpdateTime(DateUtils.getNowTimeStamp());
		tevglActivityLiveMapper.update(tevglActivityLive);
		return R.ok("修改成功");
	}
	
	/**
	 * 合法性校验
	 * @param tevglActivityLive
	 * @param loginUserId
	 * @return
	 */
	private R checkIsPass(TevglActivityLive tevglActivityLive, String loginUserId){
		if (StrUtils.isEmpty(tevglActivityLive.getPkgId()) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		if (StrUtils.isEmpty(tevglActivityLive.getActivityTitle())) {
			return R.error("标题不能为空");
		}
		String title = tevglActivityLive.getActivityTitle().trim();
		if (StrUtils.isEmpty(title)) {
			return R.error("标题不能为空");
		}
		if (title.length() > 50) {
			return R.error("标题不能超过50个字符");
		}
		return R.ok();
	}

	@Override
	public R viewLive(String activityId) {
		TevglActivityLive tevglActivityLive = tevglActivityLiveMapper.selectObjectById(activityId);
		return R.ok().put(Constant.R_DATA, tevglActivityLive);
	}

	@Override
	public R deleteLive(String activityId, String pkgId, String loginUserId) {
		if (StrUtils.isEmpty(activityId) || StrUtils.isEmpty(loginUserId) || StrUtils.isEmpty(pkgId)) {
			return R.error("必传参数为空");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("pkgId", pkgId);
		params.put("activityId", activityId);
		TevglActivityLive activityInfo = tevglActivityLiveMapper.selectObjectByIdAndPkgId(params);
		if (activityInfo == null) {
			return R.error("无效的记录");
		}
		// 如果活动已经开始或结束,控制不能删除
		/*if (!"0".equals(activityInfo.getActivityStateActual())) {
			return R.error("当前活动已被使用，无法删除");
		}*/
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectByPkgId(pkgId);
		if (tevglTchClassroom == null) {
			return R.error("参数异常，无法删除");
		}
		/*if ("3".equals(tevglTchClassroom.getClassroomState())) {
			return R.error("课堂已结束，无法活动");
		}*/
		// 权限判断，用于课堂创建者与课堂助教
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_ADD);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		// 删除与教学包的关系
		tevglPkgActivityRelationMapper.deleteByActivityId(activityId);
		// 删除
		tevglTchClassroomMapper.delete(activityId);
		// 教学包活动数量-1
		pkgUtils.plusPkgActivityReduceNum(pkgId);
		return R.ok("删除成功");
	}

	/**
	 * 开始活动
	 *
	 * @param ctId
	 * @param activityId
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R startActivityLive(String ctId, String activityId, String loginUserId, String activityEndTime) {
		if (StrUtils.isEmpty(activityId) || StrUtils.isEmpty(loginUserId)
				|| StrUtils.isEmpty(ctId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroom == null) {
			return R.error("参数异常，无效的课堂");
		}
		if ("1".equals(classroom.getClassroomState())) {
			return R.error("课堂尚未开始，无法开始活动");
		}
		if ("3".equals(classroom.getClassroomState())) {
			return R.error("课堂已经结束，无法开始活动");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pkgId", classroom.getPkgId());
		params.put("activityId", activityId);
		TevglActivityLive activityInfo = tevglActivityLiveMapper.selectObjectByIdAndPkgId(params);
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
		ps.put("pkgId", classroom.getPkgId());
		ps.put("activityId", activityId);
		List<TevglPkgActivityRelation> list = tevglPkgActivityRelationMapper.selectListByMap(ps);
		if (list == null || list.size() == 0) {
			log.debug("t_evgl_pkg_activity_relation没有数据,直接认为没有权限");
			return R.error("没有权限");
		}
		TevglPkgActivityRelation relation = list.get(0);
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(classroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_START);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		// 调整为修改关联表的状态
		relation.setActivityState("1");
		relation.setActivityBeginTime(DateUtils.getNowTimeStamp());
		relation.setActivityEndTime(null);
		tevglPkgActivityRelationMapper.update(relation);
		// 返回数据
		Map<String, Object> data = new HashMap<>();
		data.put("empiricalValue", activityInfo.getEmpiricalValue());
		data.put("activityType", GlobalActivity.ACTIVITY_2_BRAINSTORMING);
		data.put("activityId", activityId);
		data.put("activityTitle", activityInfo.getActivityTitle());
		data.put("content", activityInfo.getContent());
		// ================================================== 即时通讯相关处理 begin ==================================================
		// 找到本课堂所有有效成员
		params.clear();
		params.put("ctId", ctId);
		params.put("state", "Y");
		List<TevglTchClassroomTrainee> classroomTraineeList = tevglTchClassroomTraineeMapper.selectListByMap(params);
		// 组装数据
		String tips = "发起了活动【" + activityInfo.getActivityTitle() + "】";
		JSONObject msg = new JSONObject();
		msg.put("activity_type", GlobalActivity.ACTIVITY_2_BRAINSTORMING);
		JSONObject busiMsg = new JSONObject();
		busiMsg.put("send_id", loginUserId);
		busiMsg.put("send_name", null);
		busiMsg.put("tips", tips);
		busiMsg.put("activity_id", activityInfo.getActivityId());
		busiMsg.put("activity_type", GlobalActivity.ACTIVITY_7_LIGHT_LIVE);
		busiMsg.put("activity_title", activityInfo.getActivityTitle());
		busiMsg.put("activity_pic", "");
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
		return R.ok("活动开始").put(Constant.R_DATA, data);
	}

	/**
	 * 结束活动
	 *
	 * @param ctId
	 * @param activityId
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R endActivityLive(String ctId, String activityId, String loginUserId) {
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
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pkgId", classroom.getPkgId());
		params.put("activityId", activityId);
		TevglActivityLive activityInfo = tevglActivityLiveMapper.selectObjectByIdAndPkgId(params);
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
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(classroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_END);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		// 调整为修改关联表的状态
		relation.setActivityState("2"); // 0未开始1进行中2已结束
		relation.setActivityEndTime(DateUtils.getNowTimeStamp());
		tevglPkgActivityRelationMapper.update(relation);
		// 返回数据
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("activityId", activityId);
		data.put("activityTitle", activityInfo.getActivityTitle());
		return R.ok("成功结束活动").put(Constant.R_DATA, data);
	}
}
