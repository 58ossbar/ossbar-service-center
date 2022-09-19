package com.ossbar.modules.evgl.book.service;

import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.modules.common.GlobalRoomPermission;
import com.ossbar.modules.evgl.book.api.BookService;
import com.ossbar.modules.evgl.book.api.TevglBookSubjectService;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.book.persistence.TevglBookChapterMapper;
import com.ossbar.modules.evgl.pkg.api.TevglPkgActivityRelationService;
import com.ossbar.modules.evgl.pkg.domain.TevglBookpkgTeamDetail;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
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
import java.util.stream.Collectors;

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
        // 返回结果
        List<Map<String,Object>> list = new ArrayList<>();
        // 合法性校验
        if (StrUtils.isEmpty(id) || StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(subjectId) || StrUtils.isEmpty(type)) {
            log.debug("必传参数为空");
            return list;
        }
        TevglBookSubject tevglBookSubject = tevglBookSubjectService.selectObjectById(subjectId);
        if (tevglBookSubject == null) {
            log.error("com.creatorblue.modules.evgl.book.service.listChaptersForRoomPage() 参数异常，没有根据subjectId找到教材");
            return list;
        }
        TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
        if (tevglPkgInfo == null) {
            log.debug("无效的教学包记录");
            return list;
        }
        boolean isCreator = StrUtils.isEmpty(tevglPkgInfo.getReceiverUserId()) && loginUserId.equals(tevglPkgInfo.getCreateUserId());
        boolean isReceiver = StrUtils.isNotEmpty(tevglPkgInfo.getReceiverUserId()) && loginUserId.equals(tevglPkgInfo.getReceiverUserId());
        // 查询条件
        Map<String, Object> params = new HashMap<>();
        // 查活动
        params.clear();
        params.put("pkgId", pkgId);
        List<Map<String, Object>> activityList = tevglPkgActivityRelationService.selectSimpleListMap(params);
        if ("subject".equals(type)) {
            // 获取具有层次结构的属性数据
            params.clear();
            params.put("subjectId", id);
            list = tevglBookSubjectService.getTree(id, pkgId);
            // 处理权限
            boolean hasNodePermission = handleHasPermissionForBuildSbForPkg(tevglPkgInfo, loginUserId);
            // 递归处理
            buildSbForPkg(list, activityList, hasNodePermission);
        } else if ("chapter".equals(type)) {
            List<Map<String,Object>> res = new ArrayList<>();
            // 处理权限
            //boolean hasNodePermission = handleHasPermissionForBuildSbForPkg(tevglPkgInfo, loginUserId);
            // 获取当前登录拥有的章节权限
            params.clear();
            params.put("userId", loginUserId);
            List<TevglBookpkgTeamDetail> detailList = tevglBookpkgTeamDetailMapper.selectListByMap(params);
            List<String> chapterIds = detailList.stream().map(a -> a.getChapterId()).collect(Collectors.toList());
            // 获取所有的章节数据
            params.put("subjectId", subjectId);
            params.put("pkgId", pkgId);
            params.put("sidx", "t1.order_num");
            params.put("order", "asc");
            List<Map<String, Object>> allNodeList = tevglBookChapterMapper.selectSimpleListMap(params);
            // 取出课程的直系节点
            List<Map<String, Object>> oneLevelNodeList = allNodeList.stream().filter(a -> a.get("parentId").equals(subjectId)).collect(Collectors.toList());
            if (oneLevelNodeList != null && oneLevelNodeList.size() > 0) {
                buildForPkg(allNodeList, id, serial, activityList, chapterIds, res);
                // 如果教学包没有被移交
                if (isCreator || isReceiver) {
                    setPermission(res, tevglPkgInfo);
                }
            }
            return res;
        }
        return list;
    }

    private void setPermission(List<Map<String, Object>> res, TevglPkgInfo tevglPkgInfo) {
        res.stream().forEach(node -> {
            node.put("isBookCreator", true);
            // 来源教学包处于发布状态，不允许新增修改等操作
            if (StrUtils.isNotEmpty(tevglPkgInfo.getRefPkgId())) {
                node.put("hasPermission", false);
                node.put("hasNodePermission", false);
                // 但是
                if (tevglPkgInfo.getPkgId().equals(tevglPkgInfo.getRefPkgId())) {
                    node.put("hasPermission", true);
                    node.put("hasNodePermission", true);
                }
            } else {
                node.put("hasPermission", true);
                node.put("hasNodePermission", true);
            }
        });
    }

    /**
     * 处理各种数据
     * @param allNodeList
     * @param parentId
     * @param serial 前端ztree传递的序号
     * @param activityList 当前教学包的相关活动
     * @param chapterIds 非教学包创建，且非教学包接管者时，当前用户拥有的章节权限
     */
    private List<Map<String,Object>> buildForPkg(List<Map<String, Object>> allNodeList, String parentId, String serial, List<Map<String, Object>> activityList, List<String> chapterIds, List<Map<String,Object>> res) {
        if (allNodeList == null || allNodeList.size() == 0 || StrUtils.isEmpty(parentId)) {
            return res;
        }
        List<Map<String,Object>> list = allNodeList.stream().filter(a -> a.get("parentId").equals(parentId)).collect(Collectors.toList());
        if (list != null && list.size() > 0) {
            // 遍历
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> chapterInfo = list.get(i);
                // 本章节是否有活动
                chapterInfo.put("hasActivity", activityList.stream().anyMatch(activityInfo -> !StrUtils.isNull(activityInfo.get("chapterId")) && activityInfo.get("chapterId").equals(chapterInfo.get("chapterId"))));
                // 返回标识
                chapterInfo.put("type", "chapter");
                // 处理序号
                if (serial != null) {
                    chapterInfo.put("serial", serial + "." + (i+1));
                    list.get(i).put("chapterName", serial + "." + (i+1) +" "+ chapterInfo.get("chapterName"));
                } else {
                    chapterInfo.put("serial", (i+1));
                    chapterInfo.put("chapterName", (i+1) +" "+ chapterInfo.get("chapterName"));
                }
                // 是否有操作权限
                if (chapterIds.contains(chapterInfo.get("chapterId"))) {
                    chapterInfo.put("hasPermission", true);
                    chapterInfo.put("hasNodePermission", true);
                } else {
                    chapterInfo.put("hasPermission", false);
                    chapterInfo.put("hasNodePermission", false);
                }
                if (!res.contains(chapterInfo)) {
                    res.add(chapterInfo);
                }
                // 递归
                List<Map<String,Object>> children = buildForPkg(allNodeList, chapterInfo.get("chapterId").toString(), serial, activityList, chapterIds, res);
                children.stream().forEach(a -> {
                    if (!res.contains(a)) {
                        res.add(a);
                    }
                });
            }
        }
        return res;
    }

    /**
     * 判断，在教学包页面中，是否有操作章节树节点的权限
     * @param tevglPkgInfo 必传参数
     * @param loginUserId 必传参数
     * @return 默认返回false，标识没有权限
     */
    private boolean handleHasPermissionForBuildSbForPkg(TevglPkgInfo tevglPkgInfo, String loginUserId) {
        if (tevglPkgInfo == null || StrUtils.isEmpty(loginUserId)) {
            return false;
        }
        // 如果教学包没有被移交，且登录用户是教学包创建者，直接返回有权限
        if (StrUtils.isEmpty(tevglPkgInfo.getReceiverUserId()) && loginUserId.equals(tevglPkgInfo.getCreateUserId())) {
            // 来源教学包处于发布状态，不允许新增修改等操作
            if (StrUtils.isNotEmpty(tevglPkgInfo.getRefPkgId())) {
                // 但是
                if (tevglPkgInfo.getPkgId().equals(tevglPkgInfo.getRefPkgId())) {
                    return true;
                }
                return false;
            }
            return true;
        }
        // 如果教学包被移交
        if (StrUtils.isNotEmpty(tevglPkgInfo.getReceiverUserId()) && loginUserId.equals(tevglPkgInfo.getReceiverUserId())) {
            // 来源教学包处于发布状态，不允许新增修改等操作
            if (StrUtils.isNotEmpty(tevglPkgInfo.getRefPkgId())) {
                // 但是
                if (tevglPkgInfo.getPkgId().equals(tevglPkgInfo.getRefPkgId())) {
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 教学包页面专用，处理节点的操作权限，如新增、重名命、追加节点、删除节点
     * @param list 具有树形结构的章节树
     * @param activityList 教学包的活动，用于处理是否活动标识
     * @param hasNodePermission 用于处理是否有操作节点的权限
     */
    private void buildSbForPkg(List<Map<String,Object>> list, List<Map<String, Object>> activityList, boolean hasNodePermission){
        if (list == null || list.size() == 0) {
            return;
        }
        // 遍历
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> chapterInfo = list.get(i);
            chapterInfo.put("isBookCreator", hasNodePermission);
            chapterInfo.put("hasPermission", hasNodePermission);
            chapterInfo.put("hasNodePermission", hasNodePermission);
            // 本章节是否有活动
            chapterInfo.put("hasActivity", activityList.stream().anyMatch(activityInfo -> !StrUtils.isNull(activityInfo.get("chapterId")) && activityInfo.get("chapterId").equals(chapterInfo.get("chapterId"))));
            // 返回标识
            list.get(i).put("type", "chapter");
            // 处理序号
			/*if (serial != null) {
			    list.get(i).put("serial", serial + "." + (i+1));
			    list.get(i).put("chapterName", serial + "." + (i+1) +" "+ list.get(i).get("chapterName"));
			} else {
			    list.get(i).put("serial", (i+1));
			    list.get(i).put("chapterName", (i+1) +" "+ list.get(i).get("chapterName"));
			}*/
            // 取出当前节点的子节点
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> nodeList = (List<Map<String, Object>>) list.get(i).get("children");
            if (nodeList != null && nodeList.size() > 0) {
                for(Map<String, Object> node : nodeList) {
                    node.put("isBookCreator", hasNodePermission);
                    node.put("hasPermission", hasNodePermission);
                    node.put("hasNodePermission", hasNodePermission);
                    // 递归
                    buildSbForPkg(nodeList, activityList, hasNodePermission);
                }
            }
        }
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
        if (StrUtils.isNotEmpty(ctId)) {
            String teacherKey = "room_book::" + ctId + "_teacher";
            String traineeKey = "room_book::" + ctId + "_trainee";
            stringRedisTemplate.delete(teacherKey);
            stringRedisTemplate.delete(traineeKey);
        }
    }
}
