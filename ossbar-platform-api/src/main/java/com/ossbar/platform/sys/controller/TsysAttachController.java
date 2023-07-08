package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author huj
 * @create 2022-09-03 9:14
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/api/sys/attach")
public class TsysAttachController {

    @Reference(version = "1.0.0")
    private TsysAttachService tsysAttachService;

    /**
     * 根据条件查询记录
     *
     * @author huangwb
     * @data 2019年5月29日
     * @param params
     * @return
     */
    @GetMapping("/query")
    @PreAuthorize("hasAuthority('sys:tsysattach:query')")
    @SysLog(value = "根据条件查询记录")
    public R query(@RequestParam(required = false) Map<String, Object> params) {
        return tsysAttachService.query(params);
    }

    /**
     * 删除
     *
     * @author huangwb
     * @data 2019年5月29日
     * @param ids
     * @return
     */
    @DeleteMapping("/deletes")
    @PreAuthorize("hasAuthority('sys:tsysattach:remove')")
    @SysLog(value = "删除")
    public R delete(@RequestBody String[] ids) {
        return tsysAttachService.deleteBatch(ids);
    }

    /**
     * 查看明细
     *
     * @author huangwb
     * @data 2019年5月29日
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    @SysLog(value = "查看明细")
    public R view(@PathVariable("id") String id) {
        return tsysAttachService.view(id);
    }

}
