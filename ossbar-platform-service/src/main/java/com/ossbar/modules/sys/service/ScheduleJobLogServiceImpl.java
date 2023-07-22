package com.ossbar.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.ScheduleJobLogService;
import com.ossbar.modules.sys.domain.ScheduleJobLogEntity;
import com.ossbar.modules.sys.persistence.ScheduleJobLogMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.Identities;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/joblog")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {
	@Autowired
	private ScheduleJobLogMapper scheduleJobLogMapper;

	/**
	 * 定时任务日志信息
	 * 
	 * @param logid
	 * @return R
	 * 
	 */
	@GetMapping("/info/{logId}")
	@SentinelResource("/sys/joblog/info")
	@Override
	public R selectObjectByLogId(@PathVariable("logId") String logId) {
		return R.ok().put("data", scheduleJobLogMapper.selectListById(logId));
	}

	/**
	 * 定时任务日志列表
	 * 
	 * @param params
	 * @return R
	 */
	@GetMapping("/query")
	@SentinelResource("/sys/joblog/query")
	@Override
	public R query(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<ScheduleJobLogEntity> list = scheduleJobLogMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(list, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	/**
	 * 保存操作
	 * 
	 * @param ScheduleJobLogEntity
	 * @return R
	 */
	@GetMapping("/save")
	@SentinelResource("/sys/joblog/save")
	@Override
	public R save(ScheduleJobLogEntity ScheduleJobLogEntity) {
		ScheduleJobLogEntity.setLogId(Identities.uuid());
		scheduleJobLogMapper.insert(ScheduleJobLogEntity);
		return R.ok();
	}
}
