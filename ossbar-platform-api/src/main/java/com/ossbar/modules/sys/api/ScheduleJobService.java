package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.domain.ScheduleJobEntity;

import java.util.Map;

/**
 * 定时任务
 * @author huj
 * @create 2022-09-05 17:15
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface ScheduleJobService {

    /**
     * 根据条件分页查询定时任务列表
     *
     * @param params
     * @return R
     */
    R query(Map<String, Object> params);

    /**
     * 根据主键id查询记录
     * @param jobId
     * @return
     */
    R selectObjectByJobId(String jobId);

    /**
     * 新增定时任务
     * @return
     */
    R save(ScheduleJobEntity scheduleJob);

    /**
     * 修改定时任务
     * @return
     */
    R update(ScheduleJobEntity scheduleJob);

    /**
     * 删除定时任务
     * @param jobIds
     * @return
     */
    R deleteBatch(String[] jobIds);

    /**
     * 立即执行任务
     * @param jobIds
     * @return
     */
    R runTask(String[] jobIds);

    /**
     * 暂停定时任务
     *
     * @param jobIds
     * @return R
     */
    R pause(String[] jobIds);

    /**
     * 恢复定时任务
     *
     * @param jobIds
     * @return R
     */
    R resume(String[] jobIds);

}
