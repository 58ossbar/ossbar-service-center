package com.ossbar.modules.evgl.activity.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbRoomUtils;
import com.ossbar.modules.common.GlobalActivity;
import com.ossbar.modules.common.GlobalRoomPermission;
import com.ossbar.modules.common.PkgUtils;
import com.ossbar.modules.evgl.activity.api.TevglTchRoomPereAnswerService;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgActivityRelation;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgActivityRelationMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomTrainee;
import com.ossbar.modules.evgl.tch.domain.TevglTchRoomPereAnswer;
import com.ossbar.modules.evgl.tch.domain.TevglTchRoomPereTraineeAnswer;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomTraineeMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchRoomPereAnswerMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchRoomPereTraineeAnswerMapper;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeInfoMapper;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/tch/tevgltchroompereanswer")
public class TevglTchRoomPereAnswerServiceImpl implements TevglTchRoomPereAnswerService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglTchRoomPereAnswerServiceImpl.class);
	@Autowired
	private TevglTchRoomPereAnswerMapper tevglTchRoomPereAnswerMapper;
	@Autowired
	private TevglTchRoomPereTraineeAnswerMapper tevglTchRoomPereTraineeAnswerMapper;
	@Autowired
	private TevglTraineeInfoMapper tevglTraineeInfoMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private PkgUtils pkgUtils;
	@Autowired
	private TevglPkgInfoMapper tevglPkgInfoMapper;
	@Autowired
	private TevglTchClassroomTraineeMapper tevglTchClassroomTraineeMapper;
	@Autowired
	private TevglPkgActivityRelationMapper tevglPkgActivityRelationMapper;
	@Autowired
	private UploadFileUtils uploadPathUtils;
	@Autowired
	private CbRoomUtils cbRoomUtils;
	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@Override
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/tch/tevgltchroompereanswer/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglTchRoomPereAnswer> tevglTchRoomPereAnswerList = tevglTchRoomPereAnswerMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglTchRoomPereAnswerList, "createUserId", "updateUserId");
		convertUtil.convertUserId2RealName(tevglTchRoomPereAnswerList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tevglTchRoomPereAnswerList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@Override
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/tch/tevgltchroompereanswer/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglTchRoomPereAnswerList = tevglTchRoomPereAnswerMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglTchRoomPereAnswerList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglTchRoomPereAnswerList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglTchRoomPereAnswer
	 * @throws CreatorblueException
	 */
	@Override
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/tch/tevgltchroompereanswer/save")
	public R save(@RequestBody(required = false) TevglTchRoomPereAnswer tevglTchRoomPereAnswer) throws CreatorblueException {
		tevglTchRoomPereAnswer.setAnswerId(Identities.uuid());
		tevglTchRoomPereAnswer.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglTchRoomPereAnswer.setCreateTime(DateUtils.getNowTimeStamp());
		//ValidatorUtils.check(tevglTchRoomPereAnswer);
		tevglTchRoomPereAnswerMapper.insert(tevglTchRoomPereAnswer);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglTchRoomPereAnswer
	 * @throws CreatorblueException
	 */
	@Override
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/tch/tevgltchroompereanswer/update")
	public R update(@RequestBody(required = false) TevglTchRoomPereAnswer tevglTchRoomPereAnswer) throws CreatorblueException {
	    tevglTchRoomPereAnswer.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglTchRoomPereAnswer.setUpdateTime(DateUtils.getNowTimeStamp());
	    //ValidatorUtils.check(tevglTchRoomPereAnswer);
		tevglTchRoomPereAnswerMapper.update(tevglTchRoomPereAnswer);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws CreatorblueException
	 */
	@Override
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/tch/tevgltchroompereanswer/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tevglTchRoomPereAnswerMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@Override
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/tch/tevgltchroompereanswer/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws CreatorblueException {
		tevglTchRoomPereAnswerMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@Override
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/tch/tevgltchroompereanswer/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglTchRoomPereAnswerMapper.selectObjectById(id));
	}

	/**
	 * 课堂表现
	 * @author zhouyl加
	 * @date 2020年12月21日
	 * @param jsonObject
	 * @param loginUserId 登录用户id
	 * @return
	 */
	@Override
	@PostMapping("launchAnswer")
	public R launchAnswer(@RequestBody JSONObject jsonObject, String loginUserId) {
		String ctId = jsonObject.getString("ctId"); // 课堂id
		String pkgId = jsonObject.getString("pkgId"); // 教学包id
		// String activityType = jsonObject.getString("activityType"); // 活动类型
		Integer answerNum = jsonObject.getInteger("answerNum"); // 抢答人数
		Integer type = jsonObject.getInteger("type"); // 课堂表现类型：1 抢答、2 随机选人、3 手动选人
		String answerTitle = jsonObject.getString("answerTitle"); // 抢答活动标题
		
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId) || StrUtils.isEmpty(answerTitle)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroom == null) {
			return R.error("无效的记录，请刷新后重试");
		}
		// 权限判断，用于课堂创建者与课堂助教
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(classroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_ADD);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		// 判断当前登录人是否是创建课堂的创建人,如果不是则提示
		/*if (!loginUserId.equals(classroom.getCreateUserId())) {
			return R.error("你不是课堂创建人，不能新增课堂表现");
		}*/
		/*if ("1".equals(classroom.getClassroomState())) {
			return R.error("课堂尚未开始，请开始课堂后发起抢答");
		}
		if ("3".equals(classroom.getClassroomState())) {
			return R.error("课堂已结束，发起抢答无效");
		}*/
		Map<String, Object> params = new HashMap<>();
		// 无法创建抢答标题一样的活动
		params.put("answerTitle", answerTitle);
		params.put("ctId", ctId);
		List<TevglTchRoomPereAnswer> pereAnswers = tevglTchRoomPereAnswerMapper.selectListByMap(params);
		if (pereAnswers != null && pereAnswers.size() > 0) {
			return R.error("抢答标题已存在，请重新命名");
		}
		// 课堂表现的抢答人数做限制，不能超过课堂的成员人数
		// 1抢答2随机点名3手动选人
		if (1 == type) {
			params.clear();
			params.put("ctId", ctId);
			List<TevglTchClassroomTrainee> classroomTrainees = tevglTchClassroomTraineeMapper.selectListByMap(params);
			if (classroomTrainees != null && classroomTrainees.size() > 0 && answerNum > classroomTrainees.size()) {
				return R.error("抢答人数已超过成员人数，最多" + classroomTrainees.size() + "人");
			}
		}
		// 填充信息
		TevglTchRoomPereAnswer pereAnswer = new TevglTchRoomPereAnswer();
		pereAnswer.setAnswerId(Identities.uuid());
		pereAnswer.setAnswerTitle(answerTitle);
		pereAnswer.setAnswerNum(answerNum);
		pereAnswer.setEmpiricalValue(0);
		pereAnswer.setCreateUserId(loginUserId);
		pereAnswer.setCreateTime(DateUtils.getNowTimeStamp());
		pereAnswer.setCtId(classroom.getCtId());
		pereAnswer.setPkgId(pkgId);
		pereAnswer.setResgroupId(GlobalActivity.ACTIVITY_6_CLASSROOM_PERFORMANCE);
		pereAnswer.setType(type);
		pereAnswer.setState("Y");
		tevglTchRoomPereAnswerMapper.insert(pereAnswer);
		// 保存教学包与活动之间的关系
		pkgUtils.buildRelation(pkgId, pereAnswer.getAnswerId(), GlobalActivity.ACTIVITY_6_CLASSROOM_PERFORMANCE);
		// 更新教学包活动数量
		pkgUtils.plusPkgActivityNum(pkgId);
		return R.ok("成功发起抢答").put(Constant.R_DATA, pereAnswer);
	}

	/**
	 * 修改抢答
	 * @param jsonObject
	 * @author zhouyl加
	 * @date 2020年12月22日
	 * @param loginUserId 登录用户id
	 * @return
	 */
	@Override
	@PostMapping("updateAnswer")
	public R updateAnswer(@RequestBody JSONObject jsonObject, String loginUserId) {
		String pkgId = jsonObject.getString("pkgId");
		String answerId = jsonObject.getString("answerId");
		Integer answerNum = jsonObject.getInteger("answerNum");
		Integer type = jsonObject.getInteger("type");
		String answerTitle = jsonObject.getString("answerTitle");
		if (StrUtils.isEmpty(answerId) || StrUtils.isEmpty(loginUserId) || StrUtils.isEmpty(answerTitle)) {
			return R.error("必传参数为空");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pkgId", pkgId);
		params.put("activityId", answerId);
		// 根据活动id(抢答id)和教学包id查询课堂抢答信息
		TevglTchRoomPereAnswer roomPereAnswer = tevglTchRoomPereAnswerMapper.selectObjectByIdAndPkgId(params);
		if (roomPereAnswer == null) {
			return R.error("无效的记录，请刷新后重试");
		}
		if ("1".equals(roomPereAnswer.getActivityStateActual())) {
			return R.error("活动已开始，无法修改");
		}
		if ("2".equals(roomPereAnswer.getActivityStateActual())) {
			return R.error("活动已结束，无法修改");
		}
		// 权限校验
		/*if (!pkgPermissionUtils.hasPermissionA(pkgId, loginUserId, null)) {
			return R.error("暂无权限，无法修改此活动");
		}*/
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectByPkgId(pkgId);
		if (tevglTchClassroom != null) {
			// 权限判断，用于课堂创建者与课堂助教
			boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_ADD);
			if (!hasOperationBtnPermission) {
				return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
			}	
		}
		// 判断此章节下是否生成了[活动]分组,如果没有则生成
		TevglPkgInfo pkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
		if (pkgInfo == null) {
			return R.error("无效的教学包记录");
		}
		String refPkgId = pkgInfo.getPkgId();
		if (StrUtils.isNotEmpty(pkgInfo.getRefPkgId())) {
			refPkgId = pkgInfo.getRefPkgId();
		}
		// 若该章节下没有[活动分组]，则默认创建一个分组（使用情形：新增修改活动时）
		pkgUtils.createDefaultActivityTab(refPkgId, null, loginUserId);
		
		// 填充信息
		TevglTchRoomPereAnswer pereAnswer = new TevglTchRoomPereAnswer();
		pereAnswer.setAnswerId(answerId);
		pereAnswer.setAnswerTitle(answerTitle);
		pereAnswer.setAnswerNum(answerNum);
		pereAnswer.setUpdateUserId(loginUserId);
		pereAnswer.setUpdateTime(DateUtils.getNowTimeStamp());
		pereAnswer.setType(type);
		tevglTchRoomPereAnswerMapper.update(pereAnswer);
		return R.ok("修改成功");
	}
	
	/**
	 * 根据课堂id查询加入课堂的所有成员
	 * @param ctId 课堂id
	 * @return
	 */
	public List<String> classroomTrainees(String ctId){
		List<String> traineeIds = new ArrayList<String>();
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		params.put("state", "Y");
		// 根据课堂id查询课堂成员表信息
		List<TevglTchClassroomTrainee> classroomTrainees = tevglTchClassroomTraineeMapper.selectListByMap(params);
		if (classroomTrainees == null || classroomTrainees.size() == 0) {
			return traineeIds;
		}
		// 得到所有的成员id
		traineeIds = classroomTrainees.stream().map(x -> x.getTraineeId()).collect(Collectors.toList());
		return traineeIds;
	}
	
	/**
	 * 开始活动
	 * @author zhouyl加
	 * @date 2020年12月22日
	 * @param ctId 课堂id
	 * @param activityId 活动id(抢答id)
	 * @param loginUserId 登录用户id
	 * @param activityEndTime 结束时间
	 * @return
	 */
	@Override
	@PostMapping("startAnswerActivity")
	public R startAnswerActivity(String ctId, String activityId, String loginUserId, String activityEndTime) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(activityId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroom == null) {
			return R.error("无效的记录，请刷新后重试");
		}
		if ("1".equals(classroom.getClassroomState())) {
			return R.error("课堂尚未开始，无法开始活动");
		}
		if ("3".equals(classroom.getClassroomState())) {
			return R.error("课堂已结束，无法开始活动");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("pkgId", classroom.getPkgId());
		params.put("activityId", activityId);
		// 根据教学包id和活动id查询抢答信息
		TevglTchRoomPereAnswer roomPereAnswer = tevglTchRoomPereAnswerMapper.selectObjectByIdAndPkgId(params);
		if (roomPereAnswer == null) {
			return R.error("无效的活动，请刷新后重试");
		}
		if ("1".equals(roomPereAnswer.getActivityStateActual())) {
			return R.error("活动已开始");
		}
		if ("2".equals(roomPereAnswer.getActivityStateActual())) {
			return R.error("活动已结束");
		}
		
		Map<String, Object> ps = new HashMap<>();
		ps.put("pkgId", classroom.getPkgId());
		ps.put("activityId", activityId);
		List<TevglPkgActivityRelation> relations = tevglPkgActivityRelationMapper.selectListByMap(ps);
		if (relations == null || relations.size() == 0) {
			return R.error("没有权限");
		}
		TevglPkgActivityRelation relation = relations.get(0);
		/*if (!pkgActivityUtils.hasStartActPermission(activityId, relation, loginUserId, roomPereAnswer.getCreateUserId())) {
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
		Map<String, Object> data = new HashMap<>();
		data.put("empiricalValue", roomPereAnswer.getEmpiricalValue());
		data.put("activityType", GlobalActivity.ACTIVITY_6_CLASSROOM_PERFORMANCE);
		data.put("activityId", activityId);
		data.put("activityTitle", roomPereAnswer.getAnswerTitle());
		data.put("content", null);
		
		// 属于课堂表现活动中，学员抢答，才推送
		if (1 == roomPereAnswer.getType()) {
			// 往redis中插入可抢答人数（即库存）
			redisTemplate.opsForValue().set(getMyRedisKey(ctId, activityId), String.valueOf(roomPereAnswer.getAnswerNum()));
			// ================================================== 即时通讯相关处理 begin ==================================================
			// ================================================== 即时通讯相关处理 end ==================================================
		}
		return R.ok("开始抢答").put(Constant.R_DATA, data);
	}
	
	/**
	 * 即时通讯
	 * @param ctId 课堂id
	 * @param classId 班级id
	 * @param pkgId 教学包id
	 * @param activityId 活动id(抢答id/随机选人id/手动选人id)
	 * @param params
	 * @param loginUserId 登录用户id
	 */
	public void doSendIm(String ctId, String classId, String pkgId, String activityId, Map<String, Object> params, String loginUserId, String activityTitle, String subjectId) {
		// ================================================== 即时通讯相关处理 begin ==================================================

		// ================================================== 即时通讯相关处理 end ==================================================
	}

	/**
	 * @author zhouyl加
	 * @date 2020年12月22日
	 * @param ctId 课堂id
	 * @param activityId 活动id(随机选人id)
	 * @param loginUserId 登录用户id
	 * @return
	 */
	@Override
	@PostMapping("endAnswerActivity")
	public R endAnswerActivity(String ctId, String activityId, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(activityId) || StrUtils.isEmpty(loginUserId) ) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroom == null) {
			return R.error("无效的记录，请刷新后重试");
		}
		if ("1".equals(classroom.getClassroomState())) {
			return R.error("课堂尚未开始，结束活动无效");
		}
		if ("3".equals(classroom.getClassroomState())) {
			return R.error("课堂已结束，无法结束活动");
		}
		TevglTchRoomPereAnswer pereAnswer = tevglTchRoomPereAnswerMapper.selectObjectById(activityId);
		if ("0".equals(pereAnswer.getActivityStateActual())) {
			return R.error("活动未开始");
		}
		if ("2".equals(pereAnswer.getActivityStateActual())) {
			return R.error("活动已结束");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("pkgId", classroom.getPkgId());
		params.put("activityId", activityId);
		List<TevglPkgActivityRelation> relations = tevglPkgActivityRelationMapper.selectListByMap(params);
		if (relations == null || relations.size() == 0) {
			return R.error("没有权限");
		}
		/*if (!pkgActivityUtils.hasStartActPermission(activityId, relation, loginUserId, classroom.getCreateUserId())) {
			return R.error("暂无权限，操作失败");
		}*/
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(classroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_END);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		// 调整为修改关联表的状态
		TevglPkgActivityRelation relation = relations.get(0);
		relation.setActivityState("2"); // 0未开始1进行中2已结束
		relation.setActivityEndTime(DateUtils.getNowTimeStamp());
		tevglPkgActivityRelationMapper.update(relation);
		// 返回数据
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("activityId", activityId);
		data.put("activityTitle", pereAnswer.getAnswerTitle());
		return R.ok("成功结束抢答活动").put(Constant.R_DATA, data);
	}

	/**
	 * 手动选人以及随机选人
	 * @author zhouyl加
	 * @date 2020年12月23日
	 * @param jsonObject
	 * @param loginUserId 登录用户id
	 * @return
	 */
	@Override
	@PostMapping("selectPeopleToAnswer")
	public R selectPeopleToAnswer(@RequestBody(required = true) JSONObject jsonObject, String loginUserId) {
		// 评分
		JSONArray traineeScore = jsonObject.getJSONArray("traineeScore");
		if (traineeScore == null || traineeScore.size() == 0) {
			return R.error("没有评分，请对抢答了的学员进行评分");
		}
		// ["ctId" : "", "activityId" : "", "traineeScore": [{"traineeId" : "", "score" : null}]]
		String ctId = jsonObject.getString("ctId");
		String activityId = jsonObject.getString("activityId");
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(activityId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		
		TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroom == null) {
			return R.error("无效的记录，请刷新后重试");
		}
		if ("1".equals(classroom.getClassroomState())) {
			return R.error("课堂尚未开始，无法抢答");
		}
		if ("3".equals(classroom.getClassroomState())) {
			return R.error("课堂已结束，抢答活动无效");
		}
		// 权限判断，用于课堂创建者与课堂助教、课堂接管者
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(classroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_ADD);
		if (!hasOperationBtnPermission) {
			return R.error("你不是课堂创建者，无法发起抢答");
		}
		// 根据活动id查询抢答信息，判断该活动是否已开始
		TevglTchRoomPereAnswer pereAnswer = tevglTchRoomPereAnswerMapper.selectObjectById(activityId);
		if ("0".equals(pereAnswer.getActivityStateActual())) {
			return R.error("活动未开始");
		}
		if ("2".equals(pereAnswer.getActivityStateActual())) {
			return R.error("活动已结束，无法评分");
		}
		// 教师手动录入分数
		doTeacherEnterScore(traineeScore, ctId, activityId, loginUserId, pereAnswer.getType());
		
		Map<String, Object> params = new HashMap<>();
		params.put("pkgId", classroom.getPkgId());
		params.put("activityId", activityId);
		List<TevglPkgActivityRelation> relations = tevglPkgActivityRelationMapper.selectListByMap(params);
		if (relations == null || relations.size() == 0) {
			return R.error("没有权限");
		}
		TevglPkgActivityRelation relation = relations.get(0);
		
		// 调整为修改关联表的状态
		relation.setActivityState("2"); // 0未开始1进行中2已结束
		relation.setActivityEndTime(DateUtils.getNowTimeStamp());
		tevglPkgActivityRelationMapper.update(relation);
		
		return R.ok("评分结束");
	}
	
	/**
	 * 教师手动录入分数
	 * @param traineeScore 
	 * @param activityId 活动id
	 * @param loginUserId 登录用户id
	 */
	private void doTeacherEnterScore(JSONArray traineeScore, String ctId, String activityId, String loginUserId, Integer type) {
		if (traineeScore == null) {
			return;
		}
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		params.put("activityId", activityId);
		List<TevglTchRoomPereTraineeAnswer> traineeAnswers = tevglTchRoomPereTraineeAnswerMapper.selectListByMap(params);
		List<TevglTchRoomPereTraineeAnswer> list = new ArrayList<>();
		List<TevglTchRoomPereTraineeAnswer> updateList = new ArrayList<>();
		for (int i = 0; i < traineeScore.size(); i++) {
			// [{"traineeId" : "", "score" : null}]
			JSONObject traineeInfo = traineeScore.getJSONObject(i);
			String traineeId = traineeInfo.getString("traineeId");
			Integer score = traineeInfo.getInteger("score");
			// 通过学员id关联得到学员抢答表中的主键id
			List<TevglTchRoomPereTraineeAnswer> datas = traineeAnswers.stream().filter(a -> a.getTraineeId().equals(traineeId)).collect(Collectors.toList());
			String traineeAnswerId = null;
			// 判断：当datas不为空时就去活动datas中的主键id
			if (datas != null && datas.size() > 0) {
				traineeAnswerId = datas.get(0).getTraineeAnswerId();
			}
			// type为1时，当前为学员抢答，对已存在的学员进行修改操作
			TevglTchRoomPereTraineeAnswer traineeAnswer = new TevglTchRoomPereTraineeAnswer();
			if (type == 1) { 
				// 填充信息
				traineeAnswer.setTraineeAnswerId(traineeAnswerId);
				traineeAnswer.setTraineeEmpiricalValue(score);
				traineeAnswer.setUpdateUserId(loginUserId);
				traineeAnswer.setUpdateTime(DateUtils.getNowTimeStamp());
				updateList.add(traineeAnswer);
			}else {
				// 填充信息
				traineeAnswer.setTraineeAnswerId(Identities.uuid());
				traineeAnswer.setCtId(ctId);
				traineeAnswer.setTraineeId(traineeId);
				traineeAnswer.setAnserId(activityId);
				traineeAnswer.setTraineeEmpiricalValue(score);
				traineeAnswer.setCreateUserId(loginUserId);
				traineeAnswer.setCreateTime(DateUtils.getNowTimeStamp());
				// 存入集合
				list.add(traineeAnswer);
			}
		}
		// 批量更新
		if (updateList.size() > 0) {
			tevglTchRoomPereTraineeAnswerMapper.updateBatchByCaseWhen(updateList);
		}
		// type不为1时，当前为手动选人或随机选人，评分完后执行新增操作
		if (!type.equals(1)) { 
			params.put("list", list);
			tevglTchRoomPereTraineeAnswerMapper.batchInsert(params);
		}
		// 再次根据课堂id和活动id查询学员抢答信息
		traineeAnswers = tevglTchRoomPereTraineeAnswerMapper.selectListByMap(params);
		if (traineeAnswers != null && traineeAnswers.size() > 0) {
			// 记录学员经验获取日志
		}
	}

	/**
	 * 查询该课堂下的所有成员不分页
	 * @author zhouyl加
	 * @date 2020年12月23日
	 * @param ctId 课堂id
	 * @param loginUserId 登录用户id
	 * @return
	 */
	@Override
	@GetMapping("selectClassroomTraineeInfo")
	public R selectClassroomTraineeInfo(String ctId, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroom == null) {
			return R.error("无效的记录，请刷新后重试");
		}
		// 权限判断，用于课堂创建者与课堂助教、课堂接管者
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(classroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_ADD);
		if (!hasOperationBtnPermission) {
			return R.error("你不是课堂创建者，没有成员信息");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		List<Map<String, Object>> list = tevglTchClassroomTraineeMapper.selectListMapForWeb(params);
		list.stream().forEach(a -> {
			// 头像处理
			String traineePic = (String) a.get("traineePic");
			if (StrUtils.isNotEmpty(traineePic) && !"null".equals(traineePic)) {
				a.put("traineePic", uploadPathUtils.stitchingPath(traineePic, "16"));
			}
		});
		return R.ok().put(Constant.R_DATA, list);
	}
	
	/**
	 * 删除抢答活动
	 * @author zhouyl加
	 * @date 2020年12月23日
	 * @param activityId 活动id
	 * @param pkgId 教学包id
	 * @param loginUserId 登录用户id
	 * @return
	 * @throws CreatorblueException
	 */
	@Override
	@PostMapping("deleteAnswerActivity")
	public R deleteAnswerActivity(String activityId, String pkgId, String loginUserId) throws CreatorblueException {
		if (StrUtils.isEmpty(activityId) || StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("activityId", activityId);
		params.put("pkgId", pkgId);
		TevglTchRoomPereAnswer pereAnswer = tevglTchRoomPereAnswerMapper.selectObjectByIdAndPkgId(params);
		if (pereAnswer == null) {
			return R.error("无效的记录，请刷新后重试");
		}
		if (!"0".equals(pereAnswer.getActivityStateActual())) {
			return R.error("当前活动已被使用,无法删除");
		}
		/*if (!pkgPermissionUtils.hasPermissionDeleteActivity(pkgId, pereAnswer.getCreateUserId(), loginUserId, null)) {
			return R.error("暂无权限，操作失败");
		}*/
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectByPkgId(pkgId);
		if (tevglTchClassroom != null) {
			// 权限判断，用于课堂创建者与课堂助教
			boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_ADD);
			if (!hasOperationBtnPermission) {
				return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
			}
		}
		// 删除活动与教学包的关系
		tevglPkgActivityRelationMapper.deleteByActivityId(activityId);
		// 删除活动
		tevglTchRoomPereAnswerMapper.delete(pereAnswer);
		// 教学包活动数量-1
		pkgUtils.plusPkgActivityReduceNum(pkgId);
		return R.ok("删除成功");
	}
	
	/**
	 * 【教师端】查询课堂下抢答活动的抢答人员
	 * @param ctId 课堂id
	 * @param activityId 活动id
	 * @param loginUserId 登录用户id
	 * @return
	 */
	@Override
	@GetMapping("answerActivityTraineeInfo")
	public R answerActivityTraineeInfo(String ctId, String activityId, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(activityId) || StrUtils.isEmpty(loginUserId) ) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroom == null) {
			return R.error("无效的记录，请刷新后重试");
		}
		if ("1".equals(classroom.getClassroomState())) {
			return R.error("课堂尚未开始，无法查看抢答活动的学员");
		}
		if ("3".equals(classroom.getClassroomState())) {
			return R.error("课堂已结束，抢答活动无效");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		params.put("activityId", activityId);
		// 根据课堂id以及活动id查询学员抢答信息
		List<TevglTchRoomPereTraineeAnswer> traineeAnswers = tevglTchRoomPereTraineeAnswerMapper.selectListByMap(params);
		List<String> traineeIds = traineeAnswers.stream().map(a -> a.getTraineeId()).collect(Collectors.toList());
		List<Map<String, Object>> answerTrainees = new ArrayList<>();
		// 新增判断：当学员id有值时显示抢答了活动的学员信息，防止学员id为空时显示全部学员
		if (traineeIds != null && traineeIds.size() > 0) {
			params.clear();
			params.put("ctId", ctId);
			params.put("traineeIds", traineeIds);
			// 根据课堂id以及学员id查询参与了课堂抢答活动的学员信息
			answerTrainees = tevglTchClassroomTraineeMapper.selectListMapForWeb(params);
			answerTrainees.stream().forEach(a -> {
				// 头像处理
				String traineePic = (String) a.get("traineePic");
				if (StrUtils.isNotEmpty(traineePic) && !"null".equals(traineePic)) {
					a.put("traineePic", uploadPathUtils.stitchingPath(traineePic, "16"));
				}
			});
		}
		
		return R.ok().put(Constant.R_DATA, answerTrainees);
	}
	
	/**
	 * 查询抢答活动的总体结果
	 * @author zhouyl加
	 * @date 2020年12月26日
	 * @param ctId 课堂id
	 * @param activityId 活动id
	 * @param loginUserId 登录用户id
	 * @return
	 */
	@Override
	@GetMapping("answerSummaryResults")
	public R answerSummaryResults(String ctId, String activityId, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(activityId) || StrUtils.isEmpty(loginUserId) ) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return R.error("无法查看");
		}
		// 当前登录用户是否为
		boolean isRoomCreator = loginUserId.equals(tevglTchClassroom.getCreateUserId());
		boolean isTeachingAssistant = StrUtils.isNotEmpty(tevglTchClassroom.getTraineeId()) && tevglTchClassroom.getTraineeId().equals(loginUserId);
		Map<String, Object> params = new HashMap<>();
		// 根据活动id查询抢答活动信息
		params.put("state", "Y");
		List<Map<String, Object>> answerTrainees = new ArrayList<>();
		params.put("activityId", activityId);
		params.put("ctId", ctId);
		List<String> traineeIds = tevglTchRoomPereTraineeAnswerMapper.answerSummaryResults(params);
		if (traineeIds != null && traineeIds.size() > 0) {
			// params.clear();
			// params.put("ctId", ctId);
			// params.put("traineeIds", traineeIds);
			// 查询抢答活动的总体结果按创建时间降序排序
			params.put("sidx", "t4.create_time");
			params.put("order", "desc");
			// 根据课堂id以及学员id查询参与了课堂抢答活动的学员信息
			answerTrainees = tevglTchClassroomTraineeMapper.queryAnswerResults(params);
			answerTrainees.stream().forEach(a -> {
				// 头像处理
				String traineePic = (String) a.get("traineePic");
				if (StrUtils.isNotEmpty(traineePic) && !"null".equals(traineePic)) {
					a.put("traineePic", uploadPathUtils.stitchingPath(traineePic, "16"));
				}
				if (!isRoomCreator && !isTeachingAssistant) {
					a.remove("mobile");
				}
			});
		}
		
		return R.ok().put(Constant.R_DATA, answerTrainees);
	}

	/**
	 * 抢答活动中的key
	 * @param ctId
	 * @param activityId
	 * @return 示例结果：activity:6_classroom_performance:c1e0397b5737483c8915b958f81b46c9_263148468eda4619ba4763be58ccf2e5
	 */
	@Override
	public String getMyRedisKey(String ctId, String activityId) {
		ctId = StrUtils.isEmpty(ctId) ? "课堂id" : ctId;
		activityId = StrUtils.isEmpty(activityId) ? "活动id" : activityId;
		return "activity:6_classroom_performance:limit_num_" + ctId + "_" + activityId;
	}
}
