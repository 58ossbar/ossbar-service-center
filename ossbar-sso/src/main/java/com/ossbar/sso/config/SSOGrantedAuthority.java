package com.ossbar.sso.config;

import org.springframework.security.core.GrantedAuthority;

/**
 * 自定义授权信息类
 * @author zhuq
 *
 */
public class SSOGrantedAuthority implements GrantedAuthority {
	
	private static final long serialVersionUID = 2511676252971554088L;
	private String perms;

    @Override
    public String getAuthority() {
        return perms;
    }

    public SSOGrantedAuthority(String perms) {
        this.perms = perms;
    }
}
