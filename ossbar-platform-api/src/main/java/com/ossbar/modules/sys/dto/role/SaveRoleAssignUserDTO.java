package com.ossbar.modules.sys.dto.role;

import java.io.Serializable;
import java.util.List;

/**
 * @author huj
 * @create 2022-08-25 9:51
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class SaveRoleAssignUserDTO implements Serializable {

    /**
     * 角色id集
     */
    private List<String> roleIdList;

    /**
     * 用户id集
     */
    private List<String> userIdList;

    public SaveRoleAssignUserDTO() {
    }

    public SaveRoleAssignUserDTO(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public List<String> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    @Override
    public String toString() {
        return "SaveAssignUserDTO{" +
                "roleIdList=" + roleIdList +
                ", userIdList=" + userIdList +
                '}';
    }
}
