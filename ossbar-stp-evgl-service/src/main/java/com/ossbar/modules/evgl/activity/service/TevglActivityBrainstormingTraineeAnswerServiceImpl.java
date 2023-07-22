package com.ossbar.modules.evgl.activity.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ossbar.modules.evgl.medu.me.persistence.TmeduMeLikeMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomTraineeMapper;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeEmpiricalValueLogMapper;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeInfoMapper;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbNumsUtils;
import com.ossbar.modules.common.GlobalActivity;
import com.ossbar.modules.common.GlobalActivityFiles;
import com.ossbar.modules.common.GlobalLike;
import com.ossbar.modules.common.UploadPathUtils;
import com.ossbar.modules.common.annotation.NoRepeatSubmit;
import com.ossbar.modules.common.enums.EmpiricalValueEnum;
import com.ossbar.modules.evgl.activity.api.TevglActivityBrainstormingTraineeAnswerService;
import com.ossbar.modules.evgl.activity.domain.TevglActivityBrainstorming;
import com.ossbar.modules.evgl.activity.domain.TevglActivityBrainstormingAnswerFile;
import com.ossbar.modules.evgl.activity.domain.TevglActivityBrainstormingTraineeAnswer;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityBrainstormingAnswerFileMapper;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityBrainstormingMapper;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityBrainstormingTraineeAnswerMapper;
import com.ossbar.modules.evgl.empirical.api.TevglEmpiricalSettingService;
import com.ossbar.modules.evgl.medu.me.domain.TmeduMeLike;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeEmpiricalValueLog;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.github.pagehelper.PageHelper;

/**
 * <p> Title: 活动之头脑风暴---学员作答信息表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/activity/tevglactivitybrainstormingtraineeanswer")
public class TevglActivityBrainstormingTraineeAnswerServiceImpl implements TevglActivityBrainstormingTraineeAnswerService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglActivityBrainstormingTraineeAnswerServiceImpl.class);
	@Autowired
	private TevglActivityBrainstormingTraineeAnswerMapper tevglActivityBrainstormingTraineeAnswerMapper;
	@Autowired
	private TevglActivityBrainstormingAnswerFileMapper tevglActivityBrainstormingAnswerFileMapper;
	@Autowired
	private UploadPathUtils uploadPathUtils;
	// 文件显示地址
	@Value("${com.ossbar.file-access-path}")
	public String ossbarFieAccessPath;
	@Autowired
	private TevglActivityBrainstormingMapper tevglActivityBrainstormingMapper;
	@Autowired
	private TsysAttachService tsysAttachService;
	@Autowired
	private TevglTchClassroomTraineeMapper tevglTchClassroomTraineeMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private TmeduMeLikeMapper tmeduMeLikeMapper;
	@Autowired
	private TevglTraineeEmpiricalValueLogMapper tevglTraineeEmpiricalValueLogMapper;
	@Autowired
	private TevglTraineeInfoMapper tevglTraineeInfoMapper;
	@Autowired
	private CbNumsUtils cbNumsUtils;
	@Autowired
	private TevglEmpiricalSettingService tevglEmpiricalSettingService;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/activity/tevglactivitybrainstormingtraineeanswer/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglActivityBrainstormingTraineeAnswer> tevglActivityBrainstormingTraineeAnswerList = tevglActivityBrainstormingTraineeAnswerMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglActivityBrainstormingTraineeAnswerList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/activity/tevglactivitybrainstormingtraineeanswer/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglActivityBrainstormingTraineeAnswerList = tevglActivityBrainstormingTraineeAnswerMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglActivityBrainstormingTraineeAnswerList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglActivityBrainstormingTraineeAnswer
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/activity/tevglactivitybrainstormingtraineeanswer/save")
	public R save(@RequestBody(required = false) TevglActivityBrainstormingTraineeAnswer tevglActivityBrainstormingTraineeAnswer) throws OssbarException {
		tevglActivityBrainstormingTraineeAnswer.setAnId(Identities.uuid());
		ValidatorUtils.check(tevglActivityBrainstormingTraineeAnswer);
		tevglActivityBrainstormingTraineeAnswerMapper.insert(tevglActivityBrainstormingTraineeAnswer);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglActivityBrainstormingTraineeAnswer
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/activity/tevglactivitybrainstormingtraineeanswer/update")
	public R update(@RequestBody(required = false) TevglActivityBrainstormingTraineeAnswer tevglActivityBrainstormingTraineeAnswer) throws OssbarException {
	    ValidatorUtils.check(tevglActivityBrainstormingTraineeAnswer);
		tevglActivityBrainstormingTraineeAnswerMapper.update(tevglActivityBrainstormingTraineeAnswer);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/activity/tevglactivitybrainstormingtraineeanswer/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglActivityBrainstormingTraineeAnswerMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/activity/tevglactivitybrainstormingtraineeanswer/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglActivityBrainstormingTraineeAnswerMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/activity/tevglactivitybrainstormingtraineeanswer/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglActivityBrainstormingTraineeAnswerMapper.selectObjectById(id));
	}

	/**
	 * 查看自己提交的头脑风暴作答内容
	 * @param anId 表t_evgl_activity_brainstorming_trainee_answer的主键id
	 * @param loginUserId 当前登录用户id
	 * @return
	 */
	@Override
	@GetMapping("/viewTraineeAnswerInfo")
	public R viewTraineeAnswerInfo(String ctId, String activityId, String anId, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(activityId) ||
				StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglActivityBrainstorming brainstormingInfo = tevglActivityBrainstormingMapper.selectObjectById(activityId);
		if (brainstormingInfo == null) {
			return R.error("无效的活动记录");
		}
		Map<String, Object> ps = new HashMap<String, Object>();
		if (StrUtils.isEmpty(anId)) {
			ps.put("ctId", ctId);
			ps.put("activityId", activityId);
			ps.put("traineeId", loginUserId);
			List<TevglActivityBrainstormingTraineeAnswer> list = tevglActivityBrainstormingTraineeAnswerMapper.selectListByMap(ps);
			if (list != null && list.size() > 0) {
				anId = list.get(0).getAnId();
			}
		}
		// 基本信息
		Map<String, Object> traineeAnswerInfo = tevglActivityBrainstormingTraineeAnswerMapper.selectObjectMapById(anId);
		if (traineeAnswerInfo == null) {
			return R.error("无效的记录");
		}
		// 附件信息
		if (StrUtils.isNotEmpty(anId)) {
			ps.clear();
			ps.put("anId", anId);
			ps.put("state", "Y");
			ps.put("createUserId", traineeAnswerInfo.get("traineeId"));
			List<TevglActivityBrainstormingAnswerFile> list = tevglActivityBrainstormingAnswerFileMapper.selectListByMap(ps);
			// 路径处理
			list.stream().forEach(a -> {
				a.setUrl(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("21") + "/" + a.getUrl());
			});
			// 取部分属性
			List<Map<String,Object>> collect = list.stream().map(a -> {
				Map<String, Object> info = new HashMap<>();
				info.put("fiId", a.getFiId());
				info.put("url", a.getUrl());
				info.put("createTime", a.getCreateTime());
				info.put("fileType", a.getFileType());
				info.put("suffix", a.getFileSuffix());
				info.put("originalFilename", a.getOriginalFilename());
				info.put("durationTime", a.getDurationTime());
				info.put("firstCaptureAccessUrl", a.getFirstCaptureAccessUrl());
				return info;
			}).collect(Collectors.toList());
			traineeAnswerInfo.put("fileList", collect);
		} else {
			traineeAnswerInfo.put("fileList", null);
		}
		return R.ok().put(Constant.R_DATA, traineeAnswerInfo);
	}
	
	/**
	 * 查看所有学员的作答内容
	 * @param params 必传参数：ctId课堂id，activityId活动id，pageNum，pageSize
	 * @param loginUserId 登录用户id
	 * @return
	 */
	@Override
	@GetMapping("/viewTraineeAnswerListData")
	public R viewTraineeAnswerListData(@RequestParam Map<String, Object> params, String loginUserId) {
		String ctId = (String) params.get("ctId");
		String activityId = (String) params.get("activityId");
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(activityId)) {
			return R.error("必传参数ctId或activityId为空");
		}
		Map<String, Object> activityInfo = tevglActivityBrainstormingMapper.selectObjectMapById(activityId);
		if (activityInfo == null) {
			return R.error("无效的活动，无法查看");
		}
		Query query = new Query(params);
		List<Map<String,Object>> traineeAnswerList = tevglActivityBrainstormingTraineeAnswerMapper.selectListMapByMap(query);
		// 获取该课堂，成员提交的所有附件
		List<TevglActivityBrainstormingAnswerFile> fileList = getActivityFileInfoListData(ctId, traineeAnswerList);
		// 取第一张附件图片
		getFirstImage(traineeAnswerList, fileList);
		// 处理是否点赞
		isLikedTraineeAnswer(traineeAnswerList, loginUserId);
		// 返回数据
		PageUtils pageUtil = new PageUtils(traineeAnswerList, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	/**
	 * 处理是否点赞过
	 * @param traineeAnswerList
	 * @param loginUserId
	 */
	private void isLikedTraineeAnswer(List<Map<String, Object>> traineeAnswerList, String loginUserId) {
		List<TmeduMeLike> meLikeAllListData = getMeLikeAllListData(loginUserId);
		if (meLikeAllListData != null && meLikeAllListData.size() > 0) {
			traineeAnswerList.stream().forEach(answer -> {
				answer.put(GlobalLike.IS_LIKE, false);
				meLikeAllListData.forEach(like -> {
					if (answer.get("anId").equals(like.getTargetId())) {
						answer.put(GlobalLike.IS_LIKE, true);
					}
				});
			});	
		}
	}
	
	/**
	 * 获取当前登录用户，头脑风暴学员作答类型的所有点赞记录
	 * @param loginUserId
	 * @return
	 */
	private List<TmeduMeLike> getMeLikeAllListData(String loginUserId){
		Map<String, Object> ps = new HashMap<>();
		ps.put("traineeId", loginUserId);
		ps.put("likeType", GlobalLike.LIKE_14_ACTIVITY_BRAINSTORMING_TRAINEE_ANSWER);
		List<TmeduMeLike> list = tmeduMeLikeMapper.selectListByMap(ps);
		return list;
	}

	/**
	 * 获取该课堂，成员提交的所有附件
	 * @param ctId 课堂id
	 * @param traineeAnswerList 成员提交的所有附件
	 * @return
	 */
	private List<TevglActivityBrainstormingAnswerFile> getActivityFileInfoListData(String ctId, List<Map<String,Object>> traineeAnswerList) {
		Map<String, Object> params = new HashMap<>();
		// 获取附件
		List<String> anIds = traineeAnswerList.stream().map(a -> (String)a.get("anId")).collect(Collectors.toList());
		params.clear();
		params.put("ctId", ctId);
		params.put("type", GlobalActivityFiles.ACTIVITY_2_BRAINSTORMING_FILE);
		params.put("anIds", anIds);
		params.put("sidx", "sort_num");
		params.put("order", "asc");
		params.put("state", "Y");
		List<TevglActivityBrainstormingAnswerFile> fileList = tevglActivityBrainstormingAnswerFileMapper.selectListByMap(params);
		return fileList;
	}
	
	/**
	 * 取第一张附件图片
	 * @param traineeAnswerList 成员提交的作答内容
	 * @param fileList 附件内容
	 */
	private void getFirstImage(List<Map<String,Object>> traineeAnswerList, List<TevglActivityBrainstormingAnswerFile> fileList) {
		// 取最前的图片附件
		traineeAnswerList.forEach(traineeAnswer -> {
			// 默认未点赞
			traineeAnswer.put(GlobalLike.IS_LIKE, false);
			// 路径处理
			String traineePic = (String) traineeAnswer.get("traineePic");
			traineeAnswer.put("traineePic", uploadPathUtils.stitchingPath(traineePic, "16"));
			// url默认为头像的值
			traineeAnswer.put("url", uploadPathUtils.stitchingPath(traineePic, "16"));
			for (TevglActivityBrainstormingAnswerFile tevglActivityFileInfo : fileList) {
				if (traineeAnswer.get("anId").equals(tevglActivityFileInfo.getAnId())) {
					String suffix = uploadPathUtils.handleFileSuffix(tevglActivityFileInfo.getUrl());
					// 如果有符合图片的附件
					if ("image".equals(suffix)) {
						traineeAnswer.put("url", uploadPathUtils.stitchingPath(tevglActivityFileInfo.getUrl(), "21"));
						break;
					}
				}
			}
		});
	}
	
	/**
	 * 保存学员针对头脑风暴提交的作答内容
	 * @param jsonObject {'ctId':'', 'activityId':'', 'content', 'json':'[{'attachId':'', 'fileName':''}]'}
	 * @param loginUserId
	 * @return
	 */
	@Override
	@RequestMapping("/saveTraineeCommitAnswerContent")
	@Transactional
	@NoRepeatSubmit(value = 1000)
	public R saveTraineeCommitAnswerContent(@RequestBody JSONObject jsonObject, String loginUserId) {
		String ctId = jsonObject.getString("ctId");
		String activityId = jsonObject.getString("activityId");
		String content = jsonObject.getString("content");
		String json = jsonObject.getString("json");
		String media_id = jsonObject.getString("media_id"); // 多个逗号隔开
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(activityId) 
				|| StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		if (StrUtils.isEmpty(json)) {
			log.debug("没有上传附件");
		}
		TevglTchClassroom classroomInfo = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroomInfo == null) {
			return R.error("无效的课堂，提交失败");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("pkgId", classroomInfo.getPkgId());
		params.put("activityId", activityId);
		TevglActivityBrainstorming activityInfo = tevglActivityBrainstormingMapper.selectObjectByIdAndPkgId(params);
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
		// 保存作答信息
		TevglActivityBrainstormingTraineeAnswer ta = new TevglActivityBrainstormingTraineeAnswer();
		ta.setAnId(Identities.uuid());
		ta.setCtId(ctId);
		ta.setActivityId(activityId);
		ta.setContent(content);
		ta.setTraineeId(loginUserId);
		ta.setState("Y");
		ta.setCreateTime(DateUtils.getNowTimeStamp());
		ta.setLikeNum(0);
		tevglActivityBrainstormingTraineeAnswerMapper.insert(ta);
		// 保存附件信息
		//doSaveTraineeAnswerFileInfo(json, ta.getAnId(), loginUserId);
		// 绑定附件关系
		doBingTraineeAnswerFileInfo(media_id, ta.getAnId(), loginUserId);
		// 更新活动作答人数
		TevglActivityBrainstorming t = new TevglActivityBrainstorming();
		t.setActivityId(activityId);
		t.setAnswerNumber(1);
		tevglActivityBrainstormingMapper.plusNum(t);
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
	 * @param classroomInfo 课堂id
	 * @param activityInfo 活动id
	 * @param loginUserId 当前登录用户
	 */
	private void doRecordTraineeEmpiricalValueLog(TevglTchClassroom classroomInfo, TevglActivityBrainstorming activityInfo, String loginUserId) {
		String ctId = classroomInfo.getCtId();
		String activityId = activityInfo.getActivityId();
		TevglTraineeEmpiricalValueLog ev = new TevglTraineeEmpiricalValueLog();
		ev.setEvId(Identities.uuid());
		ev.setActivityId(activityId);
		ev.setType(GlobalActivity.ACTIVITY_2_BRAINSTORMING);
		ev.setTraineeId(loginUserId);
		ev.setCtId(ctId);
		// 获取此课堂中参与投票问卷能得到多少经验值
		Integer empiricalValue = tevglEmpiricalSettingService.getEmpiricalValueByMap(ctId, EmpiricalValueEnum.TYPE_8.getCode());
		ev.setEmpiricalValue(empiricalValue);
		ev.setCreateTime(DateUtils.getNowTimeStamp());
		ev.setState("Y");
		ev.setMsg(tevglEmpiricalSettingService.handleMessage(classroomInfo.getName(), activityInfo.getActivityTitle(), empiricalValue));
		tevglTraineeEmpiricalValueLogMapper.insert(ev);
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
	 * @param activityId 活动主键
	 * @param loginUserId 当前登录用户id
	 * @return {@link Boolean} 为true表示已经做过了提交过了
	 */
	private boolean hasBeenSubmitted(String ctId, String activityId, String loginUserId) {
		Map<String, Object> ps = new HashMap<String, Object>();
		ps.put("ctId", ctId);
		ps.put("activityId", activityId);
		ps.put("traineeId", loginUserId);
		List<TevglActivityBrainstormingTraineeAnswer> list = tevglActivityBrainstormingTraineeAnswerMapper.selectListByMap(ps);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 执行保存学员针对头脑风暴提交的附件记录
	 * @param json [{'attachId':'', 'fileName':''}]
	 * @param anId 表t_evgl_activity_brainstorming_trainee_answer主键
	 * @param loginUserId 当前登录用户
	 */
	private void doSaveTraineeAnswerFileInfo(String json, String anId, String loginUserId) {
		JSONArray jsonArray = JSON.parseArray(json);
		if (jsonArray == null || jsonArray.size() == 0) {
			return ;
		}
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = (JSONObject) jsonArray.get(i);
			String attachId = object.getString("attachId");
			String fileName = object.getString("fileName");
			String originalFilename = object.getString("originalFilename");
			TevglActivityBrainstormingAnswerFile fileInfo = new TevglActivityBrainstormingAnswerFile();
			String uuid = Identities.uuid();
			fileInfo.setFiId(uuid);
			fileInfo.setAnId(anId); // 目标id
			fileInfo.setUrl(fileName);
			fileInfo.setOriginalFilename(originalFilename);
			fileInfo.setCreateTime(DateUtils.getNowTimeStamp());
			fileInfo.setSortNum(i);
			fileInfo.setState("Y");
			fileInfo.setCreateUserId(loginUserId);
			tevglActivityBrainstormingAnswerFileMapper.insert(fileInfo);
			// 附件记录
			tsysAttachService.updateAttach(attachId, uuid, "1", "21");
		}
	}
	
	/**
	 * 绑定附件关系
	 * @param media_id
	 * @param anId
	 * @param loginUserId
	 */
	private void doBingTraineeAnswerFileInfo(String media_id, String anId, String loginUserId) {
		if (StrUtils.isEmpty(media_id) || StrUtils.isEmpty(anId)) {
			return;
		}
		String[] split = media_id.split(",");
		for (int i = 0; i < split.length; i++) {
			TevglActivityBrainstormingAnswerFile t = new TevglActivityBrainstormingAnswerFile();
			t.setFiId(split[i]);
			t.setAnId(anId);
			tevglActivityBrainstormingAnswerFileMapper.update(t);
		}
	}

	
	/**
	 * 点赞
	 * @param anId 头脑风暴学员作答表主键id
	 * @param loginUserId 当前登录用户
	 * @return
	 */
	@Override
	public R clickLike(String anId, String loginUserId) throws OssbarException {
		if (StrUtils.isEmpty(anId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglActivityBrainstormingTraineeAnswer traineeAnswerInfo = tevglActivityBrainstormingTraineeAnswerMapper.selectObjectById(anId);
		if (traineeAnswerInfo == null) {
			return R.error("无效的记录，点赞失败");
		}
		List<TmeduMeLike> list = getMeLikeListData(anId, loginUserId);
		if (list.size() > 0) {
			return R.error(GlobalLike.ALREADY_LIKE_MSG);
		}
		TmeduMeLike t = new TmeduMeLike();
		t.setLikeId(Identities.uuid());
		t.setLikeType(GlobalLike.LIKE_14_ACTIVITY_BRAINSTORMING_TRAINEE_ANSWER);
		t.setLikeTime(DateUtils.getNowTimeStamp());
		t.setTraineeId(loginUserId);
		t.setTargetId(anId);
		t.setReadState("N");
		t.setTargetTraineeId(traineeAnswerInfo.getTraineeId());
		tmeduMeLikeMapper.insert(t);
		return R.ok("点赞成功");
	}

	/**
	 * 取消点赞
	 * @param anId 头脑风暴学员作答表主键id
	 * @param loginUserId 当前登录用户
	 * @return
	 */
	@Override
	public R cancelLike(String anId, String loginUserId) {
		if (StrUtils.isEmpty(anId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglActivityBrainstormingTraineeAnswer traineeAnswerInfo = tevglActivityBrainstormingTraineeAnswerMapper.selectObjectById(anId);
		if (traineeAnswerInfo == null) {
			return R.error("无效的记录，点赞失败");
		}
		List<TmeduMeLike> list = getMeLikeListData(anId, loginUserId);
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
		params.put("likeType", GlobalLike.LIKE_14_ACTIVITY_BRAINSTORMING_TRAINEE_ANSWER);
		params.put("traineeId", loginUserId);
		return tmeduMeLikeMapper.selectListByMap(params);
	}
}
