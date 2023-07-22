package com.ossbar.sso.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ossbar.modules.sys.api.TsysResourceService;
import com.ossbar.modules.sys.api.TsysUserinfoService;
import com.ossbar.modules.sys.domain.TsysUserinfo;

/**
 * 登陆用户认证
 * 
 * @author zhuq
 *
 */
@Component
public class SSOUserDetailsService implements UserDetailsService {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Reference(version = "1.0.0")
	private TsysResourceService tsysResourceService;
	@Reference(version = "1.0.0")
	private TsysUserinfoService tsysUserinfoService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 登录成功后用户认证操作
		TsysUserinfo tsysUserinfo = tsysUserinfoService.selectObjectByUserName(username);
		if(tsysUserinfo == null) {
			log.error("账号【" + username + "】不存在");
			throw new UsernameNotFoundException("账号【" + username + "】不存在");
		}
		if(!"1".equals(tsysUserinfo.getStatus())) {
			log.error("账号【" + username + "】被禁用，请联系管理员");
			throw new UsernameNotFoundException("账号【" + username + "】被禁用，请联系管理员");
		}
		// 测试发现是grantedAuthorityList集合里的值，若有任意一个为null，则会报这个错误
		// GrantedAuthority list cannot contain any null elements
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
		Set<String> perms = tsysResourceService.getUserPermissions(tsysUserinfo.getUserId());
		perms.parallelStream().forEach(perm -> {
			SSOGrantedAuthority ssoGrantedAuthority = new SSOGrantedAuthority(perm);
			// 所以这里做下非空处理
			if (ssoGrantedAuthority != null) {
				grantedAuthorityList.add(ssoGrantedAuthority);
			}
		});
		for (int i = 0; i < grantedAuthorityList.size(); i++) {
			if (grantedAuthorityList.get(i) == null) {
				grantedAuthorityList.remove(i);
				i--;
			}
		}
		SSOUser user = new SSOUser(username, tsysUserinfo.getPassword(), grantedAuthorityList);
		user.setTsysUserinfo(tsysUserinfo);
		return user;
	}
}
