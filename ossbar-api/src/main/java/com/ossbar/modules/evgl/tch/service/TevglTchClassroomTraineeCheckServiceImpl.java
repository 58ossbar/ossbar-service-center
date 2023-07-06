package com.ossbar.modules.evgl.tch.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbRoomUtils;
import com.ossbar.modules.common.GlobalActivity;
import com.ossbar.modules.common.GlobalRoomPermission;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgActivityRelation;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgActivityRelationMapper;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomTraineeCheckService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomTrainee;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomTraineeCheck;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomTraineeCheckMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomTraineeMapper;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
@RequestMapping("/tch/tevgltchclassroomtraineecheck")
public class TevglTchClassroomTraineeCheckServiceImpl implements TevglTchClassroomTraineeCheckService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglTchClassroomTraineeCheckServiceImpl.class);
	@Autowired
	private TevglTchClassroomTraineeCheckMapper tevglTchClassroomTraineeCheckMapper;
	@Autowired
	private UploadFileUtils uploadPathUtils;
	// 显示地址
	@Value("${com.creatorblue.file-access-path}")
	public String accessPath;
	@Autowired
	private TevglTchClassroomTraineeMapper tevglTchClassroomTraineeMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private TevglPkgActivityRelationMapper tevglPkgActivityRelationMapper;
	@Autowired
	private CbRoomUtils cbRoomUtils;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/tch/tevgltchclassroomtraineecheck/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglTchClassroomTraineeCheck> tevglTchClassroomTraineeCheckList = tevglTchClassroomTraineeCheckMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglTchClassroomTraineeCheckList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/tch/tevgltchclassroomtraineecheck/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglTchClassroomTraineeCheckList = tevglTchClassroomTraineeCheckMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglTchClassroomTraineeCheckList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglTchClassroomTraineeCheck
	 * @throws CreatorblueException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/tch/tevgltchclassroomtraineecheck/save")
	public R save(@RequestBody(required = false) TevglTchClassroomTraineeCheck tevglTchClassroomTraineeCheck) throws CreatorblueException {
		tevglTchClassroomTraineeCheck.setTcId(Identities.uuid());
		//ValidatorUtils.check(tevglTchClassroomTraineeCheck);
		tevglTchClassroomTraineeCheckMapper.insert(tevglTchClassroomTraineeCheck);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglTchClassroomTraineeCheck
	 * @throws CreatorblueException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/tch/tevgltchclassroomtraineecheck/update")
	public R update(@RequestBody(required = false) TevglTchClassroomTraineeCheck tevglTchClassroomTraineeCheck) throws CreatorblueException {
	    //ValidatorUtils.check(tevglTchClassroomTraineeCheck);
		tevglTchClassroomTraineeCheckMapper.update(tevglTchClassroomTraineeCheck);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/tch/tevgltchclassroomtraineecheck/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tevglTchClassroomTraineeCheckMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/tch/tevgltchclassroomtraineecheck/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws CreatorblueException {
		tevglTchClassroomTraineeCheckMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/tch/tevgltchclassroomtraineecheck/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglTchClassroomTraineeCheckMapper.selectObjectById(id));
	}

	/**
	 * 根据条件查询等待审核通过，正式加入课堂成员的成员
	 * @param params 必传key：ctId课堂主键，
	 * @param loginUserId
	 * @return
	 */
	@Override
	@GetMapping("/queryTraineeList")
	public R queryTraineeList(@RequestParam Map<String, Object> params, String loginUserId) {
		String ctId = (String) params.get("ctId");
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		params.put("sidx", "t1.create_time");
		params.put("order", "desc");
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String,Object>> list = tevglTchClassroomTraineeCheckMapper.selectListMapByMap(query);
		list.stream().forEach(a -> {
			a.put("traineePic", uploadPathUtils.stitchingPath((String)a.get("traineePic"), "16"));
		});
		PageUtils pageUtil = new PageUtils(list, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	/**
	 * 将待审核的学员加入成课程成员
	 * @param jsonObject {'ctId':'', 'traineeIds':[]}
	 * @param loginUserId
	 * @return
	 */
	@Override
	@PostMapping("/setTraineeToClassroom")
	public R setTraineeToClassroom(@RequestBody JSONObject jsonObject, String loginUserId) throws CreatorblueException {
		String ctId = jsonObject.getString("ctId");
		String isPass = jsonObject.getString("isPass");
		if (StrUtils.isEmpty(ctId)) {
			return R.error("参数ctId为空");
		}
		JSONArray jsonArray = jsonObject.getJSONArray("traineeIds");
		if (jsonArray == null || jsonArray.size() == 0) {
			return R.error("请选择通过的成员");
		}
		TevglTchClassroom classroomInfo = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroomInfo == null) {
			return R.error("无效的记录");
		}
		// 如果登录用户不是当前课堂创建者
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(classroomInfo, loginUserId, GlobalRoomPermission.CHECK_TRAINEE_KEY);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		// 如果审核不通过
		if (!"Y".equals(isPass)) {
			// 去课堂成员表找到这些人，并删除记录，审核表的记录也删除，那么，就能重新申请加入了
			return didNotPass(ctId, jsonArray, classroomInfo.getName());
		}
		// 查询待审核的成员
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ctId", ctId);
		map.put("isPass", "N");
		List<TevglTchClassroomTraineeCheck> list = tevglTchClassroomTraineeCheckMapper.selectListByMap(map);
		if (list == null || list.size() == 0) {
			return R.ok("目前没有待审核的成员");
		}
		// 实际需要审核的成员
		List<String> ids = new ArrayList<String>();
		// 带删除的成员
		List<String> tcIds = new ArrayList<String>();
		// 该课堂待审核的所有人
		Map<String, String> data = list.stream().collect(Collectors.toMap(TevglTchClassroomTraineeCheck::getTcId, TevglTchClassroomTraineeCheck::getTraineeId));
		for (Entry<String, String> m : data.entrySet()) {
			for (Object o : jsonArray) {
				if (m.getValue().equals(o.toString())) {
					tcIds.add(m.getKey()); // 待删除的记录(逻辑删除)
					ids.add(o.toString()); // 待添加的学员
				}
			}
		}
		// 已经存在于课堂里面的课堂成员
		map.clear();
		map.put("ctId", ctId);
		List<TevglTchClassroomTrainee> classroomTraineeList = tevglTchClassroomTraineeMapper.selectListByMap(map);
		// 将待审核的成员调整成为课堂成员
		saveToClassroomTrainee(ctId, classroomInfo.getClassId(), ids, classroomTraineeList);
		// 删除待审核的记录
		if (tcIds.size() > 0) {
			// 逻辑删除
			tevglTchClassroomTraineeCheckMapper.updateNotPassBatch(tcIds);
		}
		// ================================================== 即时通讯相关处理 begin ==================================================

		// ================================================== 即时通讯相关处理 end ==================================================
		return R.ok("审核通过");
	}
	
	/**
	 * 将选择的成员，调整成为课堂成员，（如果已经在课堂里，则不重复添加）
	 * @param ctId 课堂主键
	 * @param classId 当前课堂对应的班级
	 * @param traineeIds 被选中，审核通过的学员
	 * @param classroomTraineeList 课堂里全部成员
	 */
	private void saveToClassroomTrainee(String ctId, String classId, List<String> traineeIds, List<TevglTchClassroomTrainee> classroomTraineeList) {
		if (StrUtils.isEmpty(ctId) || traineeIds == null || traineeIds.size() == 0
				|| classroomTraineeList == null || classroomTraineeList.size() == 0) {
			return;
		}
		Map<String, Object> params = new HashMap<>(); // 查询条件
	    // 待插入课堂成员表的数据
		List<TevglTchClassroomTrainee> list = new ArrayList<>();
		// 取出课堂成员
		List<String> traineeListAlreadyExisted = classroomTraineeList.stream().map(a -> a.getTraineeId()).collect(Collectors.toList());
		// 定义等待需要更新为Y的集合
		List<String> idList = new ArrayList<>();
		// 已经在当前课堂的人，且待审核的人
		params.put("ctId", ctId);
		params.put("state", "N");
		List<TevglTchClassroomTrainee> existedTevglTchClassroomTraineeList = tevglTchClassroomTraineeMapper.selectListByMap(params);
		for (String traineeId : traineeIds) {
			// 如果学员其实已经在课堂里,则不重复插入记录
			if (traineeListAlreadyExisted.contains(traineeId)) {
				List<TevglTchClassroomTrainee> tevglTchClassroomTraineeList = existedTevglTchClassroomTraineeList.stream().filter(a -> a.getTraineeId().equals(traineeId)).collect(Collectors.toList());
				// 而是更新状态即可
				if (tevglTchClassroomTraineeList != null && tevglTchClassroomTraineeList.size() > 0) {
					idList.add(tevglTchClassroomTraineeList.get(0).getId());
					// 同步维护班级成员数据
					cbRoomUtils.doHandleClassTraineeTable(tevglTchClassroomTraineeList.get(0).getTraineeId(), classId, params);
				}
				continue;
			}
			// 课堂表没有记录才插入记录
			if (!traineeListAlreadyExisted.contains(traineeId)) {
				TevglTchClassroomTrainee t = new TevglTchClassroomTrainee();
				t.setId(Identities.uuid());
				t.setCtId(ctId);
				t.setTraineeId(traineeId);
				t.setJoinDate(DateUtils.getNowTimeStamp().substring(0, 10));
				t.setCreateTime(DateUtils.getNowTimeStamp());
				t.setState("Y");
				// 加入集合
				list.add(t);
			}
		}
		log.debug("待新增的课堂成员人数 => {}" + list.size());
		// 有数据才保存
		if (list != null && list.size() > 0) {
			// 批量插入
			tevglTchClassroomTraineeMapper.insertBatch(list);
			// 更新课堂人数
			cbRoomUtils.plusStudyNum(ctId, list.size());
			// 同步维护班级成员数据
			list.stream().forEach(a -> {
				cbRoomUtils.doHandleClassTraineeTable(a.getTraineeId(), classId, params);
			});
		}
		log.debug("待更新的课堂成员人数 => {}", idList);
		if (idList.size() > 0) {
			tevglTchClassroomTraineeMapper.updateStateYBatch(idList);
		}
	}

	/**
	 * 如果审核不通过
	 * @param ctId
	 * @param traineeIds
	 * @return
	 */
	private R didNotPass(String ctId, JSONArray traineeIds, String classroomName) {
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		params.put("traineeIds", traineeIds);
		// 删除课程成员表中的记录
		List<TevglTchClassroomTrainee> list = tevglTchClassroomTraineeMapper.selectListByMap(params);
		if (list != null && list.size() > 0) {
			for (TevglTchClassroomTrainee tevglTchClassroomTrainee : list) {
				tevglTchClassroomTraineeMapper.delete(tevglTchClassroomTrainee.getId());
			}
		}
		// 删除审核表中的记录
		List<TevglTchClassroomTraineeCheck> checkList = tevglTchClassroomTraineeCheckMapper.selectListByMap(params);
		if (checkList != null && checkList.size() > 0) {
			for (TevglTchClassroomTraineeCheck tevglTchClassroomTraineeCheck : checkList) {
				tevglTchClassroomTraineeCheckMapper.delete(tevglTchClassroomTraineeCheck.getTcId());
			}
		}
		// 组装返回给前端的数据

		// 推送消息,告知此人已未通过审核

		return R.ok("审核不通过");
	}
	
	/**
	 * 加入课堂群组
	 * @param ctId 以课堂主键作为群聊主键
	 * @param loginUserId
	 */
	private void joinTimGroup(String ctId, String loginUserId) {

	}
	
	/**
	 * 获取该课堂的活动
	 * @param tchClassroom
	 * @return
	 */
	private Map<String, String> getActivityMap(TevglTchClassroom tchClassroom, Map<String, Object> params){
		if (tchClassroom == null) {
			return null;
		}
		params.clear();
		params.put("pkgId", tchClassroom.getPkgId());
		params.put("activityState", "1"); // 实际活动状态(0未开始1进行中2已结束)
		params.put("activityType", GlobalActivity.ACTIVITY_3_ANSWER_DISCUSS);
		List<TevglPkgActivityRelation> list = tevglPkgActivityRelationMapper.selectListByMap(params);
		if (list != null && list.size() > 0) {
			return list.stream().collect(Collectors.toMap(TevglPkgActivityRelation::getGroupId, TevglPkgActivityRelation::getActivityType));
		}
		return null;
	}
}
