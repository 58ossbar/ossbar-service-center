package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysLogService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 操作日志
 * @author huj
 * @create 2022-09-05 8:44
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/api/sys/log")
public class TsysLogController {

    @Reference(version = "1.0.0")
    private TsysLogService tsysLogService;

    /**
     * <p>根据条件查询记录</p>
     * @author huj
     * @data 2019年5月20日
     * @param params
     * @return
     */
    @GetMapping("/query")
    @PreAuthorize("hasAuthority('sys:tsyslog:query')")
    //@SysLog("根据条件查询操作日志信息")
    public R query(@RequestParam Map<String, Object> params) {
        return tsysLogService.query(params);
    }

    /**
     * <p>批量删除</p>
     * @author huj
     * @data 2019年5月20日
     * @param ids
     * @return
     */
    @PostMapping("/deletes")
    @PreAuthorize("hasAuthority('sys:tsyslog:remove')")
    //@SysLog("批量删除操作日志")
    public R deleteBatch(@RequestBody String[] ids) {
        return tsysLogService.deleteBatch(ids);
    }
}
