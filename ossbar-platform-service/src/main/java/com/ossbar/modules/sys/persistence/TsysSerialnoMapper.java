package com.ossbar.modules.sys.persistence;
import org.apache.ibatis.annotations.Mapper;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.TsysSerialno;
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
public interface TsysSerialnoMapper extends BaseSqlMapper<TsysSerialno> {

}