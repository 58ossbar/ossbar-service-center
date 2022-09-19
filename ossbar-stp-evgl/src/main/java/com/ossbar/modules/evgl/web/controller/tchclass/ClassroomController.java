package com.ossbar.modules.evgl.web.controller.tchclass;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.DictService;
import com.ossbar.modules.evgl.book.api.TevglBookMajorService;
import com.ossbar.modules.evgl.book.api.TevglBookSubjectService;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomService;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomTraineeService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>我的课堂 </p>
 * <p>Company: 湖南创蓝信息科技有限公司</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * @author huj
 * @date 2019年8月19日
 */
@RestController
@RequestMapping("/classroom-api")
public class ClassroomController {

    @SuppressWarnings("unused")
    private Logger log = LoggerFactory.getLogger(getClass());

    @Reference(version = "1.0.0")
    private TevglBookMajorService tevglBookMajorService;
    @Reference(version = "1.0.0")
    private TevglBookSubjectService tevglBookSubjectService;
    @Reference(version = "1.0.0")
    private DictService dictService;
    @Reference(version = "1.0.0")
    private TevglTchClassroomService tevglTchClassroomService;
    @Reference(version = "1.0.0")
    private TevglTchClassroomTraineeService tevglTchClassroomTraineeService;

    /**
     * <p>我的课堂列表（我教的课、我听的课）</p>
     * @author huj
     * @data 2019年8月20日
     * @param params {"majorId": "", "subjectId": "", "pageNum": "", "pageSize": "", "name": "", "sort": "newest/hot"}
     * @param type 1加入的课堂（我听的课）2自己创建的课堂（我教的课），为空则查全部
     * @return
     */
    @GetMapping("listMyClassroom")
    @CheckSession
    public R listClassroom(HttpServletRequest request,
                           @RequestParam Map<String, Object> params, String type) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        String loginUserId = traineeInfo.getTraineeId();
        params.put("channel", "pc");
        return tevglTchClassroomService.listClassroom(params, loginUserId, type).put("type", type);
    }


    /**
     * 根据邀请码搜索课堂
     * @param request
     * @param invitationCode
     * @return
     */
    @GetMapping("/serach")
    @CheckSession
    public R serach(HttpServletRequest request, String invitationCode) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("invitationCode", invitationCode);
        return tevglTchClassroomService.listClassroom(params, traineeInfo.getTraineeId(), null);
    }

    /**
     * 上方区域课堂信息等其它信息
     * @param id 课堂主键
     * @return
     */
    @GetMapping("/viewClassroomInfo/{id}")
    @CheckSession
    public R viewClassroomInfo(HttpServletRequest request, @PathVariable("id") String id) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        return tevglTchClassroomService.viewClassroomInfo(id, traineeInfo.getTraineeId());
    }

    /**
     * 查看课堂信息（修改课堂信息时可用）
     * @param ctId 课堂主键
     * @return
     */
    @GetMapping("/viewClassroomBaseInfo")
    @CheckSession
    public R viewClassroomBaseInfo(HttpServletRequest request, String ctId) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        return tevglTchClassroomService.viewClassroomBaseInfo(ctId, traineeInfo.getTraineeId());
    }

    /**
     * 职业路径列表
     * @param param
     * @return
     */
    @GetMapping("/listMajors")
    public R listMajors(@RequestParam Map<String, Object> param) {
        return R.ok().put(Constant.R_DATA, tevglBookMajorService.selectListMapByMapForWeb(param));
    }


    /**
     * 课程列表
     * @param params {"majorId": ""}
     * @return
     */
    @GetMapping("/listSubjects")
    public R listSubjects(@RequestParam Map<String, Object> params) {
        return tevglBookSubjectService.listSelectSubject(params);
    }

    /**
     * 课堂状态
     * @param
     * @return
     */
    @GetMapping("/listClassroomState")
    public R listClassroomState() {
        List<Map<String,Object>> dictList = dictService.getDictList("classroomState");
        // 过滤掉已结束
        //List<Map<String,Object>> list = dictList.stream().filter(a -> !a.get("dictCode").equals("3")).collect(Collectors.toList());
        return R.ok().put(Constant.R_DATA, dictList);
    }

    /**
     * <p>职业路径下拉列表</p>
     * @author huj
     * @data 2019年8月20日
     * @return
     */
    @GetMapping("/getMajorList")
    public R getMajorList(@RequestParam Map<String, Object> map) {
        map.put("state", "Y"); // 状态(Y有效N无效)
        return R.ok().put(Constant.R_DATA, tevglBookMajorService.selectListByMap(map));
    }

    /**
     * <p>获取邀请码</p>
     * @author huj
     * @data 2019年8月20日
     * @return
     */
    @GetMapping("/getInvitationCode")
    public R getInvitationCode() {
        int num = (int) ((Math.random() * 9 + 1) * 100000);
        return R.ok().put(Constant.R_DATA, num);
    }

    /**
     * 加入课堂
     * @param ctId
     * @param invitationCode
     * @return
     */
    @RequestMapping("joinTheClassroom")
    @CheckSession
    public R joinTheClassroom(HttpServletRequest request, String ctId, String invitationCode) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("ctId",  ctId);
        map.put("invitationCode",  invitationCode);
        return tevglTchClassroomTraineeService.joinTheClassroom(map, traineeInfo.getTraineeId());
    }

    /**
     * 课堂置顶
     * @param ctId
     * @param value
     * @return
     */
    @PostMapping("/setTop")
    public R setTop(HttpServletRequest request, String ctId, String value) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        if ("Y".equals(value)) {
            return R.error("不支持");
        } else {
            return R.error("不支持");
        }
    }


    /**
     * 删除课堂
     * @param ctId
     * @param
     * @return
     */
    @PostMapping("/deleteClassroom")
    @CheckSession
    public R deleteClassroom(HttpServletRequest request, String ctId) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        return tevglTchClassroomService.deleteClassroom(ctId, traineeInfo.getTraineeId());
    }

    /**
     * 开始课堂
     * @param ctId
     * @param
     * @return
     */
    @RequestMapping("/start")
    @CheckSession
    public R start(HttpServletRequest request, String ctId) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        return tevglTchClassroomService.start(ctId, traineeInfo.getTraineeId());
    }

    /**
     * 结束课堂
     * @param ctId
     * @return
     */
    @RequestMapping("/end")
    @CheckSession
    public R end(HttpServletRequest request, String ctId) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        return tevglTchClassroomService.end(ctId, traineeInfo.getTraineeId());
    }

    /**
     * 获取年份
     * @param request
     * @param type type 类型值对应：1时查询加入的课堂（我听的课）的年份 2自己创建的课堂（我教的课）的年份，为空则查全部
     * @return
     */
    @RequestMapping("/getDates")
    @CheckSession
    public R getDates(HttpServletRequest request, String type) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        return R.ok().put(Constant.R_DATA, tevglTchClassroomService.getDates(traineeInfo.getTraineeId(), type));
    }
}
