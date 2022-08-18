package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysRoleService;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 角色管理
 * @author huj
 * @create 2022-08-17 11:07
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/api/sys/role")
public class TsysRoleController {

    @Reference(version = "1.0.0")
    private TsysRoleService tsysRoleService;

    /**
     * 根据条件分页查询角色列表记录
     * @param params
     * @return
     */
    @GetMapping("/query")
    @PreAuthorize("hasAuthority('sys:role:query')")
    @SysLog("根据条件查询角色信息")
    public R query(@RequestParam Map<String, Object> params) {
        return tsysRoleService.query(params);
    }
}
