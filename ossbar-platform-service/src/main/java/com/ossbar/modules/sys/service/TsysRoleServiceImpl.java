package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.dataprivilege.annotation.DataFilter;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysRoleService;
import com.ossbar.modules.sys.domain.TsysRole;
import com.ossbar.modules.sys.dto.role.SaveRoleDTO;
import com.ossbar.modules.sys.persistence.TsysRoleMapper;
import com.ossbar.modules.sys.persistence.TsysUserinfoMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.BeanUtils;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private TsysUserinfoMapper tsysUserinfoMapper;

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
    public R save(@RequestBody SaveRoleDTO role) throws CreatorblueException {
        TsysRole tsysRole = new TsysRole();
        BeanUtils.copyProperties(tsysRole, role);
        // 角色名称唯一性校验
        TsysRole existedRole = tsysRoleMapper.checkRoleNameUnique(tsysRole.getRoleName());
        if (existedRole != null) {
            return R.error(role.getRoleName() + " 名称已存在，请更改");
        }
        // 检查权限是否越权
        R r = checkPrems(tsysRole, serviceLoginUtil.getLoginUserId());
        if (!r.get("code").equals(0)) {
            return r;
        }
        // 填充信息
        tsysRole.setRoleId(Identities.uuid());
        tsysRole.setCreateTime(DateUtils.getNowTimeStamp());
        tsysRole.setUpdateTime(tsysRole.getCreateTime());
        tsysRole.setRoleType("Y");
        // 入库
        tsysRoleMapper.insert(tsysRole);

        return R.ok("新增成功");
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @Override
    public R update(SaveRoleDTO role) {
        return null;
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
        if (userId.equalsIgnoreCase(Constant.SUPER_ADMIN)) {
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
