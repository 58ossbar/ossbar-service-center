package com.ossbar.modules.sys.persistence;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.TsysAttach;
import com.ossbar.modules.sys.query.AttachQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
public interface TsysAttachMapper extends BaseSqlMapper<TsysAttach> {

    /**
     * 根据条件查询附件id
     * @param query
     * @return
     */
    List<String> findAttachIds(AttachQuery query);

    /**
     * 批量解除绑定关系
     * @param attachIdList
     */
    void unBind(@Param("attachIdList") List<String> attachIdList);

}