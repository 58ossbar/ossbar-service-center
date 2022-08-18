package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysOrgService;
import com.ossbar.modules.sys.api.TsysRoleService;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 机构管理
 * @author huj
 * @create 2022-08-17 14:55
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/api/sys/org")
public class TsysOrgController {

    @Reference(version = "1.0.0")
    private TsysRoleService tsysRoleService;
    @Reference(version = "1.0.0")
    private TsysOrgService tsysOrgService;

    /**
     * 获取机构树形数据
     * @data 2019年5月14日
     * @return
     */
    @RequestMapping({"/getOrgTree"})
    @SysLog("获取机构树")
    public R getOrgTree() {
        return tsysOrgService.getOrgTree(new HashMap<String, Object>());
    }


}
