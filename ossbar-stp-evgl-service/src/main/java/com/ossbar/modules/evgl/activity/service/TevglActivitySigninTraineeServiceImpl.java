package com.ossbar.modules.evgl.activity.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomTraineeMapper;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeEmpiricalValueLogMapper;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbRoomUtils;
import com.ossbar.modules.common.DateUtil;
import com.ossbar.modules.common.GlobalActivity;
import com.ossbar.modules.common.GlobalRoomPermission;
import com.ossbar.modules.common.UploadPathUtils;
import com.ossbar.modules.common.enums.BizCodeEnume;
import com.ossbar.modules.evgl.activity.api.TevglActivitySigninTraineeService;
import com.ossbar.modules.evgl.activity.domain.TevglActivitySigninInfo;
import com.ossbar.modules.evgl.activity.domain.TevglActivitySigninTrainee;
import com.ossbar.modules.evgl.activity.persistence.TevglActivitySigninInfoMapper;
import com.ossbar.modules.evgl.activity.persistence.TevglActivitySigninTraineeMapper;
import com.ossbar.modules.evgl.empirical.persistence.TevglEmpiricalSettingMapper;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeEmpiricalValueLog;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.github.pagehelper.PageHelper;

/**
 * <p> Title: 学员活动签到信息表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/activity/tevglactivitysignintrainee")
public class TevglActivitySigninTraineeServiceImpl implements TevglActivitySigninTraineeService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglActivitySigninTraineeServiceImpl.class);
	@Autowired
	private TevglActivitySigninTraineeMapper tevglActivitySigninTraineeMapper;
	@Autowired
	private TevglActivitySigninInfoMapper tevglActivitySigninInfoMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private TevglTchClassroomTraineeMapper tevglTchClassroomTraineeMapper;
	@Autowired
	private UploadPathUtils uploadPathUtils;
	@Autowired
	private TsysAttachService tsysAttachService;
	@Autowired
	private TevglTraineeEmpiricalValueLogMapper tevglTraineeEmpiricalValueLogMapper;
	@Autowired
	private TevglEmpiricalSettingMapper tevglEmpiricalSettingMapper;
	@Autowired
	private CbRoomUtils cbRoomUtils;
	
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/activity/tevglactivitysignintrainee/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglActivitySigninTrainee> tevglActivitySigninTraineeList = tevglActivitySigninTraineeMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglActivitySigninTraineeList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/activity/tevglactivitysignintrainee/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglActivitySigninTraineeList = tevglActivitySigninTraineeMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglActivitySigninTraineeList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglActivitySigninTrainee
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/activity/tevglactivitysignintrainee/save")
	public R save(@RequestBody(required = false) TevglActivitySigninTrainee tevglActivitySigninTrainee) throws OssbarException {
		tevglActivitySigninTrainee.setStId(Identities.uuid());
		ValidatorUtils.check(tevglActivitySigninTrainee);
		tevglActivitySigninTraineeMapper.insert(tevglActivitySigninTrainee);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglActivitySigninTrainee
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/activity/tevglactivitysignintrainee/update")
	public R update(@RequestBody(required = false) TevglActivitySigninTrainee tevglActivitySigninTrainee) throws OssbarException {
	    ValidatorUtils.check(tevglActivitySigninTrainee);
		tevglActivitySigninTraineeMapper.update(tevglActivitySigninTrainee);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/activity/tevglactivitysignintrainee/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglActivitySigninTraineeMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/activity/tevglactivitysignintrainee/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglActivitySigninTraineeMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/activity/tevglactivitysignintrainee/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglActivitySigninTraineeMapper.selectObjectById(id));
	}

	/**
	 * 根据课堂id和活动id等查询已签到人员
	 * @param params
	 * @return
	 */
	@Override
	public R listActivitySigninTrainee(Map<String, Object> params) {
		String ctId = (String) params.get("ctId");
		String activityId = (String) params.get("activityId");
		if (StrUtils.isEmpty(activityId) || StrUtils.isEmpty(ctId)) {
			return R.error("必传参数为空");
		}
		
		return R.ok();
	}

	/**
	 * 学员签到
	 * @param tevglActivitySigninTrainee
	 * @param loginUserId
	 * @return
	 * @throws OssbarException
	 */
	@Override
	public R signIn(TevglActivitySigninTrainee tevglActivitySigninTrainee, String loginUserId) throws OssbarException {
		String ctId = tevglActivitySigninTrainee.getCtId(); // 课堂
		String activityId = tevglActivitySigninTrainee.getActivityId(); // 活动主键
		String signType = tevglActivitySigninTrainee.getSignType(); // 签到类型
		if (StrUtils.isEmpty(loginUserId) || StrUtils.isEmpty(activityId)
				|| StrUtils.isEmpty(ctId) || StrUtils.isEmpty(signType)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom tchClassroomInfo = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tchClassroomInfo == null) {
			return R.error("无效的课堂，请刷新后重试");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("pkgId", tchClassroomInfo.getPkgId());
		params.put("activityId", activityId);
		TevglActivitySigninInfo activityInfo = tevglActivitySigninInfoMapper.selectObjectByIdAndPkgId(params);
		if (activityInfo == null) {
			return R.error("无效的签到活动，请刷新后重试");
		}
		if (!"1".equals(activityInfo.getActivityStateActual())) {
			return R.error("无法签到，签到未开始或者已经结束了");
		}
		// 如果为手势签到,则数字顺序不能为空
		if ("2".equals(tevglActivitySigninTrainee.getSignType())) {
			if (StrUtils.isEmpty(tevglActivitySigninTrainee.getNumber())) {
				return R.error("请完成手势签到");
			}
			if (!tevglActivitySigninTrainee.getNumber().equals(activityInfo.getNumber())) {
				return R.error("手势动作不正确，请重新尝试");
			}
		}
		// 非法签到,不是本课堂的不允许签到
		Map<String, Object> map = new HashMap<>();
		map.put("ctId", ctId);
		List<Map<String,Object>> classroomTraineeList = tevglTchClassroomTraineeMapper.selectListMapForWeb(map);
		if (classroomTraineeList != null && classroomTraineeList.size() > 0) {
			boolean flag = classroomTraineeList.stream().anyMatch(a -> a.get("traineeId").equals(loginUserId));
			if (!flag) {
				return R.error("签到未成功，非法操作");
			}
		}
		// 如果已签到，
		map.clear();
		map.put("ctId", ctId);
		map.put("activityId", activityId);
		map.put("traineeId", loginUserId);
		List<TevglActivitySigninTrainee> signinTraineeList = tevglActivitySigninTraineeMapper.selectListByMap(map);
		if (signinTraineeList != null && signinTraineeList.size() > 0) {
			return R.ok("你已经签到成功了");
		}
		// 签到时,是否处于有效时间(手势签到、手工签到没有时间限制)
		if ("1".equals(signType)) {
			if (!isInTimeRange(activityInfo.getLimitTime(), activityInfo.getActivityBeginTime())) {
				return R.error("签到未成功，已经过了签到时间，下次记得早点~");
			}	
		}
		// 保存至数据库
		String uuid = Identities.uuid();
		tevglActivitySigninTrainee.setStId(uuid);
		tevglActivitySigninTrainee.setSignTime(com.ossbar.utils.tool.DateUtils.getNowTimeStamp());
		tevglActivitySigninTrainee.setSignState("1");
		tevglActivitySigninTrainee.setTraineeId(loginUserId);
		tevglActivitySigninTraineeMapper.insert(tevglActivitySigninTrainee);
		// 如果上传了附件
		String attachId = tevglActivitySigninTrainee.getAttachId();
		if (StrUtils.isEmpty(attachId)) {
			tsysAttachService.updateAttach(attachId, uuid, "1", "18");
		}
		// 更新签到人数
		TevglActivitySigninInfo tevglActivitySigninInfo = new TevglActivitySigninInfo();
		tevglActivitySigninInfo.setActivityId(activityId);
		tevglActivitySigninInfo.setTraineeNum(1);
		tevglActivitySigninInfoMapper.plusNum(tevglActivitySigninInfo);
		// 记录学员经验获取日志
		doRecordTraineeEmpiricalValueLog(ctId, activityId, loginUserId, activityInfo.getEmpiricalValue(), tevglActivitySigninTrainee.getSignState());
		JSONObject msg = new JSONObject();
		msg.put("busitype", "reloadtchsignlist");
		JSONObject busiMsg = new JSONObject();
		busiMsg.put("ctId", ctId);
		busiMsg.put("activityId", activityId);
		busiMsg.put("title", "当前有人在课堂 " + tchClassroomInfo.getName() + " 签到");
		msg.put("msg", busiMsg);
		String teacherId = StrUtils.isEmpty(tchClassroomInfo.getReceiverUserId()) ? tchClassroomInfo.getCreateUserId() : tchClassroomInfo.getReceiverUserId();
		return R.ok("签到成功").put(Constant.R_DATA, tevglActivitySigninTrainee.getStId());
	}
	
	/**
	 * 记录学员经验获取日志
	 * @param ctId 课堂id
	 * @param activityId 活动id
	 * @param loginUserId 当前登录用户
	 * @param empiricalValue 可获得的经验值
	 */
	private void doRecordTraineeEmpiricalValueLog(String ctId, String activityId, String loginUserId, Integer empiricalValue, String signState) {
		TevglTraineeEmpiricalValueLog ev = new TevglTraineeEmpiricalValueLog();
		ev.setEvId(Identities.uuid());
		ev.setActivityId(activityId);
		ev.setType(GlobalActivity.ACTIVITY_8_SIGININ_INFO);
		ev.setTraineeId(loginUserId);
		ev.setCtId(ctId);
		ev.setEmpiricalValue(toGetEmpiricalValue(ctId, signState));
		ev.setCreateTime(com.ossbar.utils.tool.DateUtils.getNowTimeStamp());
		ev.setState("Y");
		ev.setParams2(signState); 
		tevglTraineeEmpiricalValueLogMapper.insert(ev);
	}
	
	/**
	 * 获取经验值
	 * @param ctId
	 * @param signState 签到状态
	 * @return
	 */
	private Integer toGetEmpiricalValue(String ctId, String signState) {
		Map<String, Object> map = new HashMap<>();
		map.put("ctId", ctId);
		// 类型(来源字典1章节阅读加2查看视频加3查看音频加4正常签到一次加5迟到一次减6旷课一次减7请假一次减8参与投票问卷加9参与头脑风暴加10参与答疑讨论加)
		String dictCode = "";
		// 签到状态1正常2迟到3早退4旷课5请假
		switch (signState) {
		// 正常签到
		case "1":
			dictCode = "4";
			break;
		// 迟到一次
		case "2":
			dictCode = "5";
			break;
		// 早退	
		case "3":
			dictCode = "5";
		// 旷课	
		case "4":
			dictCode = "5";
		// 请假
		case "5":
			dictCode = "7";	
		break;
			default:
			break;
		}
		map.put("dictCode", dictCode); // 类型(来源字典1章节阅读加2查看视频加3查看音频加4正常签到一次加5迟到一次减6旷课一次减7请假一次减8参与投票问卷加9参与头脑风暴加10参与答疑讨论加)
		Integer empiricalValue = tevglEmpiricalSettingMapper.getEmpiricalValueByMap(map);
		if (Arrays.asList("2", "3", "4", "5").contains(signState)) {
			empiricalValue = empiricalValue == null ? 1 : empiricalValue;
			empiricalValue = -empiricalValue;
		}
		return empiricalValue;
	}
	
	/**
	 * 以签到活动的创建时间开始与签到时长计算，如果计算后的时间 < 当前时间 ，则说明已经过了签到的有效时间 
	 * @param limitTime 单位分钟
	 * @param createTime yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	private boolean isInTimeRange(Integer limitTime, String createTime) {
		if (limitTime != null && StrUtils.isNotEmpty(createTime)) {
			Date date = DateUtil.convertStringToDate(createTime);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MINUTE, limitTime);// 24小时制
			// 得到结算后的结果 yyyy-MM-dd HH:mm
	        date = cal.getTime();
	        if (date.before(new Date())) {
	        	return false;
	        }
		}		
		return true;
	}

	/**
	 * 根据条件查询成员
	 * @param  课堂主键
	 * @param  学员名称
	 * @param loginUserId 当前登录用户
	 * @return
	 */
	@Override
	@GetMapping("/queryTraineeList")
	public R queryTraineeList(@RequestParam Map<String, Object> params, String loginUserId) {
		if (params.get("ctId") == null || params.get("activityId") == null) {
			return R.error("必传参数为空");
		}
		String ctId = params.get("ctId").toString();
		String activityId = params.get("activityId").toString();
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return R.error("无效的课堂");
		}
		// 当前登录用户是否为
		boolean isRoomCreator = loginUserId.equals(tevglTchClassroom.getCreateUserId());
		boolean isTeachingAssistant = StrUtils.isNotEmpty(tevglTchClassroom.getTraineeId()) && tevglTchClassroom.getTraineeId().equals(loginUserId);
		// 查询此课堂所有成员
		params.put("sidx", "t1.create_time");
		params.put("order", "desc");
		List<Map<String, Object>> classroomTraineeList = tevglTchClassroomTraineeMapper.selectListMapForWeb(params);
		// 没有成员则直接返回
		if (classroomTraineeList == null || classroomTraineeList.size() == 0) {
			return R.ok().put(Constant.R_DATA, classroomTraineeList);
		}
		// 全部默认为未签到
		classroomTraineeList.stream().forEach(classroomTrainee -> {
			classroomTrainee.put("signState", "0");
			classroomTrainee.put("signStateName", "未签到成员");
			// 头像处理
			classroomTrainee.put("traineePic", uploadPathUtils.stitchingPath(classroomTrainee.get("traineePic"), "16"));
		});
		// 查询学员的签到记录
		params.clear();
		params.put("ctId", ctId);
		params.put("activityId", activityId);
		params.put("sidx", "dict.dict_code");
		params.put("order", "asc, sign_time desc");
		List<Map<String, Object>> traineeList = tevglActivitySigninTraineeMapper.selectListMapForCommon(params);
		// 如果有
		if (traineeList != null && traineeList.size() > 0) {
			classroomTraineeList.stream().forEach(traineeInfo -> {
				// 头像处理
				traineeInfo.put("traineePic", uploadPathUtils.stitchingPath(traineeInfo.get("traineePic"), "16"));
				// 遍历签到记录
				traineeList.stream().forEach(a -> {
					// 如果成员此课堂，此活动有记录
					if (traineeInfo.get("traineeId").equals(a.get("traineeId"))
							&& a.get("ctId").equals(ctId)
							&& a.get("activityId").equals(activityId)) {
						traineeInfo.put("signState", a.get("signState"));
						traineeInfo.put("signStateName", a.get("signStateName"));
						traineeInfo.put("createTime", a.get("signTime"));
						traineeInfo.put("signTime", a.get("signTime"));
					}
				});
				if (!isRoomCreator && !isTeachingAssistant) {
					traineeInfo.remove("mobile");
				}
			});
		}
		// 根据签到类型分组
		Function<Map<String,Object>, String> s = new Function<Map<String,Object>, String>() {
			@Override
			public String apply(Map<String, Object> t) {
				Object object = t.get("signStateName");
				String string = object.toString();
				return string;
			}
		};
		Map<String, List<Map<String, Object>>> collect = classroomTraineeList.stream().collect(Collectors.groupingBy(s));
		// 进一步处理
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for (Entry<String, List<Map<String, Object>>> m : collect.entrySet()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("signStateName", m.getKey());
			map.put("children", m.getValue());
			result.add(map);
		}
		int index = 0;
		for (int i = 0; i < result.size(); i++) {
			if ("未签到成员".equals(result.get(i).get("signStateName"))) {
				index = i;
			}
		}
		if (index != 0) {
			result.add(0, result.get(index));
			result.remove(index + 1);
		}
		return R.ok().put(Constant.R_DATA, result);
	}

	/**
	 * 教师批量设置学员签到状态
	 * @param jsonObject
	 * @param loginUserId
	 * @return
	 */
	@Override
	@PostMapping("/setTraineeSignState")
	public R setTraineeSignState(@RequestBody JSONObject jsonObject, String loginUserId) throws OssbarException {
		String ctId = jsonObject.getString("ctId"); // 课堂主键
		String activityId = jsonObject.getString("activityId"); // 活动主键
		String signState = jsonObject.getString("signState"); // 签到状态1正常2迟到3早退4旷课5请假
		JSONArray jsonArray = jsonObject.getJSONArray("traineeIds"); // 
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(activityId)
				|| StrUtils.isEmpty(signState)  || StrUtils.isEmpty(loginUserId)) {
			return R.error(BizCodeEnume.PARAM_MISSING.getCode(), BizCodeEnume.PARAM_MISSING.getMsg());
		}
		if (jsonArray == null || jsonArray.size() == 0) {
			return R.error("请选择成员");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return R.error(BizCodeEnume.INEFFECTIVE_CLASSROOM.getCode(), BizCodeEnume.INEFFECTIVE_CLASSROOM.getMsg());
		}
		// 如果没有权限
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.ROOM_PERM_ACT_START);
		if (!hasOperationBtnPermission) {
			return R.error(BizCodeEnume.WITHOUT_PERMISSION.getCode(), BizCodeEnume.WITHOUT_PERMISSION.getMsg());
		}
		Map<String, Object> params = new HashMap<>();
		params.put("pkgId", tevglTchClassroom.getPkgId());
		params.put("activityId", activityId);
		TevglActivitySigninInfo activityInfo = tevglActivitySigninInfoMapper.selectObjectByIdAndPkgId(params);
		if (activityInfo == null) {
			return R.error("无效的记录，请刷新后重试");
		}
		// 如果不是手工签到
		if (!"3".equals(activityInfo.getSignType())) {
			if ("0".equals(activityInfo.getActivityStateActual())) {
				return R.error("活动未开始，无法设置签到状态");
			}
			if ("3".equals(activityInfo.getActivityStateActual())) {
				return R.error("活动已结束，无法设置签到状态");
			}
		}
		Map<String, Object> map = new HashMap<>();
		List<String> traineeIds = jsonArray.stream().map(a -> (String)a).collect(Collectors.toList());
		if (traineeIds != null) {
			// 根据签到状态查询可得到经验值(可能为负数)
			Integer empiricalValue = toGetEmpiricalValue(ctId, signState);
			// 查询此课堂此活动的签到记录
			map.put("ctId", ctId);
			map.put("activityId", activityId);
			List<TevglActivitySigninTrainee> signinTraineeList = tevglActivitySigninTraineeMapper.selectListByMap(map);
			// 如果没有签到记录，直接生成
			if (signinTraineeList == null || signinTraineeList.size() == 0) {
				// 批量新增
				handleBatchInsert(tevglTchClassroom, activityInfo, traineeIds, signState, empiricalValue);
				// 更新签到人数
				TevglActivitySigninInfo sign = new TevglActivitySigninInfo();
				sign.setActivityId(activityId);
				sign.setTraineeNum(traineeIds.size());
				tevglActivitySigninInfoMapper.plusNum(sign);
			} else {
				map.clear();
				map.put("type", "8");
				map.put("ctId", ctId);
				map.put("activityId", activityId);
				List<TevglTraineeEmpiricalValueLog> traineeEmpiricalValueLogList = tevglTraineeEmpiricalValueLogMapper.selectListByMap(map);
				map.clear();
				map.put("ctId", ctId);
				map.put("activityId", activityId);
				List<TevglActivitySigninTrainee> allList = tevglActivitySigninTraineeMapper.selectListByMap(map);
				// 等待入库的数据
				List<String> stIds = new ArrayList<>();
				Map<String, Object> dataMap = new HashMap<>();
				List<TevglActivitySigninTrainee> insertList = new ArrayList<>();
				List<TevglTraineeEmpiricalValueLog> insertLogList = new ArrayList<>();
				List<TevglTraineeEmpiricalValueLog> updateLogList = new ArrayList<>();
				traineeIds.stream().forEach(traineeId -> {
					List<TevglActivitySigninTrainee> list = allList.stream().filter(a -> a.getTraineeId().equals(traineeId)).collect(Collectors.toList());
					if (list != null && list.size() > 0) {
						stIds.add(list.get(0).getStId());
					} else { // 否则，插入一条记录
						TevglActivitySigninTrainee t = new TevglActivitySigninTrainee();
						t.setStId(Identities.uuid());
						t.setCtId(ctId);
						t.setActivityId(activityId);
						t.setTraineeId(traineeId);
						t.setSignTime(com.ossbar.utils.tool.DateUtils.getNowTimeStamp());
						t.setSignType(activityInfo.getSignType());
						t.setSignState(signState);
						insertList.add(t);
					}
					// 同理更新日志
					List<TevglTraineeEmpiricalValueLog> collect = traineeEmpiricalValueLogList.stream().filter(a -> a.getTraineeId().equals(traineeId)).collect(Collectors.toList());
					if (collect != null && collect.size() > 0) {
						TevglTraineeEmpiricalValueLog valueLog = collect.get(0);
						valueLog.setEmpiricalValue(empiricalValue);
						valueLog.setParams2(signState);
						valueLog.setMsg("在课堂【" + tevglTchClassroom.getName() + "】签到活动中获得" + empiricalValue + "经验值");
						updateLogList.add(valueLog);
					} else {
						// 记录经验值日志
						TevglTraineeEmpiricalValueLog ev = new TevglTraineeEmpiricalValueLog();
						ev.setEvId(Identities.uuid());
						ev.setActivityId(activityId);
						ev.setType(GlobalActivity.ACTIVITY_8_SIGININ_INFO);
						ev.setTraineeId(traineeId);
						ev.setCtId(ctId);
						ev.setEmpiricalValue(empiricalValue);
						ev.setCreateTime(com.ossbar.utils.tool.DateUtils.getNowTimeStamp());
						ev.setState("Y");
						ev.setMsg("在课堂【" + tevglTchClassroom.getName() + "】签到活动中获得" + empiricalValue + "经验值");
						insertLogList.add(ev);
					}
				});
				// 批量更新签到记录
				if (stIds.size() > 0) {
					dataMap.put("stIds", stIds);
					dataMap.put("signState", signState);
					dataMap.put("signTime", com.ossbar.utils.tool.DateUtils.getNowTimeStamp());
					tevglActivitySigninTraineeMapper.updateBatchSignState(dataMap);
				}
				if (updateLogList.size() > 0) {
					tevglTraineeEmpiricalValueLogMapper.updateBatchByCaseWhen(updateLogList);
				}
				if (insertList.size() > 0) {
					tevglActivitySigninTraineeMapper.insertBatch(insertList);
					TevglActivitySigninInfo sign = new TevglActivitySigninInfo();
					sign.setActivityId(activityId);
					sign.setTraineeNum(insertList.size());
					tevglActivitySigninInfoMapper.plusNum(sign);
				}
				// 批量新增经验值日志
				if (insertLogList.size() > 0) {
					tevglTraineeEmpiricalValueLogMapper.insertBatch(insertLogList);
				}
			}
		}
		return R.ok("设置成功");
	}
	
	/**
	 * 批量新增签到记录、经验值日志
	 * @param tevglTchClassroom
	 * @param activityInfo
	 * @param traineeIds
	 * @param signState
	 * @param empiricalValue
	 */
	private void handleBatchInsert(TevglTchClassroom tevglTchClassroom, TevglActivitySigninInfo activityInfo, List<String> traineeIds, String signState, Integer empiricalValue) {
		String ctId = tevglTchClassroom.getCtId();
		Map<String, Object> map = new HashMap<>();
		map.put("type", "8");
		map.put("ctId", ctId);
		map.put("activityId", activityInfo.getActivityId());
		List<TevglTraineeEmpiricalValueLog> traineeEmpiricalValueLogList = tevglTraineeEmpiricalValueLogMapper.selectListByMap(map);
		List<TevglActivitySigninTrainee> insertList = new ArrayList<>();
		List<TevglTraineeEmpiricalValueLog> insertLogList = new ArrayList<>();
		traineeIds.stream().forEach(traineeId -> {
			TevglActivitySigninTrainee t = new TevglActivitySigninTrainee();
			t.setStId(Identities.uuid());
			t.setCtId(ctId);
			t.setActivityId(activityInfo.getActivityId());
			t.setTraineeId(traineeId);
			t.setSignTime(com.ossbar.utils.tool.DateUtils.getNowTimeStamp());
			t.setSignType(activityInfo.getSignType());
			t.setSignState(signState);
			insertList.add(t);
			// 记录经验值日志
			List<TevglTraineeEmpiricalValueLog> collect = traineeEmpiricalValueLogList.stream().filter(a -> a.getTraineeId().equals(traineeId)).collect(Collectors.toList());
			if (collect == null || collect.size() == 0) {
				TevglTraineeEmpiricalValueLog ev = new TevglTraineeEmpiricalValueLog();
				ev.setEvId(Identities.uuid());
				ev.setActivityId(activityInfo.getActivityId());
				ev.setType(GlobalActivity.ACTIVITY_8_SIGININ_INFO);
				ev.setTraineeId(traineeId);
				ev.setCtId(ctId);
				ev.setEmpiricalValue(empiricalValue);
				ev.setCreateTime(com.ossbar.utils.tool.DateUtils.getNowTimeStamp());
				ev.setState("Y");
				ev.setMsg("在课堂【" + tevglTchClassroom.getName() + "】的签到活动中，签到获得" + empiricalValue + "经验值");
				insertLogList.add(ev);
			}
		});
		// 批量新增签到记录
		if (insertList.size() > 0) {
			tevglActivitySigninTraineeMapper.insertBatch(insertList);
		}
		// 批量新增经验值日志
		if (insertLogList.size() > 0) {
			tevglTraineeEmpiricalValueLogMapper.insertBatch(insertLogList);
		}
	}

	/**
	 * 学员查看自己的签到信息
	 * @param ctId 课堂
	 * @param activityId 活动
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R viewTraineeSignInfo(String ctId, String activityId, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(activityId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglActivitySigninTrainee signinTrainee = null;
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		params.put("activityId", activityId);
		params.put("traineeId", loginUserId);
		List<TevglActivitySigninTrainee> list = tevglActivitySigninTraineeMapper.selectListByMap(params);
		if (list != null && list.size() > 0) {
			signinTrainee = list.get(0);
			if (signinTrainee != null) {
				// 签到图片
				signinTrainee.setFile(uploadPathUtils.stitchingPath(signinTrainee.getFile(), "18"));
				// 取时分
				if (StrUtils.isNotEmpty(signinTrainee.getSignTime())) {
					signinTrainee.setSignTime(signinTrainee.getSignTime().substring(11, 16));
				}
			}
			
		}
		return R.ok().put(Constant.R_DATA, signinTrainee);
	}
	
	/**
	 * 学员签到详情列表
	 * @param
	 * @param
	 * @param
	 * @return
	 */
	@Override
	@GetMapping("/queryTraineeSigninDetail")
	public R queryTraineeSigninDetail(@RequestParam Map<String, Object> params) {
		if (params.get("ctId") == null) {
			return R.error("参数ctId为空");
		}
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<Map<String,Object>> classroomTraineeList = tevglTchClassroomTraineeMapper.selectListMapForWeb(query);
		// 统计当前课堂累计的签到活动
		Integer totalNum = tevglTchClassroomMapper.countSignupActivityNum(params.get("ctId").toString());
		// 此课堂所有签到记录
		List<Map<String, Object>> signinTraineeListData = getSigninTraineeListData(params.get("ctId").toString(), params);
		// 遍历计算学员不同签到状态的次数
		classroomTraineeList.stream().forEach(traineeInfo -> {
			// 签到状态1正常2迟到3早退4旷课5请假
			long normalNum = signinTraineeListData.stream().filter(a -> "1".equals(a.get("signState")) && a.get("traineeId").equals(traineeInfo.get("traineeId"))).count();
			long lateNum = signinTraineeListData.stream().filter(a -> "2".equals(a.get("signState")) && a.get("traineeId").equals(traineeInfo.get("traineeId"))).count();
			long leaveEarlyNum = signinTraineeListData.stream().filter(a -> "3".equals(a.get("signState")) && a.get("traineeId").equals(traineeInfo.get("traineeId"))).count();
			long truantNum = signinTraineeListData.stream().filter(a -> "4".equals(a.get("signState")) && a.get("traineeId").equals(traineeInfo.get("traineeId"))).count();
			long leaveNum = signinTraineeListData.stream().filter(a -> "5".equals(a.get("signState")) && a.get("traineeId").equals(traineeInfo.get("traineeId"))).count();
			traineeInfo.put("normalNum", normalNum);
			traineeInfo.put("lateNum", lateNum);
			traineeInfo.put("leaveEarlyNum", leaveEarlyNum);
			traineeInfo.put("truantNum", truantNum);
			traineeInfo.put("leaveNum", leaveNum);
			// 出勤率
			traineeInfo.put("totalNum", totalNum);
			if (totalNum == null || totalNum == 0) {
				traineeInfo.put("attendanceRate", new BigDecimal("0"));
			} else {
				traineeInfo.put("attendanceRate", new BigDecimal(normalNum).divide(new BigDecimal(totalNum), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString() + "%");
			}
			// 头像处理
			if (traineeInfo.get("traineePic") != null) {
				traineeInfo.put("traineePic", uploadPathUtils.stitchingPath(traineeInfo.get("traineePic").toString(), "16"));
			}
		});
		// 返回数据
		PageUtils pageUtil = new PageUtils(classroomTraineeList, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询此课堂所有的签到记录
	 * @param ctId
	 * @param params
	 * @return
	 */
	private List<Map<String, Object>> getSigninTraineeListData(String ctId, Map<String, Object> params) {
		params.clear();
		params.put("ctId", ctId);
		List<Map<String, Object>> list = tevglActivitySigninTraineeMapper.selectListMapForCommon(params);
		return list;
	}
}
