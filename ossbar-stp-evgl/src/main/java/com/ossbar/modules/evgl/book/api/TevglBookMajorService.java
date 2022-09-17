package com.ossbar.modules.evgl.book.api;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.modules.evgl.book.domain.TevglBookMajor;

import java.util.List;
import java.util.Map;

/**
 * <p> Title: 职业课程</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglBookMajorService extends IBaseService<TevglBookMajor> {


    /**
     * <p>根据条件查询记录，无分页</p>
     * @author huj
     * @data 2019年8月20日
     * @param map
     * @return
     */
    List<TevglBookMajor> selectListByMap(Map<String, Object> map);

    /**
     * <p>职业课程路径列表</p>
     * @author huj
     * @data 2019年7月11日
     * @param map
     * @return
     */
    List<Map<String, Object>> selectListMapByMapForWeb(Map<String, Object> map);

}
