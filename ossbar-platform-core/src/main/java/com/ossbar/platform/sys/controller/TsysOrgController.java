package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysOrgService;
import com.ossbar.modules.sys.api.TsysRoleService;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 查询机构列表
     *
     * @param parentId 父机构节点ID orgName机构名称模糊查询条件 type=1代表树形菜单展开否则模糊查询情况
     * @return
     * @author huangwb
     * @date 2019-5-16 11:30
     */
    @GetMapping(value = "/query")
    @PreAuthorize("hasAuthority('sys:tsysorg:query')")
    @SysLog("查询机构列表")
    public R query(@RequestParam(required = false) Map<String, Object> map) {
        return tsysOrgService.queryByMap(map);
    }
}
