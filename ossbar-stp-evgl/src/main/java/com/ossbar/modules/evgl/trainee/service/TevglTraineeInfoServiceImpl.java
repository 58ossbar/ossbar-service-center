package com.ossbar.modules.evgl.trainee.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.*;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.book.domain.TevglBookMajor;
import com.ossbar.modules.evgl.book.persistence.TevglBookMajorMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomTrainee;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomTraineeMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClasstraineeMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchTeacherMapper;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeInfoMapper;
import com.ossbar.modules.evgl.trainee.vo.TraineeCharmInfoVO;
import com.ossbar.modules.evgl.trainee.vo.TraineeInfoVO;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
@RequestMapping("/trainee/tevgltraineeinfo")
public class TevglTraineeInfoServiceImpl implements TevglTraineeInfoService {

    @SuppressWarnings("unused")
    private Logger log = LoggerFactory.getLogger(TevglTraineeInfoServiceImpl.class);

    @Autowired
    private TevglTraineeInfoMapper tevglTraineeInfoMapper;
    @Autowired
    private TevglTchClasstraineeMapper tevglTchClasstraineeMapper;
    @Autowired
    private TevglTchClassroomMapper tevglTchClassroomMapper;
    @Autowired
    private TevglTchClassroomTraineeMapper tevglTchClassroomTraineeMapper;
    @Autowired
    private TevglPkgInfoMapper tevglPkgInfoMapper;
    @Autowired
    private TevglTchTeacherMapper tevglTchTeacherMapper;
    @Autowired
    private TevglBookMajorMapper tevglBookMajorMapper;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private ServiceLoginUtil serviceLoginUtil;
    @Autowired
    private UploadUtils uploadPathUtils;

    /**
     * 分页查询列表(返回List<Bean>)
     * @param map
     * @return
     */
    @Override
    @GetMapping("/query")
    @SentinelResource("/trainee/tevgltraineeinfo/query")
    @SysLog(value="分页查询列表(返回List<Bean>)")
    public R query(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<TevglTraineeInfo> tevglTraineeInfoList = tevglTraineeInfoMapper.selectListByMap(query);
        convertUtil.convertUserId2RealName(tevglTraineeInfoList, "createUserId", "updateUserId");
        PageUtils pageUtil = new PageUtils(tevglTraineeInfoList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 查询列表(返回List<Map<String, Object>)
     * @param map
     * @return R
     */
    @Override
    @GetMapping("/queryForMap")
    @SentinelResource("/trainee/tevgltraineeinfo/queryForMap")
    @SysLog(value="查询列表(返回List<Map<String, Object>)")
    public R queryForMap(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<Map<String, Object>> tevglTraineeInfoList = tevglTraineeInfoMapper.selectListMapByMap(query);
        convertUtil.convertUserId2RealName(tevglTraineeInfoList, "create_user_id", "update_user_id");
        PageUtils pageUtil = new PageUtils(tevglTraineeInfoList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    @Override
    @SysLog(value="新增")
    @PostMapping("save")
    @SentinelResource("/trainee/tevgltraineeinfo/save")
    public R save(TevglTraineeInfo tevglTraineeInfo) throws CreatorblueException {
        tevglTraineeInfo.setTraineeId(Identities.uuid());
        tevglTraineeInfo.setCreateUserId(serviceLoginUtil.getLoginUserId());
        tevglTraineeInfo.setCreateTime(DateUtils.getNowTimeStamp());
        //ValidatorUtils.check(tevglTraineeInfo);
        tevglTraineeInfoMapper.insert(tevglTraineeInfo);
        return R.ok();
    }


    @Override
    public R update(TevglTraineeInfo tevglTraineeInfo) throws CreatorblueException {
        return null;
    }

    /**
     * 单条删除
     * @param id
     * @throws CreatorblueException
     */
    @Override
    @SysLog(value="单条删除")
    @GetMapping("delete/{id}")
    @SentinelResource("/trainee/tevgltraineeinfo/delete")
    public R delete(@PathVariable("id") String id) throws CreatorblueException {
        tevglTraineeInfoMapper.delete(id);
        return R.ok();
    }

    /**
     * 批量删除
     * @param ids
     * @throws CreatorblueException
     */
    @SysLog(value="批量删除")
    @PostMapping("deleteBatch")
    @SentinelResource("/trainee/tevgltraineeinfo/deleteBatch")
    @Override
    public R deleteBatch(@RequestBody String[] ids) throws CreatorblueException {
        tevglTraineeInfoMapper.deleteBatch(ids);
        return R.ok();
    }

    @Override
    @SysLog(value="查看明细")
    @GetMapping("view/{id}")
    @SentinelResource("/trainee/tevgltraineeinfo/view")
    public R view(@PathVariable("id") String id) {
        TraineeInfoVO traineeInfoVO = this.selectTraineeVoById(id);
        return R.ok().put(Constant.R_DATA, traineeInfoVO);
    }


    /**
     * 根据唯一手机号码查询用户信息
     *
     * @param mobile
     * @return
     */
    @Override
    public TevglTraineeInfo selectByMobile(String mobile) {
        return tevglTraineeInfoMapper.selectByMobile(mobile);
    }

    /**
     * 根据条件查询记录
     *
     * @param params
     * @return
     */
    @Override
    public List<TevglTraineeInfo> selectListByMap(Map<String, Object> params) {
        return tevglTraineeInfoMapper.selectListByMap(params);
    }

    /**
     * 根据主键id查询
     *
     * @param id
     * @return
     */
    @Override
    public TevglTraineeInfo selectObjectById(String id) {
        return tevglTraineeInfoMapper.selectObjectById(id);
    }

    /**
     * 根据微信openid查询用户
     *
     * @param openid
     * @return
     */
    @Override
    public TevglTraineeInfo selectObjectByOpenId(String openid) {
        return tevglTraineeInfoMapper.selectObjectByOpenId(openid);
    }

    @Override
    public TraineeInfoVO selectTraineeVoById(Object id) {
        TraineeInfoVO traineeInfo = tevglTraineeInfoMapper.selectTraineeVoById(id);
        if (traineeInfo != null) {
            // 处理头像
            traineeInfo.setTraineePic(uploadPathUtils.stitchingPath(traineeInfo.getTraineePic(), "16"));
            // 处理所属班级
            List<String> classIdList = tevglTchClasstraineeMapper.selectClassIdListByTraineeId(traineeInfo.getTraineeId());
            if (classIdList != null && classIdList.size() > 0) {
                traineeInfo.setClassId(classIdList.stream().collect(Collectors.joining(",")));
            }
        }
        return traineeInfo;
    }

    /**
     * 个人信息（学生身份）
     *
     * @param traineeId
     * @return
     */
    @Override
    public R viewTraineeInfo(String traineeId) {
        if (StrUtils.isEmpty(traineeId)) {
            return R.error("参数traineeId为空");
        }
        TevglTraineeInfo traineeInfo = tevglTraineeInfoMapper.selectObjectById(traineeId);
        if (traineeInfo == null) {
            return R.error("用户信息已不存在");
        }
        // 最终返回数据
        Map<String, Object> result = new HashMap<String, Object>();
        // 基本信息
        Map<String, Object> basicInfo = toGetBasicTraineeInfo(traineeInfo);
        // 其它信息
        Map<String, Object> charmInfo = toGetCharmTraineeInfo(traineeInfo);
        // 返回数据
        result.put("basicInfo", basicInfo);
        result.put("charmInfo", charmInfo);
        return R.ok().put(Constant.R_DATA, result);
    }

    /**
     * 个人信息（教师身份）
     *
     * @param traineeId
     * @return
     */
    @Override
    public R viewTraineeInfoForManagementPanel(String traineeId) {
        if (StrUtils.isEmpty(traineeId)) {
            return R.error("参数traineeId为空");
        }
        TevglTraineeInfo traineeInfo = tevglTraineeInfoMapper.selectObjectById(traineeId);
        if (traineeInfo == null) {
            return R.error("用户信息已不存在");
        }
        // 最终返回数据
        Map<String, Object> result = new HashMap<String, Object>();
        // 基本信息
        result.put("basicInfo", toGetBasicInfo(traineeInfo));
        // 魅力信息
        result.put("charmInfo", toGetCharmInfo(traineeInfo));
        // 返回数据
        return R.ok().put(Constant.R_DATA, result);
    }

    /**
     * 基本信息
     * @param traineeInfo
     * @return
     */
    private Map<String, Object> toGetBasicInfo(TevglTraineeInfo traineeInfo){
        if (traineeInfo == null) {
            return null;
        }
        Map<String, Object> info = new HashMap<>();
        String traineeId = traineeInfo.getTraineeId();
        // 教师信息
        TevglTchTeacher teacherInfo = tevglTchTeacherMapper.selectObjectByTraineeId(traineeId);
        if (teacherInfo == null) {
            teacherInfo = new TevglTchTeacher();
            log.debug("用户 traineeId [" + traineeId +"], 不是教师了");
        }
        // 转换学员类型
        String traineeType = traineeInfo.getTraineeType();
        String traineeSex = traineeInfo.getTraineeSex();
        convertUtil.convertDict(traineeInfo, "traineeSex", "sex");
        convertUtil.convertDict(traineeInfo, "traineeType", "trainee_type");
        // 转换一级机构
        convertUtil.convertOrgId(teacherInfo, "orgId");
        // 转换二级机构
        convertUtil.convertOrgId(teacherInfo, "orgIdDepartment");
        // 主攻科目(所教专业)
        String mainSubjects = "";
        if (StrUtils.isNotEmpty(teacherInfo.getMajorId())) {
            String[] split = teacherInfo.getMajorId().split(",");
            List<String> majorIds = Stream.of(split).collect(Collectors.toList());
            if (majorIds != null && majorIds.size() > 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("majorIds", majorIds);
                List<TevglBookMajor> majorList = tevglBookMajorMapper.selectListByMap(map);
                mainSubjects = majorList.stream().map(a -> a.getMajorName()).collect(Collectors.joining(","));
            }
        }
        // 头像处理,没有教师照则用微信头像
        String teacherPic = teacherInfo.getTeacherPic();
        if (StrUtils.isNotEmpty(teacherPic)) {
            teacherPic = uploadPathUtils.stitchingPath(teacherPic, "7");
        } else {
            // 网络头像
            teacherPic = traineeInfo.getTraineeHead();
            // 如果学员表证件不为空, (教师表头像不开放给用户修改)
            if (StrUtils.isNotEmpty(traineeInfo.getTraineePic())) {
                teacherPic = uploadPathUtils.stitchingPath(traineeInfo.getTraineePic(), "16");
            } else { // 如果学生证也为空就传个默认头像
                info.put("teacherPic", "/uploads/defaulthead.png");
            }
        }
        // 填充信息
        info.put("traineeId", traineeId); // 主键(学员和教师表主键值需一致)
        info.put("traineePic", teacherPic); // 头像使用学员头像
        // 名称,修改新的时候会同步维护学员名称和教师名称
        //info.put("traineeName", StrUtils.isEmpty(traineeInfo.getTraineeName()) ? traineeInfo.getNickName() : traineeInfo.getTraineeName());
        info.put("traineeName", traineeInfo.getTraineeName());
        info.put("nickName", traineeInfo.getNickName());
        info.put("teacherErtificateNumber", teacherInfo.getTeacherErtificateNumber()); // 教师证
        info.put("teacherPostName", teacherInfo.getPostName()); // 职位
        info.put("mainSubjects", mainSubjects); // 主攻科目
        info.put("jobNumber", teacherInfo.getJobNumber()); // 工号/学号
        info.put("school", teacherInfo.getOrgId()); // 所在学校
        info.put("college", teacherInfo.getOrgIdDepartment()); // 所在院系
        info.put("mobile", traineeInfo.getMobile()); // 手机
        info.put("email", traineeInfo.getEmail()); // 邮箱
        info.put("empiricalValue", handleEmpiricalValue(traineeInfo)); // 经验值
        info.put("traineeType", traineeType); // 学员类型
        info.put("traineeTypeName", traineeInfo.getTraineeType()); // 学员类型名称
        info.put("traineeSex", traineeSex); // 性别
        info.put("traineeSexName", traineeInfo.getTraineeSex());
        info.put("traineeQq", traineeInfo.getTraineeQq());
        info.put("traineeEducation", traineeInfo.getTraineeEducation());
        return info;
    }

    private TraineeCharmInfoVO toGetCharmInfo(TevglTraineeInfo traineeInfo) {
        if (traineeInfo == null) {
            return null;
        }
        String traineeId = traineeInfo.getTraineeId();
        TraineeCharmInfoVO vo = new TraineeCharmInfoVO();
        Map<String, Object> params = new HashMap<>();
        params.put("createUserId", traineeId);
        List<TevglTchClassroom> classroomList = tevglTchClassroomMapper.selectListByMap(params);
        // 累计学生数
        Integer studentNum = tevglTchClassroomTraineeMapper.countStudentNumByTraineeId(traineeId);
        // 累计发布资源
        Integer resourceNum = tevglPkgInfoMapper.countPkgResCount(traineeId);
        // 累计开展活动
        Integer activityNum = tevglPkgInfoMapper.countPkgActCount(traineeId);
        // 返回给前端数据
        vo.setEmpiricalValue(handleEmpiricalValue(traineeInfo));
        vo.setCloudClassroomNum(classroomList == null ? 0 : classroomList.size());
        vo.setStudentNum(studentNum == null ? 0 : studentNum);
        vo.setResourceNum(resourceNum == null ? 0 : resourceNum);
        vo.setActivityNum(activityNum == null ? 0 : activityNum);
        vo.setBlogsNum(StrUtils.isNull(traineeInfo.getBlogsNum()) ? 0 : traineeInfo.getBlogsNum());
        vo.setCharmValue(0);
        return vo;
    }

    /**
     * 经验值
     * @param traineeInfo
     * @return
     */
    private Integer handleEmpiricalValue(TevglTraineeInfo traineeInfo) {
        Integer empiricalValue = tevglTraineeInfoMapper.countEmpiricalValue(traineeInfo.getTraineeId());
        // 如果两边经验值不一致了，修正一下
        if (empiricalValue != null && traineeInfo.getEmpiricalValue() != null && !empiricalValue.equals(traineeInfo.getEmpiricalValue())) {
            TevglTraineeInfo t = new TevglTraineeInfo();
            t.setTraineeId(traineeInfo.getTraineeId());
            t.setEmpiricalValue(empiricalValue);
            tevglTraineeInfoMapper.update(t);
        }
        return empiricalValue;
    }

    /**
     * 基本信息（学生）
     * @param traineeInfo
     * @return
     * @apiNote
     * {
     * traineeId学员主键，traineeName学员名称，traineeSex性别，mobile手机号码，className班级名称
     * traineeSchool就读院校，traineePic头像，studentNumber学号
     * }
     */
    private Map<String, Object> toGetBasicTraineeInfo(TevglTraineeInfo traineeInfo) {
        if (traineeInfo == null) {
            traineeInfo = new TevglTraineeInfo();
        }
        String traineeId = traineeInfo.getTraineeId();
        String traineeSex = traineeInfo.getTraineeSex();
        convertUtil.convertDict(traineeInfo, "traineeSex", "sex");
        Map<String, Object> info = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        // 班级名称
        params.put("traineeId", traineeId);
        List<Map<String, Object>> list = tevglTchClasstraineeMapper.selectListMapForWeb(params);
        if (list != null && list.size() > 0) {
            info.put("classId", list.get(0).get("classId"));
            info.put("className", list.get(0).get("className"));
        }
        // 头像处理
        String traineePic = traineeInfo.getTraineePic();
        if (StrUtils.isNotEmpty(traineePic)) {
            //traineePic = creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("16") + "/" + traineePic;
            traineePic = uploadPathUtils.stitchingPath(traineePic, "16");
        } else {
            traineePic = traineeInfo.getTraineeHead();
        }
        // 填充并返回信息
        info.put("traineeId", traineeInfo.getTraineeId());
        //info.put("traineeName", StrUtils.isEmpty(traineeInfo.getTraineeName()) ? traineeInfo.getNickName() : traineeInfo.getTraineeName());
        info.put("traineeName", traineeInfo.getTraineeName());
        info.put("nickName", traineeInfo.getNickName());
        info.put("traineeSex", traineeSex);
        info.put("traineeSexName", traineeInfo.getTraineeSex());
        info.put("mobile", traineeInfo.getMobile());
        info.put("traineePic", traineePic);
        info.put("traineeSchool", traineeInfo.getTraineeSchool());
        info.put("traineePic", traineePic);
        info.put("jobNumber", traineeInfo.getJobNumber());
        // TODO 该字段没有随之更新，替换为实时统计
        info.put("empiricalValue", handleEmpiricalValue(traineeInfo));
        info.put("email", traineeInfo.getEmail());
        info.put("traineeQq", traineeInfo.getTraineeQq());
        info.put("traineeEducation", traineeInfo.getTraineeEducation());
        return info;
    }

    /**
     * 其它信息（学生）
     * @param traineeInfo
     * @return
     */
    private Map<String, Object> toGetCharmTraineeInfo(TevglTraineeInfo traineeInfo) {
        Map<String, Object> info = new HashMap<>();
        // 参加云课堂数
        //String cloudClassroomNum = "0";
        info.clear();
        info.put("traineeId", traineeInfo.getTraineeId());
        info.put("state", "Y");
        List<TevglTchClassroomTrainee> list = tevglTchClassroomTraineeMapper.selectListByMap(info);
        long count = list.stream().map(a -> a.getCtId()).distinct().count();
        // 添加返回数据
        info.put("cloudClassroomNum", count);
        info.put("studentNum", 0);
        info.put("resourceNum", 0);
        info.put("activityNum", 0);
        info.put("blogsNum", StrUtils.isNull(traineeInfo.getBlogsNum()) ? 0 : traineeInfo.getBlogsNum());
        info.put("charmValue", 0);
        info.put("empiricalValue", handleEmpiricalValue(traineeInfo));
        return info;
    }
}
