package com.ossbar.sso.config;

import com.ossbar.utils.tool.TicketDesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 授权配置
 * 
 * @author zhuq
 *
 */
@Configuration
public class SSOSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	private SSOUserDetailsService userDetailsService;

	@Value("${com.creatorblue.anno-filter:}")
	private String[] annoFilter;
	
	@Value("${com.creatorblue.is-cors:false}")
	private boolean isCors;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {

			@Override
			public String encode(CharSequence charSequence) {
				return TicketDesUtil.encryptWithMd5(charSequence.toString(), null);
			}

			@Override
			public boolean matches(CharSequence charSequence, String s) {
				return TicketDesUtil.encryptWithMd5(charSequence.toString(), null).equals(s);
			}
		});
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if(isCors) {
			http.antMatcher("/**")
			.authorizeRequests()
			.antMatchers(annoFilter)
			.permitAll()
			.anyRequest()
			.authenticated()
			.and().cors()
			.and().csrf().disable()
			.formLogin()
			.loginPage("/login").permitAll()
			// 自动登录
			.and().rememberMe()
			// 加密的秘钥
			.key("creatorblue-yyds-666!")
			// 存放在浏览器端cookie的key
			.rememberMeCookieName("remember-me-cookie-name")
			// token失效的时间，单位为秒
			.tokenValiditySeconds(60 * 60 * 24).and()
			// 暂时禁用CSRF，否则无法提交登录表单
			.csrf().disable();
		}else {
			http.antMatcher("/**")
	        .authorizeRequests()
	        .antMatchers(annoFilter)
	        .permitAll()
	        .anyRequest()
	        .authenticated()
	        .and()
	        .formLogin()
			.loginPage("/login").permitAll()
			// 自动登录
			.and().rememberMe()
			// 加密的秘钥
			.key("creatorblue-yyds-666!")
			// 存放在浏览器端cookie的key
			.rememberMeCookieName("remember-me-cookie-name")
			// token失效的时间，单位为秒
			.tokenValiditySeconds(60 * 60 * 24).and();
		}
	}

}
