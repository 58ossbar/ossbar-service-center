package com.ossbar.modules.evgl.tch.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.PkgPermissionUtils;
import com.ossbar.modules.common.enums.CommonEnum;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgActivityRelation;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgActivityRelationMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClass;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomTraineeMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchTeacherMapper;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeInfoMapper;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author huj
 * @create 2022-09-17 9:54
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
public class TevglTchClassroomServiceImpl implements TevglTchClassroomService {

    private Logger log = LoggerFactory.getLogger(TevglTchClassroomServiceImpl.class);

    private static String INDEX_ROOM = "14";

    @Autowired
    private TevglTchClassroomMapper tevglTchClassroomMapper;
    @Autowired
    private TevglTchTeacherMapper tevglTchTeacherMapper;
    @Autowired
    private TevglTraineeInfoMapper tevglTraineeInfoMapper;
    @Autowired
    private TevglPkgInfoMapper tevglPkgInfoMapper;
    @Autowired
    private TevglTchClassroomTraineeMapper tevglTchClassroomTraineeMapper;
    @Autowired
    private TevglPkgActivityRelationMapper tevglPkgActivityRelationMapper;
    @Autowired
    private TevglTchClassMapper tevglTchClassMapper;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private UploadFileUtils uploadPathUtils;
    @Autowired
    private PkgPermissionUtils pkgPermissionUtils;

    /** 标识:课堂创建者isOwner（boolean值，true是false否） */
    private static String IS_OWNER = "isOwner";
    /** 标识:课堂是否收藏isCollected（boolean值，true已收藏false未收藏） */
    private static String IS_COLLECTED = "isCollected";
    /** 标识:用户是否加入课堂isJoined（boolean值，true已加入false未加入） */
    private static String IS_JOINED = "isJoined";

    /**
     * 根据条件查询记录
     *
     * @param map
     * @return
     */
    @Override
    public R query(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<TevglTchClassroom> tevglTchClassroomList = tevglTchClassroomMapper.selectListByMap(query);
        convertUtil.convertUserId2RealName(tevglTchClassroomList, "createUserId", "updateUserId");
        convertUtil.convertUserId2RealName(tevglTchClassroomList, "createUserId", "updateUserId");
        if (tevglTchClassroomList.size() > 0) {
            tevglTchClassroomList.forEach(a -> {
                uploadPathUtils.stitchingPath(a.getPic(), INDEX_ROOM);
            });
        }
        PageUtils pageUtil = new PageUtils(tevglTchClassroomList, query.getPage(), query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 根据条件查询记录
     *
     * @param map
     * @return
     */
    @Override
    public R queryForMap(Map<String, Object> map) {
        map.put("state", "Y");
        map.put("sidx", "t1.create_time");
        map.put("order", "desc, t1.name desc");
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<Map<String, Object>> tevglTchClassroomList = tevglTchClassroomMapper.selectListMapByMap(query);
        tevglTchClassroomList.stream().forEach(item -> {
            item.put("pic", uploadPathUtils.stitchingPath(item.get("pic"), INDEX_ROOM));
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
     * @return
     */
    @Override
    public R save(TevglTchClassroom tevglTchClassroom) {
        return null;
    }

    /**
     * 修改
     *
     * @param tevglTchClassroom
     * @return
     */
    @Override
    public R update(TevglTchClassroom tevglTchClassroom) {
        return null;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public R delete(String id) {
        return null;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public R deleteBatch(String[] ids) {
        return null;
    }

    /**
     * 查看明细
     *
     * @param id
     * @return
     */
    @Override
    public R view(String id) {
        return null;
    }

    /**
     * 新增课堂
     *
     * @param tevglTchClassroom
     * @param loginUserId
     * @return
     */
    @Override
    public R saveClassroomInfoNew(TevglTchClassroom tevglTchClassroom, String loginUserId) {
        return null;
    }

    /**
     * 修改课堂信息
     *
     * @param tevglTchClassroom
     * @param loginUserId
     * @return
     */
    @Override
    public R updateClassroomInfo(TevglTchClassroom tevglTchClassroom, String loginUserId) {
        return null;
    }

    /**
     * <p>课堂列表</p>
     *
     * @param params
     * @param loginUserId
     * @param type        类型值对应：1时查询加入的课堂（我听的课）2自己创建的课堂（我教的课），为空则查全部
     * @return
     * @author huj
     * @data 2019年8月20日
     */
    @Override
    public R listClassroom(Map<String, Object> params, String loginUserId, String type) {
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
            //List<TmeduMeFavority> tmeduMeFavorityList = new ArrayList<TmeduMeFavority>();
            // 获取登录用户置顶的课堂数据
            //List<TevglTchClassroomTop> userTopList = new ArrayList<TevglTchClassroomTop>();
            // 获取登录用户所申请或加入的课堂id
            List<Map<String, Object>> classroomTraineeList = new ArrayList<Map<String, Object>>();
            // 如果已登录
            if (StrUtils.isNotEmpty(loginUserId)) {
                params.clear();
                params.put("traineeId", loginUserId);
                //tmeduMeFavorityList = tmeduMeFavorityMapper.selectListByMap(params);
                // 获取登录用户置顶的课堂数据
                params.clear();
                params.put("traineeId", loginUserId);
                //userTopList = tevglTchClassroomTopMapper.selectListByMap(params);
                // 获取登录用户所申请或加入的课堂id
                classroomTraineeList = tevglTchClassroomMapper.getCtIdsByTraineeId(loginUserId);
            }
            for (Map<String, Object> classroomInfo : tevglTchClassroomList) {
                // 移除验证码
                if (StrUtils.isNotEmpty(type) && !"2".equals(type)) {
                    classroomInfo.remove("invitationCode");
                }
                // TODO 处理置顶标识,默认不是置顶
                classroomInfo.put("isTop", false);
                // 处理课堂是否属于当前登录用户（创建者）
                isThisClassroomBelongToLoginUser(classroomInfo, loginUserId);
                // 处理是否申请了加入课堂,并通过了审核
                isApplyAndIsPass(classroomInfo, loginUserId, classroomTraineeList);
                // 处理是否加入了此课堂
                isHasJoinedThisClassroom(classroomInfo, loginUserId, classroomTraineeList);
                // TODO 处理课堂是否收藏
                //isCollectedThisClassroom(classroomInfo, loginUserId, tmeduMeFavorityList);
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
     * 拼接课堂封面和课堂二维码的路径
     *
     * @param classroomInfo
     */
    private void handlePicAndQrcode(Map<String, Object> classroomInfo) {
        // 课堂封面图
        if (classroomInfo.get("pic") != null) {
            classroomInfo.put("pic", uploadPathUtils.stitchingPath(classroomInfo.get("pic"), INDEX_ROOM));
        }
        // 课堂二维码
        if (classroomInfo.get("qrCode") != null) {
            classroomInfo.put("qrCode", uploadPathUtils.stitchingPath(classroomInfo.get("qrCode"), INDEX_ROOM));
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
    }

    /**
     * 查看课堂信息
     *
     * @param id          课堂主键
     * @param loginUserId 当前登录用户
     * @return
     */
    @Override
    public R viewClassroomInfo(String id, String loginUserId) {
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
        pkgInfo.put("PkgTraineeEmail", tevglPkgInfo.getPkgTraineeEmail());
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
     * 取部分属性
     *
     * @param teacherId
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
     * 取部分属性
     *
     * @param tevglTchClassroom
     * @return
     */
    private Map<String, Object> convertToSimpleClassroomData(TevglTchClassroom tevglTchClassroom) {
        // 图片处理
        if (StrUtils.isNotEmpty(tevglTchClassroom.getPic())) {
            tevglTchClassroom.setPic(uploadPathUtils.stitchingPath(tevglTchClassroom.getPic(), INDEX_ROOM));
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
     * 查看课堂基本信息
     *
     * @param ctId
     * @param loginUserId
     * @return
     */
    @Override
    public R viewClassroomBaseInfo(String ctId, String loginUserId) {
        Map<String, Object> info = tevglTchClassroomMapper.selectObjectMapById(ctId);
        if (info == null) {
            if (!EvglGlobal.ADMINISTRATOR.equals(loginUserId)) {
                // 删除用户的此课堂收藏数据
                //deleteFavorityRoom(ctId, loginUserId);
            }
            return R.error("无效的记录，无法访问");
        }
        info.put("qrCode", uploadPathUtils.stitchingPath(info.get("qrCode"), "14"));
        info.put("pic", uploadPathUtils.stitchingPath(info.get("pic"), "14"));
        return R.ok().put(Constant.R_DATA, info);
    }

    /**
     * 删除课堂
     *
     * @param ctId        课堂主键
     * @param loginUserId 当前登录用户
     * @return
     */
    @Override
    public R deleteClassroom(String ctId, String loginUserId) {
        return R.ok("暂不支持");
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
     * 将学员设为助教
     *
     * @param map
     * @return
     */
    @Override
    public R setTraineeToTeachingAssistant(Map<String, Object> map) {
        return null;
    }

    /**
     * 取消课堂的助教
     *
     * @param ctId
     * @param traineeId
     * @param loginUserId
     * @return
     */
    @Override
    public R cancelTeachingAssistant(String ctId, String traineeId, String loginUserId) {
        return null;
    }

    /**
     * <p>查询-无分页</p>
     *
     * @param map
     * @return
     * @author znn
     * @data 2020年6月10日
     */
    @Override
    public List<TevglTchClassroom> queryNoPage(Map<String, Object> map) {
        Query query = new Query(map);
        // 构建查询条件对象Query
        List<TevglTchClassroom> tevglTchClassroomList = tevglTchClassroomMapper.selectListByMap(query);
        convertUtil.convertUserId2RealName(tevglTchClassroomList, "createUserId", "updateUserId");
        convertUtil.convertUserId2RealName(tevglTchClassroomList, "createUserId", "updateUserId");
        if (tevglTchClassroomList.size() > 0) {
            tevglTchClassroomList.forEach(a -> {
                uploadPathUtils.stitchingPath(a.getPic(), INDEX_ROOM);
                // 拼接班级名称
                if ("Y".equals(map.get("withClassName"))) {
                    if (StrUtils.isNotEmpty(a.getClassName())) {
                        a.setName(a.getName() + "    " + a.getClassName());
                    }
                }
            });
        }
        return tevglTchClassroomList;
    }

    /**
     * 根据邀请码搜索课堂
     *
     * @param invitationCode
     * @return
     */
    @Override
    public R selectClassroomListData(String invitationCode) {
        return null;
    }

    /**
     * 收藏课堂
     *
     * @param ctId        课堂主键
     * @param loginUserId 当前登录用户
     * @return
     */
    @Override
    public R collect(String ctId, String loginUserId) {
        return null;
    }

    /**
     * 取消收藏
     *
     * @param ctId        课堂主键
     * @param loginUserId 当前登录用户
     * @return
     */
    @Override
    public R cancelCollect(String ctId, String loginUserId) {
        return null;
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
    public R setTop(String ctId, String value, String loginUserId) {
        return null;
    }

    /**
     * 开始上课
     *
     * @param ctId        课堂主键
     * @param loginUserId 当前登录用户
     * @return
     */
    @Override
    public R start(String ctId, String loginUserId) {
        return null;
    }

    /**
     * 结束上课
     *
     * @param ctId
     * @param loginUserId 登陆用户，注意：此值可能是网站段端老师，也可能是管理端，管理人员
     * @return
     */
    @Override
    public R end(String ctId, String loginUserId) {
        return null;
    }

    /**
     * 根据条件获取年份
     *
     * @param loginUserId 当前登录用户
     * @param type        类型值对应：1时查询加入的课堂（我听的课）的年份 2自己创建的课堂（我教的课）的年份，为空则查全部
     * @return
     */
    @Override
    public List<Map<String, Object>> getDates(String loginUserId, String type) {
        return null;
    }

    /**
     * @param ctId        课程id
     * @param loginUserId
     * @return
     * @author zhouyl加
     * @date 2021年2月20日
     * 查询当前登录用户创建的所有课堂信息
     */
    @Override
    public R queryClassroomList(String ctId, String loginUserId) {
        return null;
    }

    /**
     * @param jsonObject
     * @param ctId
     * @param loginUserId
     * @return
     * @author zhouyl加
     * @date 2021年2月20日
     * 一键加入课堂
     */
    @Override
    public R oneClickToJoinClassroom(JSONObject jsonObject, String ctId, String loginUserId) {
        return null;
    }

    /**
     * 根据条件查询群成员
     *
     * @param params
     * @return
     */
    @Override
    public R queryImGroupUserList(Map<String, Object> params) {
        return null;
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
        return null;
    }

    /**
     * 根据教学包id查询课堂
     *
     * @param pkgId
     * @return
     */
    @Override
    public TevglTchClassroom selectObjectByPkgId(Object pkgId) {
        return tevglTchClassroomMapper.selectObjectByPkgId(pkgId);
    }

    /**
     * 校验是否能进入课堂页面
     * 规则如下：
     * 学生身份时，如果课堂已结束，且被设为了不允许再进入
     * 老师身份时，账号被停用了的话，也不能进入课堂
     *
     * @param ctId
     * @param traineeId
     * @return
     */
    @Override
    public R verifyAccessToClass(String ctId, String traineeId) {
        return null;
    }

}
