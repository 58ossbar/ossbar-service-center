package com.ossbar.modules.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.modules.job.utils.ScheduleUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.ScheduleJobService;
import com.ossbar.modules.sys.domain.ScheduleJobEntity;
import com.ossbar.modules.sys.persistence.ScheduleJobMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.constants.Constant.ScheduleStatus;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.github.pagehelper.PageHelper;

@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/job")
@Order(value=Integer.MAX_VALUE)
public class ScheduleJobServiceImpl implements ScheduleJobService, CommandLineRunner{
	@Autowired
	private Scheduler scheduler;
	@Autowired
	private ScheduleJobMapper scheduleJobMapper;

	/**
	 * 项目启动时，初始化定时器
	 * 
	 * @param arg0
	 * @return R
	 */
	@Override
	public void run(String... arg0) {
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
	 * 定时任务信息
	 * 
	 * @param jobId
	 * @return R
	 */
	@RequestMapping("/info/{jobId}")
	@SentinelResource("/sys/job/info")
	@Override
	public R selectObjectByJobId(@PathVariable("jobId") String jobId) {
		return R.ok().put(Constant.R_DATA, scheduleJobMapper.selectObjectById(jobId));
	}

	/**
	 * 保存或者修改
	 * 
	 * @param scheduleJob
	 * @return R
	 */
	@PostMapping("/saveOrUpdate")
	@SentinelResource("/sys/job/saveOrUpdate")
	@Transactional
	@Override
	public R saveOrUpdate(@RequestBody ScheduleJobEntity scheduleJob) {
		boolean validExpression = CronExpression.isValidExpression(scheduleJob.getCronExpression());
        if (!validExpression) {
            return R.error("请输入正确的cron表达式！");
        }
		try {
			if (scheduleJob.getJobId() == null || StringUtils.isEmpty(scheduleJob.getJobId())) {
				scheduleJob.setCreateTime(DateUtils.getNowTimeStamp());
				scheduleJob.setUpdateTime(scheduleJob.getCreateTime());
				scheduleJob.setJobId(Identities.uuid());
				scheduleJob.setStatus(ScheduleStatus.NORMAL.getValue());
				scheduleJobMapper.insert(scheduleJob);
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			} else {
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
				scheduleJob.setUpdateTime(DateUtils.getNowTimeStamp());
				scheduleJobMapper.update(scheduleJob);
			}
			return R.ok("操作成功");
		} catch (Exception e) {
			return R.error(e.getMessage());
		}
	}

	/**
	 * 删除定时任务
	 * 
	 * @param jobIds
	 * @return R
	 */
	@DeleteMapping("/remove")
	@SentinelResource("/sys/job/remove")
	@Transactional
	@Override
	public R deleteBatch(@RequestBody String[] jobIds) {
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
	 * @return R
	 */
	@PostMapping("/run")
	@SentinelResource("/sys/job/run")
	@Override
	public R runTask(@RequestBody String[] jobIds) {
		for (String jobId : jobIds) {
			ScheduleUtils.run(scheduler, (ScheduleJobEntity) selectObjectByJobId(jobId).get("data"));
		}
		return R.ok();
	}

	/**
	 * 暂停定时任务
	 * 
	 * @param arg0
	 * @return R
	 */
	@PostMapping("/pause")
	@SentinelResource("/sys/job/pause")
	@Transactional
	@Override
	public R pause(@RequestBody String[] jobIds) {
		for (String jobId : jobIds) {
			ScheduleUtils.pauseJob(scheduler, jobId);
		}
		updateBatch(jobIds, ScheduleStatus.PAUSE.getValue());
		return R.ok();
	}

	/**
	 * 恢复定时任务
	 * 
	 * @param jobIds
	 * @return R
	 */
	@PostMapping("/resume")
	@SentinelResource("/sys/job/resume")
	@Transactional
	@Override
	public R resume(@RequestBody String[] jobIds) {
		for (String jobId : jobIds) {
			ScheduleUtils.resumeJob(scheduler, jobId);
		}
		updateBatch(jobIds, ScheduleStatus.NORMAL.getValue());
		return R.ok();
	}

	/**
	 * 定时任务列表
	 * 
	 * @param params
	 * @return R
	 */
	@GetMapping("/query")
	@SentinelResource("/sys/job/query")
	@Override
	public R query(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<ScheduleJobEntity> list = scheduleJobMapper.selectListByMap(params);
		PageUtils pageUtil = new PageUtils(list, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	private int updateBatch(String[] jobIds, int status) {
		Map<String, Object> map = new HashMap<>();
		map.put("list", jobIds);
		map.put("status", status);
		return scheduleJobMapper.updateBatch(map);
	}
}
