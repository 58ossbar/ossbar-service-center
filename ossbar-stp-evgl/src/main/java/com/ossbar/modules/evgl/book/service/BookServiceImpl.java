package com.ossbar.modules.evgl.book.service;

import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.modules.common.GlobalRoomPermission;
import com.ossbar.modules.evgl.book.api.BookService;
import com.ossbar.modules.evgl.book.api.TevglBookSubjectService;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.book.persistence.TevglBookChapterMapper;
import com.ossbar.modules.evgl.pkg.api.TevglPkgActivityRelationService;
import com.ossbar.modules.evgl.pkg.persistence.TevglBookpkgTeamDetailMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomRoleprivilegeMapper;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(version = "1.0.0")
public class BookServiceImpl implements BookService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ServiceLoginUtil serviceLoginUtil;
    @Autowired
    private TevglBookChapterMapper tevglBookChapterMapper;
    @Autowired
    private TevglTchClassroomMapper tevglTchClassroomMapper;
    @Autowired
    private TevglBookSubjectService tevglBookSubjectService;
    @Autowired
    private TevglPkgActivityRelationService tevglPkgActivityRelationService;
    @Autowired
    private TevglTchClassroomRoleprivilegeMapper tevglTchClassroomRoleprivilegeMapper;
    @Autowired
    private TevglPkgInfoMapper tevglPkgInfoMapper;
    @Autowired
    private TevglBookpkgTeamDetailMapper tevglBookpkgTeamDetailMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 课堂页面，刷新ztree树专用方法
     *
     * @param loginUserId
     * @param id          主键id，可能是课程id、可能是章节id
     * @param pkgId
     * @param subjectId
     * @return
     */
    @Override
    public List<Map<String, Object>> listChaptersForRoomPage(String loginUserId, String id, String pkgId, String subjectId) {
        // 返回结果
        List<Map<String,Object>> list = new ArrayList<>();
        // 合法性校验
        if (StrUtils.isEmpty(id) || StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(subjectId)) {
            //log.debug("必传参数为空");
            return list;
        }
        TevglTchClassroom tevglTchClassroom =  tevglTchClassroomMapper.selectObjectByPkgId(pkgId);
        if (tevglTchClassroom == null) {
            log.error("com.creatorblue.modules.evgl.book.service.listChaptersForRoomPage() 参数异常，没有根据pkgId找到课堂");
            return list;
        }
        TevglBookSubject tevglBookSubject = tevglBookSubjectService.selectObjectById(subjectId);
        if (tevglBookSubject == null) {
            log.error("com.creatorblue.modules.evgl.book.service.listChaptersForRoomPage() 参数异常，没有根据subjectId找到教材");
            return list;
        }
        // 查询条件
        Map<String, Object> params = new HashMap<>();
        // 查活动
        params.clear();
        params.put("pkgId", pkgId);
        List<Map<String, Object>> activityList = tevglPkgActivityRelationService.selectSimpleListMap(params);
        // 获取此书
        params.clear();
        params.put("subjectId", id);
        // 获取具有层次结构的属性数据
        list = tevglBookSubjectService.getTree(id, pkgId);
        // 是否有【设置章节对学员是否可见】的权限
        boolean hasSetVisiblePermission = false;
        // 情况一、如果课堂没有被移交，且登录用户是课堂创建者
        boolean isCreator = StrUtils.isEmpty(tevglTchClassroom.getReceiverUserId()) && loginUserId.equals(tevglTchClassroom.getCreateUserId());
        if (isCreator) {
            hasSetVisiblePermission = true;
        }
        // 情况二、如果课堂被移交了，且登录用户是课堂的接收者才有权限
        boolean isReceiver = StrUtils.isNotEmpty(tevglTchClassroom.getReceiverUserId()) && loginUserId.equals(tevglTchClassroom.getReceiverUserId());
        if (isReceiver) {
            hasSetVisiblePermission = true;
        }
        // 情况三、当前登录用户是该课堂的助教，且被赋予该权限
        boolean isTeachingAssistant = loginUserId.equals(tevglTchClassroom.getTraineeId());
        if (isTeachingAssistant) {
            // 如果助教角色没有设置章节学员是否可见的权限
            Map<String, Object> map = new HashMap<>();
            map.put("ctId", tevglTchClassroom.getCtId());
            List<String> permissionList = tevglTchClassroomRoleprivilegeMapper.queryPermissionList(map);
            if (permissionList != null && permissionList.size() > 0) {
                hasSetVisiblePermission = permissionList.stream().anyMatch(a -> a.equals(GlobalRoomPermission.ROOM_PERM_SUBJECT_CHAPTERVISIBLE));
            }
        }
        // 递归处理
        buildSbV2(list, activityList, hasSetVisiblePermission);
        return list;
    }


    /**
     * 递归处理数据
     * @param list 必传参数，具有层次机构的树形章节数据
     * @param activityList 活动数据
     * @return
     */
    private void buildSbV2(List<Map<String,Object>> list, List<Map<String, Object>> activityList, boolean hasSetVisiblePermission) {
        if (list == null || list.size() == 0) {
            return;
        }
        // 遍历
        for (int i = 0; i < list.size(); i++) {
            // 返回标识
            list.get(i).put("type", "chapter");
            // 本章节是否有活动
            Map<String, Object> chapterInfo = list.get(i);
            chapterInfo.put("hasActivity", activityList.stream().anyMatch(activityInfo -> !StrUtils.isNull(activityInfo.get("chapterId")) && activityInfo.get("chapterId").equals(chapterInfo.get("chapterId"))));
            // 返回权限标识，课堂页面是不能新增修改删除等操作的，直接false
            list.get(i).put("hasPermission", false);
            list.get(i).put("hasNodePermission", false);
            // 是否有设置章节对学员可见的权限
            list.get(i).put("hasSetVisiblePermission", hasSetVisiblePermission);
            // 是否还有子节点
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> nodeList = (List<Map<String, Object>>) list.get(i).get("children");
            if (nodeList != null && nodeList.size() > 0) {
                // 递归
                buildSbV2(nodeList, activityList, hasSetVisiblePermission);
            }
        }
    }


    /**
     * 教学包页面，刷新ztree树专用方法
     *
     * @param loginUserId
     * @param id          主键id，可能是课程id、可能是章节id
     * @param serial      序号，前端传递过来的值，形如1.1.1
     * @param type        值可能为subject可能为chapter
     * @param pkgId
     * @param subjectId
     * @return
     */
    @Override
    public List<Map<String, Object>> listChaptersForPkgPage(String loginUserId, String id, String serial, String type, String pkgId, String subjectId) {
        return null;
    }

    /**
     * 删除缓存
     *
     * @param ctId
     * @apiNote room_book::4654d7d821f14c778d5a842e686b4f37_teacher
     * room_book::c1e0397b5737483c8915b958f81b46c9_trainee
     */
    @Override
    public void deleteRoomBookCacheable(String ctId) {

    }
}
