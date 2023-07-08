package com.ossbar.platform.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${com.creatorblue.anno-filter:}")
	private String[] annoFilter;
	@Value("${com.creatorblue.is-cors:false}")
	private boolean isCors;
	
	@Override
    public void configure(HttpSecurity http) throws Exception {
    	if(isCors) {
    		http.antMatcher("/**")
            .authorizeRequests()
            .antMatchers(annoFilter)
            .permitAll()
            .anyRequest()
            .authenticated()
            .and().cors()
            .and().csrf().disable();
    	}else {
    		http.antMatcher("/**")
            .authorizeRequests()
            .antMatchers(annoFilter)
            .permitAll()
            .anyRequest()
            .authenticated();
    	}
    }

}
