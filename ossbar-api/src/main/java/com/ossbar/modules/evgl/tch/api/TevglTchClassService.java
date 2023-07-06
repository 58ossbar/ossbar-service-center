package com.ossbar.modules.evgl.tch.api;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.tch.domain.TevglTchClass;

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
public interface TevglTchClassService extends IBaseService<TevglTchClass> {

    /**
     * 根据条件查询记录
     * @param params
     * @return
     */
    List<TevglTchClass> selectListByMap(Map<String, Object> params);

    /**
     * 查询班级列表
     * @param params
     * @param loginUserId
     * @return
     */
    R queryClassListData(Map<String, Object> params, String loginUserId);

    /**
     * 根据条件查询记录
     * @param params
     * @return
     */
    List<Map<String, Object>> selectSimpleListMap(Map<String, Object> params);

    /**
     * 获取机构年份班级树
     * @param params
     * @return
     */
    R getClassTree(Map<String, Object> params);

    R getClassDictTypeList(Map<String, Object> params);

    /**
     *
     * @param params
     * @return
     */
    R queryClassListForWeb(Map<String, Object> params);
}
