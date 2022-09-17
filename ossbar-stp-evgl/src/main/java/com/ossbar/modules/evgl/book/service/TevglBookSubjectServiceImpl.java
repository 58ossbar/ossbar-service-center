package com.ossbar.modules.evgl.book.service;

import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.*;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.GlobalRoomPermission;
import com.ossbar.modules.evgl.book.api.TevglBookSubjectService;
import com.ossbar.modules.evgl.book.domain.TevglBookMajor;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.book.domain.TevglBookSubperiod;
import com.ossbar.modules.evgl.book.persistence.TevglBookChapterMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookMajorMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubjectMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubperiodMapper;
import com.ossbar.modules.evgl.pkg.domain.TevglBookpkgTeam;
import com.ossbar.modules.evgl.pkg.domain.TevglBookpkgTeamDetail;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.pkg.persistence.TevglBookpkgTeamDetailMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglBookpkgTeamMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgActivityRelationMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomRoleprivilegeMapper;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p> Title: 课程教材</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
public class TevglBookSubjectServiceImpl implements TevglBookSubjectService {

    @SuppressWarnings("unused")
    private Logger log = LoggerFactory.getLogger(TevglBookSubjectServiceImpl.class);
    @Autowired
    private TevglBookSubjectMapper tevglBookSubjectMapper;
    @Autowired
    private TevglBookChapterMapper tevglBookChapterMapper;
    @Autowired
    private TevglBookSubperiodMapper tevglBookSubperiodMapper;
    @Autowired
    private TevglBookMajorMapper tevglBookMajorMapper;
    @Autowired
    private TsysAttachService tsysAttachService;
    @Autowired
    private TevglTchClassroomRoleprivilegeMapper tevglTchClassroomRoleprivilegeMapper;
    @Autowired
    private TevglTchClassroomMapper tevglTchClassroomMapper;
    @Autowired
    private TevglPkgInfoMapper tevglPkgInfoMapper;
    @Autowired
    private TevglBookpkgTeamMapper tevglBookpkgTeamMapper;
    @Autowired
    private TevglBookpkgTeamDetailMapper tevglBookpkgTeamDetailMapper;
    @Autowired
    private TevglPkgActivityRelationMapper tevglPkgActivityRelationMapper;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private ServiceLoginUtil serviceLoginUtil;
    @Autowired
    private DictUtil dictUtil;
    @Autowired
    private UploadFileUtils uploadFileUtils;

    /**
     * 根据条件查询记录
     *
     * @param map
     * @return
     */
    @Override
    @SysLog(value="查询列表(返回List<Bean>)")
    public R query(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<TevglBookSubject> tevglBookSubjectList = tevglBookSubjectMapper.selectListByMap(query);
        convertUtil.convertUserId2RealName(tevglBookSubjectList, "createUserId", "updateUserId");
        convertUtil.convertOrgId(tevglBookSubjectList, "orgId");
        if (tevglBookSubjectList.size() > 0) {
            tevglBookSubjectList.forEach(subject -> {
                subject.setSubjectLogo(uploadFileUtils.stitchingPath(subject.getSubjectLogo(), "10"));
            });
        }
        PageUtils pageUtil = new PageUtils(tevglBookSubjectList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 根据条件查询记录
     *
     * @param map
     * @return
     */
    @Override
    @SysLog(value="查询列表(返回List<Map<String, Object>)")
    public R queryForMap(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<Map<String, Object>> tevglBookSubjectList = tevglBookSubjectMapper.selectListMapByMap(query);
        convertUtil.convertUserId2RealName(tevglBookSubjectList, "create_user_id", "update_user_id");
        PageUtils pageUtil = new PageUtils(tevglBookSubjectList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 新增
     *
     * @param tevglBookSubject
     * @return
     */
    @Override
    @SysLog(value="新增")
    public R save(TevglBookSubject tevglBookSubject) throws CreatorblueException {
        if (StrUtils.isEmpty(tevglBookSubject.getMajorId())) {
            return R.error("职业课程路径不能为空！");
        }
        String uuid = Identities.uuid();
        tevglBookSubject.setSubjectId(uuid);
        tevglBookSubject.setCreateUserId(serviceLoginUtil.getLoginUserId());
        tevglBookSubject.setCreateTime(DateUtils.getNowTimeStamp());
        tevglBookSubjectMapper.insert(tevglBookSubject);
        // 如果上传了资源文件
        String attachId = tevglBookSubject.getAttachId();
        if (attachId != null && !"undefined".equals(attachId) && !"".equals(attachId)) {
            tsysAttachService.updateAttachForAdd(attachId, uuid,"10");
        }
        // 保存职业路径和课程的关系
        String majorId = tevglBookSubject.getMajorId(); // 前端，多个的话，会用英文逗号分隔
        if (StrUtils.isNotEmpty(majorId)) {
            String[] majorIdArray = majorId.split(",");
            for (int i = 0; i < majorIdArray.length; i++) {
                TevglBookSubperiod t = new TevglBookSubperiod();
                t.setSubperiodId(Identities.uuid());
                t.setSubjectId(tevglBookSubject.getSubjectId());
                t.setMajorId(majorIdArray[i]);
                t.setTerm(StrUtils.isEmpty(tevglBookSubject.getTerm()) ? "1" : tevglBookSubject.getTerm()); // 1为第一学期
                t.setSubjectProperty(StrUtils.isEmpty(tevglBookSubject.getSubjectProperty()) ? "1" : tevglBookSubject.getSubjectProperty()); // 1为必修
                tevglBookSubperiodMapper.insert(t);
            }
        }
        return R.ok().put("subjectId", tevglBookSubject.getSubjectId());
    }

    /**
     * 修改
     *
     * @param tevglBookSubject
     * @return
     */
    @Override
    @SysLog(value="修改")
    public R update(TevglBookSubject tevglBookSubject) throws CreatorblueException {
        if (StrUtils.isEmpty(tevglBookSubject.getMajorId())) {
            return R.error("职业课程路径不能为空！");
        }
        tevglBookSubject.setUpdateUserId(serviceLoginUtil.getLoginUserId());
        tevglBookSubject.setUpdateTime(DateUtils.getNowTimeStamp());
        //ValidatorUtils.check(tevglBookSubject);
        tevglBookSubjectMapper.update(tevglBookSubject);
        // 如果上传了资源文件
        String attachId = tevglBookSubject.getAttachId();
        if (attachId != null && !"undefined".equals(attachId) && !"".equals(attachId)) {
            tsysAttachService.updateAttachForEdit(attachId, tevglBookSubject.getAttachId(),"10");
        }
        // 保存职业路径和课程的关系
        String majorId = tevglBookSubject.getMajorId(); // 前端，多个的话，会用英文逗号分隔
        if (StrUtils.isNotEmpty(majorId)) {
            // 先删除课程教材与职业课程路径的关系
            Map<String, Object> map = new HashMap<>();
            map.put("subjectId", tevglBookSubject.getSubjectId());
            List<TevglBookSubperiod> list = tevglBookSubperiodMapper.selectListByMap(map);
            if (list.size() > 0) {
                list.forEach(a -> {
                    tevglBookSubperiodMapper.delete(a.getSubperiodId());
                });
            }
            // 再保存
            String[] majorIdArray = majorId.split(",");
            for (int i = 0; i < majorIdArray.length; i++) {
                TevglBookSubperiod t = new TevglBookSubperiod();
                t.setSubperiodId(Identities.uuid());
                t.setSubjectId(tevglBookSubject.getSubjectId());
                t.setMajorId(majorIdArray[i]);
                t.setTerm(StrUtils.isEmpty(tevglBookSubject.getTerm()) ? "1" : tevglBookSubject.getTerm()); // 1为第一学期
                t.setSubjectProperty(StrUtils.isEmpty(tevglBookSubject.getSubjectProperty()) ? "1" : tevglBookSubject.getSubjectProperty()); // 1为必修
                tevglBookSubperiodMapper.insert(t);
            }
        }
        return R.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    @SysLog(value="单条删除")
    public R delete(String id) throws CreatorblueException {
        tevglBookSubjectMapper.delete(id);
        return R.ok();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public R deleteBatch(String[] ids) throws CreatorblueException {
        tevglBookSubjectMapper.deleteBatch(ids);
        return R.ok();
    }

    /**
     * 查看明细
     *
     * @param id
     * @return
     */
    @Override
    @SysLog(value="查看明细")
    public R view(String id) {
        // 课程教材
        Map<String, Object> data = new HashMap<>();
        TevglBookSubject subject = tevglBookSubjectMapper.selectObjectById(id);
        if (subject == null) {
            return R.ok().put(Constant.R_DATA, new TevglBookSubject());
        }
        subject.setSubjectLogo(uploadFileUtils.stitchingPath(subject.getSubjectLogo(), "10"));
        data.put("subject", subject);
        // 课程教材与职业路径的关系
        Map<String, Object> map = new HashMap<>();
        map.put("subjectId", subject.getSubjectId());
        List<TevglBookSubperiod> list = tevglBookSubperiodMapper.selectListByMap(map);
        List<String> subperiodIds = new ArrayList<>();
        if (list.size() > 0) {
            list.forEach(a -> {
                subperiodIds.add(a.getSubperiodId());
            });
        }
        map.clear();
        map.put("subperiodIds", subperiodIds);
        List<TevglBookMajor> majorList = tevglBookMajorMapper.selectListByMap2(map);
        data.put("majorList", majorList);
        return R.ok().put(Constant.R_DATA, data);
    }


    /**
     * 课程下拉列表，注意此方法只会查询subject_ref为空的记录
     *
     * @param params
     * @return
     */
    @Override
    public R listSelectSubject(Map<String, Object> params) {
        List<TevglBookSubject> tevglBookSubjectList = new ArrayList<TevglBookSubject>();
        params.put("sidx", "t1.create_time");
        params.put("order", "desc");
        Query query = new Query(params);
        if (params.get("majorIds") != null && !"".equals(params.get("majorIds"))) {
            String ids = (String) params.get("majorIds");
            List<String> list = new ArrayList<>();
            String[] majorIds = ids.split(",");
            for (String string : majorIds) {
                list.add(string);
            }
            params.put("majorIds", list);
        }
        tevglBookSubjectList = tevglBookSubjectMapper.selectListByMapForCommon(query);
        if (tevglBookSubjectList != null && tevglBookSubjectList.size() > 0) {
            tevglBookSubjectList.forEach(subject -> {
                subject.setSubjectLogo(uploadFileUtils.stitchingPath(subject.getSubjectLogo(), "10")); // 图片处理
            });
        }
        List<Map<String,Object>> collect = tevglBookSubjectList.stream().map(this::converToSubjectMap).collect(Collectors.toList());
        return R.ok().put(Constant.R_DATA, collect);
    }

    /**
     * 取部分属性，若需要额外，自行补充
     * @param tevglBookSubject
     * @return
     */
    private Map<String, Object> converToSubjectMap(TevglBookSubject tevglBookSubject){
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("subjectId", tevglBookSubject.getSubjectId());
        info.put("subjectName", tevglBookSubject.getSubjectName());
        info.put("subjectLogo", tevglBookSubject.getSubjectLogo());
        return info;
    }

    /**
     * 获取一本书（章节树）（课堂页面专用）（优化版）
     *
     * @param ctId
     * @param pkgId
     * @param subjectId
     * @param chapterName
     * @param loginUserId
     * @param queryForWx
     * @param identity    身份标识，用于区别缓存，值：teacher、trainee
     * @return
     */
    @Override
    @SysLog(value="课堂页面获取教材章节树")
    @Cacheable(value = "room_book", key = "#ctId+'::'+#identity")
    public R getBookForRoomPage(String ctId, String pkgId, String subjectId, String chapterName, String loginUserId, boolean queryForWx, String identity) {
        // 合法性校验
        if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(subjectId) || StrUtils.isEmpty(loginUserId)) {
            return R.error("必传参数为空");
        }
        TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
        if (tevglTchClassroom == null) {
            return R.error("无效的课堂");
        }
        if (!pkgId.equals(tevglTchClassroom.getPkgId())) {
            return R.error("参数错误");
        }
        TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
        if (tevglPkgInfo == null) {
            return R.error("无效的教学包");
        }
        // 更换教学包版本后，前端可能传的值不对，直接取教学包对应的教材
        //TevglBookSubject tevglBookSubject = tevglBookSubjectMapper.selectObjectById(subjectId);
        TevglBookSubject tevglBookSubject = tevglBookSubjectMapper.selectObjectById(tevglPkgInfo.getSubjectId());
        if (tevglBookSubject == null) {
            return R.error("无效的教材");
        }
        // 调用方法，取教材部分属性
        Map<String, Object> subjectInfo = getSimpleSubjectInfo(tevglBookSubject);
        // 查询章节
        List<Map<String, Object>> chapterList = selectSimpleChapterListMap(subjectId, true, pkgId);
        chapterList.stream().forEach(item -> {
            item.put("ctId", tevglTchClassroom.getCtId());
        });
        subjectInfo.put("chapterNum", chapterList.size());
        // 调用方法，处理节点的操作权限，以及节点下是否有活动
        doHandleNodeFlagsForRoom(tevglTchClassroom, tevglPkgInfo, subjectInfo, loginUserId, chapterList, queryForWx);
        // 调用方法，递归构建
        //boolean filterFlag = getFilterValue(tevglTchClassroom, loginUserId); // 是否过滤掉学员不可见的章节
        boolean filterFlag = getFilterValueV2(tevglTchClassroom, loginUserId); // 是否过滤掉学员不可见的章节
        List<Map<String, Object>> children = buildBookNew(chapterList, 0, subjectInfo.get("subjectId").toString(), filterFlag);
        // 调用方法，处理序号
        handleSortNum(children);
        if (queryForWx) { // 小程序专用
            // 再次处理
            List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
            buildLine(children, resultList, chapterName);
            subjectInfo.put("children", resultList);
        } else {
            // 是否节点过滤
            if (StrUtils.isNotEmpty(chapterName)) {
                children = filter(children, chapterName);
            }
            subjectInfo.put("children", children == null ? new ArrayList<>() : children);
        }
        // 此外，浏览量+1
        TevglBookSubject t = new TevglBookSubject();
        t.setSubjectId(subjectId);
        t.setViewNum(1);
        tevglBookSubjectMapper.updateNum(t);
        return R.ok().put(Constant.R_DATA, subjectInfo);
    }

    /**
     * 返回true则需要过滤掉学员不可见的章节（课堂如果被移交了的话，原创建者也需要过滤掉数据，而接收者不需要过滤掉数据）
     * @param tevglTchClassroom
     * @param loginUserId
     * @return
     */
    private boolean getFilterValueV2(TevglTchClassroom tevglTchClassroom, String loginUserId) {
        if (tevglTchClassroom == null || StrUtils.isEmpty(loginUserId)) {
            return true;
        }
        // 如果是课堂助教者
        boolean isTeachingAssistant = StrUtils.isNotEmpty(tevglTchClassroom.getTraineeId()) && loginUserId.equals(tevglTchClassroom.getTraineeId());
        if (isTeachingAssistant) {
            // 如果助教角色没有设置章节学员是否可见的权限
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ctId", tevglTchClassroom.getCtId());
            List<String> list = tevglTchClassroomRoleprivilegeMapper.queryPermissionList(map);
            if (list == null || list.size() == 0) {
                return true;
            }
            boolean anyMatch = list.stream().anyMatch(a -> a.equals(GlobalRoomPermission.ROOM_PERM_SUBJECT_CHAPTERVISIBLE));
            if (anyMatch) {
                return false;
            } else {
                return true;
            }
        }
        // 如果课堂没被移交
        if (StrUtils.isEmpty(tevglTchClassroom.getReceiverUserId())) {
            // 如果是课堂创建者，不需要过滤
            if (loginUserId.equals(tevglTchClassroom.getCreateUserId())) {
                return false;
            }
        } else {
            // 如果是课堂创建者，但此课堂已经被移交了，需要过滤
            if (loginUserId.equals(tevglTchClassroom.getCreateUserId())) {
                return true;
            }
            if (loginUserId.equals(tevglTchClassroom.getReceiverUserId())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 处理节点的操作权限，以及节点下是否有活动（课堂页面中专用）
     * @param tevglTchClassroom
     * @param tevglPkgInfo
     * @param subjectInfo
     * @param loginUserId
     * @param chapterList
     */
    private boolean doHandleNodeFlagsForRoom(TevglTchClassroom tevglTchClassroom, TevglPkgInfo tevglPkgInfo, Map<String, Object> subjectInfo
            , String loginUserId, List<Map<String, Object>> chapterList, boolean queryForWx) {
        // 当前用户是否为此课堂的创建者
        boolean isRoomCreator = loginUserId.equals(tevglTchClassroom.getCreateUserId());
        // 当前用户是否为此课堂的接收者
        boolean hasRoomReceiver = StrUtils.isNotEmpty(tevglTchClassroom.getReceiverUserId());
        boolean isRoomReceiver = StrUtils.isNotEmpty(tevglTchClassroom.getReceiverUserId()) && loginUserId.equals(tevglTchClassroom.getReceiverUserId());
        // 当前登录用户是否为课堂的助教
        boolean isTeachingAssistant = StrUtils.isNotEmpty(tevglTchClassroom.getTraineeId()) && loginUserId.equals(tevglTchClassroom.getTraineeId());
        String pkgId = tevglPkgInfo.getPkgId();
        // 查询教学包的活动
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pkgId", pkgId);
        List<Map<String, Object>> activityList = tevglPkgActivityRelationMapper.selectSimpleListMap(params);
        // 如果这个教学包已处于发布状态，则不允许新增修改删除等操作
        subjectInfo.put("hasPermission", false);
        if (isRoomCreator || isRoomReceiver) {
            // 是否拥有设置可见的权限
            subjectInfo.put("hasSetVisiblePermission", true);
            chapterList.stream().forEach(chapter -> {
                chapter.put("hasPermission", false);
                chapter.put("hasSetVisiblePermission", true);
            });
            // 如果是接收者，那么表示这个课堂已经移交给了接收者，那么原来本身的课堂创建者，需要丧失控制权
            if (isRoomCreator && hasRoomReceiver) {
                subjectInfo.put("hasSetVisiblePermission", false);
                chapterList.stream().forEach(chapter -> {
                    chapter.put("hasPermission", false);
                    chapter.put("hasSetVisiblePermission", false);
                });
            }
        } else if (isTeachingAssistant) {
            boolean has = false;
            // 如果助教角色没有设置章节学员是否可见的权限
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ctId", tevglTchClassroom.getCtId());
            List<String> list = tevglTchClassroomRoleprivilegeMapper.queryPermissionList(map);
            if (list == null || list.size() == 0) {
                has = false;
            } else {
                has = list.stream().anyMatch(a -> a.equals(GlobalRoomPermission.ROOM_PERM_SUBJECT_CHAPTERVISIBLE));
            }
            // 如果为助教，且课堂创建者，设置助教有此权限
            if (has) {
                subjectInfo.put("hasSetVisiblePermission", true);
                chapterList.stream().forEach(chapter -> {
                    chapter.put("hasPermission", false);
                    chapter.put("hasSetVisiblePermission", true);
                });
            } else {
                subjectInfo.put("hasSetVisiblePermission", false);
                // 否则认为没有操作权限
                chapterList.stream().forEach(chapter -> {
                    chapter.put("hasPermission", false);
                    chapter.put("hasSetVisiblePermission", false);
                    boolean hasActivity = activityList.stream()
                            .anyMatch(activityInfo ->
                                    !StrUtils.isNull(activityInfo.get("chapterId"))
                                            && activityInfo.get("chapterId").equals(chapter.get("chapterId"))
                                            && Arrays.asList("1", "2").contains(activityInfo.get("activityState")));
                    chapter.put("hasActivity", hasActivity);
                });
            }

        } else {
            subjectInfo.put("hasPermission", false);
            subjectInfo.put("hasSetVisiblePermission", false);
            chapterList.stream().forEach(chapter -> {
                chapter.put("hasPermission", false);
                chapter.put("hasSetVisiblePermission", false);
                // 学生身份只能看到进行中、已结束的活动
                boolean hasActivity = activityList.stream()
                        .anyMatch(activityInfo ->
                                !StrUtils.isNull(activityInfo.get("chapterId"))
                                        && activityInfo.get("chapterId").equals(chapter.get("chapterId"))
                                        && Arrays.asList("1", "2").contains(activityInfo.get("activityState")));
                chapter.put("hasActivity", hasActivity);
            });
        }
        return true;
    }

    /**
     * 获取一本书（章节树）（教学包页面专用）（优化版）
     *
     * @param pkgId
     * @param subjectId
     * @param chapterName
     * @param loginUserId
     * @return
     */
    @Override
    public R getBookForPkgPage(String pkgId, String subjectId, String chapterName, String loginUserId) {
        log.debug("            教学页面中查询章节树形数据              ");
        // 合法性校验
        if (StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(subjectId) || StrUtils.isEmpty(loginUserId)) {
            return R.error("必传参数为空");
        }
        TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
        if (tevglPkgInfo == null) {
            return R.error("无效的教学包");
        }
        TevglBookSubject tevglBookSubject = tevglBookSubjectMapper.selectObjectById(subjectId);
        if (tevglBookSubject == null) {
            return R.error("无效的教材");
        }
        // 调用方法，取教材部分属性
        Map<String, Object> subjectInfo = getSimpleSubjectInfo(tevglBookSubject);
        subjectInfo.put("pkgLimit", tevglPkgInfo.getPkgLimit());
        // 处理是否有【授权】的按钮
        hasAuthButton(tevglPkgInfo, subjectInfo, loginUserId);
        // 查询章节
        List<Map<String, Object>> chapterList = selectSimpleChapterListMap(subjectId, true, pkgId);
        subjectInfo.put("chapterNum", chapterList.size());
        // 调用方法，处理节点的操作权限，以及节点下是否有活动
        boolean flag = doHandleNodeFlagsForPkg(tevglPkgInfo, subjectInfo, loginUserId, chapterList);
        if (!flag) {
            log.info("当前用户[{}], 没有教学包[{}]的权限，无法查看章节树", loginUserId, pkgId);
            return R.ok().put(Constant.R_DATA, new ArrayList<>());
        }
        // 调用方法，递归构建
        List<Map<String, Object>> children = buildBookNew(chapterList, 0, subjectInfo.get("subjectId").toString(), false);
        // 调用方法，处理序号
        handleSortNum(children);
        subjectInfo.put("children", children);
        // 此外，浏览量+1
        TevglBookSubject t = new TevglBookSubject();
        t.setSubjectId(subjectId);
        t.setViewNum(1);
        tevglBookSubjectMapper.updateNum(t);
        return R.ok().put(Constant.R_DATA, subjectInfo);
    }

    private void hasAuthButton(TevglPkgInfo tevglPkgInfo, Map<String, Object> subjectInfo, String loginUserId) {
        subjectInfo.put("hasAuthBtn", false);
        if (tevglPkgInfo == null || StrUtils.isEmpty(loginUserId)) {
            return;
        }
        boolean isCreator = StrUtils.isEmpty(tevglPkgInfo.getReceiverUserId()) && loginUserId.equals(tevglPkgInfo.getCreateUserId());
        boolean isReceiver = StrUtils.isNotEmpty(tevglPkgInfo.getReceiverUserId()) && loginUserId.equals(tevglPkgInfo.getReceiverUserId());
        // 免费的理应不需要授权
        if ("2".equals(tevglPkgInfo.getPkgLimit())) {
            subjectInfo.put("hasAuthBtn", false);
            return;
        }
        // 如果是此教学包的创建，有权限
        if (isCreator || isReceiver) {
            subjectInfo.put("hasAuthBtn", true);
            return;
        }
    }

    /**
     * 处理节点的操作权限，以及节点下是否有活动（教学包页面中专用）
     * @param tevglPkgInfo
     * @param subjectInfo
     * @param loginUserId
     * @param chapterList
     * @return
     */
    private boolean doHandleNodeFlagsForPkg(TevglPkgInfo tevglPkgInfo, Map<String, Object> subjectInfo, String loginUserId, List<Map<String, Object>> chapterList) {
        // 默认没有权限
        subjectInfo.put("hasPermission", false);
        subjectInfo.put("isBookCreator", false);
        String pkgId = tevglPkgInfo.getPkgId();
        // 查询教学包的活动
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pkgId", pkgId);
        List<Map<String, Object>> activityList = tevglPkgActivityRelationMapper.selectSimpleListMap(params);
        boolean isCreator = StrUtils.isEmpty(tevglPkgInfo.getReceiverUserId()) && loginUserId.equals(tevglPkgInfo.getCreateUserId());
        boolean isReceiver = StrUtils.isNotEmpty(tevglPkgInfo.getReceiverUserId()) && loginUserId.equals(tevglPkgInfo.getReceiverUserId());
        List<TevglBookpkgTeam> tevglBookpkgTeamList = selectBookpkgTeam(pkgId, loginUserId);
        boolean isTogether = tevglBookpkgTeamList != null && tevglBookpkgTeamList.size() > 0;
        boolean isBookCreator = isCreator || isReceiver;
        // 不满足条件，直接返回
        if (!isCreator && !isReceiver && !isTogether) {
            return false;
        }
        // 如果这个教学包已处于发布状态，则不允许新增修改删除等操作
        if ("Y".equals(tevglPkgInfo.getReleaseStatus())) {
            subjectInfo.put("isBookCreator", isBookCreator);
            chapterList.stream().forEach(chapter -> {
                chapter.put("isBookCreator", isBookCreator);
                // 本节点是否允许新增修改删除等操作
                chapter.put("hasPermission", false);
                // 本章节是否有活动
                boolean hasActivity = activityList.stream().anyMatch(activityInfo -> !StrUtils.isNull(activityInfo.get("chapterId")) && activityInfo.get("chapterId").equals(chapter.get("chapterId")));
                chapter.put("hasActivity", hasActivity);
            });
            return true;
        } else {
            if (isCreator || isReceiver) {
                subjectInfo.put("isBookCreator", true);
                subjectInfo.put("hasPermission", true);
                chapterList.stream().forEach(chapter -> {
                    chapter.put("isBookCreator", true);
                    // 本节点是否允许新增修改删除等操作
                    chapter.put("hasPermission", true);
                    // 本章节是否有活动
                    boolean hasActivity = activityList.stream().anyMatch(activityInfo -> !StrUtils.isNull(activityInfo.get("chapterId")) && activityInfo.get("chapterId").equals(chapter.get("chapterId")));
                    chapter.put("hasActivity", hasActivity);
                });
                return true;
            }
            // 如果是共建者
            if (isTogether) {
                List<String> hasPermissionChapterIdList = getUserHasChapterIdPermissionList(pkgId, loginUserId, tevglBookpkgTeamList.get(0).getTeamId());
                chapterList.stream().forEach(chapter -> {
                    chapter.put("isBookCreator", false);
                    // 本节点是否允许新增修改删除等操作
                    chapter.put("hasPermission", hasPermissionChapterIdList.stream().anyMatch(a -> a.equals(chapter.get("chapterId"))));
                    // 本章节是否有活动
                    boolean hasActivity = activityList.stream().anyMatch(activityInfo -> !StrUtils.isNull(activityInfo.get("chapterId")) && activityInfo.get("chapterId").equals(chapter.get("chapterId")));
                    chapter.put("hasActivity", hasActivity);
                });
                return true;
            }
        }
        return false;
    }

    /**
     * 递归
     * @param allList
     * @param level
     * @param parentId
     * @param filterVisible 布尔值，为true则会过滤掉【学员不可见】的章节
     * @return
     */
    public List<Map<String, Object>> buildBookNew(List<Map<String, Object>> allList, int level, String parentId, boolean filterVisible) {
        if (allList == null || allList.size() == 0) {
            return null;
        }
        List<Map<String, Object>> nodeList = new ArrayList<Map<String,Object>>();
        // 学员身份时，是否过滤调用【学员不可见】的章节
        if (filterVisible) {
            nodeList = allList.stream().filter(a -> a.get("parentId").equals(parentId) && "Y".equals(a.get("isTraineesVisible"))).collect(Collectors.toList());
        } else {
            nodeList = allList.stream().filter(a -> a.get("parentId").equals(parentId)).collect(Collectors.toList());
        }
        if (nodeList != null && nodeList.size() > 0) {
            // level计算当前处于第几级
            level ++;
            for (int i = 0; i < nodeList.size(); i++) {
                // 当前节点
                Map<String, Object> a = nodeList.get(i);
                // 标识为章节节点
                a.put("type", "chapter");
                // 当前层级
                a.put("level", level);
                List<Map<String, Object>> list = buildBookNew(allList, level, a.get("chapterId").toString(), filterVisible);
                if (list != null && list.size() > 0) {
                    a.put("children", list);
                } else {
                    a.put("children", null);
                }
            }
        }
        return nodeList;
    }

    /**
     *
     * @param pkgId
     * @param loginUserId
     * @return
     */
    private List<TevglBookpkgTeam> selectBookpkgTeam(String pkgId, String loginUserId){
        Map<String, Object> map = new HashMap<>();
        map.put("pgkId", pkgId);
        map.put("userId", loginUserId);
        List<TevglBookpkgTeam> list = tevglBookpkgTeamMapper.selectListByMap(map);
        return list;
    }

    /**
     * 获取用户的此教学包对应教材的章节权限
     * @param loginUserId
     * @param pkgId
     * @return
     */
    private List<String> getUserHasChapterIdPermissionList(String pkgId, String loginUserId, String teamId){
        Map<String, Object> map = new HashMap<>();
        map.put("teamId", teamId);
        map.put("userId", loginUserId);
        List<TevglBookpkgTeamDetail> tevglBookpkgTeamDetailList = tevglBookpkgTeamDetailMapper.selectListByMap(map);
        if (tevglBookpkgTeamDetailList == null || tevglBookpkgTeamDetailList.size() == 0) {
            log.debug("教学包["+pkgId+"]中，" + "用户["+loginUserId+"]，" + "细表没有权限记录，直接返回");
            return new ArrayList<>();
        }
        return tevglBookpkgTeamDetailList.stream().map(a -> a.getChapterId()).collect(Collectors.toList());
    }

    /**
     * 查出该书所有的章节
     * @param subjectId 必传参数
     * @param isInRoomPage 选传参数，当在课堂页面时，需传true
     * @param pkgId isInRoomPage为true时，必传，用于过滤掉章节，对学员不可见
     * @return
     */
    private List<Map<String,Object>> selectSimpleChapterListMap(String subjectId, boolean isInRoomPage, String pkgId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sidx", "order_num");
        map.put("order", "asc");
        map.put("state", "Y");
        map.put("subjectId", subjectId);
        if (isInRoomPage) {
            map.put("pkgId", pkgId);
        }
        List<Map<String,Object>> list = tevglBookChapterMapper.selectSimpleListMap(map);
        log.debug("根据条件查询章节：" + map);
        log.debug("查询结果：" + list.size());
        return list;
    }

    /**
     * 取部分属性
     * @param subject
     * @return
     */
    private Map<String, Object> getSimpleSubjectInfo(TevglBookSubject subject){
        Map<String, Object> subjectInfo = new HashMap<>();
        // 标识为课程节点
        subjectInfo.put("type", "subject");
        subjectInfo.put("id", subject == null ? null : subject.getSubjectId());
        subjectInfo.put("chapterId", subject == null ? null : subject.getSubjectId());
        subjectInfo.put("subjectId", subject == null ? null : subject.getSubjectId());
        subjectInfo.put("subjectName", subject == null ? null : subject.getSubjectName());
        subjectInfo.put("chapterName", subject == null ? null : subject.getSubjectName());
        subjectInfo.put("subjectAuthor", subject == null ? null : subject.getSubjectAuthor());
        subjectInfo.put("subjectDesc", subject == null ? null : subject.getSubjectDesc());
        subjectInfo.put("subjectLogo", subject == null ? null : uploadFileUtils.stitchingPath("subject.getSubjectLogo()", "10"));
        subjectInfo.put("createUserId", subject == null ? null : subject.getCreateUserId());
        return subjectInfo;
    }

    /**
     * 处理序号
     * @param list
     */
    public void handleSortNum(List<Map<String, Object>> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        int index = 1;
        // 找到根节点
        for (Map<String, Object> node : list) {
            node.put("serial", index);
            deep(node, node);
            node.put("chapterName", +index+" "+node.get("chapterName"));
            index ++;
        }
    }

    /**
     * 递归处理序号
     * @param currNode 当前节点
     * @param parentNode 父节点
     */
    private void deep(Map<String, Object> currNode, Map<String, Object> parentNode) {
        int index = 0;
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> nodeList = (List<Map<String, Object>>) currNode.get("children");
        if (nodeList != null && nodeList.size() > 0) {
            for (Map<String, Object> node : nodeList) {
                index ++;
                node.put("serial", parentNode.get("serial")+"."+index);
                node.put("chapterName", parentNode.get("serial")+"."+index+" "+node.get("chapterName"));
                deep(node, node);

            }
        }
    }

    /**
     * 递归构建章节集合数据
     * @param sourceData
     * @param resultList
     */
    private void buildLine(List<Map<String,Object>> sourceData, List<Map<String,Object>> resultList, String queryName) {
        if (sourceData == null || sourceData.size() == 0) {
            return ;
        }
        for (int i = 0; i < sourceData.size(); i++) {
            // 加入新集合
            if (StrUtils.isNotEmpty(queryName)) {
                String chapterName = (String) sourceData.get(i).get("chapterName");
                // 如果匹配上了
                if (chapterName.indexOf(queryName) != -1) {
                    resultList.add(sourceData.get(i));
                }
            } else {
                resultList.add(sourceData.get(i));
            }
            // 如果当前节点还有子节点
            @SuppressWarnings("unchecked")
            List<Map<String,Object>> list = (List<Map<String, Object>>) sourceData.get(i).get("children");
            if (list != null && list.size() > 0) {
                // 递归
                buildLine(list, resultList, queryName);
                // 之后清空冗余的
                sourceData.get(i).put("children", null);
            }
        }
    }

    /**
     * 过滤节点
     * @param allList
     * @param chapterName
     * @return
     */
    private List<Map<String, Object>> filter(List<Map<String, Object>> allList, String chapterName) {
        if (allList == null || allList.size() == 0) {
            return null;
        }
        List<Map<String, Object>> newArrayList = new ArrayList<Map<String,Object>>();
        allList.forEach(obj -> {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> children = (List<Map<String, Object>>) obj.get("children");
            List<Map<String, Object>> subs = filter(children, chapterName);
            if (isMatching(obj, chapterName)) {
                newArrayList.add(obj);
            } else if (subs != null && subs.size() > 0) {
                obj.put("children", subs);
                newArrayList.add(obj);
            }
        });
        return newArrayList;
    }

    private boolean isMatching(Map<String, Object> obj, String queryName) {
        String chapterName = (String)obj.get("chapterName");
        if (chapterName.indexOf(queryName) > -1) {
            return true;
        }
        return false;
    }
}
