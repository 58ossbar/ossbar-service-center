package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.domain.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 * @author huj
 * @create 2022-09-05 17:09
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface ScheduleJobLogService {

    /**
     * 定时任务日志信息
     *
     * @param logId
     * @return R
     *
     */
    public R selectObjectByLogId(String logId);

    /**
     * 根据条件查询定时任务日志列表
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
