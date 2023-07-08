package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.ScheduleJobLogService;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 定时任务日志
 * @author huj
 * @create 2022-09-05 17:06
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/api/sys/jobLog")
public class ScheduleJobLogController {

    @Reference(version = "1.0.0")
    private ScheduleJobLogService scheduleJobLogService;


    /**
     * <p>查询列表</p>
     * @author huj
     * @data 2019年5月30日
     * @param params
     * @return
     */
    @GetMapping("/query")
    @SysLog("查询定时任务日志列表")
    public R query(@RequestParam Map<String, Object> params) {
        return scheduleJobLogService.query(params);
    }
}
