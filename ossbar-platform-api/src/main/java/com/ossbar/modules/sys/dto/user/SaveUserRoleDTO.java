package com.ossbar.modules.sys.dto.user;

import java.io.Serializable;
import java.util.List;

/**
 * 为用户分配角色
 * @author huj
 * @create 2022-09-13 9:31
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class SaveUserRoleDTO implements Serializable {

    private List<String> roleIds;

    private List<String> userIds;

    public SaveUserRoleDTO() {
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    @Override
    public String toString() {
        return "SaveUserRoleDTO{" +
                "roleIds=" + roleIds +
                ", userIds=" + userIds +
                '}';
    }
}
