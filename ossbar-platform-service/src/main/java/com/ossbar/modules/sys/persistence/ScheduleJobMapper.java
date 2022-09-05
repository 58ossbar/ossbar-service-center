package com.ossbar.modules.sys.persistence;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * Title:定时任务 Copyright: Copyright (c) 2017 Company:creatorblue.co.,ltd
 * 
 * @author creatorblue.co.,ltd
 * @version 1.0
 */
@Mapper
public interface ScheduleJobMapper extends BaseSqlMapper<ScheduleJobEntity> {

	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
