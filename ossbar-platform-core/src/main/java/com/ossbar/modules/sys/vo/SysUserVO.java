package com.ossbar.modules.sys.vo;

import java.io.Serializable;

/**
 *
 * @author huj
 * @create 2019-05-15 15:03
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class SysUserVO implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户头像
     */
    private String userimg;

    /**
     * 用户真实姓名
     */
    private String userRealname;

    private String token;

    public SysUserVO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SysUserVO{" +
                "userId='" + userId + '\'' +
                ", userimg='" + userimg + '\'' +
                ", userRealname='" + userRealname + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
