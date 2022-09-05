package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.ScheduleJobLogService;
import com.ossbar.modules.sys.domain.ScheduleJobLogEntity;
import com.ossbar.modules.sys.persistence.ScheduleJobLogMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author huj
 * @create 2022-09-05 17:10
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/joblog")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {

    @Autowired
    private ScheduleJobLogMapper scheduleJobLogMapper;

    /**
     * 定时任务日志信息
     *
     * @param logId
     * @return R
     */
    @Override
    public R selectObjectByLogId(String logId) {
        return R.ok().put(Constant.R_DATA, scheduleJobLogMapper.selectListById(logId));
    }

    /**
     * 根据条件查询定时任务日志列表
     *
     * @param params
     * @return R
     */
    @Override
    @GetMapping("/query")
    @SentinelResource("/sys/joblog/query")
    public R query(Map<String, Object> params) {
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<ScheduleJobLogEntity> list = scheduleJobLogMapper.selectListByMap(params);
        PageUtils pageUtil = new PageUtils(list, query.getPage(), query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 保存操作
     *
     * @param ScheduleJobLogEntity
     * @return R
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R save(ScheduleJobLogEntity ScheduleJobLogEntity) {
        ScheduleJobLogEntity.setLogId(Identities.uuid());
        scheduleJobLogMapper.insert(ScheduleJobLogEntity);
        return R.ok();
    }
}
