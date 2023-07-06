package com.ossbar.modules.evgl.book.api;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.book.domain.TevglBookChapter;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.book.vo.TevglBookSubjectSelectVo;
import com.ossbar.modules.sys.domain.TsysDict;

import java.util.List;
import java.util.Map;

/**
 * <p> Title: 课程教材</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglBookSubjectService extends IBaseService<TevglBookSubject> {

    /**
     * 根据主键id查询记录
     * @param id
     * @return
     */
    TevglBookSubject selectObjectById(String id);

    /**
     * 获取树形数据（只包含章节）
     * @param subjectId
     * @return
     */
    List<Map<String, Object>> getTree(String subjectId, String pkgId);

    /**
     * 课程下拉列表，注意此方法只会查询subject_ref为空的记录
     * @param params
     * @return
     */
    R listSelectSubject(Map<String, Object> params);

    /**
     * 获取一本书（章节树）（课堂页面专用）（优化版）
     * @param ctId
     * @param pkgId
     * @param subjectId
     * @param chapterName
     * @param loginUserId
     * @param queryForWx
     * @param identity 身份标识，用于区别缓存，值：teacher、trainee
     * @return
     */
    R getBookForRoomPage(String ctId, String pkgId, String subjectId, String chapterName, String loginUserId, boolean queryForWx, String identity);

    /**
     * 获取一本书（章节树）（教学包页面专用）（优化版）
     * @param pkgId
     * @param subjectId
     * @param chapterName
     * @param loginUserId
     * @return
     */
    R getBookForPkgPage(String pkgId, String subjectId, String chapterName, String loginUserId);

    /**
     * 递归构建树
     * @param parentId
     * @param allList
     * @param level
     * @return
     */
    List<TevglBookChapter> buildBook(String parentId, List<TevglBookChapter> allList, int level);

    /**
     * <p>根据条件查询记录</p>
     * @author huj
     * @data 2019年12月25日
     * @param map 参数中若isSubjectRefNull为"Y"则查询的是课程，若isSubjectRefNotNull为"Y"则查询的是活教材
     * @return
     */
    R selectListByMapForMgr(Map<String, Object> map);

    /**
     * 管理端获取树形数据
     * @param subjectId
     * @return
     */
    R getTreeForMgr(String subjectId);

    /**
     * <p>从字典获取活教材封面图，满足前端录入界面需要</p>
     * @author huj
     * @data 2019年8月6日
     * @return
     */
    List<TsysDict> getSubjectLogo();

    /**
     * <p>更新状态</p>
     * @author huj
     * @data 2019年7月27日
     * @param tevglBookSubject
     * @return
     */
    R updateState(TevglBookSubject tevglBookSubject);

    /**
     * 根据条件查询课程
     * @param map
     * @return
     */
    List<TevglBookSubjectSelectVo> querySubjectList(Map<String, Object> map);
}
