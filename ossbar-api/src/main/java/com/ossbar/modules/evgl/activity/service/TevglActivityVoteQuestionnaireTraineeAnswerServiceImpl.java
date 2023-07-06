package com.ossbar.modules.evgl.activity.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.annotation.NoRepeatSubmit;
import com.ossbar.modules.evgl.activity.api.TevglActivityVoteQuestionnaireTraineeAnswerService;
import com.ossbar.modules.evgl.activity.domain.TevglActivityVoteQuestionnaire;
import com.ossbar.modules.evgl.activity.domain.TevglActivityVoteQuestionnaireTraineeAnswer;
import com.ossbar.modules.evgl.activity.domain.TevglActivityVoteQuestionnaireTraineeAnswerFile;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityVoteQuestionnaireMapper;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityVoteQuestionnaireTraineeAnswerFileMapper;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityVoteQuestionnaireTraineeAnswerMapper;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomTraineeMapper;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeInfoMapper;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/activity/tevglactivityvotequestionnairetraineeanswer")
public class TevglActivityVoteQuestionnaireTraineeAnswerServiceImpl implements TevglActivityVoteQuestionnaireTraineeAnswerService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglActivityVoteQuestionnaireTraineeAnswerServiceImpl.class);
	@Autowired
	private TevglActivityVoteQuestionnaireTraineeAnswerMapper tevglActivityVoteQuestionnaireTraineeAnswerMapper;
	@Autowired
	private TevglActivityVoteQuestionnaireTraineeAnswerFileMapper tevglActivityVoteQuestionnaireTraineeAnswerFileMapper;
	@Autowired
	private TsysAttachService tsysAttachService;
	@Autowired
	private TevglActivityVoteQuestionnaireMapper tevglActivityVoteQuestionnaireMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private TevglTchClassroomTraineeMapper tevglTchClassroomTraineeMapper;
	@Autowired
	private TevglTraineeInfoMapper tevglTraineeInfoMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswer/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglActivityVoteQuestionnaireTraineeAnswer> tevglActivityVoteQuestionnaireTraineeAnswerList = tevglActivityVoteQuestionnaireTraineeAnswerMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglActivityVoteQuestionnaireTraineeAnswerList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswer/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglActivityVoteQuestionnaireTraineeAnswerList = tevglActivityVoteQuestionnaireTraineeAnswerMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglActivityVoteQuestionnaireTraineeAnswerList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglActivityVoteQuestionnaireTraineeAnswer
	 * @throws CreatorblueException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswer/save")
	public R save(@RequestBody(required = false) TevglActivityVoteQuestionnaireTraineeAnswer tevglActivityVoteQuestionnaireTraineeAnswer) throws CreatorblueException {
		tevglActivityVoteQuestionnaireTraineeAnswer.setId(Identities.uuid());
		//ValidatorUtils.check(tevglActivityVoteQuestionnaireTraineeAnswer);
		tevglActivityVoteQuestionnaireTraineeAnswerMapper.insert(tevglActivityVoteQuestionnaireTraineeAnswer);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglActivityVoteQuestionnaireTraineeAnswer
	 * @throws CreatorblueException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswer/update")
	public R update(@RequestBody(required = false) TevglActivityVoteQuestionnaireTraineeAnswer tevglActivityVoteQuestionnaireTraineeAnswer) throws CreatorblueException {
	    //ValidatorUtils.check(tevglActivityVoteQuestionnaireTraineeAnswer);
		tevglActivityVoteQuestionnaireTraineeAnswerMapper.update(tevglActivityVoteQuestionnaireTraineeAnswer);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswer/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tevglActivityVoteQuestionnaireTraineeAnswerMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswer/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws CreatorblueException {
		tevglActivityVoteQuestionnaireTraineeAnswerMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswer/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglActivityVoteQuestionnaireTraineeAnswerMapper.selectObjectById(id));
	}

	/**
	 * 学员提交投票/问卷的作答内容
	 * @param jsonObject
	 * @param loginUserId
	 * @return
	 * @apiNote 
	 * "[
	 * 	   {'questionId': '', 'questionType': '', 'content': '',
	 *      'optionIds': ['123', '456'],
	 *      'fileList': [{'attachId': '', 'fileName': ''}]
	 *     }
	 *  ]"
	 */
	@Override
	@PostMapping("/saveTraineeCommitAnswerContent")
	@Transactional
	@NoRepeatSubmit(value = 1000)
	public R saveTraineeCommitAnswerContent(@RequestBody JSONObject jsonObject
			, String loginUserId) throws CreatorblueException {
		String ctId = jsonObject.getString("ctId"); // 课堂主键
		String activityId = jsonObject.getString("activityId"); // 活动主键
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(activityId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroomInfo = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroomInfo == null) {
			return R.error("无效的课堂，提交失败");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("pkgId", classroomInfo.getPkgId());
		map.put("activityId", activityId);
		TevglActivityVoteQuestionnaire activityInfo = tevglActivityVoteQuestionnaireMapper.selectObjectByIdAndPkgId(map);
		//TevglActivityVoteQuestionnaire activityInfo = tevglActivityVoteQuestionnaireMapper.selectObjectById(activityId);
		if (activityInfo == null) {
			return R.error("无效的活动，提交失败");
		}
		if ("0".equals(activityInfo.getActivityStateActual())) {
			return R.error("活动未开始，暂不能提交");
		}
		if ("2".equals(activityInfo.getActivityStateActual())) {
			return R.error("活动已结束，不能提交了");
		}
		// 判断当前登录用户是否属于此课堂成员,如果不是,则不能提交
		if (!isThisClassroomTrainee(ctId, loginUserId)) {
			return R.error("非法操作，未加入课堂，不允许提交");
		}
		// 判断是否已经提交过,已提交的则不能再次提交
		if (hasBeenSubmitted(ctId, activityId, loginUserId)) {
			return R.error("你已经提交过了，不能再次提交或修改了");
		}
		String json = jsonObject.getString("json");
		if (StrUtils.isEmpty(json)) {
			return R.error("请作答");
		}
		JSONArray jsonArray = JSON.parseArray(json);
		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = (JSONObject) jsonArray.get(i);
				JSONArray fileList = obj.getJSONArray("fileList");
				JSONArray fillList = obj.getJSONArray("fillList");
				// 保存针对题目的回答
				List<String> ids = doSaveTraineeAnswerQuestionInfo(obj, ctId, activityId, fillList, loginUserId);
				// 保存对应的附件记录
				doSaveTraineeAnswerQuestionFileInfo2(fileList, ids, loginUserId);
			}
		}
		// 更新此活动作答人数
		TevglActivityVoteQuestionnaire t = new TevglActivityVoteQuestionnaire();
		t.setActivityId(activityId);
		t.setAnswerNumber(1);
		tevglActivityVoteQuestionnaireMapper.plusNum(t);
		// 实际记录经验活动日志
		doRecordTraineeEmpiricalValueLog(classroomInfo, activityInfo, loginUserId);
		// 更新学员总经验值
		TevglTraineeInfo tevglTraineeInfo = new TevglTraineeInfo();
		tevglTraineeInfo.setTraineeId(loginUserId);
		tevglTraineeInfo.setEmpiricalValue(activityInfo.getEmpiricalValue());
		tevglTraineeInfoMapper.plusNum(tevglTraineeInfo);
		return R.ok("提交成功");
	}
	
	/**
	 * 记录学员经验获取日志
	 * @param classroomInfo 课堂
	 * @param activityInfo 活动
	 * @param loginUserId 当前登录用户
	 */
	private void doRecordTraineeEmpiricalValueLog(TevglTchClassroom classroomInfo, TevglActivityVoteQuestionnaire activityInfo, String loginUserId) {

	}
	
	/**
	 * 判断当前登录用户是否属于此课堂成员
	 * @param ctId 课堂主键
	 * @param loginUserId 当前登录用户id
	 * @return {@link Boolean} 为true表示是此课堂成员，允许提交
	 */
	private boolean isThisClassroomTrainee(String ctId, String loginUserId) {
		Map<String, Object> ps = new HashMap<String, Object>();
		ps.put("ctId", ctId);
		ps.put("traineeId", loginUserId);
		ps.put("state", "Y");
		List<Map<String,Object>> list = tevglTchClassroomTraineeMapper.selectListMapForWeb(ps);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			log.debug("当前登录用户【" + loginUserId + "】，已不属于此课堂【"+ctId+"】");
		}
		return false;
	}
	
	/**
	 * 判断学员此课堂此投票/问卷活动是否已经回答，提交了，
	 * @param ctId 课堂主键
	 * @param activityId 投票/问卷活动主键
	 * @param loginUserId 当前登录用户id
	 * @return {@link Boolean} 为true表示已经做过了提交过了
	 */
	private boolean hasBeenSubmitted(String ctId, String activityId, String loginUserId) {
		Map<String, Object> ps = new HashMap<String, Object>();
		ps.put("ctId", ctId);
		ps.put("activityId", activityId);
		ps.put("traineeId", loginUserId);
		List<TevglActivityVoteQuestionnaireTraineeAnswer> list = tevglActivityVoteQuestionnaireTraineeAnswerMapper.selectListByMap(ps);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 执行保存学员提交的题目回答内容
	 * @param obj 示例：{'questionId':'123456', 'questionType':'1', 'optionIds':'asdwqe123,321as,5896', 'content':''}
	 * @param ctId 课堂主键
	 * @param activityId 活动主键
	 * @param fillList 选填的内容 示例： {"optionId": "c9cb62a6fc2e4846ad6c76c5f7c55ced", "content" : "按实际可行那是"}
	 * @param loginUserId 当前登录用户
	 * @return 返回学员作答表主键（t_evgl_activity_vote_questionnaire_trainee_answer）
	 */
	private List<String> doSaveTraineeAnswerQuestionInfo(JSONObject obj, String ctId, String activityId, JSONArray fillList, String loginUserId) {
		// 题目主键
		String questionId = obj.getString("questionId"); 
		// 题目类型
		String questionType = obj.getString("questionType");
		// 学员选择的选项主键(选择题的情况,多个已逗号隔开)
		String optionIds = obj.getString("optionIds"); 
		String[] optionIdArrays = optionIds.split(",");
		// 简答题时填写的内容
		String content = obj.getString("content");
		List<String> ids = new ArrayList<String>();
		// 查询对象
		Map<String, Object> params = new HashMap<>();
		// 存放选填内容的集合
		List<Map<String, Object>> list = new ArrayList<>();
		
		for (String optionId : optionIdArrays) {
			// 先保存回答的题目信息
			TevglActivityVoteQuestionnaireTraineeAnswer t = new TevglActivityVoteQuestionnaireTraineeAnswer();
			t.setId(Identities.uuid());
			t.setActivityId(activityId);
			t.setCtId(ctId);
			t.setTraineeId(loginUserId);
			t.setQuestionId(questionId);
			t.setQuestionType(questionType);
			t.setCreateTime(DateUtils.getNowTimeStamp());
			if ("1".equals(questionType) || "2".equals(questionType)) {
				t.setOptionId(optionId);
			} else if ("3".equals(questionType)) {
				t.setOptionId(optionId);
				t.setContent(content);
			}
			params.clear();
			// 根据条件查询数据库中是否存在该记录，如果不存在则新增
			params.put("activityId", activityId);
			params.put("traineeId", loginUserId);
			params.put("questionId", questionId);
			params.put("optionId", optionId);
			List<TevglActivityVoteQuestionnaireTraineeAnswer> traineeAnswerList = tevglActivityVoteQuestionnaireTraineeAnswerMapper.selectListByMap(params);
			if (traineeAnswerList == null || traineeAnswerList.size() == 0) {
				tevglActivityVoteQuestionnaireTraineeAnswerMapper.insert(t);
				ids.add(t.getId());
			}
		}
		if (fillList != null && fillList.size() > 0) {
			for (int i = 0; i < fillList.size(); i++) {
				JSONObject fillInfo = (JSONObject) fillList.get(i);
				String fillOptionId = fillInfo.getString("optionId");
				String fillContent = fillInfo.getString("content");
				if (StrUtils.isNotEmpty(fillOptionId) && StrUtils.isNotEmpty(fillContent)) {
					// 存放选填内容
					Map<String, Object> data = new HashMap<>();
					data.put("optionId", fillOptionId);
					data.put("content", fillContent);
					list.add(data);
				}
			}
		}
		
		params.clear();
		// 查询已经入库的题目信息
		params.put("activityId", activityId);
		params.put("traineeId", loginUserId);
		params.put("questionId", questionId);
		List<TevglActivityVoteQuestionnaireTraineeAnswer> traineeAnswerList = tevglActivityVoteQuestionnaireTraineeAnswerMapper.selectListByMap(params);
		if (list.size() > 0 && traineeAnswerList.size() > 0) {
			list.stream().forEach(fillInfo -> {
				// 取出选项id
				List<String> optionsId = traineeAnswerList.stream().filter(a -> a.getOptionId().equals(fillInfo.get("optionId"))).map(a -> a.getOptionId()).collect(Collectors.toList());
				if (optionsId.size() > 0) {
					String optionId = optionsId.get(0);
					params.clear();
					// 查询这条选项的记录
					params.put("optionId", optionId);
					params.put("activityId", activityId);
					params.put("traineeId", loginUserId);
					params.put("questionId", questionId);
					List<TevglActivityVoteQuestionnaireTraineeAnswer> answerList = tevglActivityVoteQuestionnaireTraineeAnswerMapper.selectListByMap(params);
					if (answerList != null && answerList.size() > 0) {
						// 取出学员作答的主键id
						String id = answerList.get(0).getId();
						// 修改选填内容
						TevglActivityVoteQuestionnaireTraineeAnswer traineeAnswer = new TevglActivityVoteQuestionnaireTraineeAnswer();
						traineeAnswer.setId(id);
						traineeAnswer.setContent(fillInfo.get("content").toString());
						tevglActivityVoteQuestionnaireTraineeAnswerMapper.update(traineeAnswer);
					}
				}
			});
		}
		
		return ids;
	}

	/**
	 * 绑定与附件的关系
	 * @param fileList
	 * @param ids
	 * @param loginUserId
	 */
	private void doSaveTraineeAnswerQuestionFileInfo2(JSONArray fileList, List<String> ids, String loginUserId) {
		if (fileList == null || fileList.size() == 0) {
			return ;
		}
		for (int i = 0; i < fileList.size(); i++) {
			for (String id : ids) {
				String fileId = fileList.get(i).toString();
				TevglActivityVoteQuestionnaireTraineeAnswerFile file = new TevglActivityVoteQuestionnaireTraineeAnswerFile();
				file.setFileId(fileId);
				file.setId(id);
				tevglActivityVoteQuestionnaireTraineeAnswerFileMapper.update(file);
			}
		}
	}

	
}
