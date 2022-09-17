package com.ossbar.modules.evgl.tch.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchTeacherMapper;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author huj
 * @create 2022-09-17 9:54
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
public class TevglTchClassroomServiceImpl implements TevglTchClassroomService {

    private static String INDEX_ROOM = "14";

    @Autowired
    private TevglTchClassroomMapper tevglTchClassroomMapper;
    @Autowired
    private TevglTchTeacherMapper tevglTchTeacherMapper;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private UploadFileUtils uploadPathUtils;

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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
