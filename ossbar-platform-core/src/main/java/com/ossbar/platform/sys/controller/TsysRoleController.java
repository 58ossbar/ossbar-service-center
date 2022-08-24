package com.ossbar.platform.sys.controller;

import com.ossbar.common.validator.group.AddGroup;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysRoleService;
import com.ossbar.modules.sys.dto.role.SaveRoleDTO;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 新增角色
     * @param role
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:role:add')")
    @SysLog("执行角色相关数据新增和修改保存")
    public R save(@RequestBody @Validated({AddGroup.class}) SaveRoleDTO role) {
        return tsysRoleService.save(role);
    }
}
