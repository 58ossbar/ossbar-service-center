package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysResourceService;
import com.ossbar.modules.sys.api.TsysUserinfoService;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import com.ossbar.platform.core.common.utils.LoginUtils;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

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

    @Autowired
    private LoginUtils loginUtils;

    /**
     * 获取登陆用户的权限标识
     * @author huj
     * @data 2019年5月21日
     * @return
     */
    @GetMapping("/findPermissions")
    public R findPermissions() {
        Set<String> set = tsysResourceService.getUserPermissions(loginUtils.getLoginUserId());
        return R.ok().put(Constant.R_DATA, set);
    }

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
}
