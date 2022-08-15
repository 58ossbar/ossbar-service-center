package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysResourceService;
import com.ossbar.platform.core.common.utils.LoginUtils;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private LoginUtils loginUtils;
    @Reference(version = "1.0.0")
    private TsysResourceService tsysResourceService;

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

}
