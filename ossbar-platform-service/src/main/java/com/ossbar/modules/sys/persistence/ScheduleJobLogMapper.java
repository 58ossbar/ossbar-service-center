package com.ossbar.modules.sys.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.ScheduleJobLogEntity;
/**
 * Title:定时任务日志
 * Copyright: Copyright (c) 2017
 * Company:ossbar.co.,ltd
 * 
 * @author ossbar.co.,ltd
 * @version 1.0
 */
@Mapper
public interface ScheduleJobLogMapper extends BaseSqlMapper<ScheduleJobLogEntity> {
	
}
