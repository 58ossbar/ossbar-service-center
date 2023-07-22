package com.ossbar.modules.evgl.tch.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.ossbar.modules.evgl.pkg.persistence.TevglPkgActivityRelationMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.modules.evgl.pkg.service.TevglPkgInfoServiceImpl;
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
import com.alibaba.fastjson.JSONArray;
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
import com.ossbar.modules.common.ConstantProd;
import com.ossbar.modules.common.DictService;
import com.ossbar.modules.common.GlobalActivity;
import com.ossbar.modules.common.GlobalEmpiricalValueGetType;
import com.ossbar.modules.common.GlobalFavority;
import com.ossbar.modules.common.PkgPermissionUtils;
import com.ossbar.modules.common.PkgUtils;
import com.ossbar.modules.common.QrcodeUtils;
import com.ossbar.modules.common.UploadPathUtils;
import com.ossbar.modules.common.enums.BizCodeEnume;
import com.ossbar.modules.common.enums.CommonEnum;
import com.ossbar.modules.evgl.book.domain.TevglBookChapterVisible;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.book.persistence.TevglBookChapterMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookChapterVisibleMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubjectMapper;
import com.ossbar.modules.evgl.book.service.TevglBookSubjectServiceImpl;
import com.ossbar.modules.evgl.cloudpan.persistence.TcloudPanDirectoryMapper;
import com.ossbar.modules.evgl.cloudpan.persistence.TcloudPanFileMapper;
import com.ossbar.modules.evgl.empirical.domain.TevglEmpiricalSetting;
import com.ossbar.modules.evgl.empirical.domain.TevglEmpiricalType;
import com.ossbar.modules.evgl.empirical.persistence.TevglEmpiricalSettingMapper;
import com.ossbar.modules.evgl.empirical.persistence.TevglEmpiricalTypeMapper;
import com.ossbar.modules.evgl.medu.me.domain.TmeduMeFavority;
import com.ossbar.modules.evgl.medu.me.persistence.TmeduMeFavorityMapper;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgActivityRelation;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomService;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomTraineeService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClass;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomTop;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomTrainee;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomTraineeCheck;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomTopMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomTraineeCheckMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomTraineeMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClasstraineeMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchTeacherMapper;
import com.ossbar.modules.evgl.tch.vo.TevglTchClassroomTraineeVo;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeEmpiricalValueLog;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeEmpiricalValueLogMapper;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeInfoMapper;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.modules.sys.domain.TsysDict;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.BeanUtils;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.github.pagehelper.PageHelper;



@Service(version = "1.0.0")
@RestController
@RequestMapping("/tch/tevgltchclassroom")
public class TevglTchClassroomServiceImpl implements TevglTchClassroomService {

	private Logger log = LoggerFactory.getLogger(TevglTchClassroomServiceImpl.class);

	private static String classroomPicIndex = "14";

	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private TevglTchClassroomTopMapper tevglTchClassroomTopMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	// 上传地址
	@Value("${com.ossbar.file-upload-path}")
	private String ossbarFieUploadPath;
	// 显示地址
	@Value("${com.ossbar.file-access-path}")
	public String ossbarFieAccessPath;
	@Autowired
	private UploadPathUtils uploadPathUtils;
	@Autowired
	private TsysAttachService tsysAttachService;
	@Autowired
	private TevglPkgInfoMapper tevglPkgInfoMapper;
	@Autowired
	private TevglTchTeacherMapper tevglTchTeacherMapper;
	@Autowired
	private TevglTraineeInfoMapper tevglTraineeInfoMapper;
	@Autowired
	private TevglTchClassMapper tevglTchClassMapper;
	@Autowired
	private TevglBookSubjectMapper tevglBookSubjectMapper;
	@Autowired
	private TevglBookSubjectServiceImpl tevglBookSubjectServiceImpl;
	@Autowired
	private TevglTchClassroomTraineeMapper tevglTchClassroomTraineeMapper;
	/** 标识:课堂创建者isOwner（boolean值，true是false否） */
	private static String IS_OWNER = "isOwner";
	/** 标识:课堂是否收藏isCollected（boolean值，true已收藏false未收藏） */
	private static String IS_COLLECTED = "isCollected";
	/** 标识:用户是否加入课堂isJoined（boolean值，true已加入false未加入） */
	private static String IS_JOINED = "isJoined";
	@Autowired
	private TmeduMeFavorityMapper tmeduMeFavorityMapper;
	@Autowired
	private TevglPkgInfoServiceImpl tevglPkgInfoServiceImpl;
	@Autowired
	private QrcodeUtils qrcodeUtils;
	@Autowired
	private PkgPermissionUtils pkgPermissionUtils;

	@Autowired
	private TevglPkgActivityRelationMapper tevglPkgActivityRelationMapper;

	@Autowired
	private PkgUtils pkgUtils;
	@Autowired
	private TevglTchClassroomTraineeCheckMapper tevglTchClassroomTraineeCheckMapper;
	@Autowired
	private TevglTchClassroomTraineeService tevglTchClassroomTraineeService;
	@Autowired
	private TevglBookChapterMapper tevglBookChapterMapper;
	@Autowired
	private TevglBookChapterVisibleMapper tevglBookChapterVisibleMapper;

	@Autowired
	private CbRoomUtils roomUtils;
	@Autowired
	private TcloudPanDirectoryMapper tcloudPanDirectoryMapper;
	@Autowired
	private TcloudPanFileMapper tcloudPanFileMapper;
	@Autowired
	private DictService dictService;
	@Autowired
	private TevglEmpiricalTypeMapper tevglEmpiricalTypeMapper;
	@Autowired
	private TevglEmpiricalSettingMapper tevglEmpiricalSettingMapper;
	@Autowired
	private TevglTchClasstraineeMapper tevglTchClasstraineeMapper;
	@Autowired
	private TevglTraineeEmpiricalValueLogMapper tevglTraineeEmpiricalValueLogMapper;

	/**
	 * 查询列表(返回List<Bean>)
	 * 
	 * @param
	 * @return R
	 */
	@SysLog(value = "查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/tch/tevgltchclassroom/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<TevglTchClassroom> tevglTchClassroomList = tevglTchClassroomMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglTchClassroomList, "createUserId", "updateUserId");
		convertUtil.convertUserId2RealName(tevglTchClassroomList, "createUserId", "updateUserId");
		if (tevglTchClassroomList.size() > 0) {
			tevglTchClassroomList.forEach(a -> {
				if (a.getPic() != null && !"".equals(a.getPic())) {
					int i = a.getPic().indexOf(ossbarFieAccessPath);
					if (i == -1) {
						a.setPic(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("14") + "/" + a.getPic()); // 图片处理
					}
				}
			});
		}
		PageUtils pageUtil = new PageUtils(tevglTchClassroomList, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	/**
	 * 查询列表-无分页(返回List<Bean>)
	 * 
	 * @author znn
	 * @data 2020年6月10日
	 * @param
	 * @return R
	 */
	@SysLog(value = "查询列表(返回List<Bean>)")
	@GetMapping("/queryNoPage")
	@SentinelResource("/tch/tevgltchclassroom/queryNoPage")
	public List<TevglTchClassroom> queryNoPage(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		// 构建查询条件对象Query
		List<TevglTchClassroom> tevglTchClassroomList = tevglTchClassroomMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglTchClassroomList, "createUserId", "updateUserId");
		convertUtil.convertUserId2RealName(tevglTchClassroomList, "createUserId", "updateUserId");
		if (tevglTchClassroomList.size() > 0) {
			tevglTchClassroomList.forEach(a -> {
				if (a.getPic() != null && !"".equals(a.getPic())) {
					int i = a.getPic().indexOf(ossbarFieAccessPath);
					if (i == -1) {
						a.setPic(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("14") + "/" + a.getPic()); // 图片处理
					}
				}
				// 拼接班级名称
				if ("Y".equals(params.get("withClassName"))) {
					if (StrUtils.isNotEmpty(a.getClassName())) {
						a.setName(a.getName() + "    " + a.getClassName());
					}
				}
			});
		}
		return tevglTchClassroomList;
	}

	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * 
	 * @param
	 * @return R
	 */
	@SysLog(value = "查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/tch/tevgltchclassroom/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		params.put("state", "Y");
		params.put("sidx", "t1.create_time");
		params.put("order", "desc, t1.name desc");
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<Map<String, Object>> tevglTchClassroomList = tevglTchClassroomMapper.selectListMapByMap(query);
		tevglTchClassroomList.stream().forEach(item -> {
			item.put("pic", uploadPathUtils.stitchingPath(item.get("pic"), "14"));
			item.put("classroomStateName", item.get("classroom_state"));
			// 处理课堂被移交的情况
			if (!StrUtils.isNull(item.get("receiver_user_id"))) {
				TevglTchTeacher tevglTchTeacher = tevglTchTeacherMapper.selectObjectByTraineeId(item.get("receiver_user_id"));
				if (tevglTchTeacher != null) {
					item.put("teacher_name", tevglTchTeacher.getTeacherName());
					item.put("mobile", tevglTchTeacher.getUsername());
				}
			}
		});
		convertUtil.convertDict(tevglTchClassroomList, "classroomStateName", "classroomState"); // 课堂状态
		PageUtils pageUtil = new PageUtils(tevglTchClassroomList, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	/**
	 * 新增
	 * 
	 * @param tevglTchClassroom
	 * @throws OssbarException
	 */
	@SysLog(value = "新增")
	@PostMapping("save")
	@SentinelResource("/tch/tevgltchclassroom/save")
	public R save(@RequestBody(required = false) TevglTchClassroom tevglTchClassroom) throws OssbarException {
		return R.ok();
	}

	/**
	 * 修改
	 * 
	 * @param tevglTchClassroom
	 * @throws OssbarException
	 */
	@SysLog(value = "修改")
	@PostMapping("update")
	@SentinelResource("/tch/tevgltchclassroom/update")
	public R update(@RequestBody(required = false) TevglTchClassroom tevglTchClassroom) throws OssbarException {
		if (StrUtils.isEmpty(tevglTchClassroom.getUpdateUserId())) {
			tevglTchClassroom.setUpdateUserId(serviceLoginUtil.getLoginUserId());
		}
		// 不传值则默认不置顶
		if (StrUtils.isEmpty(tevglTchClassroom.getIsTop())) {
			tevglTchClassroom.setIsTop("N");
		}
		tevglTchClassroom.setUpdateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglTchClassroom);
		tevglTchClassroomMapper.update(tevglTchClassroom);
		// 如果上传了资源文件
		String attachId = tevglTchClassroom.getAttachId();
		if (attachId != null && !"".equals(attachId)) {
			tsysAttachService.updateAttach(attachId, tevglTchClassroom.getCtId(), "0", "14");
		}
		return R.ok();
	}

	/**
	 * 单条删除
	 * 
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value = "单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/tch/tevgltchclassroom/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglTchClassroomMapper.delete(id);
		return R.ok();
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value = "批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/tch/tevgltchclassroom/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglTchClassroomMapper.deleteBatch(ids);
		return R.ok();
	}

	/**
	 * 查看明细
	 * 
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value = "查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/tch/tevgltchclassroom/view")
	public R view(@PathVariable("id") String id) {
		TevglTchClassroom a = tevglTchClassroomMapper.selectObjectById(id);
		if (a == null) {
			return R.ok().put(Constant.R_DATA, new TevglTchClassroom());
		}
		if (StrUtils.isNotEmpty(a.getPic())) {
			int i = a.getPic().indexOf(ossbarFieAccessPath);
			if (i == -1) {
				a.setPic(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("14") + "/" + a.getPic()); // 图片处理
			}
		}
		return R.ok().put(Constant.R_DATA, a);
	}

	/**
	 * 根据课堂主键查询
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public TevglTchClassroom selectObjectById(Object id) {
		return tevglTchClassroomMapper.selectObjectById(id);
	}

	/**
	 * <p>
	 * 课堂列表
	 * </p>
	 * 
	 * @author huj
	 * @data 2019年8月20日
	 * @param params
	 * @param loginUserId
	 * @param type        类型值对应：1时查询加入的课堂（我听的课）2自己创建的课堂（我教的课），为空则查全部
	 * @return
	 * @apiNote { ctId 课堂主键，name 课堂名称，pic 课堂封面， studyNum 学习人数， subjectId 教材主键，
	 *          className 班级名称， teacherName 教师名称， teacherPic 教师头像， subjectName 教材名称，
	 *          pkgName 教学包名称， pkgResCount 教学包资源数， pkgActCount活动数 subjectProperty
	 *          必修、选修， teachingAssistantId 助教（学员主键）， traineeName 助教名称， traineePic
	 *          助教头像， }
	 */
	@Override
	@PostMapping("/listClassroom")
	public R listClassroom(@RequestParam Map<String, Object> params, String loginUserId, String type) {
		Object channel = params.get("channel");
		Object name = params.get("name");
		// 注意点,只查询未被逻辑删除的课堂
		params.put("state", "Y");
		// 处理查询条件
		handleQueryParameters(params, loginUserId, type);
		List<Map<String, Object>> tevglTchClassroomList = new ArrayList<>();
		PageUtils pageUtil;
		if (StrUtils.isEmpty(type) || "2".equals(type)) {
			// 处理查询条件
			handleQueryParametersV2(params, loginUserId, type);
			// 构建查询条件对象Query
			Query query = new Query(params);
			PageHelper.startPage(query.getPage(), query.getLimit());
			tevglTchClassroomList = tevglTchClassroomMapper.selectListMapForCommonV2(query);
			pageUtil = new PageUtils(tevglTchClassroomList, query.getPage(), query.getLimit());
		} else {
			// 处理查询条件
			handleQueryParameters(params, loginUserId, type);
			// 构建查询条件对象Query
			Query query = new Query(params);
			PageHelper.startPage(query.getPage(), query.getLimit());
			tevglTchClassroomList = tevglTchClassroomMapper.selectListMapForCommon(query);
			pageUtil = new PageUtils(tevglTchClassroomList, query.getPage(), query.getLimit());
		}
		TevglTchTeacher tevglTchTeacher = null;
        if (StrUtils.isNotEmpty(loginUserId)) {
            tevglTchTeacher = tevglTchTeacherMapper.selectObjectByTraineeId(loginUserId);
        }
		convertUtil.convertDict(tevglTchClassroomList, "classroomStateName", "classroomState"); // 课堂状态
		convertUtil.convertDict(tevglTchClassroomList, "subjectProperty", "subjectProperty"); // 课程属性(来源字典:选修，必修等)
		convertUtil.convertOrgId(tevglTchClassroomList, "orgIdTeacher");
		// convertUtil.convertOrgId(tevglTchClassroomList, "orgIdClass"); // 需求：取机构简称
		if (tevglTchClassroomList != null && tevglTchClassroomList.size() > 0) {
			// 获取登录用户的所有课堂收藏数据
			List<TmeduMeFavority> tmeduMeFavorityList = new ArrayList<TmeduMeFavority>();
			// 获取登录用户置顶的课堂数据
			List<TevglTchClassroomTop> userTopList = new ArrayList<TevglTchClassroomTop>();
			// 获取登录用户所申请或加入的课堂id
			List<Map<String, Object>> classroomTraineeList = new ArrayList<Map<String, Object>>();
			// 如果已登录
			if (StrUtils.isNotEmpty(loginUserId)) {
				params.clear();
				params.put("traineeId", loginUserId);
				tmeduMeFavorityList = tmeduMeFavorityMapper.selectListByMap(params);
				// 获取登录用户置顶的课堂数据
				params.clear();
				params.put("traineeId", loginUserId);
				userTopList = tevglTchClassroomTopMapper.selectListByMap(params);
				// 获取登录用户所申请或加入的课堂id
				classroomTraineeList = tevglTchClassroomMapper.getCtIdsByTraineeId(loginUserId);
			}
			for (Map<String, Object> classroomInfo : tevglTchClassroomList) {
				// 移除验证码
				if (StrUtils.isNotEmpty(type) && !"2".equals(type)) {
					classroomInfo.remove("invitationCode");
				}
				// 处理置顶标识,默认不是置顶
				boolean isTop = userTopList.stream().anyMatch(a -> a.getCtId().equals(classroomInfo.get("ctId")));
				classroomInfo.put("isTop", isTop);
				// 处理课堂是否属于当前登录用户（创建者）
				isThisClassroomBelongToLoginUser(classroomInfo, loginUserId);
				// 处理是否申请了加入课堂,并通过了审核
				isApplyAndIsPass(classroomInfo, loginUserId, classroomTraineeList);
				// 处理是否加入了此课堂
				isHasJoinedThisClassroom(classroomInfo, loginUserId, classroomTraineeList);
				// 处理课堂是否收藏
				isCollectedThisClassroom(classroomInfo, loginUserId, tmeduMeFavorityList);
				// 处理课堂封面和二维码
				handlePicAndQrcode(classroomInfo);
				// 处理教师信息
				handleTeacherInfo(classroomInfo);
				// 助教图片
				if (classroomInfo.get("traineePic") != null) {
					String traineePic = (String) classroomInfo.get("traineePic");
					classroomInfo.put("traineePic", uploadPathUtils.stitchingPath(traineePic, "16"));
				}
				// 处理当课堂已结束时，是否还能进入课堂
                handleAccessState(classroomInfo, loginUserId, classroomTraineeList, tevglTchTeacher);
			}
		}
		// 获取一些其它数据(小程序端才去获取下，PC端没有地方展示，暂不查询)
		Map<String, Object> otherInfo = channel == null ? getOtherInfo(pageUtil, loginUserId, type, name)
				: new HashMap<>();
		// 返回数据
		return R.ok().put(Constant.R_DATA, pageUtil).put("otherInfo", otherInfo).put("traineeId", loginUserId);
	}

    /**
     * 处理当课堂已结束时，是否还能进入课堂
     * @param classroomInfo
     * @apiNote 当课堂状态为3已结束，且accessState为N时，表示不再允许学生进入课堂！
     */
    private void handleAccessState(Map<String, Object> classroomInfo, String loginUserId, List<Map<String, Object>> classroomTraineeList, TevglTchTeacher tevglTchTeacher) {
    	// 如果是老师身份，且账号被禁用，且课堂是该用户创建
        String createUserId = StrUtils.notNull(classroomInfo.get("createUserId")) ? classroomInfo.get("createUserId").toString() : "";
        String receiverUserId = StrUtils.notNull(classroomInfo.get("receiverUserId")) ? classroomInfo.get("receiverUserId").toString() : "";
        boolean isCreator = StrUtils.isEmpty(receiverUserId) && StrUtils.isNotEmpty(loginUserId) && loginUserId.equals(createUserId);
        boolean isReceiver = StrUtils.isNotEmpty(receiverUserId) && StrUtils.isNotEmpty(loginUserId) && loginUserId.equals(receiverUserId);
        if (isCreator || isReceiver) {
            classroomInfo.put("accessState", CommonEnum.STATE_YES.getCode());
            if (tevglTchTeacher == null || CommonEnum.STATE_NO.getCode().equals(tevglTchTeacher.getState())) {
                classroomInfo.put("accessState", CommonEnum.STATE_NO.getCode());
            }
        } else {
            if ("3".equals(classroomInfo.get("classroomState"))) {
                // 默认都不可以进入课堂了
                classroomInfo.put("accessState", CommonEnum.STATE_NO.getCode());
                // 如果且登录的情况
                if (StrUtils.isNotEmpty(loginUserId)) {
                    // 判断该用户在某课堂依旧是否被允许进入
                    boolean flag = classroomTraineeList.stream()
                            .anyMatch(a -> a.get("ctId").equals(classroomInfo.get("ctId"))
                                    && CommonEnum.STATE_YES.getCode().equals(a.get("accessState"))
                            );
                    classroomInfo.put("accessState", flag ? CommonEnum.STATE_YES.getCode() : CommonEnum.STATE_NO.getCode());
                }

            } else {
                classroomInfo.put("accessState", CommonEnum.STATE_YES.getCode());
            }
        }
    }
	
	private Map<String, Object> getOtherInfo(PageUtils pageUtil, String loginUserId, String type, Object name) {
		// 查询条件
		Map<String, Object> map = new HashMap<>();
		// 需要返回的额外数据
		Map<String, Object> otherInfo = new HashMap<>();
		// 点击【我要学习】，去查我教的课
		if ("1".equals(type)) {
			// 听课的数量
			otherInfo.put("lecturesNum", pageUtil.getTotalCount());
			// 教课的数量
			map.clear();
			map.put("name", name);
			map.put("state", "Y");
			map.put("createUserId", loginUserId);
			// 小程序我教的课，已结束的课堂中，只展示当前年份的
			map.put("justShowThisYear", "Y");
			String string = DateUtils.getNowTimeStamp();
			map.put("nowYear", string.substring(0, 4));
			Integer teachNum = tevglTchClassroomMapper.countNumByMap(map);
			// 再查下接收的课
			map.put("createUserId", null);
			map.put("receiverUserId", loginUserId);
			Integer receNum = tevglTchClassroomMapper.countNumByMap(map);
			otherInfo.put("teachNum", teachNum + receNum);
		}
		// 点击【我教的课】，去查询我学的课的数量
		if ("2".equals(type)) {
			// 听课的数量
			map.clear();
			map.put("state", "Y");
			map.put("name", name);
			map.put("traineeId", loginUserId);
			Integer lecturesNum = tevglTchClassroomMapper.countNumByMap(map);
			otherInfo.put("lecturesNum", lecturesNum);
			// 教课的数量+接管的课堂
			map.put("createUserId", null);
			map.put("receiverUserId", loginUserId);
			Integer receNum = tevglTchClassroomMapper.countNumByMap(map);
			otherInfo.put("teachNum", pageUtil.getTotalCount() + receNum);
		}
		return otherInfo;
	}

	/**
	 * 处理排序等条件
	 * 
	 * @param params
	 * @param loginUserId 当前登录用户id
	 * @param type        类型值对应：1时查询加入的课堂（我听的课）2自己创建的课堂（我教的课），为空则查全部
	 */
	private void handleQueryParameters(Map<String, Object> params, String loginUserId, String type) {
		Object sortBy = params.get("sortBy");
		if ("newest".equals(sortBy)) {
			params.put("sidx", "t1.classroom_state");
			params.put("order", "asc, t1.create_time desc");
		} else if ("hot".equals(sortBy)) {
			params.put("sidx", "t1.classroom_state");
			params.put("order", "asc, t1.study_num desc, t1.create_time desc");
		} else {
			// 默认按状态升序，置顶时间降序，创建时间降序
			params.put("sidx", "t1.classroom_state");
			params.put("order", "asc, t9.update_time desc, t1.create_time desc");
		}
		// 区分查询[我听的课][我教的课][全部]
		if (StrUtils.isEmpty(type)) {
			// 不查询已结束的课堂
			// params.put("noEnd", "Y");
		} else if ("1".equals(type)) {
			params.put("traineeId", loginUserId);
		} else if ("2".equals(type)) { // 我的教的课
			params.put("sidx", "t1.classroom_state");
			params.put("order", "asc, t9.update_time desc, t1.create_time desc");
			params.put("createUserId", loginUserId);
		}
	}

	private void handleQueryParametersV2(Map<String, Object> params, String loginUserId, String type) {
		Object sortBy = params.get("sortBy");
		if ("newest".equals(sortBy)) {
			params.put("sidx", "classroom_state");
			params.put("order", "asc, create_time desc");
		} else if ("hot".equals(sortBy)) {
			params.put("sidx", "classroom_state");
			params.put("order", "asc, study_num desc, create_time desc");
		} else {
			// 默认按状态升序，置顶时间降序，创建时间降序
			params.put("sidx", "classroom_state");
			params.put("order", "asc, update_time desc, create_time desc");
		}
		// 区分查询[我听的课][我教的课][全部]
		if (StrUtils.isEmpty(type)) {
			// 不查询已结束的课堂
			// params.put("noEnd", "Y");
		} else if ("1".equals(type)) {
			params.put("traineeId", loginUserId);
		} else if ("2".equals(type)) { // 我的教的课
			params.put("createUserId", loginUserId);
		}
		log.debug("查询条件 handleQueryParametersV2() => {}", params);
	}

	/**
	 * 处理是否加入了此课堂（如果课堂需要审核，则认为审核通过了，才算加入了此课堂）
	 * 
	 * @param classroomInfo        课堂信息
	 * @param loginUserId          当前登录用户
	 * @param classroomTraineeList
	 */
	private void isHasJoinedThisClassroom(Map<String, Object> classroomInfo, String loginUserId,
			List<Map<String, Object>> classroomTraineeList) {
		boolean isJoined = classroomTraineeList.stream()
				.anyMatch(a -> a.get("ctId").equals(classroomInfo.get("ctId")) && "Y".equals(a.get("state")));
		classroomInfo.put(IS_JOINED, isJoined);
	}

	/**
	 * 新增课堂（新版优化）
	 * 
	 * @param tevglTchClassroom
	 * @param loginUserId
	 * @return
	 */
	@Override
	@Transactional
	//@NoRepeatSubmit
	public R saveClassroomInfoNew(TevglTchClassroom tevglTchClassroom, String loginUserId) {
		TevglTraineeInfo tevglTraineeInfo = tevglTraineeInfoMapper.selectObjectById(loginUserId);
		if (tevglTraineeInfo == null) {
			return R.error("无效的用户信息");
		}
		// 合法性校验
		R r = checkIsPassForCommon(tevglTchClassroom, loginUserId);
		Integer code = (Integer) r.get("code");
		if (code != 0) {
			return r;
		}
		// 判断当前登录用户是否有权限开设课堂
		if (!hasOfferCoursesPermission(loginUserId)) {
			return R.error("您暂无权限开设课堂");
		}
		// 前端用户是否采用了教学包，不为空表示采用了，为空表示没采用
		String inputPkgId = tevglTchClassroom.getPkgId();
		boolean empty = StrUtils.isEmpty(inputPkgId);
		log.debug("是否采用了[教学包]: " + inputPkgId);
		TevglPkgInfo userSelectedPkgInfo = null;
		String finalPkgId = null;
		// 查询条件
		Map<String, Object> params = new HashMap<>();
		// 用户选择的职业路径
		String userSelectedMajorId = tevglTchClassroom.getMajorId();
		// 用户选择的课程体系
		String userSelectedSubjectId = tevglTchClassroom.getSubjectId();
		// 如果没采用包，先根据同职业路径同课程体系同用户去查找未发布状态且状态有效的的教学包
		if (empty) {
			// 如果有源教学包
			TevglPkgInfo sourceTevglPkgInfo = pkgUtils.getOnlyPkgByMajorIdSubjectId(userSelectedMajorId,
					userSelectedSubjectId, loginUserId, params);
			if (sourceTevglPkgInfo != null) {
				// 进一步检验源教学包是否有衍生版本
				params.clear();
				params.put("state", "Y");
				params.put("releaseStatus", "Y");
				params.put("refPkgId", sourceTevglPkgInfo.getPkgId());
				List<TevglPkgInfo> tevglPkgInfoList = tevglPkgInfoMapper.selectListByMap(params);
				if (tevglPkgInfoList == null || tevglPkgInfoList.size() == 0) {
					return R.error("请采用教学包，如若没有教学包可用，请自行去【教学包】页面发布一个，或联系他人授权一个");
				}
				return R.error("请采用教学包");
			}
			// 如果没有源教学包，则系统为其创建一个v1.0.0的源教学包，且根据这个创建好的源教学包，生成发布一个v1.0.1衍生版本
			if (sourceTevglPkgInfo == null) {
				TevglPkgInfo sourcePackage = createSourcePackage(userSelectedMajorId, userSelectedSubjectId,
						loginUserId);
				if (sourcePackage != null) {
					// 成功之后，根据这个去发布一个
					R result = releaseSimpleTeachingPackage(sourcePackage.getPkgId(), userSelectedSubjectId,
							loginUserId, userSelectedMajorId);
					Object object = result.get(Constant.R_DATA);
					finalPkgId = object.toString();
					log.debug("教学包发布结果：" + result);
					userSelectedPkgInfo = tevglPkgInfoMapper.selectObjectById(finalPkgId);
				}
			}
		} else {
			userSelectedPkgInfo = tevglPkgInfoMapper.selectObjectById(inputPkgId);
		}
		if (userSelectedPkgInfo == null || !"Y".equals(userSelectedPkgInfo.getState())) {
			return R.error("无效的教学包，请重新选择");
		}
		// 调用方法，获取待插入数据库的课堂
		List<TevglTchClassroom> classroomList = listClassroomList(tevglTchClassroom, loginUserId);
		if (classroomList == null || classroomList.size() == 0) {
			return R.error("无法创建课堂，请联系系统管理员");
		}
		// 获取教学包对应教材的章节
		List<String> chapterIdList = tevglBookChapterMapper.selectChapterIdList(userSelectedPkgInfo.getSubjectId());
		// 获取教学包对应的活动
		params.clear();
		// params.put("pkgId", inputPkgId);
		params.put("pkgId", userSelectedPkgInfo.getPkgId());
		List<TevglPkgActivityRelation> activityList = tevglPkgActivityRelationMapper.selectListByMap(params);
		// 等待入库，章节是否可见
		List<TevglBookChapterVisible> tevglBookChapterVisibleList = new ArrayList<TevglBookChapterVisible>();
		// 等待入库，教学包与活动关系
		List<TevglPkgActivityRelation> insertRelationList = new ArrayList<TevglPkgActivityRelation>();

		// 额外处理
		String sbId = null;
		// 遍历课堂
		for (int i = 0; i < classroomList.size(); i++) {
			// 课堂信息
			TevglTchClassroom room = classroomList.get(i);
			// 课堂群,已课堂主键作为群聊主键

			// 教学包主键
			String theNewPkgId = null;
			// [情况一]: 未采用教学包时的逻辑(则需要生成一个新的教学包)
			if (empty) {
				if (StrUtils.isNotEmpty(finalPkgId)) {
					TevglPkgInfo pkgInfo = tevglPkgInfoMapper.selectObjectById(finalPkgId);
					if (pkgInfo == null || !"Y".equals(pkgInfo.getState())) {
						return R.error("无效的教学包，请重新选择");
					}
					R res = tevglPkgInfoServiceImpl.copyThen(finalPkgId, loginUserId, "3", tevglTchClassroom.getName());
					log.debug("教学包复制结果" + res.toString());
					// 拿到新的教学包与对应的课程
					theNewPkgId = (String) res.get(Constant.R_DATA);
					// 设置教学包，每个课堂对应一个教学包
					room.setPkgId(theNewPkgId);
				}
			}
			// [情况二]：采用教学包的情况
			if (!empty) {
				log.debug("进入采用教学包的逻辑");
				/*R res = tevglPkgInfoServiceImpl.copyThen(inputPkgId, loginUserId, "3", tevglTchClassroom.getName());
				log.debug("教学包引用结果" + res.toString());
				// 拿到新的教学包与对应的课程
				theNewPkgId = (String)res.get(Constant.R_DATA);*/
				theNewPkgId = copyThen(inputPkgId, userSelectedPkgInfo, tevglTraineeInfo, tevglTchClassroom.getName());
				log.debug("新的课堂教学包：" + theNewPkgId);
				// 设置教学包，每个课堂对应一个教学包
				room.setPkgId(theNewPkgId);
			}
			if (StrUtils.isEmpty(theNewPkgId)) {
				continue;
			}
			// 教学包与活动的关系
			for (TevglPkgActivityRelation relation : activityList) {
				TevglPkgActivityRelation t = new TevglPkgActivityRelation();
				t.setPaId(Identities.uuid());
				t.setPkgId(theNewPkgId);
				t.setActivityId(relation.getActivityId());
				t.setActivityType(relation.getActivityType());
				t.setActivityState("0"); // 实际活动状态(0未开始1进行中2已结束)
				if (GlobalActivity.ACTIVITY_3_ANSWER_DISCUSS.equals(relation.getActivityType())) {
					t.setGroupId(Identities.uuid());
				}
				insertRelationList.add(t);
			}
			// 设置章节学员是否可见
			for (String chapterId : chapterIdList) {
				TevglBookChapterVisible v = new TevglBookChapterVisible();
				v.setId(Identities.uuid());
				v.setPkgId(theNewPkgId);
				v.setChapterId(chapterId);
				v.setIsTraineesVisible("Y");
				tevglBookChapterVisibleList.add(v);
			}
			// ================================================== 即时通讯相关处理 begin
			// ==================================================
			// 挪到 【开始课堂】步骤，由于未开始的课堂也允许加入，再次挪回来，【开始活动】那边的代码注释
			/*// 创建课堂群
			String className = "";
			TevglTchClass tchClass = tevglTchClassMapper.selectObjectById(room.getClassId());
			if (tchClass != null) {
			    className = tchClass.getClassName();
			}
			String groupId = createTimGroup(room.getCtId(), room.getName(), room.getPic(), loginUserId, className);
			// 课堂创建者自己成为群主
			createTimGroupUserAdmin(groupId, loginUserId);
			// 更新聊天列表记录
			timChatListService.save(null, loginUserId, groupId, "2", 0, "", true);*/
			// ================================================== 即时通讯相关处理 end
			// ==================================================
		}
		// 批量新增
		if (classroomList != null && classroomList.size() > 0) {
			// 批量新增课堂
			tevglTchClassroomMapper.insertBatch(classroomList);
			// 从字典获取经验值
			Integer empiricalValue = findEmpiricalValue();
			List<TevglTraineeEmpiricalValueLog> logList = new ArrayList<>();
			// 将班级里面的学员加入到课堂里面
			for (TevglTchClassroom room : classroomList) {
				String ctId = room.getCtId();
				String classId = room.getClassId();
				List<String> traineeIdList = tevglTchClasstraineeMapper.selectTraineeIdListByClassId(classId);
				tevglTchClassroomTraineeService.saveClassroomTraineeBatch(ctId, traineeIdList);
				// 记录老师获取的经验值
				TevglTraineeEmpiricalValueLog t = new TevglTraineeEmpiricalValueLog();
				t.setEvId(Identities.uuid());
				t.setType(GlobalEmpiricalValueGetType.TEACHER_GEN_CLASS_15);
				t.setTraineeId(loginUserId);
				t.setCtId(room.getCtId());
				t.setEmpiricalValue(empiricalValue);
				t.setState("Y");
				t.setCreateTime(DateUtils.getNowTimeStamp());
				t.setMsg("创建课堂 " + room.getName() + " 获得" + t.getEmpiricalValue() + "经验值");
				logList.add(t);
			}
			// 批量新增经验值日志
			tevglTraineeEmpiricalValueLogMapper.insertBatch(logList);
		}
		// 批量插入
		if (tevglBookChapterVisibleList != null && tevglBookChapterVisibleList.size() > 0) {
			tevglBookChapterVisibleMapper.insertBatch(tevglBookChapterVisibleList);
		}
		if (insertRelationList.size() > 0) {
			tevglPkgActivityRelationMapper.insertBatch(insertRelationList);
		}
		// 更新被采用的教学包的引用数
		pkgUtils.plusPkgRefNum(inputPkgId, classroomList.size());
		// 更新用户创建的课堂数
		TevglTraineeInfo traineeInfo = new TevglTraineeInfo();
		traineeInfo.setTraineeId(loginUserId);
		traineeInfo.setClassroomNum(classroomList.size());
		tevglTraineeInfoMapper.plusNum(traineeInfo);
		// 返回数据
		Map<String, Object> data = new HashMap<>();
		data.put("ctId", classroomList.get(0).getCtId());
		data.put("pkgId", classroomList.get(0).getPkgId());
		TevglPkgInfo pkgInfo = tevglPkgInfoMapper.selectObjectById(inputPkgId);
		data.put("subjectId", sbId);
		if (pkgInfo != null) {
			data.put("subjectId", pkgInfo.getSubjectId());
		}
		data.put("classId", classroomList.get(0).getClassId());
		return R.ok("课堂开设成功").put(Constant.R_DATA, data);
	}

	/**
	 * 从字典 teach_exp_type 获取老师获取经验值的方式
	 * @return
	 */
	private Integer findEmpiricalValue() {
		List<TsysDict> list = dictService.getTsysDictListByType("teach_exp_type");
		if (list != null && list.size() > 0) {
			List<TsysDict> collect = list.stream().filter(a -> a.getDictCode().equals(GlobalEmpiricalValueGetType.TEACHER_GEN_CLASS_15)).collect(Collectors.toList());
			if (collect != null && collect.size() > 0) {
				return Integer.valueOf(collect.get(0).getDictValue());
			}
		}
		return 10;
	}

	/**
	 * 创建一个课堂教学包
	 * 
	 * @param sourcePkgId
	 * @param sourcePkgInfo
	 * @param traineeInfo
	 * @param classroomName
	 * @return
	 */
	private String copyThen(String sourcePkgId, TevglPkgInfo sourcePkgInfo, TevglTraineeInfo traineeInfo,
			String classroomName) {
		if (sourcePkgInfo == null || StrUtils.isEmpty(sourcePkgId) || !sourcePkgId.equals(sourcePkgInfo.getPkgId())) {
			return null;
		}
		String newPkgId = Identities.uuid();
		String oldSubjectId = sourcePkgInfo.getSubjectId();
		// 示例化对象，重置一些数据
		TevglPkgInfo t = (TevglPkgInfo) BeanUtils.cloneBean(sourcePkgInfo);
		if (StrUtils.isNotEmpty(classroomName)) {
			t.setPkgName(classroomName);
		}
		t.setPkgId(newPkgId);
		t.setSubjectId(oldSubjectId);
		t.setBaseSubject(oldSubjectId);
		t.setCreateUserId(traineeInfo.getTraineeId());
		t.setDisplay("3"); // 是否可见
		t.setRefPkgId(sourcePkgId); // 教学包来源
		t.setCreateTime(DateUtils.getNowTimeStamp());
		t.setUpdateUserId(null);
		t.setUpdateUserId(null);
		t.setState("Y");
		t.setReleaseStatus("N");
		t.setPkgNo(Identities.uuid());
		t.setPkgTraineeName(traineeInfo.getTraineeName());
		t.setPkgTraineeMobile(traineeInfo.getMobile());
		t.setPkgTraineeQq(traineeInfo.getTraineeQq());
		t.setPkgTraineeEmail(traineeInfo.getEmail());
		tevglPkgInfoMapper.insert(t);
		return newPkgId;
	}

	/**
	 * 发布一个简单的教学包
	 * 
	 * @param inputPkgId
	 * @param loginUserId
	 * @return
	 */
	private R releaseSimpleTeachingPackage(String inputPkgId, String subjectRef, String loginUserId,
			String userSelectedMajorId) {
		TevglPkgInfo pkgInfo = tevglPkgInfoMapper.selectObjectById(inputPkgId);
		if (pkgInfo == null) {
			return R.error("无效的教学包");
		}
		if (!"Y".equals(pkgInfo.getState())) {
			return R.error("教学包状态已无效");
		}
		String newPkgId = Identities.uuid();
		String newSubjectId = Identities.uuid();
		// 复制一份源教学包对应的课程
		String subjectId = pkgInfo.getSubjectId();
		TevglBookSubject subjectInfo = tevglBookSubjectMapper.selectObjectById(subjectId);
		if (subjectInfo != null) {
			log.debug("复制一份源教学包对应的课程" + subjectId);
			TevglBookSubject tevglBookSubject = subjectInfo;
			tevglBookSubject.setSubjectId(newSubjectId);
			tevglBookSubject.setSubjectRef(subjectRef);
			tevglBookSubject.setCreateUserId(loginUserId);
			tevglBookSubject.setCreateTime(DateUtils.getNowTimeStamp());
			// 生成新的课程,并得到新的课程ID
			tevglBookSubjectMapper.insert(tevglBookSubject);
			// 复制
			tevglBookSubjectServiceImpl.copy(subjectId, newSubjectId, loginUserId, newPkgId);
		}
		// 示例化对象，重置一些数据
		TevglPkgInfo t = new TevglPkgInfo();
		t = pkgInfo;
		t.setPkgId(newPkgId);
		t.setRefPkgId(inputPkgId); // 教学包来源
		t.setMajorId(userSelectedMajorId);
		t.setSubjectId(newSubjectId);
		t.setPkgVersion("v1.0.1");
		t.setBaseSubject(null);
		t.setReleaseStatus("Y");
		t.setCreateUserId(loginUserId);
		t.setDisplay(null); // 是否可见
		t.setCreateTime(DateUtils.getNowTimeStamp());
		t.setUpdateUserId(null);
		t.setUpdateUserId(null);
		t.setState("Y");
		t.setPkgNo(Identities.uuid());
		tevglPkgInfoMapper.insert(t);
		log.debug("新的教学包:" + newPkgId);
		log.debug("新的课程:" + newSubjectId);
		return R.ok("复制成功").put(Constant.R_DATA, newPkgId).put("newSubjectId", newSubjectId);
	}

	/**
	 * 创建一个源教学包
	 * 
	 * @param userSelectedSubjectId 前端用户选择的课程体系（课程）
	 * @param loginUserId           当前登录用户
	 * @return
	 */
	private TevglPkgInfo createSourcePackage(String userSelectedMajorId, String userSelectedSubjectId,
			String loginUserId) {
		TevglTraineeInfo traineeInfo = tevglTraineeInfoMapper.selectObjectById(loginUserId);
		// 平台的课程
		TevglBookSubject tevglBookSubject = tevglBookSubjectMapper.selectObjectById(userSelectedSubjectId);
		if (tevglBookSubject != null) {
			// 生成新的教材
			String newSubjectId = createSubjectInfo(tevglBookSubject, userSelectedSubjectId, loginUserId);
			// 填充教学包信息
			TevglPkgInfo t = doFillPkgInfo(tevglBookSubject, newSubjectId, traineeInfo, userSelectedMajorId);
			String newPkgId = t.getPkgId();
			t.setSubjectId(newSubjectId);
			tevglPkgInfoMapper.insert(t);
			// 根据课程生成教材
			R r = tevglBookSubjectServiceImpl.copy(userSelectedSubjectId, newSubjectId, loginUserId, newPkgId);
			log.debug("复制结果" + r);
			return t;
		}
		return null;
	}

	/**
	 * 填充教学包信息
	 * 
	 * @param tevglBookSubject 用户前端选择的平台的课程体系
	 * @param newSubjectId     根据课程体系生成的新的教材
	 * @param traineeInfo      当前登录用户的信息
	 */
	private TevglPkgInfo doFillPkgInfo(TevglBookSubject tevglBookSubject, String newSubjectId,
			TevglTraineeInfo traineeInfo, String userSelectedMajorId) {
		TevglPkgInfo t = new TevglPkgInfo();
		t.setPkgId(Identities.uuid());
		t.setPkgName(tevglBookSubject.getSubjectName());
		t.setMajorId(userSelectedMajorId);
		t.setSubjectId(newSubjectId);
		t.setRefPkgId(null);
		t.setPkgLogo(null);
		t.setDisplay(null);
		t.setReleaseStatus("N");
		t.setPkgVersion("v1.0.0");
		t.setState("Y");
		// 适用层次(来源字典：1本科，2高职，3中职)
		t.setPkgLevel("1");
		// 使用限制(来源字典：1授权，2免费)
		t.setPkgLimit("1");
		// 发布方大类(来源字典：1学校，2个人，3创蓝)
		t.setDeployMainType("2");
		t.setPkgActCount(0);
		t.setPkgRefCount(0);
		t.setPkgResCount(0);
		t.setViewNum(0);
		t.setCreateTime(DateUtils.getNowTimeStamp());
		t.setCreateUserId(traineeInfo.getTraineeId());
		t.setPkgTraineeName(StrUtils.isNull(traineeInfo) ? null
				: StrUtils.isEmpty(traineeInfo.getTraineeName()) ? traineeInfo.getNickName()
						: traineeInfo.getTraineeName());
		t.setPkgTraineeQq(StrUtils.isNull(traineeInfo) ? null
				: StrUtils.isEmpty(traineeInfo.getTraineeQq()) ? null : traineeInfo.getTraineeQq());
		t.setPkgTraineeEmail(StrUtils.isNull(traineeInfo) ? null
				: StrUtils.isEmpty(traineeInfo.getEmail()) ? null : traineeInfo.getEmail());
		t.setPkgTraineeMobile(StrUtils.isNull(traineeInfo) ? null
				: StrUtils.isEmpty(traineeInfo.getMobile()) ? null : traineeInfo.getMobile());
		return t;
	}

	/**
	 * 生成教学包对应的教材
	 * 
	 * @param subjectInfo
	 * @param userSelectedSubjectId 用户选择的课程体系
	 * @param loginUserId
	 * @return
	 */
	private String createSubjectInfo(TevglBookSubject subjectInfo, String userSelectedSubjectId, String loginUserId) {
		String uuid = Identities.uuid();
		TevglBookSubject t = new TevglBookSubject();
		t = subjectInfo;
		// 重新填充部分信息
		t.setSubjectId(uuid);
		// 重点
		t.setSubjectRef(userSelectedSubjectId);
		t.setCreateUserId(loginUserId);
		t.setCreateTime(DateUtils.getNowTimeStamp());
		t.setUpdateTime(null);
		t.setUpdateUserId(null);
		t.setState("Y");
		t.setViewNum(0);
		t.setStarLevel(0);
		t.setLikeNum(0);
		t.setStoreNum(0);
		t.setReportNum(0);
		// 执行保存
		tevglBookSubjectMapper.insert(t);
		return t.getSubjectId();
	}

	/**
	 * 填充并返回单个或多个课堂的基本数据
	 * 
	 * @param tevglTchClassroom 前端用户输入选择的一些课堂基本信息
	 * @param loginUserId       当前登录的前端用户
	 * @return
	 */
	private List<TevglTchClassroom> listClassroomList(TevglTchClassroom tevglTchClassroom, String loginUserId) {
		List<TevglTchClassroom> classroomList = new ArrayList<>();
		// 处理单个或多个课堂的情况，班级id，多个逗号隔开
		String classId = tevglTchClassroom.getClassId();
		String[] split = classId.split(",");
		for (int i = 0; i < split.length; i++) {
			String uuid = Identities.uuid();
			// 调用方法填充信息
			TevglTchClassroom t = createClassroomInfo(tevglTchClassroom, loginUserId, uuid);
			// 为了满足不超过32位
			// t：为type调整类型的简称，01表示跳课堂
			// c：为code邀请码的简称
			// e:为time时间的简称，示例：20200724162900
			String qrcode = qrcodeUtils
					.getQrcode("t=01&c=" + t.getInvitationCode() + "&e=" + DateUtils.getNow("yyyyMMddHHmmss"), "14");
			t.setQrCode(qrcode);
			log.debug("二维码生成地址：" + qrcode);
			t.setClassId(split[i]);
			classroomList.add(t);
		}
		return classroomList;
	}

	/**
	 * 验证当前用户是否有开设课堂的权限，（教师表中有记录才认为有权限）
	 * 
	 * @param loginUserId
	 * @return
	 */
	private boolean hasOfferCoursesPermission(String loginUserId) {
		TevglTchTeacher tevglTchTeacher = tevglTchTeacherMapper.selectObjectByTraineeId(loginUserId);
		if (tevglTchTeacher == null) {
			log.debug("此用户[" + loginUserId + "]未能匹配到教师账号");
			return false;
		}
		// 无效的教师也不允许开设
		if (!"Y".equals(tevglTchTeacher.getState())) {
			log.debug("此用户[" + loginUserId + "]教师账号状态已无效，不允许开设课堂");
			return false;
		}
		return true;
	}

	/**
	 * 创建一个群聊
	 * 
	 * @param ctId        课堂主键，直接作为群聊主键
	 * @param name        课堂名称，直接作为群聊名称
	 * @param pic         课堂封面，这里直接以课堂封面的显示路径作为群聊图片值
	 * @param loginUserId
	 * @param className   班级名称
	 * @return
	 */
	private String createTimGroup(String ctId, String name, String pic, String loginUserId, String className) {
		String groupId = null;
		log.debug("群组ID:" + groupId);
		return groupId;
	}

	/**
	 * 课堂创建者自己成为群主
	 * 
	 * @param groupId     群聊主键
	 * @param loginUserId 当前登录用户
	 */
	private void createTimGroupUserAdmin(String groupId, String loginUserId) {


	}

	/**
	 * 修改课堂信息
	 * 
	 * @param tevglTchClassroom
	 * @param loginUserId
	 * @return
	 */
	@Override
	@PostMapping("/updateClassroomInfo")
	@Transactional
	public R updateClassroomInfo(@RequestBody TevglTchClassroom tevglTchClassroom, String loginUserId)
			throws OssbarException {
		if (StrUtils.isEmpty(tevglTchClassroom.getClassId())) {
			return R.error("请选择班级");
		}
		TevglTchClassroom classroomInfo = tevglTchClassroomMapper.selectObjectById(tevglTchClassroom.getCtId());
		if (classroomInfo == null) {
			return R.error("无效的记录");
		}
		if (!"Y".equals(classroomInfo.getState())) {
			return R.error("修改失败，状态已无效");
		}
		// 如果既不是创建者，又不是课堂接管者，又不是管理员
		boolean isRoomCreator = loginUserId.equals(classroomInfo.getCreateUserId());
		boolean isRoomReveiver = loginUserId.equals(classroomInfo.getReceiverUserId());
		boolean isAdministrator = ConstantProd.ADMINISTRATOR.equals(loginUserId);
		if (!isRoomCreator && !isRoomReveiver && !isAdministrator) {
			return R.error("非法操作，不能操作他人的数据");
		}
		if ("2".equals(classroomInfo.getClassroomState())) {
			// return R.error("课堂正在进行中，无法修改");
		}
		if ("3".equals(classroomInfo.getClassroomState())) {
			return R.error("课堂已经结束，无法修改");
		}
		// 如果上传了附件
		if (StrUtils.isNotEmpty(tevglTchClassroom.getAttachId())) {
			tsysAttachService.updateAttach(tevglTchClassroom.getAttachId(), tevglTchClassroom.getCtId(), "0", "14");
		}
		tevglTchClassroom.setMajorId(classroomInfo.getMajorId());
		tevglTchClassroom.setSubjectId(classroomInfo.getSubjectId());
		tevglTchClassroom.setPkgId(classroomInfo.getPkgId());
		tevglTchClassroom.setClassId(classroomInfo.getClassId());
		tevglTchClassroom.setTraineeId(classroomInfo.getTraineeId());
		boolean flag = (StrUtils.isEmpty(tevglTchClassroom.getIfLiveLesson())
				|| "undefined".equals(tevglTchClassroom.getIfLiveLesson()));
		tevglTchClassroom.setIfLiveLesson(flag ? "N" : tevglTchClassroom.getIfLiveLesson());
		boolean flag2 = (StrUtils.isEmpty(tevglTchClassroom.getLinkUrl())
				|| "undefined".equals(tevglTchClassroom.getLinkUrl()));
		tevglTchClassroom.setLinkUrl(flag2 ? "" : tevglTchClassroom.getLinkUrl());
		tevglTchClassroom.setPic(
				StrUtils.isEmpty(tevglTchClassroom.getPic()) ? classroomInfo.getPic() : tevglTchClassroom.getPic());
		tevglTchClassroomMapper.update(tevglTchClassroom);
		// 随之修改课堂群部分信息
		updateClassroomGroupInfo(classroomInfo.getCtId(), tevglTchClassroom.getName(), tevglTchClassroom.getPic(),
				classroomInfo.getClassId());
		return R.ok("保存成功");
	}

	/**
	 * 更新课堂群部分数据
	 * 
	 * @param ctId 课堂主键（也是课堂群主键）
	 * @param name 课堂名称
	 * @param pic  课堂封面
	 */
	private void updateClassroomGroupInfo(String ctId, String name, String pic, String classId) {
		TevglTchClass tevglTchClass = tevglTchClassMapper.selectObjectById(classId);

	}

	/**
	 * 查看课堂信息
	 * 
	 * @param id 课堂主键
	 * @return
	 */
	@Override
	@GetMapping("/viewClassroomInfo/{id}")
	public R viewClassroomInfo(@PathVariable("id") String id, String loginUserId) {
		if (StrUtils.isEmpty(loginUserId) || StrUtils.isEmpty(id)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(id);
		if (classroom == null) {
			return R.error("无效的记录");
		}
		if (!"Y".equals(classroom.getState())) {
			return R.error("无效的课堂");
		}
		String pkgId = classroom.getPkgId();
		String classId = classroom.getClassId();
		TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
		if (tevglPkgInfo == null) {
			return R.error("课堂部分数据获取失败");
		}
		// 查询条件
		Map<String, Object> params = new HashMap<>();
		// 最终返回数据
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> classroomInfo = new HashMap<>();
		Map<String, Object> classInfo = new HashMap<>();
		Map<String, Object> teacherInfo = new HashMap<>();
		Map<String, Object> pkgInfo = new HashMap<>();
		Map<String, Object> numData = new HashMap<>();
		boolean hasPermission = false;
		// 如果课堂没有被移交
		if (StrUtils.isEmpty(classroom.getReceiverUserId())) {
			// 当前登录用户是否课堂创建者
			if (loginUserId.equals(classroom.getCreateUserId())) {
				hasPermission = true;
			}
		}
		// 如果课堂被移交了
		if (StrUtils.isNotEmpty(classroom.getReceiverUserId())) {
			if (loginUserId.equals(classroom.getCreateUserId())) {
				hasPermission = false;
			}
			if (loginUserId.equals(classroom.getReceiverUserId())) {
				hasPermission = true;
			}
		}
		// 拿到课堂对应的教材，从而拿到教学包，就能得到适用层次了
		convertUtil.convertOrgId(tevglPkgInfo, "orgId"); // 所属机构、院校
		convertUtil.convertDict(tevglPkgInfo, "pkgLevel", "pkgLevel"); // 适用层次(来源字典：本科，高职，中职)
		convertUtil.convertDict(tevglPkgInfo, "pkgLimit", "pkgLimit"); // 使用限制(来源字典：授权，购买，免费)
		convertUtil.convertDict(tevglPkgInfo, "deployMainType", "deployMainType"); // 发布方大类(来源字典：学校，个人，创蓝)
		convertUtil.convertDict(tevglPkgInfo, "state", "state0"); // 状态(Y已发布N未发布)
		classroomInfo.put("pkgLevel", tevglPkgInfo.getPkgLevel());
		classroomInfo.put("pkgLimit", tevglPkgInfo.getPkgLimit());
		classroomInfo.put("deployMainType", tevglPkgInfo.getDeployMainType());
		// 教学包信息
		pkgInfo.put("pkgId", tevglPkgInfo.getPkgId());
		pkgInfo.put("pkgName", tevglPkgInfo.getPkgName());
		pkgInfo.put("subjectId", tevglPkgInfo.getSubjectId());
		pkgInfo.put("refPkgId", tevglPkgInfo.getRefPkgId());
		pkgInfo.put("pkgVersion", tevglPkgInfo.getPkgVersion());
		pkgInfo.put("pkgActCount", tevglPkgInfo.getPkgActCount()); // 活动数量
		pkgInfo.put("pkgResCount", tevglPkgInfo.getPkgResCount()); // 资源数量
		pkgInfo.put("pkgLevel", tevglPkgInfo.getPkgLevel());
		pkgInfo.put("pkgLimit", tevglPkgInfo.getPkgLimit());
		pkgInfo.put("deployMainType", tevglPkgInfo.getDeployMainType());
		pkgInfo.put("createUserId", tevglPkgInfo.getCreateUserId());
		pkgInfo.put("pkgTraineeName", tevglPkgInfo.getPkgTraineeName());
		pkgInfo.put("pkgTraineeMobile", tevglPkgInfo.getPkgTraineeMobile());
		pkgInfo.put("pkgTraineeEmail", tevglPkgInfo.getPkgTraineeEmail());
		pkgInfo.put("pkgTraineeQq", tevglPkgInfo.getPkgTraineeQq());
		// 谁发布的
		pkgInfo.put("createUserName", "");
		if (StrUtils.isEmpty(tevglPkgInfo.getDeployMainType())) {
			TevglTraineeInfo createUserInfo = tevglTraineeInfoMapper.selectObjectById(tevglPkgInfo.getCreateUserId());
			if (createUserInfo != null) {
				pkgInfo.put("deployMainType",
						StrUtils.isEmpty(createUserInfo.getTraineeName()) ? createUserInfo.getNickName()
								: createUserInfo.getTraineeName());
			}
		}
		// 实时统计课堂人数
		Integer countTraineeNum = countTraineeNum(id, loginUserId);
		if (!classroom.getStudyNum().equals(countTraineeNum)) {
			fixBugStudyNum(classroom, countTraineeNum);
		}
		// 处理右上角文字显示
		handleTextShow(pkgInfo, tevglPkgInfo.getCreateUserId(), loginUserId, classroom);
		// 课堂信息
		classroomInfo = convertToSimpleClassroomData(classroom);
		// 班级信息
		TevglTchClass tevglTchClass = tevglTchClassMapper.selectObjectById(classId);
		if (tevglTchClass != null) {
			classInfo.put("classId", tevglTchClass.getClassId());
			classInfo.put("className", tevglTchClass.getClassName());
		}
		// 教师信息
		teacherInfo = convertToSimpleTeacherData(StrUtils.isEmpty(classroom.getReceiverUserId()) ? classroom.getTeacherId() : classroom.getReceiverUserId());
		// 助教信息
		handleTeacherAssistantInfo(teacherInfo, classroom);
		// 添加并返回数据
		result.put("classroomInfo", classroomInfo);
		result.put("classInfo", classInfo);
		result.put("teacherInfo", teacherInfo);
		result.put("pkgInfo", pkgInfo);
		// 当前登录用户是否课堂创建者
		result.put("hasPermission", hasPermission);
		// 是否有操作活动的权限
		result.put("hasPermissionActual", "Y".equals(tevglPkgInfo.getReleaseStatus()) ? false : true);
		// 是否有操作左侧章节，资源等权限
		// result.put("hasPermissionTree", hasPermissionTree);
		// 课堂对应教学包的 所使用的来源教学包
		result.put("refPkgVersion", "");
		if (!tevglPkgInfo.getPkgId().equals(tevglPkgInfo.getRefPkgId())) {
			TevglPkgInfo refPkgInfo = tevglPkgInfoMapper.selectObjectById(tevglPkgInfo.getRefPkgId());
			if (refPkgInfo != null) {
				result.put("refPkgVersion", refPkgInfo.getPkgVersion());
			}
		}
		// 默认返回的各种数量，如活动数，课堂成员数，云盘数
		Integer activityNum = countActivityNum(classroom, tevglPkgInfo, loginUserId, params);
		pkgInfo.put("pkgActCount", activityNum);
		numData.put("activityNum", activityNum);
		numData.put("traineeNum", countTraineeNum);
		numData.put("cloudPanNum", 0); // countCloudPanNum(tevglPkgInfo, loginUserId, params)
		result.put("numData", numData);
		return R.ok().put(Constant.R_DATA, result);
	}

	/**
	 * 兼容性的修复下，实际的人数与课堂表中的study_num经常不一致的问题
	 * @param tevglTchClassroom
	 * @param studyNum
	 */
	private void fixBugStudyNum(TevglTchClassroom tevglTchClassroom, Integer studyNum) {
		if (tevglTchClassroom == null || studyNum == null || studyNum < 0) {
            return;
        }
        // 如果两这者不一致
        if (!tevglTchClassroom.getStudyNum().equals(studyNum)) {
            // 数据库更新为最新的
            TevglTchClassroom t = new TevglTchClassroom();
            t.setCtId(tevglTchClassroom.getCtId());
            t.setStudyNum(studyNum);
            tevglTchClassroomMapper.update(t);
            // 重置为最新的
            tevglTchClassroom.setStudyNum(studyNum);
        }
	}
	
	/**
	 * 统计课堂人数（包含待审核的人）
	 * 
	 * @param ctId
	 * @param loginUserId
	 * @return
	 */
	private Integer countTraineeNum(String ctId, String loginUserId) {
		return tevglTchClassroomTraineeMapper.countTotalTraineeNumberByCtId(ctId);
	}

	/**
	 * 统计云盘目录与文件个数
	 * 
	 * @param tevglPkgInfo
	 * @param loginUserId
	 * @param params
	 * @return
	 */
	private Integer countCloudPanNum(TevglPkgInfo tevglPkgInfo, String loginUserId, Map<String, Object> params) {
		if (tevglPkgInfo == null) {
			return 0;
		}
		params.clear();
		List<String> pkgIds = new ArrayList<>();
		pkgIds.add(tevglPkgInfo.getPkgId());
		if (StrUtils.isNotEmpty(tevglPkgInfo.getRefPkgId())
				&& !tevglPkgInfo.getPkgId().equals(tevglPkgInfo.getRefPkgId())) {
			pkgIds.add(tevglPkgInfo.getRefPkgId());
		}
		Integer num = 0;
		params.put("pkgIds", pkgIds);
		List<Map<String, Object>> list = tcloudPanDirectoryMapper.countNumByMap(params);
		if (list != null && list.size() > 0) {
			num = list.size();
			List<Object> dirIds = list.stream().map(a -> a.get("dir_id")).collect(Collectors.toList());
			params.clear();
			params.put("dirIds", dirIds);
			List<Map<String, Object>> fileList = tcloudPanFileMapper.countNumByMap(params);
			if (fileList != null && fileList.size() > 0) {
				num += fileList.size();
			}
		}
		return num;
	}

	/**
	 * 
	 * @param tevglPkgInfo
	 * @param loginUserId
	 * @return
	 */
	private Integer countActivityNum(TevglTchClassroom tevglTchClassroom, TevglPkgInfo tevglPkgInfo, String loginUserId,
			Map<String, Object> params) {
		if (tevglPkgInfo == null) {
			return 0;
		}
		if (StrUtils.isEmpty(tevglPkgInfo.getRefPkgId())) {
			return tevglPkgInfo.getPkgActCount();
		}
		params.clear();
		params.put("pkgId", tevglPkgInfo.getPkgId());
		//params.put("pkgIds", Arrays.asList(tevglPkgInfo.getPkgId(), tevglPkgInfo.getRefPkgId()));
		boolean isCreator = StrUtils.isEmpty(tevglTchClassroom.getReceiverUserId()) && tevglTchClassroom.getCreateUserId().equals(loginUserId);
		boolean isTeachingAssistant = StrUtils.isNotEmpty(tevglTchClassroom.getTraineeId()) && loginUserId.equals(tevglTchClassroom.getTraineeId());
		boolean isReceiver = StrUtils.isNotEmpty(tevglTchClassroom.getReceiverUserId()) && tevglTchClassroom.getReceiverUserId().equals(loginUserId);
		// 学员只能看到进行中、已结束的活动
		if (!isCreator && !isTeachingAssistant && !isReceiver) {
			params.put("activityStateList", Arrays.asList("1", "2"));
		}
		List<TevglPkgActivityRelation> list = tevglPkgActivityRelationMapper.selectListByMap(params);
		return list.size();
	}

	/**
	 * 助教信息
	 * 
	 * @param teacherInfo
	 * @param tevglTchClassroom
	 */
	private void handleTeacherAssistantInfo(Map<String, Object> teacherInfo, TevglTchClassroom tevglTchClassroom) {
		TevglTraineeInfo traineeInfo = null;
		// 如果课堂没有设置过助教，则去采用班级的助教
		if (StrUtils.isEmpty(tevglTchClassroom.getTraineeId())) {
			TevglTchClass tevglTchClass = tevglTchClassMapper.selectObjectById(tevglTchClassroom.getClassId());
			if (tevglTchClass != null && StrUtils.isNotEmpty(tevglTchClass.getTeachingAssistant())) {
				traineeInfo = tevglTraineeInfoMapper.selectObjectById(tevglTchClass.getTeachingAssistant());
			}
		} else {
			traineeInfo = tevglTraineeInfoMapper.selectObjectById(tevglTchClassroom.getTraineeId());
		}
		// 助教信息
		teacherInfo.put("teachingAssistantName", "");
		teacherInfo.put("teachingAssistantPic", "/uploads/defaulthead.png");
		teacherInfo.put("teachingAssistantId", "");
		if (traineeInfo != null) {
			String teachingAssistantName = StrUtils.isEmpty(traineeInfo.getTraineeName()) ? traineeInfo.getNickName()
					: traineeInfo.getTraineeName();
			String teachingAssistantPic = StrUtils.isEmpty(traineeInfo.getTraineePic()) ? traineeInfo.getTraineeHead()
					: traineeInfo.getTraineePic();
			if (StrUtils.isNotEmpty(teachingAssistantPic)) {
				teachingAssistantPic = uploadPathUtils.stitchingPath(teachingAssistantPic, "16");
			}
			teacherInfo.put("teachingAssistantId", traineeInfo.getTraineeId()); // 主键，其实也就是课堂成员
			teacherInfo.put("teachingAssistantName", teachingAssistantName); // 助教名称
			teacherInfo.put("teachingAssistantPic", teachingAssistantPic); // 助教头像
		}
	}

	/**
	 * 取部分属性
	 * 
	 * @param
	 * @return
	 */
	private Map<String, Object> convertToSimpleTeacherData(String teacherId) {
		Map<String, Object> info = new HashMap<>();
		info.put("teacherId", "");
		info.put("teacherName", "");
		info.put("teacherPic", "");
		TevglTchTeacher teacher = tevglTchTeacherMapper.selectObjectById(teacherId);
		if (teacher != null) {
			convertUtil.convertOrgId(teacher, "orgId");
			// 当教师没有头像时给个默认头像
			String teacherPic = "/uploads/defaulthead.png";
			if (StrUtils.isNotEmpty(teacher.getTeacherPic())) {
				teacherPic = uploadPathUtils.stitchingPath(teacher.getTeacherPic(), "7");
			}
			info.put("teacherId", teacher.getTeacherId());
			info.put("teacherName", teacher.getTeacherName());
			info.put("teacherPic", teacherPic);
		} else { // 如果没有教师账号
			TevglTraineeInfo traineeInfo = tevglTraineeInfoMapper.selectObjectById(teacherId);
			if (traineeInfo != null) {
				info.put("teacherPic", traineeInfo.getTraineeHead());
				info.put("teacherName", StrUtils.isEmpty(traineeInfo.getTraineeName()) ? traineeInfo.getNickName()
						: traineeInfo.getTraineeName());
			}
		}
		return info;
	}

	/**
	 * 处理文字
	 * 
	 * @param info         教学包
	 * @param createUserId 教学包的创建者
	 * @param loginUserId  当前登录用户
	 * @apiNote <br>
	 *          如果未登录，则右上角的文字，只需要字典转换 <br>
	 *          如果登录了，且登录用户为此教学包创建者，则显示【拥有者】 <br>
	 *          如果登录了，但不是此教学包的创建者，则去判断教学包创建者是否授权了当前登录用户，如果有权限，则显示【已授权】，否则显示字典转换后的就行了
	 */
	private void handleTextShow(Map<String, Object> info, String createUserId, String loginUserId,
			TevglTchClassroom tevglTchClassroom) {
		if (StrUtils.isEmpty(loginUserId)) {
			// 前面已经字典转换了无需处理
		} else {
			// 如果课堂没有被移交
			if (StrUtils.isEmpty(tevglTchClassroom.getReceiverUserId())) {
				if (loginUserId.equals(createUserId)) {
					info.put("pkgLimit", "拥有者");
				} else {
					String pkgId = (String) info.get("pkgId");
					boolean flag = pkgPermissionUtils.hasPermission(pkgId, loginUserId, createUserId);
					if (flag) {
						info.put("pkgLimit", "已授权");
					} else {
						info.put("pkgLimit", "学习中");
					}
				}
			}
			// 如果课堂被移交了
			if (StrUtils.isNotEmpty(tevglTchClassroom.getReceiverUserId())) {
				if (loginUserId.equals(tevglTchClassroom.getReceiverUserId())) {
					info.put("pkgLimit", "接管者");
				} else {
					info.put("pkgLimit", "学习中");
				}
			}
		}
	}

	@Deprecated
	private void handleTextShow(Map<String, Object> info, String createUserId, String loginUserId) {
		if (StrUtils.isEmpty(loginUserId)) {
			// 前面已经字典转换了无需处理
		} else {
			log.debug("createUserId:" + createUserId);
			log.debug("loginUserId:" + loginUserId);
			log.debug("是否为创建者" + loginUserId.equals(createUserId));
			if (loginUserId.equals(createUserId)) {
				info.put("pkgLimit", "拥有者");
			} else {
				String pkgId = (String) info.get("pkgId");
				boolean flag = pkgPermissionUtils.hasPermission(pkgId, loginUserId, createUserId);
				if (flag) {
					info.put("pkgLimit", "已授权");
				} else {
					info.put("pkgLimit", "学习中");
				}
			}
		}
	}

	/**
	 * 查看课堂基本信息
	 * 
	 * @param ctId
	 * @return
	 */
	@Override
	@GetMapping("/viewClassroomBaseInfo")
	public R viewClassroomBaseInfo(String ctId, String loginUserId) {
		Map<String, Object> info = tevglTchClassroomMapper.selectObjectMapById(ctId);
		if (info == null) {
			if (!ConstantProd.ADMINISTRATOR.equals(loginUserId)) {
				// 删除用户的此课堂收藏数据
				deleteFavorityRoom(ctId, loginUserId);
			}
			return R.error("无效的记录，无法访问");
		}
		if ("N".equals(info.get("state"))) {
			if (!ConstantProd.ADMINISTRATOR.equals(loginUserId)) {
				// 删除用户的此课堂收藏数据
				deleteFavorityRoom(ctId, loginUserId);
			}
			return R.error("课堂已被删除，无法访问");
		}
		info.put("qrCode", uploadPathUtils.stitchingPath(info.get("qrCode"), "14"));
		info.put("pic", uploadPathUtils.stitchingPath(info.get("pic"), "14"));
		return R.ok().put(Constant.R_DATA, info);
	}

	/**
	 * 删除用户的此课堂收藏数据
	 * 
	 * @param ctId
	 * @param loginUserId
	 */
	private void deleteFavorityRoom(String ctId, String loginUserId) {
		Map<String, Object> params = new HashMap<>();
		params.put("traineeId", loginUserId);
		params.put("favorityType", GlobalFavority.FAVORITY_11_CLASSROOM);
		params.put("targetId", ctId);
		List<TmeduMeFavority> list = tmeduMeFavorityMapper.selectListByMap(params);
		for (TmeduMeFavority tmeduMeFavority : list) {
			tmeduMeFavorityMapper.delete(tmeduMeFavority.getFavorityId());
		}
	}

	/**
	 * 删除课堂
	 * 
	 * @param ctId        课堂主键
	 * @param loginUserId 当前登录用户
	 * @return
	 */
	@Override
	@GetMapping("deleteClassroom")
	public R deleteClassroom(String ctId, String loginUserId) throws OssbarException {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroomInfo = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroomInfo == null) {
			return R.error("无效的课堂");
		}
		boolean hasAuth = roomUtils.hasOperatingAuthorization(classroomInfo, loginUserId);
		if (!hasAuth) {
			return R.error("非法操作，没有权限");
		}
		if (!"Y".equals(classroomInfo.getState())) {
			return R.error("课堂已删除");
		}
		if ("2".equals(classroomInfo.getClassroomState())) {
			return R.error("课堂正在进行中，不允许删除");
		}
		if ("3".equals(classroomInfo.getClassroomState())) {
			return R.error("课堂已结束，不允许删除");
		}
		TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(classroomInfo.getPkgId());
		if (tevglPkgInfo == null) {
			return R.error("删除失败，无效的记录");
		}
		// 查询条件
		Map<String, Object> params = new HashMap<>();
		// 直接物理删除未开始的课堂相关数据
		// 删除云盘数据
		pkgUtils.doDeleteCloudPanDatas(classroomInfo.getPkgId(), loginUserId, params);
		// 删除活动基本数据
		// pkgActivityUtils.deleteActivity(classroomInfo.getPkgId(), loginUserId,
		// params);
		roomUtils.deleteClassroomActivityDatas(classroomInfo.getPkgId(), tevglPkgInfo.getRefPkgId(), params);
		// 删除课堂小组与小组成员数据
		roomUtils.deleteClassroomGroupDatas(ctId, params);
		roomUtils.deleteClassroomTraineeDatas(ctId, params);
		// 删除课堂数据
		tevglPkgInfoMapper.delete(classroomInfo.getPkgId());
		tevglTchClassroomMapper.delete(ctId);
		// 当前用户创建课堂数-1
		TevglTraineeInfo traineeInfo = new TevglTraineeInfo();
		traineeInfo.setTraineeId(loginUserId);
		traineeInfo.setClassroomNum(-1);
		tevglTraineeInfoMapper.plusNum(traineeInfo);
		// 此课堂对应的教学包状态设为无效
		TevglPkgInfo p = new TevglPkgInfo();
		p.setPkgId(classroomInfo.getPkgId());
		p.setState("N");
		tevglPkgInfoMapper.update(p);
		// 处理即时通讯相关数据
		handleImDatas(ctId, classroomInfo);
		return R.ok("删除成功");
	}

	/**
	 * 处理即时通讯相关数据
	 * 
	 * @param ctId          课堂主键
	 * @param classroomInfo 根据课堂主键查出的对象信息
	 */
	private void handleImDatas(String ctId, TevglTchClassroom classroomInfo) {

		// ================================================== 即时通讯相关处理 end
		// ==================================================
	}

	/**
	 * 更新消息为已读
	 * 
	 * @param groupId
	 * @param params
	 */
	private void updateToReaded(String groupId, Map<String, Object> params) {
		params.clear();
		params.put("groupId", groupId);

	}

	/**
	 * 获取该课堂的活动
	 * 
	 * @param tchClassroom
	 * @param params
	 * @return
	 */
	private Map<String, String> getActivityMap(TevglTchClassroom tchClassroom, Map<String, Object> params) {
		if (tchClassroom == null) {
			return null;
		}
		params.clear();
		params.put("pkgId", tchClassroom.getPkgId());
		params.put("activityType", GlobalActivity.ACTIVITY_3_ANSWER_DISCUSS);
		List<TevglPkgActivityRelation> list = tevglPkgActivityRelationMapper.selectListByMap(params);
		if (list != null && list.size() > 0) {
			return list.stream().collect(Collectors.toMap(TevglPkgActivityRelation::getActivityId,
					TevglPkgActivityRelation::getActivityType));
		}
		return null;
	}

	/**
	 * 将学员设为课堂助教
	 * 
	 * @param map
	 * @return
	 * @apiNote 必传参数{'ctId':'', 'traineeId':'', 'loginUserId':'loginUserId'}
	 */
	@Override
	@GetMapping("/setTraineeToTeachingAssistant")
	public R setTraineeToTeachingAssistant(@RequestParam Map<String, Object> map) {
		String traineeId = (String) map.get("traineeId");
		String ctId = (String) map.get("ctId"); // 课堂(教室)主键
		String loginUserId = (String) map.get("loginUserId");
		// 合法性校验
		R r = checkIsPass(traineeId, ctId, loginUserId);
		Integer code = (Integer) r.get("code");
		if (code != 0) {
			return r;
		}
		TevglTchClassroom classroom = (TevglTchClassroom) r.get("classroom");
		if (classroom != null) {
			classroom.setTraineeId(traineeId);
			tevglTchClassroomMapper.update(classroom);
			// 如果此人有待审核记录，直接更新为通过
			Map<String, Object> params = new HashMap<>();
			params.put("ctId", ctId);
			params.put("traineeId", traineeId);
			params.put("isPass", "N");
			List<TevglTchClassroomTraineeCheck> list = tevglTchClassroomTraineeCheckMapper.selectListByMap(params);
			if (list != null && list.size() > 0) {
				TevglTchClassroomTraineeCheck traineeCheck = list.get(0);
				traineeCheck.setIsPass("Y");
				tevglTchClassroomTraineeCheckMapper.update(traineeCheck);
			}
			params.clear();
			params.put("ctId", ctId);
			params.put("traineeId", traineeId);
			params.put("state", "N");
			List<TevglTchClassroomTrainee> traineeList = tevglTchClassroomTraineeMapper.selectListByMap(params);
			if (traineeList != null && traineeList.size() > 0) {
				TevglTchClassroomTrainee classroomTrainee = traineeList.get(0);
				classroomTrainee.setState("Y");
				tevglTchClassroomTraineeMapper.update(classroomTrainee);
				// 更新课堂人数
				TevglTchClassroom room = new TevglTchClassroom();
				room.setCtId(ctId);
				room.setStudyNum(1);
				tevglTchClassroomMapper.plusNum(room);
			}
		}
		return R.ok("助教设置成功");
	}
	

    /**
     * 取消课堂的助教
     *
     * @param ctId
     * @param loginUserId
     * @return
     */
    @Override
    public R cancelTeachingAssistant(String ctId, String traineeId, String loginUserId) {
    	if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId) || StrUtils.isEmpty(traineeId)) {
            return R.error("必传参数为空");
        }
        TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
        if (tevglTchClassroom == null || !"Y".equals(tevglTchClassroom.getState())) {
            return R.error("课堂已不存在");
        }
        boolean hasAuth = roomUtils.hasOperatingAuthorization(tevglTchClassroom, loginUserId);
        if (!hasAuth) {
            return R.error("非法操作，没有权限！");
        }
        // 找到该课堂对应的班级
        TevglTchClass tevglTchClass = tevglTchClassMapper.selectObjectById(tevglTchClassroom.getClassId());
        if (tevglTchClass != null) {
            // 如果课堂没有设置助教的情况下，默认展示的是班级助教，这里控制下，不能取消
            if (StrUtils.isEmpty(tevglTchClassroom.getTraineeId()) && traineeId.equals(tevglTchClass.getTeachingAssistant())) {
                return R.error("默认的班级助教，你不能取消");
            }
        }
        TevglTchClassroom t = new TevglTchClassroom();
        t.setCtId(ctId);
        t.setTraineeId(null);
        tevglTchClassroomMapper.updateTraineeId(t);
        return R.ok("取消成功");
    }


	/**
	 * 取部分属性
	 * 
	 * @param tevglTchClassroom
	 * @return
	 */
	private Map<String, Object> convertToSimpleClassroomData(TevglTchClassroom tevglTchClassroom) {
		// 图片处理
		if (StrUtils.isNotEmpty(tevglTchClassroom.getPic())) {
			int i = tevglTchClassroom.getPic().indexOf(ossbarFieAccessPath);
			if (i == -1) {
				tevglTchClassroom.setPic(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("14") + "/"
						+ tevglTchClassroom.getPic());
			}
		}
		Map<String, Object> info = new HashMap<>();
		info.put("ctId", tevglTchClassroom.getCtId()); // 课堂主键
		info.put("classId", tevglTchClassroom.getClassId()); // 班级主键
		info.put("pkgId", tevglTchClassroom.getPkgId()); // 教学包主键
		info.put("name", tevglTchClassroom.getName()); // 课堂名称
		info.put("pic", tevglTchClassroom.getPic()); // 课堂封面
		info.put("intro", tevglTchClassroom.getIntro()); // 简介
		info.put("invitationCode", tevglTchClassroom.getInvitationCode()); // 邀请码
		info.put("studyNum", tevglTchClassroom.getStudyNum()); // 学习人数
		info.put("qrCode", uploadPathUtils.stitchingPath(tevglTchClassroom.getQrCode(), "14"));
		info.put("classroomState", tevglTchClassroom.getClassroomState());
		info.put("ifLiveLesson", tevglTchClassroom.getIfLiveLesson());
		info.put("linkUrl", tevglTchClassroom.getLinkUrl());
		return info;
	}

	/**
	 * 合法性校验（新增所用和修改共用，无法共用的额外处理）
	 * 
	 * @param tevglTchClassroom
	 * @param loginUserId
	 * @return
	 */
	private R checkIsPassForCommon(TevglTchClassroom tevglTchClassroom, String loginUserId) {
		if (StrUtils.isEmpty(loginUserId)) {
			return R.error("参数loginUserId为空");
		}
		// 判断登录用户是否为老师, 注：后台新增老师时，会以traineeId作为主键ID
		/*TevglTchTeacher teacher = tevglTchTeacherMapper.selectObjectByTraineeId(loginUserId);
		if (teacher == null) {
			return R.error("很抱歉，你不是教师，无权开设课堂");
		}*/
		if (StrUtils.isEmpty(tevglTchClassroom.getMajorId())) {
			return R.error("请选择职业路径");
		}
		if (StrUtils.isEmpty(tevglTchClassroom.getClassId())) {
			return R.error("请选择上课班级");
		}
		if (StrUtils.isEmpty(tevglTchClassroom.getPkgId())) {
			if (StrUtils.isEmpty(tevglTchClassroom.getSubjectId())) {
				return R.error("参数subjectId为空");
			}
		}
		if (StrUtils.isEmpty(tevglTchClassroom.getName())) {
			return R.error("请输入课堂名称");
		}
		if (tevglTchClassroom.getName().length() > 50) {
			return R.error("名称不能超过50个字符");
		}
		return R.ok();
	}

	/**
	 * 合法性校验（设为助教所用）
	 * 
	 * @param traineeId
	 * @param ctId
	 * @param loginUserId
	 * @return
	 */
	private R checkIsPass(String traineeId, String ctId, String loginUserId) {
		if (StrUtils.isEmpty(traineeId)) {
			return R.error("参数traineeId为空");
		}
		if (StrUtils.isEmpty(ctId)) {
			return R.error("参数ctId为空");
		}
		if (StrUtils.isEmpty(loginUserId)) {
			return R.error("参数loginUserId为空");
		}
		TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroom == null) {
			return R.error("课堂已不存在");
		}
		if (!"Y".equals(classroom.getState())) {
			return R.error("课堂已无效");
		}
		/*if (!loginUserId.equals(classroom.getTeacherId())) {
			return R.error("非法操作，不能操作他人的数据！");
		}*/
		boolean hasAuth = roomUtils.hasOperatingAuthorization(classroom, loginUserId);
		if (!hasAuth) {
			return R.error("非法操作，没有权限！");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", classroom.getCtId());
		List<TevglTchClassroomTrainee> classroomTraineeList = tevglTchClassroomTraineeMapper.selectListByMap(params);
		List<String> traineeIds = classroomTraineeList.stream().map(classroomTrainee -> classroomTrainee.getTraineeId())
				.collect(Collectors.toList());
		if (!traineeIds.contains(traineeId)) {
			return R.error("该学员不属于此课堂，无法设为助教");
		}
		return R.ok().put("classroom", classroom);
	}

	/**
	 * 创建课堂信息
	 * 
	 * @param tevglTchClassroom
	 * @param loginUserId       当前登录用户id
	 * @param uuid              32位的uuid
	 * @return
	 */
	private TevglTchClassroom createClassroomInfo(TevglTchClassroom tevglTchClassroom, String loginUserId,
			String uuid) {
		// 实例化并填充数据
		TevglTchClassroom t = new TevglTchClassroom();
		t.setClassroomState("1"); // 课堂状态(1未开始2进行中3已结束)
		t.setCtId(uuid);
		t.setName(tevglTchClassroom.getName());
		t.setPic(tevglTchClassroom.getPic());
		t.setMajorId(tevglTchClassroom.getMajorId());
		t.setIntro(tevglTchClassroom.getIntro());
		t.setTeacherId(loginUserId);
		t.setCreateUserId(loginUserId);
		t.setCreateTime(DateUtils.getNowTimeStamp());
		t.setState("Y"); // 状态(Y有效N无效)
		t.setIsTop("N"); // 默认不置顶
		// 是否公开课
		t.setIsPublic(StrUtils.isEmpty(tevglTchClassroom.getIsPublic()) ? "N" : tevglTchClassroom.getIsPublic());
		// 加入班级是否审核（Y是N否）
		t.setIsCheck(StrUtils.isEmpty(tevglTchClassroom.getIsCheck()) ? "N" : tevglTchClassroom.getIsCheck());
		t.setStudyNum(0); // 学习人数默认0人
		t.setCollectNum(0); // 收藏数
		t.setLikeNum(0); // 点赞数
		// 邀请码
		int num = (int) ((Math.random() * 9 + 1) * 100000);
		t.setInvitationCode(String.valueOf(num));
		// 开发阶段直接默认平台审核通过
		t.setPlatformAuditStatus("Y"); // Y通过N不通过
		t.setIfLiveLesson(tevglTchClassroom.getIfLiveLesson());
		t.setLinkUrl(tevglTchClassroom.getLinkUrl());
		return t;
	}

	/**
	 * 生成教学包编号
	 * 
	 * @return
	 */
	private String createPkgNo() {
		return Identities.uuid();
	}

	/**
	 * 根据邀请码搜索课堂
	 * 
	 * @param invitationCode
	 * @return
	 */
	@Override
	public R selectClassroomListData(String invitationCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("invitationCode", invitationCode);
		map.put("state", "Y");
		map.put("sidx", "create_time");
		map.put("order", "desc");
		List<Map<String, Object>> list = tevglTchClassroomMapper.selectListMapByMap(map);
		return R.ok().put(Constant.R_DATA, list);
	}

	/**
	 * 判断课堂是否属于当前登录用户
	 * 
	 * @param classroomInfo 课堂信息
	 * @param loginUserId   当前登录用户id
	 */
	private void isThisClassroomBelongToLoginUser(Map<String, Object> classroomInfo, String loginUserId) {
		if (StrUtils.isEmpty(loginUserId)) {
			// 如果没登陆,则不是创建者
			classroomInfo.put(IS_OWNER, false);
		} else {
			// 如果课堂没有被移交
			if (StrUtils.isNull(classroomInfo.get("receiverUserId"))) {
				if (loginUserId.equals(classroomInfo.get("createUserId"))) {
					classroomInfo.put(IS_OWNER, true);
				} else {
					classroomInfo.put(IS_OWNER, false);
				}
			} else {
				// 否则课堂已经被移交了
				if (loginUserId.equals(classroomInfo.get("receiverUserId"))) {
					classroomInfo.put(IS_OWNER, true);
				} else {
					classroomInfo.put(IS_OWNER, false);
				}
			}
		}
	}

	/**
	 * 判断非课堂创建者时，当前登录用户是否申请了加入此课堂，是否被审核通过
	 * 
	 * @param classroomInfo
	 * @param loginUserId   param classroomTraineeList
	 */
	private void isApplyAndIsPass(Map<String, Object> classroomInfo, String loginUserId,
			List<Map<String, Object>> classroomTraineeList) {
		if (StrUtils.isEmpty(loginUserId)) {
			classroomInfo.put("isPass", false);
			classroomInfo.put("isApply", false);
			return;
		}
		if (!loginUserId.equals(classroomInfo.get("createUserId"))) {
			// 如果加入课堂需要审核
			// 返回状态标识是否已审核，课堂成员表中记录为有效则认为通过了审核
			// 前端[待审核]的盖章，根据ischeck=Y且isApply=true且isPass=false才显示
			classroomInfo.put("isApply", false); // 标识是否申请了加入课堂，t_evgl_tch_classroom_trainee表有记录则认为申请了
			classroomInfo.put("isPass", false);
			if (classroomTraineeList == null || classroomTraineeList.size() == 0) {
				classroomInfo.put("isPass", false);
				classroomInfo.put("isApply", false);
			} else {
				boolean isPass = classroomTraineeList.stream()
						.anyMatch(a -> a.get("ctId").equals(classroomInfo.get("ctId")) && a.get("state").equals("Y"));
				boolean isApply = classroomTraineeList.stream()
						.anyMatch(a -> a.get("ctId").equals(classroomInfo.get("ctId")));
				classroomInfo.put("isPass", isPass);
				classroomInfo.put("isApply", isApply);
			}
		}
	}

	/**
	 * 判断是否收藏了此课堂
	 * 
	 * @param classroomInfo
	 * @param loginUserId   可选参数
	 */
	private void isCollectedThisClassroom(Map<String, Object> classroomInfo, String loginUserId,
			List<TmeduMeFavority> list) {
		// 没登陆直接认为未收藏
		if (StrUtils.isEmpty(loginUserId)) {
			classroomInfo.put(IS_COLLECTED, false);
		} else {
			// 如果匹配上了,则认为收藏了该课堂
			boolean flag = list.stream().anyMatch(a -> a.getTargetId().equals(classroomInfo.get("ctId")));
			if (flag) {
				classroomInfo.put(IS_COLLECTED, true);
			} else {
				classroomInfo.put(IS_COLLECTED, false);
			}
		}
	}

	/**
	 * 拼接课堂封面和课堂二维码的路径
	 * 
	 * @param classroomInfo
	 */
	private void handlePicAndQrcode(Map<String, Object> classroomInfo) {
		// 课堂封面图
		if (classroomInfo.get("pic") != null) {
			classroomInfo.put("pic",
					ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("14") + "/" + classroomInfo.get("pic"));
		}
		// 课堂二维码
		if (classroomInfo.get("qrCode") != null) {
			classroomInfo.put("qrCode", ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("14") + "/"
					+ classroomInfo.get("qrCode"));
		}
	}

	/**
	 * 处理教师姓名、头像
	 * 
	 * @param classroomInfo
	 */
	private void handleTeacherInfo(Map<String, Object> classroomInfo) {
		// 教师名称
		if (classroomInfo.get("teacherName") == null) {
			TevglTraineeInfo traineeInfo = tevglTraineeInfoMapper.selectObjectById(classroomInfo.get("teacherId"));
			if (traineeInfo != null) {
				classroomInfo.put("teacherName",
						StrUtils.isEmpty(traineeInfo.getTraineeName()) ? traineeInfo.getNickName()
								: traineeInfo.getTraineeName());
			}
		}
		// 教师图片，没有教师证件照就显示学员网络头像
		classroomInfo.put("teacherPic", uploadPathUtils.stitchingPath(classroomInfo.get("teacherPic"), "7"));
		/*if (StrUtils.isNull(classroomInfo.get("teacherPic"))) {
			TevglTraineeInfo traineeInfo = tevglTraineeInfoMapper.selectObjectById(classroomInfo.get("createUserId"));
			classroomInfo.put("teacherPic", "");
			if (traineeInfo != null) {
				String traineeHead = traineeInfo.getTraineeHead();
				if (traineeHead.indexOf("uploads") != -1) {
					String first = traineeHead.substring(0, 1);
					if (!"/".equals(first)) {
						traineeHead = "/" + traineeHead;
					}
				}
				classroomInfo.put("teacherPic", traineeHead);	
			}
		} else {
			if (classroomInfo.get("teacherPic").toString().indexOf("uploads") < 0) {
				//classroomInfo.put("teacherPic", ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("16") + "/" + classroomInfo.get("teacherPic"));
				classroomInfo.put("teacherPic", uploadPathUtils.stitchingPath(classroomInfo.get("teacherPic"), "16"));
			}
		}*/
	}

	/**
	 * 收藏课堂
	 * 
	 * @param ctId        课堂主键
	 * @param
	 * @return
	 */
	@Override
	public R collect(String ctId, String loginUserId) throws OssbarException {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroomInfo = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroomInfo == null) {
			return R.error("无效的课堂，无法收藏");
		}
		// 查询当前登录用户是否收藏此课堂
		List<TmeduMeFavority> list = getMeFavorityListData(ctId, loginUserId);
		if (list.size() > 0) {
			return R.error("你已经收藏过该课堂了");
		}
		TmeduMeFavority tmeduFavority = new TmeduMeFavority();
		tmeduFavority.setFavorityId(Identities.uuid());
		tmeduFavority.setTargetId(ctId);
		tmeduFavority.setFavorityType(GlobalFavority.FAVORITY_11_CLASSROOM);
		tmeduFavority.setTraineeId(loginUserId);
		tmeduFavority.setFavorityTime(DateUtils.getNowTimeStamp());
		tmeduMeFavorityMapper.insert(tmeduFavority);
		// 收藏数+1
		TevglTchClassroom t = new TevglTchClassroom();
		t.setCtId(ctId);
		t.setCollectNum(1);
		tevglTchClassroomMapper.plusNum(t);
		// TODO 推送课堂收藏消息

		return R.ok("收藏成功");
	}

	/**
	 * 取消收藏
	 * 
	 * @param ctId 课堂主键
	 * @return 此课堂的创建者
	 */
	@Override
	public R cancelCollect(String ctId, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroomInfo = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroomInfo == null) {
			return R.error("无效的课堂");
		}
		// 查询当前登录用户是否收藏此课堂
		List<TmeduMeFavority> list = getMeFavorityListData(ctId, loginUserId);
		if (list.size() == 0) {
			return R.error("你尚未收藏该课堂");
		}
		List<String> favorityIds = list.stream().map(a -> a.getFavorityId()).collect(Collectors.toList());
		String[] array = favorityIds.stream().toArray(String[]::new);
		if (array.length > 0) {
			tmeduMeFavorityMapper.deleteBatch(array);
			// 收藏数-N
			TevglTchClassroom t = new TevglTchClassroom();
			t.setCtId(ctId);
			t.setCollectNum(-array.length);
			tevglTchClassroomMapper.plusNum(t);
		}
		return R.ok("取消收藏");
	}

	/**
	 * 查询当前登录用户是否收藏此课堂
	 * 
	 * @param ctId
	 * @param loginUserId
	 * @return
	 */
	private List<TmeduMeFavority> getMeFavorityListData(String ctId, String loginUserId) {
		Map<String, Object> map = new HashMap<>();
		map.put("favorityType", GlobalFavority.FAVORITY_11_CLASSROOM);
		map.put("targetId", ctId);
		map.put("traineeId", loginUserId);
		List<TmeduMeFavority> list = tmeduMeFavorityMapper.selectListByMap(map);
		return list;
	}

	/**
	 * 将课堂设置为置顶
	 * 
	 * @param ctId        课堂主键
	 * @param value       Y设为置顶N取消置顶
	 * @param loginUserId 当前登录用户
	 * @return
	 */
	@Override
	@PostMapping("/setTop")
	public R setTop(String ctId, String value, String loginUserId) throws OssbarException {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(value) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroomInfo = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroomInfo == null) {
			return R.error("无效的记录");
		}
		// 如果登录用户不是该课堂的创建者
		if (!loginUserId.equals(classroomInfo.getCreateUserId())) {
			return R.error("非法操作，没有权限");
		}
		String msg = "";
		TevglTchClassroom t = new TevglTchClassroom();
		t.setCtId(ctId);
		t.setIsTop(value);
		t.setUpdateTime(DateUtils.getNowTimeStamp());
		t.setUpdateUserId(loginUserId);
		tevglTchClassroomMapper.update(t);
		if ("Y".equals(value)) {
			msg = "置顶成功";
		} else {
			msg = "已取消置顶";
		}
		return R.ok(msg);
	}

	/**
	 * 开始上课
	 * 
	 * @param ctId        课堂主键
	 * @param loginUserId 当前登录用户
	 * @return
	 */
	@Override
	public R start(String ctId, String loginUserId) throws OssbarException {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroomInfo = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroomInfo == null) {
			return R.error("无效的记录");
		}
		if (!"Y".equals(classroomInfo.getState())) {
			return R.error("该课堂已被删除，请刷新后重试");
		}
		boolean hasAuth = roomUtils.hasOperatingAuthorization(classroomInfo, loginUserId);
		if (!hasAuth) {
			return R.error("非法操作，没有权限");
		}
		if ("2".equals(classroomInfo.getClassroomState())) {
			return R.error("课堂已开始");
		}
		if ("3".equals(classroomInfo.getClassroomState())) {
			return R.error("课堂已结束，无法开始");
		}
		TevglTchClassroom t = new TevglTchClassroom();
		t.setCtId(ctId);
		t.setClassroomState("2"); // 课堂状态(1未开始2进行中3已结束)
		tevglTchClassroomMapper.update(t);
		// 处理是否需要更换群主
		changeGroupAdmin(classroomInfo, loginUserId);
		// ================================================== 即时通讯相关处理 begin
		// ==================================================
		// 创建课堂群
		// 此步骤已于2021-05-17 16:18 调整至创建课堂方法中创建
		/*String className = "";
		TevglTchClass tchClass = tevglTchClassMapper.selectObjectById(classroomInfo.getClassId());
		if (tchClass != null) {
			className = tchClass.getClassName();
		}
		String groupId = createTimGroup(classroomInfo.getCtId(), classroomInfo.getName(), classroomInfo.getPic(), loginUserId, className);
		// 课堂创建者自己成为群主
		createTimGroupUserAdmin(groupId, loginUserId);
		// 更新聊天列表记录
		timChatListService.save(null, loginUserId, groupId, "2", 0, "", true);*/
		// ================================================== 即时通讯相关处理 end
		// ==================================================
		// 不管这个课堂是否审核,直接将班级成员导入成课堂成员
		// becomeClassroomTrainee(classroomInfo.getClassId(), classroomInfo.getCtId(),
		// classroomInfo.getPkgId(), loginUserId, new HashMap<String, Object>(),
		// classroomInfo.getName());
		// 推送更新课堂群成员的消息列表

		Map<String, Object> map = new HashMap<>();
		map.put("ctId", ctId);
		List<TevglTchClassroomTrainee> list = tevglTchClassroomTraineeMapper.selectListByMap(map);
		// 1.将老师手动导入的成员的即时通讯数据生成、2.课堂未开始前，学生提前加入课堂
		handleTraineeIm(classroomInfo, list);
		// 准备
		JSONObject msg = new JSONObject();
		msg.put("busitype", "reloadmsglist");
		msg.put("msgtype", "alert");
		JSONObject busitype = new JSONObject();
		busitype.put("title", "课堂【" + classroomInfo.getName() + "】开始了~");
		msg.put("msg", busitype);

		// 生成课堂经验值规则
		R r = createDefaultEmpiricalSetting(ctId, loginUserId);
		log.debug("开始课堂时，生成默认经验值规则结果：" + r.toString());
		return R.ok("开始上课");
	}

	private void handleTraineeIm(TevglTchClassroom classroomInfo, List<TevglTchClassroomTrainee> classroomTraineeList) {
		if (classroomTraineeList == null || classroomTraineeList.size() == 0) {
			return;
		}

		// 取出学员id
		List<String> classroomTraineeIdList = classroomTraineeList.stream()
				.filter(a -> !a.getTraineeId().equals(classroomInfo.getCreateUserId())).map(a -> a.getTraineeId())
				.distinct().collect(Collectors.toList());
		// 查询条件
		Map<String, Object> params = new HashMap<String, Object>();
		// 0.找到此课堂群的群成员
		params.put("groupId", classroomInfo.getCtId());

		}

		// 等待入库的课堂群成员



	private R createDefaultEmpiricalSetting(String ctId, String loginUserId) {
		List<Map<String, Object>> dictList = dictService.getDictList("empirical_type");
		List<Map<String, Object>> empiricalRulesList = dictService.getDictList("empirical_rules");
		if (dictList == null || dictList.size() == 0 || empiricalRulesList == null || empiricalRulesList.size() == 0) {
			return R.error("字典数据为空，无法生成默认经验值设置");
		}
		// 规则大类
		List<Map<String, Object>> bigTypeList = getBigTypeList(ctId, loginUserId, dictList);
		// 具体规则
		List<Map<String, Object>> ruleList = getRuleList(ctId, loginUserId, empiricalRulesList, bigTypeList);
		bigTypeList.stream().forEach(typeInfo -> {
			List<Map<String, Object>> children = ruleList.stream()
					.filter(a -> a.get("typeId").equals(typeInfo.get("typeId"))).collect(Collectors.toList());
			// 单独设置下
			if ("3".equals(typeInfo.get("dictCode"))) {
				Map<String, Object> m = new HashMap<>();
				m.put("stId", Identities.uuid());
				m.put("typeId", typeInfo.get("typeId"));
				m.put("flag", true);
				m.put("name", "测试活动，总分累加");
				m.put("value", null);
				children.add(m);
				Map<String, Object> m2 = new HashMap<>();
				m2.put("stId", Identities.uuid());
				m2.put("typeId", typeInfo.get("typeId"));
				m2.put("flag", true);
				m2.put("name", "课堂表现，老师评分");
				m2.put("value", null);
				children.add(m2);
				Map<String, Object> m3 = new HashMap<>();
				m3.put("stId", Identities.uuid());
				m3.put("typeId", typeInfo.get("typeId"));
				m3.put("flag", true);
				m3.put("name", "实现考核，总分累加");
				m3.put("value", null);
				children.add(m3);
			}
			typeInfo.put("children", children);
		});
		return R.ok();
	}

	private List<Map<String, Object>> getBigTypeList(String ctId, String loginUserId,
			List<Map<String, Object>> dictList) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		// 先判断课堂是否有记录，有则更新，无则新增
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctId", ctId);
		List<TevglEmpiricalType> list = tevglEmpiricalTypeMapper.selectListByMap(params);
		// 等待入库的集合
		List<TevglEmpiricalType> insertEmpiricalTypeList = new ArrayList<>();

		for (Map<String, Object> dict : dictList) {
			String dictCode = dict.get("dictCode").toString();
			String dictValue = dict.get("dictValue").toString();
			if (!list.stream().anyMatch(a -> a.getDictCode().equals(dictCode))) {
				int val = 0;
				switch (dictCode) {
				case "1":
					val = 40;
					break;
				case "2":
					val = 40;
					break;
				case "3":
					val = 20;
					break;
				default:
					break;
				}
				TevglEmpiricalType t = new TevglEmpiricalType();
				t.setTypeId(Identities.uuid());
				t.setCtId(ctId);
				t.setDictCode(dictCode);
				t.setName(dictValue);
				t.setWeight(new BigDecimal(val));
				t.setCreateTime(DateUtils.getNowTimeStamp());
				t.setCreateUserId(loginUserId);
				// tevglEmpiricalTypeMapper.insert(t);
				insertEmpiricalTypeList.add(t);
				Map<String, Object> m = new HashMap<>();
				m.put("typeId", t.getTypeId());
				m.put("dictCode", dictCode);
				m.put("name", t.getName());
				m.put("weight", val);
				if (!resultList.contains(m)) {
					resultList.add(m);
				}
			} else {
				List<TevglEmpiricalType> collect = list.stream().filter(a -> a.getDictCode().equals(dictCode))
						.collect(Collectors.toList());
				if (collect != null && collect.size() > 0) {
					TevglEmpiricalType t = collect.get(0);
					Map<String, Object> m = new HashMap<>();
					m.put("typeId", t.getTypeId());
					m.put("dictCode", dictCode);
					m.put("name", t.getName());
					m.put("weight", t.getWeight());
					if (!resultList.contains(m)) {
						resultList.add(m);
					}
				}
			}
		}
		if (insertEmpiricalTypeList != null && insertEmpiricalTypeList.size() > 0) {
			tevglEmpiricalTypeMapper.insertBatch(insertEmpiricalTypeList);
		}
		return resultList;
	}

	/**
	 * 
	 * @param ctId
	 * @param loginUserId
	 * @param dictList
	 * @param bigTypeList
	 * @return
	 */
	private List<Map<String, Object>> getRuleList(String ctId, String loginUserId, List<Map<String, Object>> dictList,
			List<Map<String, Object>> bigTypeList) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctId", ctId);
		List<TevglEmpiricalSetting> list = tevglEmpiricalSettingMapper.selectListByMap(params);
		List<TevglEmpiricalSetting> inserList = new ArrayList<TevglEmpiricalSetting>();
		for (Map<String, Object> dict : dictList) {
			String dictCode = dict.get("dictCode").toString();
			String dictValue = dict.get("dictValue").toString();
			if (!list.stream().anyMatch(a -> a.getDictCode().equals(dictCode))) {
				TevglEmpiricalSetting t = new TevglEmpiricalSetting();
				t.setStId(Identities.uuid());
				t.setCtId(ctId);
				t.setDictCode(dictCode);
				t.setName(dictValue);
				String ev = setEmpiricalValue(dictCode);
				t.setValue(new BigDecimal(ev));
				// 设置typeId
				String type = getByMap(dictCode);
				List<Map<String, Object>> collect = bigTypeList.stream().filter(a -> a.get("dictCode").equals(type))
						.collect(Collectors.toList());
				if (collect != null && collect.size() > 0) {
					t.setTypeId(collect.get(0).get("typeId").toString());
				}
				// tevglEmpiricalSettingMapper.insert(t);
				inserList.add(t);
				Map<String, Object> m = new HashMap<>();
				m.put("stId", t.getStId());
				m.put("typeId", t.getTypeId());
				m.put("dictCode", dictCode);
				m.put("name", t.getName());
				m.put("value", t.getValue());
				if (!resultList.contains(m)) {
					resultList.add(m);
				}
			} else {
				List<TevglEmpiricalSetting> tevglEmpiricalSettingList = list.stream()
						.filter(a -> a.getDictCode().equals(dictCode)).collect(Collectors.toList());
				TevglEmpiricalSetting t = tevglEmpiricalSettingList.get(0);
				Map<String, Object> m = new HashMap<>();
				m.put("stId", t.getStId());
				m.put("typeId", t.getTypeId());
				m.put("dictCode", dictCode);
				m.put("name", t.getName());
				m.put("value", t.getValue());
				if (!resultList.contains(m)) {
					resultList.add(m);
				}
			}
		}
		if (inserList != null && inserList.size() > 0) {
			tevglEmpiricalSettingMapper.insertBatch(inserList);
		}
		return resultList;
	}

	/**
	 * 人工归类，获取当前归属的大类
	 * 
	 * @param dictCode
	 * @return
	 */
	private String getByMap(String dictCode) {
		Map<String, Object> m = new HashMap<>();
		// 活教材
		m.put("1", "1"); // 章节阅读(每个章节知识点)，
		m.put("2", "1"); // 查看视频，加
		m.put("3", "1"); // 查看音频，加
		// 考勤
		m.put("4", "2"); // 正常签到一次，加
		m.put("5", "2"); // 迟到一次，扣
		m.put("6", "2"); // 旷课一次，扣
		m.put("7", "2"); // 请假一次，扣
		// 活动
		m.put("8", "3"); // 投票问卷，加
		m.put("9", "3"); // 头脑风暴，加
		m.put("10", "3"); // 答疑讨论，加
		return m.get(dictCode).toString();
	}

	/**
	 * 默认的规则分
	 * 
	 * @param key 1章节阅读(每个章节知识点)2查看视频3查看音频4正常签到一次5迟到一次6旷课一次7请假一次8投票问卷9头脑风暴10答疑讨论
	 * @return
	 */
	private String setEmpiricalValue(String key) {
		Map<String, Object> map = new HashMap<>();
		map.put("1", "1"); // 章节阅读(每个章节知识点)，已阅读加1分
		map.put("2", "5"); // 章节阅读(每个章节知识点)，已阅读加1分
		map.put("3", "3");
		map.put("4", "2");
		map.put("5", "5");
		map.put("6", "1");
		map.put("7", "1");
		map.put("8", "1");
		map.put("9", "1");
		map.put("10", "1");
		return map.get(key).toString();
	}

	/**
	 * 结束上课
	 * 
	 * @param ctId
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R end(String ctId, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom classroomInfo = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroomInfo == null) {
			return R.error("无效的记录");
		}
		if (!"Y".equals(classroomInfo.getState())) {
			return R.error("该课堂已被删除，请刷新后重试");
		}
		// 如果既不是创建者，又不是管理员
		boolean isRoomCreator = loginUserId.equals(classroomInfo.getCreateUserId());
		boolean isAdministrator = ConstantProd.ADMINISTRATOR.equals(loginUserId);
		if (!isRoomCreator && !isAdministrator) {
			return R.error("非法操作！");
		}
		if ("1".equals(classroomInfo.getClassroomState())) {
			return R.error("课堂尚未开始，无需结束");
		}
		if ("3".equals(classroomInfo.getClassroomState())) {
			return R.error("课堂已结束");
		}
		TevglTchClassroom t = new TevglTchClassroom();
		t.setCtId(ctId);
		t.setClassroomState("3"); // 课堂状态(1未开始2进行中3已结束)
		tevglTchClassroomMapper.update(t);
		// 结束课堂（暂时注释，如果需要，去掉即可）
		handleImDatas(ctId, classroomInfo);
		// 结束活动
		String activityState = "2"; // 活动状态(0未开始1进行中2已结束)
		Map<String, Object> ps = new HashMap<String, Object>();
		ps.put("ctId", classroomInfo.getCtId());
		ps.put("pkgId", classroomInfo.getPkgId());
		List<TevglPkgActivityRelation> list = tevglPkgActivityRelationMapper.selectListByMap(ps);
		// 修改实际的活动状态
		list.stream().forEach(relation -> {
			relation.setActivityState(activityState);
			tevglPkgActivityRelationMapper.update(relation);
		});
		// 即时通讯
		JSONObject msg = new JSONObject();
		msg.put("busitype", "endclassroom");
		msg.put("msgtype", "alert");
		JSONObject busitype = new JSONObject();
		busitype.put("title", "【" + classroomInfo.getName() + "】 课堂结束了");
		busitype.put("ctId", ctId);
		busitype.put("ct_id", ctId);
		msg.put("msg", busitype);

		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		List<TevglTchClassroomTrainee> classroomTraineeList = tevglTchClassroomTraineeMapper.selectListByMap(params);
		if (classroomTraineeList != null && classroomTraineeList.size() > 0) {
			for (TevglTchClassroomTrainee traineeInfo : classroomTraineeList) {
					}
		}
		return R.ok("结束上课");
	}

	/**
	 * 根据条件获取年份
	 * 
	 * @author zhouyunlong加
	 * @data 2020年12月4日
	 * @param loginUserId 当前登录用户
	 * @param type        类型值对应：1时查询加入的课堂（我听的课）的年份 2自己创建的课堂（我教的课）的年份，为空则查全部
	 * @return
	 */
	@Override
	@GetMapping("getDates")
	public List<Map<String, Object>> getDates(String loginUserId, String type) {
		List<Map<String, Object>> clsList = new ArrayList<>();
		// 第一种用sql语句实现获取年份
		Map<String, Object> params = new HashMap<>();
		if ("1".equals(type)) {// type为1时查询加入的课堂
			params.put("traineeId", loginUserId);
			List<Map<String, Object>> list = tevglTchClassroomTraineeMapper.selectJoinClassroomYear(params);
			for (Map<String, Object> trainees : list) {
				clsList.add(trainees);
			}
		} else if ("2".equals(type)) {
			params.clear();
			params.put("createUserId", loginUserId);
			List<Map<String, Object>> list = tevglTchClassroomMapper.selectCreateClassroomYear(params);
			for (Map<String, Object> classrooms : list) {
				clsList.add(classrooms);
			}
		} else {
			params.clear();
			params.put("createUserId", loginUserId);
			List<Map<String, Object>> classroomList = tevglTchClassroomMapper.selectCreateClassroomYear(params);
			for (Map<String, Object> classrooms : classroomList) {
				clsList.add(classrooms);
			}
			params.clear();
			params.put("traineeId", loginUserId);
			List<Map<String, Object>> traineeList = tevglTchClassroomTraineeMapper.selectJoinClassroomYear(params);
			for (Map<String, Object> trainees : traineeList) {
				clsList.add(trainees);
			}
		}

		// 第二种用代码实现获取年份
		/*if ("1".equals(type)) {//type为1时查询加入的课堂
		classroomTrainees(loginUserId, type, clsList);
		}else if ("2".equals(type)){//type为2时查询创建的课堂
			tchClassrooms(loginUserId, type, clsList);
		}else {//查询全部
			//查询加入的课堂
			classroomTrainees(loginUserId, type, clsList);
			//查询创建的课堂
			tchClassrooms(loginUserId, type, clsList);
		}*/

		return clsList;
	}

	/**
	 * 查询加入的课堂年份
	 * 
	 * @param loginUserId
	 * @param type
	 * @param clsList
	 * @return
	 */
	public List<Map<String, Object>> classroomTrainees(String loginUserId, String type,
			List<Map<String, Object>> clsList) {
		Map<String, Object> params = new HashMap<>();
		params.put("traineeId", loginUserId);
		List<Map<String, Object>> classroomTrainees = tevglTchClassroomTraineeMapper.selectListMapByMap(params);
		if (classroomTrainees != null && classroomTrainees.size() > 0) {
			for (Map<String, Object> trainees : classroomTrainees) {
				Set<String> keys = trainees.keySet();
				// 迭代器遍历Set集合
				Iterator<String> it = keys.iterator();
				String key = null;
				String value = null;
				while (it.hasNext()) {
					key = it.next();
					value = (String) trainees.get(key);
					if (key.equals("join_date")) {
						break;
					}
				}
				if (key.equals("join_date")) {
					// 截取年份
					String year = value.substring(0, 4);
					trainees.put("year", year);
				}
				clsList.add(trainees);
			}
		}
		return clsList;
	}

	/**
	 * 查询创建的课堂
	 * 
	 * @param loginUserId 登录人
	 * @param type        类型
	 * @param clsList
	 * @return
	 */
	public List<Map<String, Object>> tchClassrooms(String loginUserId, String type, List<Map<String, Object>> clsList) {
		Map<String, Object> params = new HashMap<>();
		params.put("createUserId", loginUserId);
		List<Map<String, Object>> tchClassrooms = tevglTchClassroomMapper.selectClassroomYear(params);
		if (tchClassrooms != null && tchClassrooms.size() > 0) {
			for (Map<String, Object> classrooms : tchClassrooms) {
				Set<String> keys = classrooms.keySet();
				// 迭代器遍历Set集合
				Iterator<String> it = keys.iterator();
				String key = null;
				String value = null;
				while (it.hasNext()) {
					key = it.next();
					value = (String) classrooms.get(key);
					if (key.equals("create_time")) {
						break;
					}
				}
				if (key.equals("create_time")) {
					String year = value.substring(0, 4);
					classrooms.put("year", year);
				}
				clsList.add(classrooms);
			}
		}
		return clsList;
	}

	/**
	 * @author zhouyl加
	 * @date 2021年2月20日 查询当前登录用户创建的所有课堂信息
	 * @param ctId        课程id
	 * @param loginUserId
	 * @return
	 */
	@Override
	@GetMapping("queryClassroomList")
	public R queryClassroomList(String ctId, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}

		TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroom == null) {
			return R.error("无效的记录");
		}
		/*if (!loginUserId.equals(classroom.getCreateUserId())) {
			return R.error("非法操作，不能操作他人的数据");
		}*/
		boolean hasAuth = roomUtils.hasOperatingAuthorization(classroom, loginUserId);
		if (!hasAuth) {
			return R.error("非法操作，没有权限");
		}
		if (!"Y".equals(classroom.getState())) {
			return R.error("无效的课堂");
		}
		if ("1".equals(classroom.getClassroomState())) {
			return R.error("课堂尚未开始，无法查看");
		}
		if ("3".equals(classroom.getClassroomState())) {
			return R.error("课堂已结束");
		}
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> params = new HashMap<>();
		// 获取当前教师1年内创建的课堂
		params.put("teacherId", loginUserId);
		params.put("withinyear", "true");
		List<Map<String, Object>> classroomList = tevglTchClassroomMapper.selectListMapByMap(params);
		List<Object> ctIds = classroomList.stream().map(a -> a.get("ct_id")).collect(Collectors.toList());
		// 通过课堂id查找当前课堂的成员
		params.clear();
		params.put("ctId", ctId);
		List<TevglTchClassroomTrainee> currentClasses = tevglTchClassroomTraineeMapper.selectListByMap(params);

		// 存放成员未全部导入的课堂id
		List<String> ctIdList = new ArrayList<>();

		// 查找其它课堂的所有成员并获取成员未全部导入的课堂id
		for (int i = 0; i < ctIds.size(); i++) {
			params.clear();
			params.put("ctId", ctIds.get(i));
			List<TevglTchClassroomTrainee> otherClasses = tevglTchClassroomTraineeMapper.selectListByMap(params);

			if (currentClasses.size() == 0 && otherClasses.size() > 0) {
				ctIdList.add(ctIds.get(i).toString());
			} else if (otherClasses.size() > 0 && currentClasses.size() > 0) {
				for (int j = 0; j < otherClasses.size(); j++) {
					Boolean temp = true;
					for (int k = 0; k < currentClasses.size(); k++) {
						if (otherClasses.get(j).getTraineeId().equals(currentClasses.get(k).getTraineeId())) {
							temp = false;
						}
					}
					if (temp) {
						ctIdList.add(ctIds.get(i).toString());
						break;
					}
				}
			}
		}

		// 获取成员未全部导入的课堂信息
		if (ctIdList.size() > 0) {
			params.clear();
			params.put("ctIds", ctIdList);
			List<Map<String, Object>> showClassList = tevglTchClassroomTraineeMapper.showClassroomList(params);
			// 查询所有的班级信息与课堂信息班级id相同的数据
			params.clear();
			List<TevglTchClass> classList = tevglTchClassMapper.selectListByMap(params);
			showClassList.stream().forEach(info -> {
				// 取出班级信息与
				List<TevglTchClass> collect = classList.stream()
						.filter(a -> a.getClassId().equals(info.get("class_id"))).collect(Collectors.toList());
				if (collect != null && collect.size() > 0) {
					info.put("name", info.get("name") + " " + collect.get(0).getClassName());
				}
			});
			list.addAll(showClassList);
		}

		return R.ok().put(Constant.R_DATA, list);
	}

	/**
	 * @author zhouyl加
	 * @date 2021年2月20日 一键加入课堂
	 * @param jsonObject
	 * @param ctId
	 * @param loginUserId
	 * @return
	 */
	@Override
	@PostMapping("oneClickToJoinClassroom")
	public R oneClickToJoinClassroom(@RequestBody JSONObject jsonObject, String ctId, String loginUserId) {
		if (StrUtils.isEmpty(loginUserId) || StrUtils.isEmpty(ctId)) {
			return R.error("必传参数为空");
		}
		JSONArray ctIds = jsonObject.getJSONArray("ctIds");
		if (ctIds == null || ctIds.size() == 0) {
			return R.error("请选择你要一键加课堂成员的课堂");
		}
		Map<String, Object> params = new HashMap<>();
		// 根据课堂id查询课堂学员信息
		params.put("ctIds", ctIds);
		List<TevglTchClassroomTrainee> classroomTrainees = tevglTchClassroomTraineeMapper.selectListByMap(params);
		if (classroomTrainees == null || classroomTrainees.size() == 0) {
			return R.ok("没有需要加入的学员");
		}
		List<String> traineeIds = classroomTrainees.stream().filter(a -> "Y".equals(a.getState()))
				.map(a -> a.getTraineeId()).distinct().collect(Collectors.toList());
		R r = tevglTchClassroomTraineeService.importTraineeBatchNew(ctId, loginUserId, traineeIds);
		log.debug("结果：" + r.toString());
		if (!r.get("code").equals(0)) {
			return r;
		}
		/*
		// 截取当前时间的时分秒
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String joinDate = sdf.format(new Date());
		for (TevglTchClassroomTrainee tevglTchClassroomTrainee : classroomTrainees) {
			System.out.println("一键加课堂成员的课堂有： " + tevglTchClassroomTrainee);
		}
		String classId = null;
		// 找出班级id
		for (TevglTchClassroomTrainee classroomTrainee : classroomTrainees) {
			classId = classroomTrainee.getClassId();
		}
		// 取出课堂的所有学员id  
		List<String> traineeIds = classroomTrainees.stream().map(a -> a.getTraineeId()).collect(Collectors.toList());
		// 存放课堂学员的集合
		List<TevglTchClassroomTrainee> list = new ArrayList<>();
		Integer tempInt = 0;	//新增的学员数
		for (String traineeId : traineeIds) {
			// 填充信息
			TevglTchClassroomTrainee classroomTrainee = new TevglTchClassroomTrainee();
			classroomTrainee.setId(Identities.uuid());
			classroomTrainee.setCtId(ctId);
			classroomTrainee.setTraineeId(traineeId);
			classroomTrainee.setClassId(classId);
			classroomTrainee.setJoinDate(joinDate);
			classroomTrainee.setCreateTime(DateUtils.getNowTimeStamp());
			classroomTrainee.setState("Y");
			// 控制不重复插入记录
			params.clear();
			params.put("ctId", ctId);
			params.put("traineeId", traineeId);
			List<TevglTchClassroomTrainee> trainees = tevglTchClassroomTraineeMapper.selectListByMap(params);
			if (trainees == null || trainees.size() == 0) {
				// 添加到集合中
				list.add(classroomTrainee);
				tempInt++;
			}
			if (list != null && list.size() > 0) {
				tevglTchClassroomTraineeMapper.insertBatch(list);
			}
			// 清空集合
			list.clear();
		}
		//更新课堂学习人数
		params.clear();
		params.put("ctId", ctId);
		TevglTchClassroom tevglTchClassroom = new TevglTchClassroom();
		tevglTchClassroom.setCtId(ctId);
		tevglTchClassroom.setStudyNum(tempInt);
		tevglTchClassroomMapper.plusNum(tevglTchClassroom);
		*/
		return R.ok("添加成功");
	}

	/**
	 * 根据条件查询群成员
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public R queryImGroupUserList(Map<String, Object> params) {
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<Map<String, Object>> list = tevglTchClassroomMapper.queryImGroupUserList(params);
		list.stream().forEach(a -> {
			// 如果是群主
			if ("2".equals(a.get("groupuserAdmin"))) {
				TevglTchTeacher tevglTchTeacher = tevglTchTeacherMapper.selectObjectByTraineeId(a.get("userId"));
				if (tevglTchTeacher != null) {
					a.put("userimg", uploadPathUtils.stitchingPath(tevglTchTeacher.getTeacherPic(), "7"));
				}
			} else {
				a.put("userimg", uploadPathUtils.stitchingPath(a.get("userimg"), "16"));
			}
		});
		PageUtils pageUtil = new PageUtils(list, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	/**
	 * 移交课堂（管理端将课堂移交给某老师）
	 *
	 * @param ctId      必传参数，课堂主键
	 * @param traineeId 必传参数，用户主键
	 * @return
	 */
	@Override
	public R turnOver(String ctId, String traineeId) {
		R r = checkIsPassForTurnOver(ctId, traineeId);
        if (!r.get("code").equals(0)) {
            return r;
        }
        // 1.增接了新的receiver_user_id来维护
        // 2.但是，系统中，诸多代码则要兼容处理移交之后的逻辑
        TevglTchClassroom tevglTchClassroom = (TevglTchClassroom) r.get("tevglTchClassroom");
        if (traineeId.equals(tevglTchClassroom.getCreateUserId())) {
            // 将接收者id置空
            TevglTchClassroom tch = new TevglTchClassroom();
            tch.setCtId(ctId);
            tch.setReceiverUserId(null);
            tevglTchClassroomMapper.updateReceiverUserId(tch);
            // 如果被选老师已经加入了该课堂，将其移除
            removeTeacherFromClassroomTrainee(tevglTchClassroom, traineeId);
            // 更换群主，将原群主改为成员，将被选老师设为群主
            changeGroupAdmin(tevglTchClassroom, traineeId);
            return R.ok("移交成功");
        }
        TevglTchClassroom t = new TevglTchClassroom();
        t.setCtId(ctId);
        t.setReceiverUserId(traineeId);
        tevglTchClassroomMapper.update(t);
        // 如果被选老师已经加入了该课堂，将其移除
        removeTeacherFromClassroomTrainee(tevglTchClassroom, traineeId);
        // 更换群主，将原群主改为成员，将被选老师设为群主
        changeGroupAdmin(tevglTchClassroom, traineeId);
        // 答疑讨论群，亦是如此

        return R.ok("移交成功");
	}
	
	/**
	 * 如果被选老师已经加入了该课堂，将其移除
	 * @param tevglTchClassroom
	 * @param traineeId
	 */
	private void removeTeacherFromClassroomTrainee(TevglTchClassroom tevglTchClassroom, String traineeId){
		if (tevglTchClassroom == null || StrUtils.isEmpty(traineeId)) {
            return;
        }
		Map<String, Object> map = new HashMap<>();
		map.put("ctId", tevglTchClassroom.getCtId());
		map.put("traineeId", traineeId);
		List<TevglTchClassroomTrainee> classroomTraineeList = tevglTchClassroomTraineeMapper.selectListByMap(map);
		if (classroomTraineeList != null && classroomTraineeList.size() > 0) {
			for (TevglTchClassroomTrainee tevglTchClassroomTrainee : classroomTraineeList) {
				tevglTchClassroomTraineeMapper.delete(tevglTchClassroomTrainee.getId());
				roomUtils.plusStudyNum(tevglTchClassroom.getCtId(), -1);
			}
		}
	}
	
    /**
     * 更换群主，将原群主改为成员，将被选老师设为群主
     * @param tevglTchClassroom
     * @param traineeId
     */
    private void changeGroupAdmin(TevglTchClassroom tevglTchClassroom, String traineeId){
        if (tevglTchClassroom == null || StrUtils.isEmpty(traineeId)) {
            return;
        }
        String ctId = tevglTchClassroom.getCtId();
        // 将当前用户变成课堂群群主，原群主将为群员，注意切来切去的情况
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", ctId);

    }

	/**
	 * 移交课堂时合法性校验
	 * 
	 * @param ctId
	 * @param traineeId
	 * @return
	 */
	private R checkIsPassForTurnOver(String ctId, String traineeId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(traineeId)) {
            return R.error("必传参数为空");
        }
        TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
        if (tevglTchClassroom == null) {
            return R.error("此课堂已不存在，无法移交");
        }
        if (StrUtils.isEmpty(tevglTchClassroom.getReceiverUserId()) && (tevglTchClassroom.getCreateUserId().equals(traineeId))) {
            return R.error("当前课堂教师与您选择的教师一致，无需更换");
        }
        TevglTraineeInfo traineeInfo = tevglTraineeInfoMapper.selectObjectById(traineeId);
        if (traineeInfo == null) {
            return R.error("该被选用户没有网站账号，无法移交");
        }
        // 注意：老师身份需满足如下数据：
        // ① t_evgl_trainee_info表主键id作为教师表的主键id以及trainee_id的值
        // ② (废弃)且需保证t_evgl_trainee_info表的trainee_type为4
        /*if (!"4".equals(traineeInfo.getTraineeType())) {
            return R.error("参数异常，请联系系统管理人员处理");
        }*/
        return R.ok().put("tevglTchClassroom", tevglTchClassroom);
	}

	@Override
	public TevglTchClassroom selectObjectByPkgId(Object pkgId) {
		return tevglTchClassroomMapper.selectObjectByPkgId(pkgId);
	}

	@Override
	public R verifyAccessToClass(String ctId, String traineeId) {
		// 参数检验
        if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(traineeId)) {
            return R.error(BizCodeEnume.PARAM_MISSING.getCode(), BizCodeEnume.PARAM_MISSING.getMsg());
        }
        TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
        if (tevglTchClassroom == null) {
            return R.error(BizCodeEnume.INEFFECTIVE_CLASSROOM.getCode(), BizCodeEnume.INEFFECTIVE_CLASSROOM.getMsg());
        }
        // 如果是老师身份，且账号被禁用，且课堂是该用户创建
        boolean isCreator = StrUtils.isEmpty(tevglTchClassroom.getReceiverUserId()) && traineeId.equals(tevglTchClassroom.getCreateUserId());
        boolean isReceiver = StrUtils.isNotEmpty(tevglTchClassroom.getReceiverUserId()) && traineeId.equals(tevglTchClassroom.getReceiverUserId());
        if (isCreator || isReceiver) {
            TevglTchTeacher tevglTchTeacher = tevglTchTeacherMapper.selectObjectByTraineeId(traineeId);
            if (tevglTchTeacher == null || CommonEnum.STATE_NO.getCode().equals(tevglTchTeacher.getState())) {
                return R.error(BizCodeEnume.THE_ACCOUNT_HAS_BEEN_DEACTIVATED.getCode(), BizCodeEnume.THE_ACCOUNT_HAS_BEEN_DEACTIVATED.getMsg());
            }
        } else {
            if ("3".equals(tevglTchClassroom.getClassroomState())) {
                // 如果且登录的情况
                if (StrUtils.isNotEmpty(traineeId)) {
                    // 查询课堂成员
                    Map<String, Object> map = new HashMap<>();
                    map.put("ctId", tevglTchClassroom.getCtId());
                    List<TevglTchClassroomTraineeVo> classroomTraineeList = tevglTchClassroomTraineeMapper.findClassroomTraineeList(map);
                    // 判断该用户在某课堂依旧是否被允许进入
                    boolean flag = classroomTraineeList.stream().anyMatch(a -> CommonEnum.STATE_YES.getCode().equals(a.getAccessState()) && a.getTraineeId().equals(traineeId));
                    // 如果没设置允许
                    if (!flag) {
                        return R.error(BizCodeEnume.CLASS_IS_OVER.getCode(), BizCodeEnume.CLASS_IS_OVER.getMsg());
                    }
                } else {
                    return R.error();
                }
            }
        }
        return R.ok("校验通过");
	}
}