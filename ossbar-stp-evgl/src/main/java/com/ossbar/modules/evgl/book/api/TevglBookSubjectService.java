package com.ossbar.modules.evgl.book.api;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;

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
     * 课程下拉列表，注意此方法只会查询subject_ref为空的记录
     * @param params
     * @return
     */
    R listSelectSubject(Map<String, Object> params);

}
