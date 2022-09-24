package com.ossbar.sso.config;

import com.ossbar.modules.sys.api.TsysResourceService;
import com.ossbar.modules.sys.api.TsysUserinfoService;
import com.ossbar.modules.sys.domain.TsysUserinfo;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
		Set<String> perms = tsysResourceService.getUserPermissions(tsysUserinfo.getUserId());
		log.debug("当前用户拥有的菜单权限 {}", perms);
		if (perms != null && !perms.isEmpty()) {
			perms.parallelStream().forEach(perm -> {
				grantedAuthorityList.add(new SSOGrantedAuthority(perm));
			});
		}
		SSOUser user = new SSOUser(username, tsysUserinfo.getPassword(), grantedAuthorityList);
		user.setTsysUserinfo(tsysUserinfo);
		return user;
	}
}
