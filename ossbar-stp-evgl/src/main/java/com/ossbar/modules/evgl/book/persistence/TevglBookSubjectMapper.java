package com.ossbar.modules.evgl.book.persistence;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import org.apache.ibatis.annotations.Mapper;

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

@Mapper
public interface TevglBookSubjectMapper extends BaseSqlMapper<TevglBookSubject> {

    /**
     * 根据条件查询记录，返回List<Map>
     * @param map
     * @return
     */
    List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);

    /**
     * 公共方法，注意此方法只会查询subject_ref为空的记录
     * @param map
     * @return
     */
    List<TevglBookSubject> selectListByMapForCommon(Map<String, Object> map);

}
