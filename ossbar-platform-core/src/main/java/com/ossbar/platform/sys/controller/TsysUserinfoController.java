package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysResourceService;
import com.ossbar.modules.sys.api.TsysUserinfoService;
import com.ossbar.modules.sys.dto.user.SaveUserDTO;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户管理
 * @author huj
 * @create 2019-05-13 10:19
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/api/sys/user")
public class TsysUserinfoController {

    @Reference(version = "1.0.0")
    private TsysResourceService tsysResourceService;
    @Reference(version = "1.0.0")
    private TsysUserinfoService tsysUserinfoService;

    /**
     * 根据条件分页查询记录
     *
     * @author huj
     * @data 2019年5月13日
     * @param params
     * @return
     */
    @GetMapping("/findPage")
    @PreAuthorize("hasAuthority('sys:tsysuserinfo:query')")
    @SysLog("根据条件查询用户信息")
    public R findPage(@RequestParam Map<String, Object> params) {
        return tsysUserinfoService.query(params);
    }

    /**
     * 查看明细
     * @author huj
     * @data 2019年5月14日
     * @param userId
     * @return
     */
    @GetMapping("/view/{userId}")
    @PreAuthorize("hasAuthority('sys:tsysuserinfo:view')")
    @SysLog("查看用户详细信息")
    public R view(@PathVariable("userId") String userId) {
        return tsysUserinfoService.view(userId);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:tsysuserinfo:add')")
    @SysLog("执行用户相关数据新增和修改保存")
    public R save(@RequestBody @Validated SaveUserDTO user) {
        return tsysUserinfoService.save(user);
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:tsysuserinfo:edit')")
    @SysLog("执行用户相关数据新增和修改保存")
    public R update(@RequestBody @Validated SaveUserDTO user) {
        return tsysUserinfoService.update(user);
    }

    /**
     * 批量删除
     * @author huj
     * @data 2019年5月14日
     * @return
     */
    @PostMapping("/deletes")
    @PreAuthorize("hasAuthority('sys:tsysuserinfo:remove')")
    @SysLog("删除用户")
    public R deletes(@RequestBody String[] ids) {
        return tsysUserinfoService.deleteBatch(ids);
    }

    /**
     * 清空用户的所有权限(表t_user_role)
     * @author huj
     * @data 2019年5月14日
     * @param userIds
     * @return
     */
    @PostMapping("/clearPermissions")
    @PreAuthorize("hasAuthority('sys:tsysuserinfo:clear')")
    @SysLog("清空用户的所有权限")
    public R clearPermissions(@RequestBody List<String> userIds) {
        return tsysUserinfoService.clearPermissions(userIds);
    }

    /**
     * 重置密码,注:密码重置默认为123456
     * @author huj
     * @data 2019年5月14日
     * @return
     */
    @RequestMapping("/resetPassword")
    @PreAuthorize("hasAuthority('sys:tsysuserinfo:reset')")
    @SysLog("重置用户密码")
    public R resetPassword(@RequestBody List<String> ids) {
        return tsysUserinfoService.resetPassword(ids);

    }
}
