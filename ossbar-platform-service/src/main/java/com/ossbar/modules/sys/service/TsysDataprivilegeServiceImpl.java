package com.ossbar.modules.sys.service;

import com.ossbar.common.cbsecurity.dataprivilege.annotation.DataFilter;
import com.ossbar.modules.sys.api.TsysDataprivilegeService;
import com.ossbar.modules.sys.api.TsysOrgService;
import com.ossbar.modules.sys.domain.TsysRole;
import com.ossbar.modules.sys.persistence.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色与数据权限关系
 * @author huj
 * @create 2022-08-17 11:12
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/dataprivilege")
public class TsysDataprivilegeServiceImpl implements TsysDataprivilegeService {

    private static Logger log = LoggerFactory.getLogger(TsysDataprivilegeServiceImpl.class);

    @Autowired
    private TuserRoleMapper tuserRoleMapper;
    @Autowired
    private TsysRoleMapper tsysRoleMapper;
    @Autowired
    private TsysDataprivilegeMapper tsysDataprivilegeMapper;
    @Autowired
    private TsysOrgMapper tsysOrgMapper;
    @Autowired
    private TorgUserMapper torgUserMapper;
    @Autowired
    private TsysOrgService tsysOrgService;

    /**
     * 获取数据过滤的SQL
     *
     * @param userId
     * @param tableAlias
     * @param point
     * @return
     */
    @Override
    @Cacheable(value = "authorization_cache", key = "'getSQLFilter_'+#userId+'_'+#tableAlias")
    public String getSQLFilter(String userId, String tableAlias, Object point) {
        MethodSignature signature = (MethodSignature) ((JoinPoint)point).getSignature();
        DataFilter dataFilter = signature.getMethod().getAnnotation(DataFilter.class);
        String datascope = dataFilter.customDataAuth();
        /******************************
         * 基于注解型或配置型数据权限支持
         *************************************************************/
        // 1、通过用户id查询用户归属部门：同一用户会涉及多个部门的情况，同时用户可能多角色的情况(配置数据权限时合并)
        Set<String> orgIdList = new HashSet<>();
        StringBuilder sqlFilter = new StringBuilder();
        // 数据范围（1：所有数据；2：所在机构及以下数据；3：所在机构数据；4：仅本人数据；5：按明细设置）
        // 获取用户分配的角色数据权限，根据角色数据权限范围来匹配相应的数据权限
        // 用户自定义角色:5：按明细设置
        List<String> roleIdList = tuserRoleMapper.selectRoleListByUserId(userId);
        if (roleIdList.size() > 0) {
            for (String roleId : roleIdList) {
                TsysRole tsysRole = tsysRoleMapper.selectObjectById(roleId);
                datascope = tsysRole.getDataScope();
                // 5自定义明细的情况
                if (dataFilter.customOrg() || datascope.equalsIgnoreCase("5")) {
                    List<String> useOrgIdList = tsysDataprivilegeMapper.selectOrgListByRoleIds(roleIdList.toArray(new String[roleIdList.size()]));
                    orgIdList.addAll(useOrgIdList);
                }

                // 1查询所有数据
                if (dataFilter.allOrg() || datascope.equalsIgnoreCase("1")) {
                    List<String> allOrgIdList = tsysOrgMapper.getAllOrgIds();
                    orgIdList.addAll(allOrgIdList);
                }
                // 2：所在机构及以下数据
                List<String> org = torgUserMapper.selectListByUserId(userId);
                for (String orgid : org) {
                    // 用户查询本部门及子部门数据
                    if (dataFilter.selfAndSubOrg() || datascope.equalsIgnoreCase("2")) {
                        @SuppressWarnings("unchecked")
                        List<String> subOrgIdList = tsysOrgService.getSubOrgIdList(orgid);
                        orgIdList.add(orgid);
                        orgIdList.addAll(subOrgIdList);
                    }
                    // 3：所在机构:本机构数据(此问题暂时还没解决，构造树时根结点的问题)
                    if (dataFilter.selfOrg() || datascope.equalsIgnoreCase("3")) {
                        // List<String> subOrgIdList = tsysOrgService.getSubOrgIdList(orgid);
                        // orgIdList.add("-1");
                        // orgIdList.addAll(subOrgIdList);
                        orgIdList.add(orgid);
                    }
                }
                // orgIdList.addAll(org);
                // 4用户只能查询本人数据
                if (dataFilter.selfUser() || datascope.equalsIgnoreCase("4")) {
                    sqlFilter.append(tableAlias).append("create_user_id='").append(userId + "' ");
                }
            }
        }
        if (orgIdList.size() > 0) {
            if (sqlFilter.toString().length() > 0) {
                sqlFilter.append(" or");
            }
            sqlFilter.append(" (");
            sqlFilter.append(tableAlias).append("org_id in(").append("'").append(StringUtils.join(orgIdList, "','"))
                    .append("'").append(")");
            sqlFilter.append(")");
        }else {
            if (sqlFilter.toString().length() > 0) {
                sqlFilter.append(" or");
            }
            sqlFilter.append(" 1!=1");
        }
        log.debug("数据权限sql：" + sqlFilter);
        return sqlFilter.toString();
    }
}
