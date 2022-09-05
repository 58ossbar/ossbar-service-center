package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.ScheduleJobService;
import com.ossbar.modules.sys.domain.ScheduleJobEntity;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author huj
 * @create 2022-09-05 17:06
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/api/sys/job")
public class ScheduleJobController {

    @Reference(version = "1.0.0")
    private ScheduleJobService scheduleJobService;

    /**
     * <p>查询列表</p>
     * @author huj
     * @data 2019年5月30日
     * @param params
     * @return
     */
    @GetMapping("/query")
    @PreAuthorize("hasAuthority('sys:job:query')")
    @SysLog("查询定时任务列表")
    public R query(@RequestParam Map<String, Object> params) {
        return scheduleJobService.query(params);
    }

    /**
     * <p>查询明细</p>
     * @author huj
     * @data 2019年5月30日
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    @PreAuthorize("hasAuthority('sys:job:view')")
    @SysLog("查询定时任务明细")
    public R view(@PathVariable("id") String id) {
        return scheduleJobService.selectObjectByJobId(id);
    }

    /**
     * <p>新增定时任务</p>
     * @author huj
     * @data 2019年5月30日
     * @param scheduleJob
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:job:add')")
    @SysLog("执行定时任务数据新增")
    public R save(@RequestBody ScheduleJobEntity scheduleJob) {
        return scheduleJobService.save(scheduleJob);
    }

    /**
     * 修改定时任务
     * @param scheduleJob
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:job:edit')")
    @SysLog("执行定时任务数据修改")
    public R update(@RequestBody ScheduleJobEntity scheduleJob) {
        return scheduleJobService.update(scheduleJob);
    }

    /**
     * <p>删除</p>
     * @author huj
     * @data 2019年5月30日
     * @param ids
     * @return
     */
    @PostMapping("/deletes")
    @PreAuthorize("hasAuthority('sys:job:remove')")
    @SysLog("删除定时任务")
    public R deletes(@RequestBody String[] ids) {
        return scheduleJobService.deleteBatch(ids);
    }

    /**
     * <p>暂停</p>
     * @author huj
     * @data 2019年5月30日
     * @param ids
     * @return
     */
    @PostMapping("/pause")
    @PreAuthorize("hasAuthority('sys:job:pause')")
    @SysLog("暂停定时任务")
    public R pause(@RequestBody String[] ids) {
        return scheduleJobService.pause(ids);
    }


    /**
     * <p>恢复</p>
     * @author huj
     * @data 2019年5月30日
     * @param ids
     * @return
     */
    @PostMapping("/resume")
    @PreAuthorize("hasAuthority('sys:job:resume')")
    @SysLog("恢复定时任务")
    public R resume(@RequestBody String[] ids) {
        return scheduleJobService.resume(ids);
    }

    /**
     * <p>立即执行</p>
     * @author huj
     * @data 2019年5月30日
     * @param ids
     * @return
     */
    @PostMapping("/runTask")
    @PreAuthorize("hasAuthority('sys:job:run')")
    @SysLog("立即定时任务")
    public R runTask(@RequestBody String[] ids) {
        return scheduleJobService.runTask(ids);
    }
}
