package com.ossbar.modules.sys.service;

import com.github.pagehelper.PageHelper;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.job.utils.ScheduleUtils;
import com.ossbar.modules.sys.api.ScheduleJobService;
import com.ossbar.modules.sys.domain.ScheduleJobEntity;
import com.ossbar.modules.sys.persistence.ScheduleJobMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huj
 * @create 2022-09-05 17:18
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/job")
@Order(value=Integer.MAX_VALUE)
public class ScheduleJobServiceImpl implements ScheduleJobService, CommandLineRunner {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    /**
     * 项目启动时，初始化定时器
     * Callback used to run the bean.
     *
     * @param arg0 incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... arg0) throws Exception {
        List<ScheduleJobEntity> scheduleJobList = scheduleJobMapper.selectListByMap(new HashMap<>());
        for (ScheduleJobEntity scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            // 如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    /**
     * 根据条件分页查询定时任务列表
     *
     * @param params
     * @return R
     */
    @Override
    public R query(Map<String, Object> params) {
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<ScheduleJobEntity> list = scheduleJobMapper.selectListByMap(params);
        PageUtils pageUtil = new PageUtils(list, query.getPage(), query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 根据主键id查询记录
     *
     * @param jobId
     * @return
     */
    @Override
    public R selectObjectByJobId(String jobId) {
        return R.ok().put(Constant.R_DATA, scheduleJobMapper.selectObjectById(jobId));
    }

    /**
     * 新增定时任务
     *
     * @param scheduleJob
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R save(ScheduleJobEntity scheduleJob) {
        scheduleJob.setCreateTime(DateUtils.getNowTimeStamp());
        scheduleJob.setUpdateTime(scheduleJob.getCreateTime());
        scheduleJob.setJobId(Identities.uuid());
        scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        scheduleJobMapper.insert(scheduleJob);
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        return R.ok();
    }

    /**
     * 修改定时任务
     *
     * @param scheduleJob
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R update(ScheduleJobEntity scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        scheduleJob.setUpdateTime(DateUtils.getNowTimeStamp());
        scheduleJobMapper.update(scheduleJob);
        return R.ok();
    }

    /**
     * 删除定时任务
     *
     * @param jobIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R deleteBatch(String[] jobIds) {
        for (String jobId : jobIds) {
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
        }
        // 删除数据
        scheduleJobMapper.deleteBatch(jobIds);
        return R.ok();
    }

    /**
     * 立即执行任务
     *
     * @param jobIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R runTask(String[] jobIds) {
        for (String jobId : jobIds) {
            ScheduleJobEntity scheduleJobEntity = scheduleJobMapper.selectObjectById(jobId);
            ScheduleUtils.run(scheduler, scheduleJobEntity);
        }
        return R.ok();
    }

    /**
     * 暂停定时任务
     *
     * @param jobIds
     * @return R
     */
    @Override
    public R pause(String[] jobIds) {
        for (String jobId : jobIds) {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }
        updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
        return R.ok();
    }

    /**
     * 恢复定时任务
     *
     * @param jobIds
     * @return R
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R resume(String[] jobIds) {
        for (String jobId : jobIds) {
            ScheduleUtils.resumeJob(scheduler, jobId);
        }
        updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
        return R.ok();
    }

    private int updateBatch(String[] jobIds, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", jobIds);
        map.put("status", status);
        return scheduleJobMapper.updateBatch(map);
    }

}
