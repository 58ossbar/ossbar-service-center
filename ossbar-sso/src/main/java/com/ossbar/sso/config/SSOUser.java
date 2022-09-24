package com.ossbar.sso.config;

import java.util.Collection;

import com.ossbar.modules.sys.domain.TsysUserinfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


/**
 * 自定义认证信息
 * @author zhuq
 *
 */
public class SSOUser extends User{

	private static final long serialVersionUID = 8680386386142028320L;

	public SSOUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	private TsysUserinfo tsysUserinfo;

	public TsysUserinfo getTsysUserinfo() {
		return tsysUserinfo;
	}

	public void setTsysUserinfo(TsysUserinfo tsysUserinfo) {
		this.tsysUserinfo = tsysUserinfo;
	}
	
}
