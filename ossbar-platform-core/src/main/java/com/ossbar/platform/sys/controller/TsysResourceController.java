package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysResourceService;
import com.ossbar.modules.sys.domain.TsysResource;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 资源菜单管理
 * @author huj
 * @create 2019-05-15 16:26
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/api/sys/resource")
public class TsysResourceController {

    @Reference(version = "1.0.0")
    private TsysResourceService tsysResourceService;

    /**
     * 角色授权菜单
     * @author huj
     * @data 2019年5月14日
     * @return
     */
    @RequestMapping("/perms")
    @SysLog("查询角色授权菜单")
    public R perms() {
        List<TsysResource> list = tsysResourceService.perms();
        return R.ok().put("data", list);
    }


}
