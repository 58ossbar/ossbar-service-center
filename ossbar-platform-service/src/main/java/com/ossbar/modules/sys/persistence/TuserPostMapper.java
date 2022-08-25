package com.ossbar.modules.sys.persistence;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.TuserPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhujw
 * @version 1.0
 */

@Mapper
public interface TuserPostMapper extends BaseSqlMapper<TuserPost> {

    List<TuserPost> selectListByPostIds(String[] ids);

    /**
     * 批量新增
     * @param list
     */
    void insertBatch(List<TuserPost> list);

}