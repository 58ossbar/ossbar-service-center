package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.dataprivilege.annotation.DataFilter;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.constants.ExecStatus;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysDataprivilegeService;
import com.ossbar.modules.sys.api.TsysRoleService;
import com.ossbar.modules.sys.api.TsysRoleprivilegeService;
import com.ossbar.modules.sys.api.TuserRoleService;
import com.ossbar.modules.sys.domain.TsysRole;
import com.ossbar.modules.sys.dto.role.SaveRoleAssignUserDTO;
import com.ossbar.modules.sys.dto.role.SaveRoleDTO;
import com.ossbar.modules.sys.persistence.*;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.BeanUtils;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huj
 * @create 2022-08-17 11:09
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/role")
public class TsysRoleServiceImpl implements TsysRoleService {

    @Autowired
    private TsysRoleMapper tsysRoleMapper;
    @Autowired
    private TuserRoleMapper tuserRoleMapper;
    @Autowired
    private TsysUserinfoMapper tsysUserinfoMapper;

    @Autowired
    private TsysRoleprivilegeService tsysRoleprivilegeService;
    @Autowired
    private TsysDataprivilegeService tsysDataprivilegeService;
    @Autowired
    private TuserRoleService tuserRoleService;
    @Autowired
    private TsysRoleprivilegeMapper tsysRoleprivilegeMapper;
    @Autowired
    private TsysDataprivilegeMapper tsysDataprivilegeMapper;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private ServiceLoginUtil serviceLoginUtil;

    /**
     * 根据条件分页查询记录
     *
     * @param map
     * @return
     * @author huj
     * @data 2019年5月5日
     */
    @Override
    @RequestMapping("/query")
    @SentinelResource("/sys/role/query")
    @DataFilter(tableAlias = "o", customDataAuth = "", selfUser = false)
    public R query(Map<String, Object> map) {
        // 查询列表数据
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<TsysRole> list = tsysRoleMapper.selectListByMap(map);
        convertUtil.convertOrgId(list, "orgId");
        PageUtils pageUtil = new PageUtils(list, query.getPage(), query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 根据条件查询记录
     *
     * @param map
     * @return
     * @author huj
     * @data 2019年5月5日
     */
    @Override
    @RequestMapping("/queryAll")
    @SentinelResource("/sys/role/queryAll")
    @DataFilter(tableAlias = "o", customDataAuth = "", selfUser = false)
    public R queryAll(Map<String, Object> map) {
        map.put("status", "1");
        Query query = new Query(map);
        List<TsysRole> list = tsysRoleMapper.selectListByMap(query);
        return R.ok().put(Constant.R_DATA, list);
    }

    /**
     * 查看明细
     *
     * @param roleId
     * @return
     */
    @Override
    @RequestMapping("/view")
    @SentinelResource("/sys/role/view")
    public R view(String roleId) {
        TsysRole tsysRole = tsysRoleMapper.selectObjectById(roleId);
        if (tsysRole == null) {
            return R.error(ExecStatus.INVALID_PARAM.getMsg());
        }
        // 根据角色ID,获取菜单ID列表
        tsysRole.setMenuIdList(tsysRoleprivilegeMapper.selectMenuListByRoleId(roleId));
        // 根据角色ID,获取机构ID列表
        tsysRole.setOrgIdList(tsysDataprivilegeMapper.selectOrgListByRoleId(roleId));
        return R.ok().put(Constant.R_DATA, tsysRole);
    }

    /**
     * 根据角色id查询记录
     *
     * @param roleId
     * @return
     */
    @Override
    public TsysRole selectObjectByRoleId(String roleId) {
        return tsysRoleMapper.selectObjectById(roleId);
    }

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @Override
    @PostMapping("/save")
    @SentinelResource("/sys/role/save")
    @CacheEvict(value = "authorization_cache", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public R save(@RequestBody SaveRoleDTO role) throws CreatorblueException {
        TsysRole tsysRole = new TsysRole();
        BeanUtils.copyProperties(tsysRole, role);
        // 角色名称唯一性校验
        TsysRole existedRole = tsysRoleMapper.checkRoleNameUnique(tsysRole.getRoleName());
        if (existedRole != null) {
            return R.error(role.getRoleName() + " 名称已存在，请更改");
        }
        // 检查权限是否越权
        tsysRole.setCreateUserId(serviceLoginUtil.getLoginUserId());
        R r = checkPrems(tsysRole, serviceLoginUtil.getLoginUserId());
        if (!r.get("code").equals(0)) {
            return r;
        }
        // 填充信息
        tsysRole.setRoleId(Identities.uuid());
        tsysRole.setCreateTime(DateUtils.getNowTimeStamp());
        tsysRole.setUpdateTime(tsysRole.getCreateTime());
        tsysRole.setStatus(StrUtils.isEmpty(tsysRole.getStatus()) ? "1" : tsysRole.getStatus());
        // 角色状态：1启用、0禁用
        tsysRole.setStatus("1");
        // 入库
        tsysRoleMapper.insert(tsysRole);
        // 保存角色与菜单关系
        tsysRoleprivilegeService.saveOrUpdate(tsysRole.getRoleId(), role.getMenuIdList());
        // 保存角色与数据权限关系
        tsysDataprivilegeService.saveOrUpdate(tsysRole.getRoleId(), role.getOrgIdList());
        return R.ok("角色新增成功");
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @Override
    @PostMapping("/update")
    @SentinelResource("/sys/role/update")
    @CacheEvict(value = {"menu_list_cache", "authorization_cache"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public R update(SaveRoleDTO role) {
        TsysRole tsysRole = new TsysRole();
        BeanUtils.copyProperties(tsysRole, role);
        // 角色名称唯一性校验
        TsysRole existedRole = tsysRoleMapper.checkRoleNameUnique(tsysRole.getRoleName());
        if (existedRole != null && !existedRole.getRoleId().equals(role.getRoleId())) {
            return R.error(role.getRoleName() + " 名称已存在，请更改");
        }
        // 检查权限是否越权
        String userId = serviceLoginUtil.getLoginUserId();
        R r = checkPrems(tsysRole, userId);
        if (!r.get("code").equals(0)) {
            return r;
        }
        tsysRole.setStatus(StrUtils.isEmpty(tsysRole.getStatus()) ? "1" : tsysRole.getStatus());
        tsysRole.setUpdateUserId(userId);
        tsysRole.setUpdateTime(DateUtils.getNowTimeStamp());
        tsysRoleMapper.update(tsysRole);
        // 保存角色与菜单关系
        tsysRoleprivilegeService.saveOrUpdate(tsysRole.getRoleId(), role.getMenuIdList());
        // 保存角色与数据权限关系
        tsysDataprivilegeService.saveOrUpdate(tsysRole.getRoleId(), role.getOrgIdList());

        return R.ok("角色修改成功");
    }

    /**
     * 批量删除角色信息
     *
     * @param roleIds
     * @return
     * @author huj
     * @data 2019年5月5日
     */
    @Override
    @RequestMapping("/remove")
    @SentinelResource("/sys/role/remove")
    @Transactional
    @SysLog("删除角色信息")
    @CacheEvict(value = {"menu_list_cache", "authorization_cache"}, allEntries = true)
    public R deleteBatch(@RequestBody String[] roleIds) {
        if (roleIds == null || roleIds.length == 0) {
            return R.ok("没有要删除的角色");
        }
        tsysRoleMapper.deleteBatchUserRole(roleIds);
        tsysRoleMapper.deleteBatchRolePrivilege(roleIds);
        tsysRoleMapper.deleteBatchRoleDataPrivilege(roleIds);
        tsysRoleMapper.deleteBatchRole(roleIds);
        return R.ok("角色删除成功");
    }

    /**
     * 分配用户，将角色分配
     *
     * @param roleIds
     * @return
     * @author huj
     * @data 2019年5月5日
     */
    @Override
    @RequestMapping("/sys/role/setUser")
    @SentinelResource("/sys/role/setUser")
    @CacheEvict(value = {"menu_list_cache", "authorization_cache"}, allEntries = true)
    public R setUser(String[] roleIds) {
        if (roleIds == null || roleIds.length == 0) {
            return R.error(ExecStatus.INVALID_PARAM.getCode(), ExecStatus.INVALID_PARAM.getMsg());
        }
        // 获取角色信息
        Map<String, Object> map = new HashMap<>();
        map.put("roleIdArray", roleIds);
        List<TsysRole> list = tsysRoleMapper.selectListByMap(map);
        // 查询角色用户
        list.stream().forEach(tsysRole -> {
            tsysRole.setUserIdList(tuserRoleMapper.selectUserListByRoleId(tsysRole.getRoleId()));
        });
        if (roleIds.length >= 2) {
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) {
                    list.get(i).getUserIdList().retainAll(list.get(i-1).getUserIdList());
                }
            }
        }
        List<String> userIdList = new ArrayList<>();
        if (list.size() > 0) {
            userIdList = list.get(list.size() - 1).getUserIdList();
        }
        return R.ok().put(Constant.R_DATA, list).put("intersection", userIdList);
    }

    /**
     * 保存-分配用户
     *
     * @param role
     * @return
     * @author huj
     * @data 2019年5月6日
     */
    @Override
    @RequestMapping("/saveRoleUser")
    @SentinelResource("/sys/role/saveRoleUser")
    @CacheEvict(value = {"menu_list_cache", "authorization_cache"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public R saveRoleUser(SaveRoleAssignUserDTO role) {
        return tuserRoleService.saveOrUpdate(role.getRoleIdList(), role.getUserIdList());
    }

    /**
     * <p>检查权限是否越权</p>
     * @author huj
     * @data 2019年5月5日
     * @param role 角色
     * @param userId 用户ID
     */
    private R checkPrems(TsysRole role, String userId) {
        // 如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (Constant.SUPER_ADMIN.equalsIgnoreCase(userId)) {
            return R.ok();
        }
        // 查询用户所拥有的菜单列表
        List<String> menuIdList = tsysUserinfoMapper.selectAllMenuId(role.getCreateUserId());
        // 判断是否越权
        if (!menuIdList.containsAll(role.getMenuIdList())) {
            return R.error("新增角色的权限，已超出你的权限范围");
        }
        return R.ok();
    }
}
