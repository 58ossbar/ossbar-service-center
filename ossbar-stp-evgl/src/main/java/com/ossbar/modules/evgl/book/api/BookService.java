package com.ossbar.modules.evgl.book.api;

import java.util.List;
import java.util.Map;

/**
 * @author huj
 * @create 2022-09-19 10:02
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface BookService {


    /**
     * 课堂页面，刷新ztree树专用方法
     * @param id 主键id，可能是课程id、可能是章节id
     * @param pkgId
     * @param subjectId
     * @return
     */
    List<Map<String, Object>> listChaptersForRoomPage(String loginUserId, String id, String pkgId, String subjectId);

    /**
     * 教学包页面，刷新ztree树专用方法
     * @param loginUserId
     * @param id 主键id，可能是课程id、可能是章节id
     * @param serial 序号，前端传递过来的值，形如1.1.1
     * @param type 值可能为subject可能为chapter
     * @param pkgId
     * @param subjectId
     * @return
     */
    List<Map<String, Object>> listChaptersForPkgPage(String loginUserId, String id, String serial, String type, String pkgId, String subjectId);

    /**
     * 删除缓存
     * @param ctId
     * @apiNote
     * room_book::4654d7d821f14c778d5a842e686b4f37_teacher
     * room_book::c1e0397b5737483c8915b958f81b46c9_trainee
     */
    void deleteRoomBookCacheable(String ctId);

}
