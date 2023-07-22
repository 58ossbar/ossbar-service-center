package com.ossbar.modules.sys.api;

import java.util.Map;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.domain.ScheduleJobLogEntity;

/**
 * Title:定时任務日誌服務類 Copyright: Copyright (c) 2017 Company:ossbar.co.,ltd
 * 
 * @author ossbar.co.,ltd
 * @version 1.0
 */
public interface ScheduleJobLogService {
	/**
	 * 定时任务日志信息
	 * 
	 * @param logid
	 * @return R
	 * 
	 */
	public R selectObjectByLogId(String logId);

	/**
	 * 定时任务日志列表
	 * 
	 * @param params
	 * @return R
	 */
	R query(Map<String, Object> params);

	/**
	 * 保存操作
	 * 
	 * @param ScheduleJobLogEntity
	 * @return R
	 */
	R save(ScheduleJobLogEntity ScheduleJobLogEntity);
}
