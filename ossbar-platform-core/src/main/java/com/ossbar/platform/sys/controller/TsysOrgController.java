package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysOrgService;
import com.ossbar.modules.sys.api.TsysRoleService;
import com.ossbar.modules.sys.dto.org.SaveOrgDTO;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 保存或者修改操作
     *
     * @param dto
     * @return R
     * @author huangwb
     * @date 2019-5-16 11:30
     */
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:tsysorg:add') and hasAuthority('sys:tsysorg:edit')")
    @SysLog("机构保存或修改")
    public R save(@RequestBody @Validated SaveOrgDTO dto) {
        return tsysOrgService.save(dto);
    }

    /**
     * 删除操作
     *
     * @param ids
     * @return
     * @author huangwb
     * @date 2019-5-16 11:30
     */
    @DeleteMapping("deletes")
    @PreAuthorize("hasAuthority('sys:tsysorg:remove')")
    @SysLog("删除机构")
    public R deleteBatch(@RequestBody String[] ids) {
        return tsysOrgService.deleteBatch(ids);
    }


    /**
     * 拖拽移动机构管理操作
     *
     *
     * @param map moveId移动对象的id targetId目标对象的id moveType移动的类型
     * @author huangwb
     * @date 2019-06-03 17:18
     * @return
     */
    @PostMapping("/drag")
    @SysLog("拖拽移动机构管理")
    @PreAuthorize("hasAuthority('sys:tsysorg:move')")
    public R drag(@RequestBody(required = true) Map<String, Object> map) {
        if (map.get("moveId") == null || StringUtils.isEmpty(map.get("moveId").toString())) {
            return R.error("移动节点不能为空");
        } else if (map.get("targetId") == null || StringUtils.isEmpty(map.get("targetId").toString())) {
            return R.error("目标节点不能为空");
        } else if (map.get("moveType") == null || StringUtils.isEmpty(map.get("moveType").toString())) {
            return R.error("移动类型不能为空");
        }
        return tsysOrgService.drag(map.get("moveId").toString(), map.get("targetId").toString(),
                map.get("moveType").toString());
    }

    /**
     * 点击上下按钮移动机构管理节点操作
     *
     * @param moveId   移动对象的id
     * @param targetId 目标对象的id
     * @return
     * @author huangwb
     * @date 2019-06-17 14:18
     */
    @PostMapping("/clickDrag")
    @SysLog("点击按钮移动机构管理")
    @PreAuthorize("hasAuthority('sys:tsysorg:move')")
    public R clickDrag(@RequestBody(required = true) Map<String, Object> map) {
        if (map.get("moveId") == null || StringUtils.isEmpty(map.get("moveId").toString())) {
            return R.error("移动节点不能为空");
        } else if (map.get("targetId") == null || StringUtils.isEmpty(map.get("targetId").toString())) {
            return R.error("目标节点不能为空");
        }
        return tsysOrgService.clickDrag(map.get("moveId").toString(), map.get("targetId").toString());
    }
}
