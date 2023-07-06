package com.ossbar.modules.evgl.tch.api;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.tch.dto.SaveTeacherDTO;

import java.util.List;
import java.util.Map;


/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglTchTeacherService extends IBaseService<TevglTchTeacher> {

    TevglTchTeacher selectObjectById(Object id);

    TevglTchTeacher selectObjectByTraineeId(Object id);

    List<TevglTchTeacher> selectListByMap(Map<String, Object> map);

    /**
     * 管理端新增教师
     * @param dto
     * @return
     */
    R saveTeacherInfo(SaveTeacherDTO dto);

    /**
     * 管理端修改教师
     * @param dto
     * @return
     */
    R updateTeacherInfo(SaveTeacherDTO dto);

    R updateStateOrShowIndex(TevglTchTeacher tevglTchTeacher);

    /**
     * 根据条件查询记录，返回List<T>，关联学员表
     * @param map
     * @return
     */
    List<TevglTchTeacher> selectListByMapInnerJoinTraineeTable(Map<String, Object> map);
}
