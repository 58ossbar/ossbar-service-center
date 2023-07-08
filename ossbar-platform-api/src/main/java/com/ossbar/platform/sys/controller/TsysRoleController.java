package com.ossbar.platform.sys.controller;

import com.ossbar.common.validator.group.AddGroup;
import com.ossbar.common.validator.group.UpdateGroup;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysRoleService;
import com.ossbar.modules.sys.dto.role.SaveRoleAssignUserDTO;
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
    @SysLog("根据条件分页查询角色信息")
    public R query(@RequestParam Map<String, Object> params) {
        return tsysRoleService.query(params);
    }

    /**
     * 根据条件查询角色列表记录
     * @param params
     * @return
     */
    @GetMapping("/queryAll")
    @PreAuthorize("hasAuthority('sys:role:query')")
    @SysLog("根据条件查询角色信息")
    public R queryAll(@RequestParam Map<String, Object> params) {
        return tsysRoleService.queryAll(params);
    }

    /**
     * 查看
     * @author huj
     * @data 2019年5月29日
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    @PreAuthorize("hasAuthority('sys:role:view')")
    public R selectObjectById(@PathVariable("id") String id) {
        return tsysRoleService.view(id);
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

    /**
     * 新增角色
     * @param role
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @SysLog("执行角色相关数据新增和修改保存")
    public R update(@RequestBody @Validated({UpdateGroup.class}) SaveRoleDTO role) {
        return tsysRoleService.update(role);
    }

    /**
     * <p>删除</p>
     * <p>角色表t_sys_role, 用户与角色关系t_user_role, 角色与菜单关系t_sys_roleprivilege, 角色与数据权限关系t_sys_dataprivilege</p>
     * @author huj
     * @data 2019年5月13日
     * @param ids
     * @return
     */
    @RequestMapping("/deletes")
    @PreAuthorize("hasAuthority('sys:role:remove')")
    @SysLog("删除角色")
    public R delete(@RequestBody(required = false) String[] ids) {
        return tsysRoleService.deleteBatch(ids);
    }

    /**
     * 分配用户，获取拥有相关角色的用户，用于回显选中数据
     * @author huj
     * @data 2019年5月13日
     * @param roleIds
     * @return
     */
    @PostMapping("/setUser")
    @PreAuthorize("hasAuthority('sys:role:setUser')")
    @SysLog("分配用户")
    public R setUser(@RequestBody String[] roleIds) {
        return tsysRoleService.setUser(roleIds);
    }

    /**
     * 分配用户，保存用户角色，表t_user_role
     * @author huj
     * @data 2019年5月16日
     * @param role
     * @param role
     * @return
     */
    @RequestMapping("/saveRoleUser")
    @PreAuthorize("hasAuthority('sys:role:save')")
    @SysLog("分配用户-保存用户与角色关系")
    public R saveRoleUser(@RequestBody SaveRoleAssignUserDTO role) {
        return tsysRoleService.saveRoleUser(role);
    }
}
