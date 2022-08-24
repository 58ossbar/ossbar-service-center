package com.ossbar.modules.sys.dto.role;

import com.ossbar.common.validator.group.AddGroup;
import com.ossbar.common.validator.group.UpdateGroup;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author huj
 * @create 2022-08-22 14:47
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class SaveRoleDTO implements Serializable {

    /**
     * 角色id
     */
    @NotNull(message = "修改必须指定id", groups = {UpdateGroup.class})
    @Null(message = "新增不能指定id，请不传或传null", groups = {AddGroup.class})
    private String roleId;

    /**
     * 角色名称
     */
    @NotEmpty(message="角色名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 20, message = "角色名称 长度不能超过20位", groups = {AddGroup.class, UpdateGroup.class})
    private String roleName;

    /**
     * 数据范围
     */
    @NotEmpty(message="数据范围不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 2, message = "数据范围 长度不能超过2位", groups = {AddGroup.class, UpdateGroup.class})
    private String dataScope;

    /**
     * 所属机构部门
     */
    @NotEmpty(message="所属机构不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 32, message = "所属机构 长度不能超过32位", groups = {AddGroup.class, UpdateGroup.class})
    private String orgId;

    /**
     * 角色描述
     */
    @Size(min = 0, max = 100, message = "角色描述 长度不能超过100位", groups = {AddGroup.class, UpdateGroup.class})
    private String remark;

    /**
     * 菜单集合
     */
    private List<String> menuIdList;

    /**
     * 所属机构集合
     */
    private List<String> orgIdList;

    public SaveRoleDTO() {
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList) {
        this.menuIdList = menuIdList;
    }

    public List<String> getOrgIdList() {
        return orgIdList;
    }

    public void setOrgIdList(List<String> orgIdList) {
        this.orgIdList = orgIdList;
    }

    @Override
    public String toString() {
        return "SaveRoleDTO{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", dataScope='" + dataScope + '\'' +
                ", orgId='" + orgId + '\'' +
                ", remark='" + remark + '\'' +
                ", menuIdList=" + menuIdList +
                ", orgIdList=" + orgIdList +
                '}';
    }
}
