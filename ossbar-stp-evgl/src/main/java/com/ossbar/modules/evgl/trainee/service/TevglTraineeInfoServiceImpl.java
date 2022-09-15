package com.ossbar.modules.evgl.trainee.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.*;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClasstraineeMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/trainee/tevgltraineeinfo")
public class TevglTraineeInfoServiceImpl implements TevglTraineeInfoService {

    @Autowired
    private TevglTraineeInfoMapper tevglTraineeInfoMapper;
    @Autowired
    private TevglTchClasstraineeMapper tevglTchClasstraineeMapper;

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
//        result.put("basicInfo", toGetBasicInfo(traineeInfo));
        // 魅力信息
        result.put("charmInfo", toGetCharmInfo(traineeInfo));
        // 返回数据
        return R.ok().put(Constant.R_DATA, result);
    }

    /**
     * 个人信息（学生身份）
     *
     * @param traineeId
     * @return
     */
    @Override
    public R viewTraineeInfo(String traineeId) {
        return null;
    }

    private TraineeCharmInfoVO toGetCharmInfo(TevglTraineeInfo traineeInfo) {
        if (traineeInfo == null) {
            return null;
        }
        String traineeId = traineeInfo.getTraineeId();
        TraineeCharmInfoVO vo = new TraineeCharmInfoVO();
        Map<String, Object> params = new HashMap<>();
        //
        params.put("createUserId", traineeId);
        // TODO
        /*
        List<TevglTchClassroom> classroomList = tevglTchClassroomMapper.selectListByMap(params);
        // 累计学生数
        Integer studentNum = tevglTchClassroomTraineeMapper.countStudentNumByTraineeId(traineeId);
        // 累计发布资源
        Integer resourceNum = tevglPkgInfoMapper.countPkgResCount(traineeId);
        // 累计开展活动
        Integer activityNum = tevglPkgInfoMapper.countPkgActCount(traineeId);
        // 返回给前端数据

        info.put("cloudClassroomNum", classroomList.size()); // 累计开设的课堂数
        info.put("studentNum", studentNum); // 累计学生数
        info.put("resourceNum", resourceNum == null ? 0 : resourceNum); // 累计发布资源数
        info.put("activityNum", activityNum == null ? 0 : activityNum); // 累计开展活动数
        info.put("blogsNum", StrUtils.isNull(traineeInfo.getBlogsNum()) ? 0 : traineeInfo.getBlogsNum()); // 个人总博客数
        // TODO 魅力值（等待公式计算）
        info.put("charmValue", 0);
        vo.setEmpiricalValue(handleEmpiricalValue(traineeInfo));
        vo.setCloudClassroomNum(classroomList == null ? 0 : classroomList.size());
        vo.setStudentNum(studentNum == null ? 0 : studentNum);
        vo.setResourceNum(resourceNum == null ? 0 : resourceNum);
        vo.setActivityNum();*/
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
}
