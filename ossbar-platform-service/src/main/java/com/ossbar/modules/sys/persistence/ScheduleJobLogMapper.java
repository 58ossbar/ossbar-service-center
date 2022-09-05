package com.ossbar.modules.sys.persistence;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Title:定时任务日志
 * Copyright: Copyright (c) 2017
 * Company:creatorblue.co.,ltd
 * 
 * @author creatorblue.co.,ltd
 * @version 1.0
 */
@Mapper
public interface ScheduleJobLogMapper extends BaseSqlMapper<ScheduleJobLogEntity> {
	
}
