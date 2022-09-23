package com.ossbar.modules.evgl.tch.service;

import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.medu.domain.TmeduApiToken;
import com.ossbar.modules.evgl.medu.persistence.TmeduApiTokenMapper;
import com.ossbar.modules.evgl.tch.api.TevglTchTeacherService;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.tch.dto.SaveTeacherDTO;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchTeacherMapper;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeInfoMapper;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.modules.sys.api.TsysUserinfoService;
import com.ossbar.modules.sys.domain.TsysUserinfo;
import com.ossbar.modules.sys.dto.user.SaveUserDTO;
import com.ossbar.modules.sys.persistence.TsysUserinfoMapper;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.BeanUtils;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p> Title: 教师管理</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/tch/tevgltchteacher")
public class TevglTchTeacherServiceImpl implements TevglTchTeacherService {

    private final static String INDEX_TEACHER = "7";

    @Autowired
    private TsysUserinfoService tsysUserinfoService;
    @Autowired
    private TsysAttachService tsysAttachService;

    @Autowired
    private TevglTchTeacherMapper tevglTchTeacherMapper;
    @Autowired
    private TsysUserinfoMapper tsysUserinfoMapper;
    @Autowired
    private TevglTchClassroomMapper tevglTchClassroomMapper;
    @Autowired
    private TmeduApiTokenMapper tmeduApiTokenMapper;
    @Autowired
    private TevglTraineeInfoMapper tevglTraineeInfoMapper;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private ServiceLoginUtil serviceLoginUtil;
    @Autowired
    private UploadFileUtils uploadPathUtils;

    @Override
    @SysLog(value="查询列表(返回List<Bean>)")
    public R query(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<TevglTchTeacher> tevglTchTeacherList = tevglTchTeacherMapper.selectListByMap(query);
        convertUtil.convertOrgId(tevglTchTeacherList, "orgId"); // 转换机构
        convertUtil.convertDict(tevglTchTeacherList, "state", "teacher_state"); // 教师状态(Y有效N无效)
        convertUtil.convertDict(tevglTchTeacherList, "showIndex", "state1"); // 是否推荐到首页(Y是N否)
        tevglTchTeacherList.forEach(a -> {
            a.setTeacherPic(uploadPathUtils.stitchingPath(a.getTeacherPic(), INDEX_TEACHER));
        });
        PageUtils pageUtil = new PageUtils(tevglTchTeacherList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 查询列表(返回List<Map<String, Object>)
     * @param map
     * @return R
     */
    @Override
    @SysLog(value="查询列表(返回List<Map<String, Object>)")
    public R queryForMap(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<Map<String, Object>> tevglTchTeacherList = tevglTchTeacherMapper.selectListMapByMap(query);
        PageUtils pageUtil = new PageUtils(tevglTchTeacherList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    @Override
    public R save(TevglTchTeacher tevglTchTeacher) {
        return R.error("该接口未实现！");
    }

    @Override
    public R update(TevglTchTeacher tevglTchTeacher) {
        return R.error("该接口未实现！");
    }

    @Override
    public R delete(String id) {
        return R.error("该接口未实现！");
    }

    @Override
    @SysLog(value="批量删除教师")
    @Transactional(rollbackFor = Exception.class)
    public R deleteBatch(String[] ids) throws CreatorblueException {
        if (ids == null || ids.length == 0) {
            return R.ok("没有需要删除的数据");
        }
        List<String> teacherIds = Arrays.asList(ids);
        // 处理外键约束
        List<String> teacherNames = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        for (String teacherId : teacherIds) {
            map.put("teacherId", teacherId);
            map.put("createUserId", teacherId);
            map.put("state", "Y");
            List<Map<String, Object>> list = tevglTchClassroomMapper.selectListMapByMap(map);
            if (list != null && list.size() > 0) {
                String teacherName = list.get(0).get("teacher_name").toString();
                if (!teacherNames.contains(teacherName)) {
                    teacherNames.add(teacherName);
                }
                continue;
            } else {
                tevglTchTeacherMapper.delete(teacherId);
            }
        }
        String msg = "删除成功";
        String str = teacherNames.stream().collect(Collectors.joining(","));
        if (!StrUtils.isEmpty(str)) {
            msg = "删除成功，有如下老师：" + str + "开设了课堂（已忽略删除）";
        }
        return R.ok(msg);
    }

    @Override
    public R view(String id) {
        TevglTchTeacher tevglTchTeacher = tevglTchTeacherMapper.selectObjectById(id);
        if (tevglTchTeacher == null) {
            return R.error("该教师已不存在，请重新获取数据");
        }
        tevglTchTeacher.setTeacherPic(uploadPathUtils.stitchingPath(tevglTchTeacher.getTeacherPic(), INDEX_TEACHER));
        return R.ok().put(Constant.R_DATA, tevglTchTeacher);
    }

    @Override
    public TevglTchTeacher selectObjectById(Object id) {
        return tevglTchTeacherMapper.selectObjectById(id);
    }

    @Override
    public TevglTchTeacher selectObjectByTraineeId(Object id) {
        return tevglTchTeacherMapper.selectObjectByTraineeId(id);
    }

    @Override
    public List<TevglTchTeacher> selectListByMap(Map<String, Object> map) {
        Query query = new Query(map);
        return tevglTchTeacherMapper.selectListByMap(query);
    }

    /**
     * 管理端新增教师
     *
     * @param dto
     * @return
     */
    @Override
    @SysLog(value="新增教师")
    @Transactional(rollbackFor = Exception.class)
    public R saveTeacherInfo(SaveTeacherDTO dto) throws CreatorblueException {
        TevglTchTeacher tevglTchTeacher = new TevglTchTeacher();
        BeanUtils.copyProperties(tevglTchTeacher, dto);
        // 查询数据库中是否已经存在正常的同名账号
        TsysUserinfo user = tsysUserinfoService.selectObjectByUserName(tevglTchTeacher.getUsername().trim());
        if (user != null && !"".equals(user.getUserType())) {
            throw new CreatorblueException(-1, "该教师账号已经存在");
        }
        TmeduApiToken token = tmeduApiTokenMapper.selectTokenByUserId(tevglTchTeacher.getTraineeId());
        // 如果该粉丝状态不是游客 注:用户类型，1、客户，2、系统用户，3、学员，4、教师。 注: 状态:报名、在册、退学、毕业、就职
        if (token != null && !"1".equals(token.getUserType())) {
            throw new CreatorblueException(-1, "所选关联粉丝ID已被标识为学员");
        } else { // 否则标记为教师
            TmeduApiToken tk = new TmeduApiToken();
            tk.setUserId(tevglTchTeacher.getTraineeId());
            tk.setUserType("4");
            tmeduApiTokenMapper.update(tk);
            TevglTraineeInfo t = new TevglTraineeInfo();
            t.setTraineeId(tevglTchTeacher.getTraineeId());
            t.setTraineeState("2");
            t.setTraineeType("4");
            tevglTraineeInfoMapper.update(t);
        }
        // 如果该账号存在，且被锁定，则启用
        if (user != null) {
            user.setUserType("1");
            tsysUserinfoMapper.update(user);
        } else { // 否则新建一个账号
            SaveUserDTO userDTO = new SaveUserDTO();
            userDTO.setUserId(tevglTchTeacher.getTraineeId());
            userDTO.setUsername(tevglTchTeacher.getUsername().trim());
            userDTO.setUserRealname(tevglTchTeacher.getTeacherName());
            // 默认教师角色 如有改动 自行替换
            userDTO.setRoleIdList(Arrays.asList("b0d61a55132540e6a1c10b88852a29a8"));
            // 默认主机构为教师所在的教育中心
            userDTO.setOrgIdList(Arrays.asList(tevglTchTeacher.getOrgId()));
            // 所属岗位
            if (StrUtils.isNotEmpty(tevglTchTeacher.getTeacherPost())) {
                userDTO.setPostIdList(Arrays.asList(tevglTchTeacher.getTeacherPost()));
            }
            tsysUserinfoService.save(userDTO);
        }
        tevglTchTeacher.setOrgId(StrUtils.isEmpty(tevglTchTeacher.getOrgId()) ? getDefaultOrgIdDepartment() : tevglTchTeacher.getOrgId());
        tevglTchTeacher.setOrgIdDepartment(getDefaultOrgIdDepartment());
        tevglTchTeacher.setTeacherId(tevglTchTeacher.getTraineeId());
        tevglTchTeacher.setCreateUserId(serviceLoginUtil.getLoginUserId());
        tevglTchTeacher.setCreateTime(DateUtils.getNowTimeStamp());
        tevglTchTeacher.setUpdateTime(DateUtils.getNowTimeStamp());
        tevglTchTeacher.setState("Y");
        tevglTchTeacherMapper.insert(tevglTchTeacher);
        // 如果上传了附件
        tsysAttachService.updateAttachForAdd(dto.getTeacherPicAttachId(), tevglTchTeacher.getTraineeId(), INDEX_TEACHER);
        return R.ok("教师新增成功");
    }

    /**
     * 默认的学校机构id
     * @return
     */
    private String getDefaultOrgIdDepartment() {
        return "10865";
    }

    /**
     * 管理端修改教师
     *
     * @param dto
     * @return
     */
    @Override
    public R updateTeacherInfo(SaveTeacherDTO dto) {
        return null;
    }

    @Override
    public R updateStateOrShowIndex(TevglTchTeacher tevglTchTeacher) {
        return null;
    }

    /**
     * 根据条件查询记录，返回List<T>，关联学员表
     *
     * @param map
     * @return
     */
    @Override
    public List<TevglTchTeacher> selectListByMapInnerJoinTraineeTable(Map<String, Object> map) {
        map.put("state", "Y");
        map.put("sidx", "t1.create_time");
        map.put("order", "desc");
        Query query = new Query(map);
        List<TevglTchTeacher> list = tevglTchTeacherMapper.selectListByMapInnerJoinTraineeTable(query);
        list.stream().forEach(a -> {
            a.setTeacherPic(uploadPathUtils.stitchingPath(a.getTeacherPic(), "7"));
        });
        return list;
    }
}
