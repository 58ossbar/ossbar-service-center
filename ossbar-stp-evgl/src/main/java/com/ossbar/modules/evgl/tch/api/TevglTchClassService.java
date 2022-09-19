package com.ossbar.modules.evgl.tch.api;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.tch.domain.TevglTchClass;

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
     * 查询班级列表
     * @param params
     * @param loginUserId
     * @return
     */
    R queryClassListData(Map<String, Object> params, String loginUserId);

}
