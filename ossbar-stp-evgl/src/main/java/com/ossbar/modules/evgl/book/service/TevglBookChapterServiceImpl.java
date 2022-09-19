package com.ossbar.modules.evgl.book.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbRoomUtils;
import com.ossbar.modules.common.GlobalRoomPermission;
import com.ossbar.modules.common.PkgPermissionUtils;
import com.ossbar.modules.common.PkgUtils;
import com.ossbar.modules.evgl.book.api.TevglBookChapterService;
import com.ossbar.modules.evgl.book.domain.TevglBookChapter;
import com.ossbar.modules.evgl.book.domain.TevglBookChapterVisible;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.book.persistence.TevglBookChapterMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookChapterVisibleMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubjectMapper;
import com.ossbar.modules.evgl.book.vo.BookTreeVo;
import com.ossbar.modules.evgl.book.vo.SaveChapterVisibleVo;
import com.ossbar.modules.evgl.book.vo.SaveChapterVo;
import com.ossbar.modules.evgl.pkg.domain.TevglBookpkgTeam;
import com.ossbar.modules.evgl.pkg.domain.TevglBookpkgTeamDetail;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.pkg.persistence.*;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author huj
 * @create 2022-09-17 10:25
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
public class TevglBookChapterServiceImpl implements TevglBookChapterService {

    @SuppressWarnings("unused")
    private Logger log = LoggerFactory.getLogger(TevglBookChapterServiceImpl.class);
    @Autowired
    private TevglBookChapterMapper tevglBookChapterMapper;
    @Autowired
    private TevglBookSubjectMapper tevglBookSubjectMapper;
    @Autowired
    private TevglPkgInfoMapper tevglPkgInfoMapper;
    @Autowired
    private TevglPkgResgroupMapper tevglPkgResgroupMapper;
    @Autowired
    private TevglBookpkgTeamMapper tevglBookpkgTeamMapper;
    @Autowired
    private TevglBookpkgTeamDetailMapper tevglBookpkgTeamDetailMapper;
    @Autowired
    private TevglPkgResMapper tevglPkgResMapper;
    @Autowired
    private TevglBookChapterVisibleMapper tevglBookChapterVisibleMapper;
    @Autowired
    private TevglTchClassroomMapper tevglTchClassroomMapper;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private ServiceLoginUtil serviceLoginUtil;
    @Autowired
    private PkgUtils pkgUtils;
    @Autowired
    private PkgPermissionUtils pkgPermissionUtils;
    @Autowired
    private CbRoomUtils cbRoomUtils;

    /**
     * <p>重命名章节名称</p>
     *
     * @param pkgId       教学包id
     * @param chapterId   章节主键
     * @param name        章节名称
     * @param loginUserId 当前登录用户
     * @return
     * @author huj
     * @data 2019年7月27日
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R rename(String pkgId, String chapterId, String name, String loginUserId) {
        // 非空判断
        if (StrUtils.isEmpty(chapterId) || StrUtils.isEmpty(name) || StrUtils.isEmpty(loginUserId)
                || StrUtils.isEmpty(pkgId)) {
            return R.error("必传参数为空");
        }
        TevglPkgInfo pkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
        if (pkgInfo == null) {
            return R.error("无效的记录");
        }
		/*if (StrUtils.isNotEmpty(pkgInfo.getRefPkgId())) {
			return R.error("已不允许重命名");
		}*/
        if (StrUtils.isNotEmpty(pkgInfo.getRefPkgId()) && !"3".equals(pkgInfo.getDisplay())
                && !pkgInfo.getPkgId().equals(pkgInfo.getRefPkgId())) {
            return R.error("已不允许重命名");
        }
        // 权限校验
        TevglBookChapter chapterInfo = tevglBookChapterMapper.selectObjectById(chapterId);
        if (chapterInfo == null) {
            return R.error("无效的记录，操作失败");
        }
        // 如果教学包没有被移交
        if (StrUtils.isEmpty(pkgInfo.getReceiverUserId())) {
            if (!loginUserId.equals(chapterInfo.getCreateUserId()) && !loginUserId.equals(pkgInfo.getCreateUserId())) {
                Map<String, Object> map = new HashMap<>();
                map.put("userId", loginUserId);
                map.put("chapterId", chapterId);
                List<TevglBookpkgTeamDetail> list = tevglBookpkgTeamDetailMapper.selectListByMap(map);
                if (list == null || list.size() == 0) {
                    String msg = "章节【" + chapterInfo.getChapterName() + "】，";
                    return R.error(msg + "没有权限，操作失败，可联系作者授权");
                }
            }
        }
        // 如果教学包被移交了
        if (StrUtils.isNotEmpty(pkgInfo.getReceiverUserId())) {
            // 且登录用户不是接管者时，进行校验
            if (!loginUserId.equals(pkgInfo.getReceiverUserId())) {
                Map<String, Object> map = new HashMap<>();
                map.put("userId", loginUserId);
                map.put("chapterId", chapterId);
                List<TevglBookpkgTeamDetail> list = tevglBookpkgTeamDetailMapper.selectListByMap(map);
                if (list == null || list.size() == 0) {
                    String msg = "章节【" + chapterInfo.getChapterName() + "】，";
                    return R.error(msg + "没有权限，操作失败，可联系作者授权");
                }
            }
        }
        TevglBookChapter chapter = new TevglBookChapter();
        chapter.setChapterId(chapterId);
        chapter.setChapterName(name);
        // 同级名称是否重复
        Map<String, Object> map = new HashMap<>();
        map.put("state", "Y");
        map.put("parentId", chapterInfo.getParentId());
        List<TevglBookChapter> list = tevglBookChapterMapper.selectListByMap(map);
        if (list != null && list.size() > 0) {
            for (TevglBookChapter a : list) {
                if (!a.getChapterId().equals(chapterInfo.getChapterId())) {
                    if (a.getChapterName().equals(chapter.getChapterName())) {
                        return R.error("重命名失败,同级章节中,输入的名称已经存在").put("chapterId", chapterId).put("chapterName",
                                chapterInfo.getChapterName());
                    }
                }
            }
        }
        tevglBookChapterMapper.update(chapter);
        return R.ok("重命名成功").put("chapterId", chapterId);
    }

    /**
     * 删除章节（同时删除其节点下所有子节点）
     *
     * @param pkgId
     * @param chapterId
     * @param loginUserId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R removeV2(String pkgId, String chapterId, String loginUserId) {
        if (StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(chapterId) || StrUtils.isEmpty(loginUserId)) {
            return R.error("必传参数为空");
        }
        TevglPkgInfo pkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
        if (pkgInfo == null) {
            return R.error("无效的记录");
        }
        if (StrUtils.isNotEmpty(pkgInfo.getRefPkgId()) && !"3".equals(pkgInfo.getDisplay())
                && !pkgInfo.getPkgId().equals(pkgInfo.getRefPkgId())) {
            return R.error("已不允许删除");
        }
        TevglBookChapter chapterInfo = tevglBookChapterMapper.selectObjectById(chapterId);
        if (chapterInfo == null) {
            return R.error("无效的记录，操作失败");
        }
        // 找到该节点下所有子节点
        List<String> chapterIdList = findChapterIdList(chapterInfo.getSubjectId(), chapterInfo.getChapterId());
        log.info("chapterIdList => {}", chapterIdList);
        // 查询条件
        Map<String, Object> map = new HashMap<>();
        // 如果不是教学包创建，又不是章节创建者
        if (!loginUserId.equals(pkgInfo.getCreateUserId()) && !loginUserId.equals(chapterInfo.getCreateUserId())) {
            map.clear();
            map.put("pgkId", pkgId);
            map.put("userId", loginUserId);
            List<TevglBookpkgTeam> tevglBookpkgTeams = tevglBookpkgTeamMapper.selectListByMap(map);
            if (tevglBookpkgTeams == null || tevglBookpkgTeams.size() == 0) {
                return R.error("没有权限，操作失败，可联系作者授权");
            }
            map.clear();
            map.put("userId", loginUserId);
            map.put("teamId", tevglBookpkgTeams.get(0).getTeamId());
            List<TevglBookpkgTeamDetail> list = tevglBookpkgTeamDetailMapper.selectListByMap(map);
            if (list == null || list.size() == 0) {
                String msg = "章节【" + chapterInfo.getChapterName() + "】，";
                return R.error(msg + "没有权限，操作失败，可联系作者授权");
            }
            // 用户有权限的章节
            List<String> hasAuthChapterIdList = list.stream().map(a -> a.getChapterId()).collect(Collectors.toList());
            for (String id : chapterIdList){
                if (!hasAuthChapterIdList.stream().anyMatch(v -> v.equals(id))) {
                    return R.error("被删除节点中，出现了没有权限的节点，操作失败，请联系管理员");
                }
            }
        }
        if (chapterIdList != null && chapterIdList.size() > 0) {
            // 找出分组
            map.clear();
            map.put("chapterIdList", chapterIdList);
            List<Map<String, Object>> pkgResgroupList = tevglPkgResgroupMapper.selectSimpleListMap(map);
            if (pkgResgroupList != null && pkgResgroupList.size() > 0) {
                // 是否有具体资源
                List<String> resgroupIds = pkgResgroupList.stream().map(a -> (String) a.get("resgroupId")).collect(Collectors.toList());
                map.clear();
                map.put("resgroupIds", resgroupIds);
                List<String> pkgResIdList = tevglPkgResMapper.selectResIdListByMap(map);
                // 删除资源
                if (pkgResIdList != null && pkgResIdList.size() > 0) {
                    String[] array = pkgResIdList.stream().toArray(String[]::new);
                    tevglPkgResMapper.deleteBatch(array);
                }
                // 删除分组
                tevglPkgResgroupMapper.deleteBatch(resgroupIds.stream().toArray(String[]::new));
                // 教学包资源数对应减少
                pkgUtils.plusPkgResReduceNum(pkgId, -pkgResgroupList.size());
            }
            // 删除章节
            tevglBookChapterMapper.deleteBatch(chapterIdList.stream().toArray(String[]::new));
        }
        return R.ok("删除成功");
    }

    /**
     * 获取指定节点下的所有子孙节点
     *
     * @param subjectId       章节所在的课程
     * @param sourceChapterId 必传参数
     * @return 返回空集合，或者ok的数据
     */
    @Override
    public List<String> findChapterIdList(String subjectId, String sourceChapterId) {
        List<BookTreeVo> allChapterList = tevglBookChapterMapper.findAllChapterList(subjectId);
        return this.findChapterIdList(sourceChapterId, allChapterList);
    }

    /**
     * 获取指定节点下的所有子孙节点
     *
     * @param sourceChapterId
     * @param allChapterList
     * @return
     */
    @Override
    public List<String> findChapterIdList(String sourceChapterId, List<BookTreeVo> allChapterList) {
        List<String> resultList = new ArrayList<>();
        recursion(sourceChapterId, allChapterList, resultList);
        resultList.add(sourceChapterId);
        return resultList;
    }

    private void recursion(String parentId, List<BookTreeVo> allChapterList, List<String> resultList) {
        if (StrUtils.isEmpty(parentId) || allChapterList == null || allChapterList.size() == 0) {
            return;
        }
        // 取出当前节点的子节点
        List<BookTreeVo> nodeList = allChapterList.stream().filter(a -> a.getParentId().equals(parentId)).collect(Collectors.toList());
        // 遍历节点
        if (nodeList != null && nodeList.size() > 0) {
            nodeList.stream().forEach(node -> {
                // 加入集合
                resultList.add(node.getId());
                // 继续
                recursion(node.getId(), allChapterList, resultList);
            });
        }
    }

    /**
     * <p>移动</p>
     *
     * @param currId      当前ID
     * @param targetId    目标ID
     * @param loginUserId
     * @param pkgId
     * @return
     * @author huj
     * @data 2019年7月27日
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R move(String currId, String targetId, String loginUserId, String pkgId) {
        if (StrUtils.isEmpty(currId) || StrUtils.isEmpty(targetId) || StrUtils.isEmpty(loginUserId)
                || StrUtils.isEmpty(pkgId)) {
            return R.error("操作失败，必传参数为空");
        }
        TevglPkgInfo pkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
        if (pkgInfo == null) {
            return R.error("无效的记录");
        }
		/*if (StrUtils.isNotEmpty(pkgInfo.getRefPkgId())) {
			return R.error("已不允许移动");
		}*/
        if (StrUtils.isNotEmpty(pkgInfo.getRefPkgId()) && !"3".equals(pkgInfo.getDisplay())
                && !pkgInfo.getPkgId().equals(pkgInfo.getRefPkgId())) {
            return R.error("已不允许移动");
        }
        // 权限校验
        TevglBookChapter chapterInfo = tevglBookChapterMapper.selectObjectById(currId);
        if (chapterInfo == null) {
            return R.error("操作失败");
        }
        if (!loginUserId.equals(chapterInfo.getCreateUserId()) && !loginUserId.equals(pkgInfo.getCreateUserId())) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", loginUserId);
            map.put("chapterId", chapterInfo.getChapterId());
            List<TevglBookpkgTeamDetail> list = tevglBookpkgTeamDetailMapper.selectListByMap(map);
            if (list == null || list.size() == 0) {
                String msg = "章节【" + chapterInfo.getChapterName() + "】，";
                return R.error(msg + "没有权限，操作失败，可联系作者授权");
            }
        }
        TevglBookChapter curr = tevglBookChapterMapper.selectObjectById(currId); // 当前对象
        TevglBookChapter target = tevglBookChapterMapper.selectObjectById(targetId); // 目标对象
        if (curr != null && target != null) {
            if (!curr.getParentId().equals(target.getParentId())) {
                return R.error("只能同级移动");
            }
            Integer cSort = curr.getOrderNum();
            String currChapterNo = curr.getChapterNo();
            Integer tSort = target.getOrderNum();
            String targetChapterNo = target.getChapterNo();

            curr.setOrderNum(tSort); // 排序号
            curr.setChapterNo(targetChapterNo); // 章节编号
            tevglBookChapterMapper.update(curr);

            target.setOrderNum(cSort);
            target.setChapterNo(currChapterNo);
            tevglBookChapterMapper.update(target);
        }
        return R.ok();
    }

    /**
     * 新增章节
     *
     * @param tevglBookChapter
     * @param loginUserId
     * @return
     * @author huj
     * @data 2019年8月8日
     * @apiNote 新增章节时必传参数: chapterName，subjectId，parentId
     */
    @Override
    public R saveChapterInfo(TevglBookChapter tevglBookChapter, String loginUserId) {
        // 合法性校验
        String subjectId = tevglBookChapter.getSubjectId();
        String parentId = tevglBookChapter.getParentId();
        String chapterName = tevglBookChapter.getChapterName();
        String pkgId = tevglBookChapter.getPkgId();
        if (StrUtils.isEmpty(subjectId) || StrUtils.isEmpty(chapterName) || StrUtils.isEmpty(parentId)
                || StrUtils.isEmpty(loginUserId) || StrUtils.isEmpty(pkgId)) {
            return R.error("必传参数为空");
        }
        // 获取到教学包主键
        TevglPkgInfo pkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
        if (pkgInfo == null) {
            return R.error("无效的记录");
        }
        if (StrUtils.isNotEmpty(pkgInfo.getRefPkgId()) && !"3".equals(pkgInfo.getDisplay())
                && !pkgInfo.getPkgId().equals(pkgInfo.getRefPkgId())) {
            return R.error("已不允许新增");
        }
        // 去空格
        tevglBookChapter.setChapterName(tevglBookChapter.getChapterName().trim());
        if (StrUtils.isEmpty(tevglBookChapter.getChapterName())) {
            return R.error("章节名称不能为空");
        }
        if (tevglBookChapter.getChapterName().length() > 50) {
            return R.error("字符长度不能超过50");
        }
        // 同级别不允许有同名的名称
        if (isRepetitionName(parentId, chapterName)) {
            return R.error("同级章节下，不允许有重复的章节名称");
        }
        // 权限校验
        R r2 = checkPermission(tevglBookChapter, loginUserId, pkgInfo);
        if ((Integer) r2.get("code") != 0) {
            return r2;
        }
        tevglBookChapter.setChapterId(Identities.uuid());
        tevglBookChapter.setIsTraineesVisible(StrUtils.isEmpty(tevglBookChapter.getIsTraineesVisible()) ? "Y"
                : tevglBookChapter.getIsTraineesVisible());
        tevglBookChapter.setCreateUserId(loginUserId);
        tevglBookChapter.setCreateTime(DateUtils.getNowTimeStamp());
        tevglBookChapter.setState("Y"); // 状态(Y有效N无效)
        //ValidatorUtils.check(tevglBookChapter);
        // 限制不能超过?级
        int level = 3;
        if (isOverLevel(tevglBookChapter.getParentId(), tevglBookChapter.getSubjectId(), level)) {
            return R.error("目前最多允许增加" + level + "级章节节点");
        }
        // 排序号处理
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", parentId);
        tevglBookChapter.setOrderNum(tevglBookChapterMapper.selectMaxSortByMap(map) + 1);
        // 判断当前节点是否有父节点，如果父节点设置为学员不可见，则本节点也设置为不可见
        TevglBookChapter parentNode = tevglBookChapterMapper.selectObjectById(parentId);
        if (parentNode != null) {
			/*if ("N".equals(parentNode.getIsTraineesVisible())) {
				tevglBookChapter.setIsTraineesVisible("N");
			}*/
            map.clear();
            map.put("pkgId", pkgId);
            map.put("chapterId", parentNode.getChapterId());
            List<TevglBookChapterVisible> list = tevglBookChapterVisibleMapper.selectListByMap(map);
            if (list != null && list.size() > 0) {
                tevglBookChapter.setIsTraineesVisible(list.get(0).getIsTraineesVisible());
            }
        }
        // 入库
        tevglBookChapterMapper.insert(tevglBookChapter);
        // 设置学员是否可见
        TevglBookChapterVisible v = new TevglBookChapterVisible();
        v.setId(Identities.uuid());
        v.setPkgId(pkgId);
        v.setChapterId(tevglBookChapter.getChapterId());
        v.setIsTraineesVisible(tevglBookChapter.getIsTraineesVisible());
        tevglBookChapterVisibleMapper.insert(v);
        // 如果当前登录用户不是此教学包创建者
        //pkgUtils.createTeamDetailData(pkgId, tevglBookChapter.getChapterId(), loginUserId, pkgInfo.getCreateUserId());
        pkgUtils.createTeamDetailDataV2(pkgInfo, tevglBookChapter.getChapterId(), loginUserId);
        // 默认生成[课程内容]分组
        pkgUtils.doCreateDefaultGroup(pkgId, pkgInfo.getSubjectId(), Arrays.asList(tevglBookChapter.getChapterId()), loginUserId);
        // 即时通讯
        TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectByPkgId(pkgId);
        // TODO 重新刷新章节与资源
        return R.ok("新增成功").put("chapterId", tevglBookChapter.getChapterId());
    }

    /**
     * 修改章节信息
     *
     * @param tevglBookChapter
     * @param loginUserId
     * @return
     */
    @Override
    public R updateChapterInfo(TevglBookChapter tevglBookChapter, String loginUserId) {
        return null;
    }

    /**
     * <p>获取指定教材或课程且状态为Y有效的的直系章节</p>
     *
     * @param subjectId
     * @return
     * @author huj
     * @data 2019年12月23日
     */
    @Override
    public List<TevglBookChapter> getDirectLineChapters(String subjectId) {
        return null;
    }

    /**
     * <p>明细</p>
     *
     * @param id
     * @return
     * @author huj
     * @data 2019年12月25日
     */
    @Override
    public TevglBookChapter selectObjectById(String id) {
        return null;
    }

    /**
     * 查看章节
     *
     * @param chapterId   章节主键
     * @param loginUserId 登录用户
     * @param pkgId       教学包主键
     * @param type
     * @return
     */
    @Override
    public R viewChapterInfo(String chapterId, String loginUserId, String pkgId, String type) {
        if (StrUtils.isEmpty(chapterId) || StrUtils.isEmpty(loginUserId) || StrUtils.isEmpty(pkgId)) {
            return R.error("必传参数为空");
        }
        TevglBookChapter tevglBookChapter = tevglBookChapterMapper.selectObjectById(chapterId);
        if (tevglBookChapter == null) {
            return R.error("无效的章节");
        }
        // 获取到教学包主键
        TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
        if (tevglPkgInfo == null) {
            return R.error("无效的记录");
        }
        // 是否在课堂页面中
        //boolean isInRoomPage = StrUtils.isEmpty(type);
        // 最终返回数据
        Map<String, Object> result = new HashMap<String, Object>();
        // 查询条件
        Map<String, Object> params = new HashMap<>();
        // 3.重新获取该章节的资源分组
        List<Map<String, Object>> pkgResGroupList = new ArrayList<>();
        params.put("ctPkgId", pkgId);
        params.put("chapterId", chapterId);
        params.put("pkgId", pkgId);
        params.put("sidx", "t1.sort_num");
        params.put("order", "asc");
        // 未传值，表示是课堂页面，查看章节
        if (StrUtils.isEmpty(type)) {
            params.put("subjectId", tevglPkgInfo.getSubjectId());
            pkgResGroupList = tevglPkgResgroupMapper.selectSimpleListMap2(params);
            log.debug("根据条件查询章节标签数据：" + params);
            log.debug("结果：" + pkgResGroupList.size());
            // 当当前课堂对应的教学包没有是否允许复制的记录时,则生成一条记录
            TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectByPkgId(pkgId);
            if (tevglTchClassroom != null) {
                /*if (pkgResGroupList != null && pkgResGroupList.size() > 0) {
                    Map<String, Object> map = new HashMap<>();
                    pkgResGroupList.stream().forEach(resgroup -> {
                        map.put("pkgId", tevglTchClassroom.getPkgId());
                        map.put("subjectId", tevglPkgInfo.getSubjectId());
                        map.put("resgroupId", resgroup.get("resgroupId"));
                        List<TevglPkgResgroupAllowCopy> allowCopyList = tevglPkgResgroupAllowCopyMapper
                                .selectListByMap(map);
                        log.debug("是否有记录：" + allowCopyList.size());
                        if (allowCopyList == null || allowCopyList.size() == 0) {
                            TevglPkgResgroupAllowCopy cp = new TevglPkgResgroupAllowCopy();
                            cp.setCpId(Identities.uuid());
                            cp.setPkgId(tevglTchClassroom.getPkgId());
                            cp.setSubjectId(tevglPkgInfo.getSubjectId());
                            cp.setResgroupId(StrUtils.isNull(resgroup.get("resgroupId")) ? null
                                    : resgroup.get("resgroupId").toString());
                            cp.setIsCanCopy("Y"); // Y允许复制资源内容N不允许
                            tevglPkgResgroupAllowCopyMapper.insert(cp);
                        }
                    });
                }*/
                boolean hasBtnPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.ROOM_PERM_SUBJECT_RESGROUPVISIBLE);
                // 如果当前用户不是课堂创建者
                if (!loginUserId.equals(tevglTchClassroom.getCreateUserId()) && !hasBtnPermission) {
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    pkgResGroupList.stream().forEach(pkgResGroup -> {
                        if (!StrUtils.isNull(pkgResGroup.get("isTraineesVisible")) && "Y".equals(pkgResGroup.get("isTraineesVisible"))) {
                            dataList.add(pkgResGroup);
                        }
                    });
                    pkgResGroupList = dataList;
                }
                pkgResGroupList.stream().forEach(pkgResGroup -> {
                    if (hasBtnPermission) {
                        pkgResGroup.put("hasSetResVisiblePermission", true);
                    } else {
                        pkgResGroup.put("hasSetResVisiblePermission", false);
                    }
                });
            }
        } else {
			/*// 兼容下
			if (StrUtils.isNotEmpty(pkgInfo.getDisplay()) && "3".equals(pkgInfo.getDisplay())) {
				params.put("subjectId", pkgInfo.getSubjectId());
			}*/
            params.put("subjectId", tevglPkgInfo.getSubjectId());
            pkgResGroupList = tevglPkgResgroupMapper.selectSimpleListMap2(params);
            log.debug("查询条件：" + params);
            log.debug("资源分组数量：" + pkgResGroupList.size());
        }
        // 处理包含的视频数量
        pkgResGroupList.stream().forEach(a -> {
            String content = (String) a.get("resContent");
            int num = getNum(content);
            a.put("num", num);
        });
        // 4.返回数据
        Map<String, Object> chapterInfo = converToSimpleMapInfo(tevglBookChapter);
        result.put("chapterInfo", chapterInfo); // 章节数据
        result.put("pkgResGroupList", pkgResGroupList); // 章节分组
        result.put("resNum", tevglPkgInfo.getPkgResCount()); // 教学包资源数量
        return R.ok().put(Constant.R_DATA, result);
    }

    /**
     * 取部分属性
     *
     * @param tevglBookChapter
     * @return
     */
    private Map<String, Object> converToSimpleMapInfo(TevglBookChapter tevglBookChapter) {
        Map<String, Object> chapterInfo = new HashMap<String, Object>();
        if (tevglBookChapter == null) {
            return chapterInfo;
        }
        chapterInfo.put("chapterId", tevglBookChapter.getChapterId());
        chapterInfo.put("subjectId", tevglBookChapter.getSubjectId());
        chapterInfo.put("chapterName", tevglBookChapter.getChapterName());
        chapterInfo.put("chapterIcon", tevglBookChapter.getChapterIcon());
        chapterInfo.put("chapterContent", tevglBookChapter.getChapterContent());
        return chapterInfo;
    }

    /**
     * 统计数量
     *
     * @param content
     * @return
     */
    private int getNum(String content) {
        int num = 0;
        String regEx_img = "<source.*src\\s*=\\s*(.*?)[^>]*?>";
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(content);
        while (m_image.find()) {
            num++;
        }
        return num;
    }

    /**
     * 批量设置章节对学员是否可见（小程序专用）
     *
     * @param jsonObject
     * @param loginUserId
     * @return
     */
    @Override
    public R batchSetChapterIsTraineesVisible(JSONObject jsonObject, String loginUserId) {
        String ctId = jsonObject.getString("ctId");
        String pkgId = jsonObject.getString("pkgId");
        String isTraineesVisible = jsonObject.getString("isTraineesVisible"); // Y可见/N不可见
        JSONArray jsonArray = jsonObject.getJSONArray("chapterIds"); // 小程序传入的是，未选中的章节，则需要把它们设为不可见
        if (StrUtils.isEmpty(loginUserId) || StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(isTraineesVisible)) {
            return R.error("必传参数为空");
        }
        if (jsonArray == null) {
            return R.error("请选择章节");
        }
        TevglTchClassroom tevglTchClassroom = null;
        if (StrUtils.isNotEmpty(ctId)) {
            tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
        } else {
            tevglTchClassroom = tevglTchClassroomMapper.selectObjectByPkgId(pkgId);
        }
        if (tevglTchClassroom == null) {
            return R.error("无效的课堂");
        }
        TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
        if (tevglPkgInfo == null) {
            return R.error("无效的记录");
        }
        // 如果既不是课堂创建者，又不是课堂助教，则不允许设置
        boolean hasPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.ROOM_PERM_SUBJECT_CHAPTERVISIBLE);
        if (!hasPermission) {
            return R.error("没有权限，设置失败");
        }
        // 找到本课程所有章节
        Map<String, Object> params = new HashMap<>();
        params.put("subjectId", tevglPkgInfo.getSubjectId());
        List<TevglBookChapter> allChapterList = tevglBookChapterMapper.selectListByMap(params);
        List<String> allChapterIds = allChapterList.stream().map(a -> a.getChapterId()).collect(Collectors.toList());
        // 所有可见数据
        params.clear();
        params.put("pkgId", pkgId);
        List<TevglBookChapterVisible> allVisibleList = tevglBookChapterVisibleMapper.selectListByMap(params);
        // 如果小程序端传了值，且为Y，则需要把传入的值，全部更新为Y
        if ("Y".equals(isTraineesVisible)) {
            changeVisibleY(pkgId, allChapterIds, allVisibleList);
        } else {
            changeVisibleN(pkgId, allChapterIds, allVisibleList, jsonArray);
        }
        return R.ok("设置成功");
    }

    private void changeVisibleN(String pkgId, List<String> allChapterIds, List<TevglBookChapterVisible> allVisibleList, JSONArray jsonArray){
        // 等待入库的集合
        List<TevglBookChapterVisible> insertList = new ArrayList<>();
        // 等待更新集合
        List<String> chapterIdList = new ArrayList<>();
        // 等待被去重的集合
        List<String> ids = new ArrayList<>();
        // 找到需要被设为不可见的章节
        for (Object chapterId : jsonArray) {
            List<TevglBookChapterVisible> visibleList = allVisibleList.stream().filter(a -> a.getChapterId().equals(chapterId)).collect(Collectors.toList());
            if (visibleList != null && visibleList.size() > 0) {
                TevglBookChapterVisible t = visibleList.get(0);
                t.setIsTraineesVisible("N");
                chapterIdList.add(t.getChapterId());
                ids.add(t.getChapterId());
            } else {
                TevglBookChapterVisible t = new TevglBookChapterVisible();
                t.setId(Identities.uuid());
                t.setPkgId(pkgId);
                t.setChapterId(chapterId.toString());
                t.setIsTraineesVisible("N");
                insertList.add(t);
            }
        }
        if (chapterIdList != null && chapterIdList.size() > 0) {
            Map<String, Object> data = new HashMap<>();
            data.put("pkgId", pkgId);
            data.put("isTraineesVisible", "N");
            data.put("chapterIds", chapterIdList);
            tevglBookChapterVisibleMapper.updateVisibleBatch(data);
            chapterIdList.clear();
        }
        // 取差集后,更新其它章节为可见
        allChapterIds.removeAll(ids);
        for (String chapterId : allChapterIds) {
            List<TevglBookChapterVisible> visibleList = allVisibleList.stream().filter(a -> a.getChapterId().equals(chapterId)).collect(Collectors.toList());
            if (visibleList != null && visibleList.size() > 0) {
                TevglBookChapterVisible t = visibleList.get(0);
                t.setIsTraineesVisible("Y");
                chapterIdList.add(t.getChapterId());
            }  else {
                TevglBookChapterVisible t = new TevglBookChapterVisible();
                t.setId(Identities.uuid());
                t.setPkgId(pkgId);
                t.setChapterId(chapterId.toString());
                t.setIsTraineesVisible("Y");
                insertList.add(t);
            }
        }
        if (insertList != null && insertList.size() > 0) {
            tevglBookChapterVisibleMapper.insertBatch(insertList);
        }
        if (chapterIdList != null && chapterIdList.size() > 0) {
            Map<String, Object> data = new HashMap<>();
            data.put("pkgId", pkgId);
            data.put("isTraineesVisible", "Y");
            data.put("chapterIds", chapterIdList);
            tevglBookChapterVisibleMapper.updateVisibleBatch(data);
        }
    }

    /**
     * 小程序端，一次性设置，章节对学员可见
     * @param pkgId
     * @param allChapterIds
     * @param allVisibleList
     */
    private void changeVisibleY(String pkgId, List<String> allChapterIds, List<TevglBookChapterVisible> allVisibleList){
        // 等待入库的集合
        List<TevglBookChapterVisible> insertList = new ArrayList<>();
        // 等待更新集合
        List<String> chapterIdList = new ArrayList<>();
        for (String chapterId : allChapterIds) {
            List<TevglBookChapterVisible> visibleList = allVisibleList.stream().filter(a -> a.getChapterId().equals(chapterId)).collect(Collectors.toList());
            if (visibleList != null && visibleList.size() > 0) {
                TevglBookChapterVisible t = visibleList.get(0);
                t.setIsTraineesVisible("Y");
                chapterIdList.add(t.getChapterId());
            } else {
                TevglBookChapterVisible t = new TevglBookChapterVisible();
                t.setId(Identities.uuid());
                t.setPkgId(pkgId);
                t.setChapterId(chapterId.toString());
                t.setIsTraineesVisible("Y");
                insertList.add(t);
            }
        }
        if (insertList != null && insertList.size() > 0) {
            tevglBookChapterVisibleMapper.insertBatch(insertList);
        }
        if (chapterIdList != null && chapterIdList.size() > 0) {
            Map<String, Object> data = new HashMap<>();
            data.put("pkgId", pkgId);
            data.put("isTraineesVisible", "Y");
            data.put("chapterIds", chapterIdList);
            tevglBookChapterVisibleMapper.updateVisibleBatch(data);
        }
    }

    /**
     * 设置章节对学员是否可见（web端专用）
     *
     * @param ctId
     * @param pkgId             课堂表对应的那个pkgId
     * @param chapterId
     * @param isTraineesVisible Y可见N不可见
     * @param loginUserId
     * @return
     */
    @Override
    public R setChapterIsTraineesVisible(String ctId, String pkgId, String chapterId, String isTraineesVisible, String loginUserId) {
        return null;
    }

    /**
     * 设置课程视频标签对学员是否可见
     *
     * @param ctId              课堂id
     * @param pkgId             课堂教学包
     * @param resgroupId
     * @param isTraineesVisible Y可见N不可见
     * @param loginUserId       当前登录用户
     * @return
     */
    @Override
    public R setTraineesVisibleResgroup(String ctId, String pkgId, String resgroupId, String isTraineesVisible, String loginUserId) {
        return null;
    }

    /**
     * 批量设置 课程视频等标签对学员是否可见
     *
     * @param jsonObject
     * @param loginUserId
     * @return
     */
    @Override
    public R setTraineesVisibleResgroupBatch(JSONObject jsonObject, String loginUserId) {
        return null;
    }

    /**
     * 根据条件查询记录
     *
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> selectSimpleListMap(Map<String, Object> params) {
        return null;
    }

    /**
     * 左右滑动进入上下章节
     *
     * @param pkgId
     * @param chapterId
     * @param loginUserId
     * @return
     */
    @Override
    public R slide(String pkgId, String chapterId, String loginUserId) {
        return null;
    }

    /**
     * 一键生成（任务描述、任务知识点、任务准备、任务实时）
     *
     * @param pkgId
     * @param chapterId
     * @param loginUserId
     * @return
     */
    @Override
    public R create(String pkgId, String chapterId, String loginUserId) {
        return null;
    }

    /**
     * 章节下拉列表
     *
     * @param params
     * @return
     */
    @Override
    public R listSelectChapter(Map<String, Object> params) {
        return null;
    }

    /**
     * 查询当前课堂上课教材的源课程
     *
     * @param pkgId
     * @param loginUserId
     * @return
     */
    @Override
    public R queryTopChapterList(String pkgId, String loginUserId) {
        return null;
    }

    /**
     * 获取层次机构的树形数据（且章节名称后面拼接此章节授权给了谁）
     *
     * @param subjectId
     * @param pkgId
     * @param loginUserId
     * @return
     */
    @Override
    public R getChapterTreeWithTeacherName(String subjectId, String pkgId, String loginUserId) {
        return null;
    }

    /**
     * 章节树（可见性）
     *
     * @param pkgId
     * @param loginUserId
     * @return
     */
    @Override
    public R getChapterTreeVisibleForWeb(String pkgId, String loginUserId) {
        return null;
    }

    /**
     * 批量设置 课程视频等标签对学员是否可见
     *
     * @param vo
     * @param loginUserId
     * @return
     */
    @Override
    public R setTraineesVisibleBatchForWeb(SaveChapterVisibleVo vo, String loginUserId) {
        return null;
    }

    /**
     * 追加同级节点
     *
     * @param tevglBookChapter
     * @return
     */
    @Override
    public R appendPeerNodes(TevglBookChapter tevglBookChapter) {
        // 合法性校验
        R r = checkIsPassForAppendPeerNodes(tevglBookChapter);
        if (!r.get("code").equals(0)) {
            return r;
        }
        String pkgId = tevglBookChapter.getPkgId();
        String subjectId = tevglBookChapter.getSubjectId();
        String parentId = tevglBookChapter.getParentId();
        String loginUserId = tevglBookChapter.getCreateUserId();
        // 拿取数据
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) r.get(Constant.R_DATA);
        TevglPkgInfo tevglPkgInfo = (TevglPkgInfo) data.get("tevglPkgInfo");
        TevglBookChapter previousNode = (TevglBookChapter) data.get("tevglBookChapter");
        // 权限校验
        R r2 = checkPermission(tevglBookChapter, loginUserId, tevglPkgInfo);
        if ((Integer)r2.get("code") != 0) {
            return r2;
        }
        // 填充信息
        tevglBookChapter.setChapterId(Identities.uuid());
        tevglBookChapter.setIsTraineesVisible(StrUtils.isEmpty(tevglBookChapter.getIsTraineesVisible()) ? "Y" : tevglBookChapter.getIsTraineesVisible());
        tevglBookChapter.setCreateUserId(loginUserId);
        tevglBookChapter.setCreateTime(DateUtils.getNowTimeStamp());
        tevglBookChapter.setState("Y"); // 状态(Y有效N无效)
        //ValidatorUtils.check(tevglBookChapter);
        // 限制不能超过?级
        int level = 3;
        if (isOverLevel(tevglBookChapter.getParentId(), tevglBookChapter.getSubjectId(), level)) {
            return R.error("目前最多允许增加"+level+"级章节节点");
        }
        // 将同级下后面的节点排序号都+1
        Map<String, Object> map = new HashMap<>();
        map.put("subjectId", subjectId);
        map.put("parentId", parentId);
        List<TevglBookChapter> list = tevglBookChapterMapper.selectListByMap(map);
        List<TevglBookChapter> collect = list.stream().filter(a -> a.getOrderNum() > previousNode.getOrderNum()).collect(Collectors.toList());
        List<TevglBookChapter> updateList = new ArrayList<>();
        collect.stream().forEach(item -> {
            item.setOrderNum(item.getOrderNum() + 1);
            updateList.add(item);
        });
        if (updateList.size() > 0) {
            tevglBookChapterMapper.updateBatchByCaseWhen(updateList);
        }
        // 当前新追节点的节点排序号+1
        tevglBookChapter.setOrderNum(previousNode.getOrderNum() + 1);
        // 入库
        tevglBookChapterMapper.insert(tevglBookChapter);
        // 如果当前登录用户不是此教学包创建者
        //pkgUtils.createTeamDetailData(pkgId, tevglBookChapter.getChapterId(), loginUserId, tevglPkgInfo.getCreateUserId());
        pkgUtils.createTeamDetailDataV2(tevglPkgInfo, tevglBookChapter.getChapterId(), loginUserId);
        // 默认生成[课程内容]分组
        pkgUtils.doCreateDefaultGroup(pkgId, tevglPkgInfo.getSubjectId(), Arrays.asList(tevglBookChapter.getChapterId()), loginUserId);
        return R.ok("追加成功").put("chapterId", tevglBookChapter.getChapterId());
    }

    /**
     * 新增章节时，控制同级不能有重复的名称
     *
     * @param parentId
     * @param chapterName
     * @return
     */
    private boolean isRepetitionName(String parentId, String chapterName) {
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", parentId);
        List<TevglBookChapter> list = tevglBookChapterMapper.selectListByMap(map);
        return list.stream().anyMatch(a -> a.getChapterName().equals(chapterName.trim()));
    }

    /**
     * 新增章节时，验证节点是否已经有3级了
     *
     * @param parentId  目标父节点
     * @param subjectId 课程主键
     * @param level     控制不能超过几级，默认为4
     * @return
     * @apiNote 课程的直系章节理解为一级节点（即parentId匹配subjectId）
     */
    private boolean isOverLevel(String parentId, String subjectId, Integer level) {
        level = level == null ? 3 : level;
        // 如果父节点与课程节点不匹配,则说明增加的不是一级节点,需要校验
        if (!parentId.equals(subjectId)) {
            // 判断
            int i = doCalculationLevel(parentId, subjectId, 1);
            if (i >= level) {
                return true;
            }
        }
        return false;
    }

    /**
     * 计算当前被右键新增的节点，处于树形数据中的第几级
     *
     * @param parentId
     * @param subjectId
     * @param i         固定值1
     * @return
     */
    private int doCalculationLevel(String parentId, String subjectId, int i) {
        TevglBookChapter chapterInfo = tevglBookChapterMapper.selectObjectById(parentId);
        if (chapterInfo == null) {
            return i;
        }
        boolean flag = chapterInfo.getParentId().equals(subjectId);
        // 如果匹配,说明已经到顶级(一级节点了), 则返回结果
        if (flag) {
            return i;
        } else {
            // 如果不匹配,继续递归
            i++;
            return doCalculationLevel(chapterInfo.getParentId(), subjectId, i);
        }
    }

    /**
     * 权限校验(新增章节时所用)
     *
     * @param tevglBookChapter
     * @param loginUserId
     * @return
     */
    private R checkPermission(TevglBookChapter tevglBookChapter, String loginUserId, TevglPkgInfo tevglPkgInfo) {
        String createUserId = null;
        String subjectId = tevglBookChapter.getSubjectId();
        String parentId = tevglBookChapter.getParentId();
        // 表示新增的是直系(一级)章节
        if (subjectId.equals(parentId)) {
            TevglBookSubject subjectInfo = tevglBookSubjectMapper.selectObjectById(subjectId);
            if (subjectInfo == null) {
                return R.error("无效的记录");
            }
            createUserId = subjectInfo.getCreateUserId();
        } else {
            TevglBookChapter chapterInfo = tevglBookChapterMapper.selectObjectById(parentId);
            if (chapterInfo == null) {
                return R.error("无效的记录");
            }
            createUserId = chapterInfo.getCreateUserId();
        }
        log.debug("当前登录用户:[" + loginUserId + "]" + "创建者:[" + createUserId + "]");
        if (createUserId == null) {
            return R.error("暂无权限，操作失败");
        }
        if (tevglPkgInfo == null) {
            return R.error("教学包参数异常");
        }
        // 如果登录用户是教学包的接管者
        if (loginUserId.equals(tevglPkgInfo.getReceiverUserId())) {
            return R.ok();
        }
        // 当创建者不是当前登录用户时,校验
        if (!loginUserId.equals(createUserId)) {
            Map<String, Object> ps = new HashMap<String, Object>();
            ps.put("subjectId", subjectId);
            ps.put("userId", loginUserId);
            List<TevglBookpkgTeam> teamList = tevglBookpkgTeamMapper.selectListByMap(ps);
            if (teamList == null || teamList.size() == 0) {
                return R.error("暂无权限，操作失败");
            }
            // 如果是新增直系(一级)章节
            if (subjectId.equals(parentId)) {
                return R.error("暂无权限新增一级章节");
            } else {
                // 细化到章节校验
                String teamId = teamList.get(0).getTeamId();
                ps.clear();
                ps.put("teamId", teamId);
                List<TevglBookpkgTeamDetail> teamDetailList = tevglBookpkgTeamDetailMapper.selectListByMap(ps);
                log.debug("用户[" + loginUserId + "],拥有章节权限的数量:" + teamDetailList.size());
                if (teamDetailList == null || teamDetailList.size() == 0) {
                    return R.error("没有权限，无法操作，可联系作者授权");
                }
                // 获取拥有的章节权限
                List<String> chapterIdList = teamDetailList.stream().map(a -> a.getChapterId())
                        .collect(Collectors.toList());
                // 去空
                chapterIdList = chapterIdList.stream().filter(a -> StrUtils.isNotEmpty(a)).collect(Collectors.toList());
                if (chapterIdList == null || chapterIdList.size() == 0) {
                    return R.error("没有章节权限，无法操作，可联系作者授权");
                }
                // 当前操作的章节和拥有的权限比较
                if (!chapterIdList.contains(parentId)) {
                    String msg = "";
                    TevglBookChapter chapterInfo = tevglBookChapterMapper.selectObjectById(parentId);
                    if (chapterInfo != null) {
                        msg = "章节【" + chapterInfo.getChapterName() + "】，";
                    }
                    return R.error(msg + "没有权限，操作失败，可联系作者授权");
                }
            }
        }
        // 控制创建者为课程的创建者
        tevglBookChapter.setCreateUserId(createUserId);
        tevglBookChapter.setUpdateUserId(loginUserId);
        return R.ok();
    }

    /**
     * 追加节点时的校验
     * @param tevglBookChapter
     * @return
     */
    private R checkIsPassForAppendPeerNodes(TevglBookChapter tevglBookChapter) {
        String loginUserId = tevglBookChapter.getCreateUserId();
        String pkgId = tevglBookChapter.getPkgId();
        String previousChapterId = tevglBookChapter.getPreviousChapterId();
        // 合法性校验
        String subjectId = tevglBookChapter.getSubjectId();
        String parentId = tevglBookChapter.getParentId();
        String chapterName = tevglBookChapter.getChapterName();
        if (StrUtils.isEmpty(subjectId) || StrUtils.isEmpty(chapterName)
                || StrUtils.isEmpty(parentId) || StrUtils.isEmpty(loginUserId)
                || StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(previousChapterId)) {
            return R.error("必传参数为空");
        }
        // 获取到教学包主键
        TevglPkgInfo pkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
        if (pkgInfo == null) {
            return R.error("无效的记录");
        }
        if (StrUtils.isNotEmpty(pkgInfo.getRefPkgId()) && !"3".equals(pkgInfo.getDisplay()) && !pkgInfo.getPkgId().equals(pkgInfo.getRefPkgId())) {
            return R.error("已不允许新增");
        }
        // 去空格
        tevglBookChapter.setChapterName(tevglBookChapter.getChapterName().trim());
        if (StrUtils.isEmpty(tevglBookChapter.getChapterName())) {
            return R.error("章节名称不能为空");
        }
        if (tevglBookChapter.getChapterName().length() > 50) {
            return R.error("字符长度不能超过50");
        }
        // 同级别不允许有同名的名称
        if (isRepetitionName(parentId, chapterName)) {
            return R.error("同级章节下，不允许有重复的章节名称");
        }
        TevglBookChapter previousNode = tevglBookChapterMapper.selectObjectById(previousChapterId);
        if (previousNode == null) {
            return R.error("无法在该节点下追加节点");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("tevglPkgInfo", pkgInfo);
        data.put("tevglBookChapter", previousNode);
        return R.ok().put(Constant.R_DATA, data);
    }

    /**
     * 拖拽节点
     *
     * @param pkgId
     * @param subjectId   教学包对应的课程教材id
     * @param soureNodeId 当前被选中托拽的节点
     * @param targetId    必传参数，情况：①被拖拽至该节点下，成为其下子节点。②被拖拽至某节点后。详见ztree api <a>http://www.treejs.cn/v3/demo.php#_302</a>
     * @param moveType    指定移动到目标节点的相对位置 "inner"：成为子节点，"prev"：成为同级前一个节点，"next"：成为同级后一个节点
     * @param traineeId   当前登录用户
     * @param list
     * @return
     */
    @Override
    public R dragNode(String pkgId, String subjectId, String soureNodeId, String targetId, String moveType, String traineeId, String list) {
        return null;
    }

    /**
     * 升级节点（例如升级2.1.1三级，则变成2.1二级）
     *
     * @param chapterId
     * @param traineeId 当前登录用户
     * @return
     */
    @Override
    public R upgradeNodes(String chapterId, String traineeId) {
        return null;
    }

    /**
     * 降级
     *
     * @param chapterId
     * @param traineeId
     * @return
     */
    @Override
    public R demotionNodes(String chapterId, String traineeId) {
        return null;
    }

    /**
     * 老师批量新增教材章节目录
     *
     * @param vo
     * @param traineeId
     * @return
     */
    @Override
    public R batchSaveChapter(SaveChapterVo vo, String traineeId) {
        return null;
    }

    /**
     * 根据条件查询记录
     *
     * @param map
     * @return
     */
    @Override
    public R query(Map<String, Object> map) {
        return null;
    }

    /**
     * 根据条件查询记录
     *
     * @param map
     * @return
     */
    @Override
    public R queryForMap(Map<String, Object> map) {
        return null;
    }

    /**
     * 新增
     *
     * @param tevglBookChapter
     * @return
     */
    @Override
    public R save(TevglBookChapter tevglBookChapter) {
        return null;
    }

    /**
     * 修改
     *
     * @param tevglBookChapter
     * @return
     */
    @Override
    public R update(TevglBookChapter tevglBookChapter) {
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
}
