package com.ossbar.modules.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import com.ossbar.modules.core.common.exception.AuthExceptionEntryPoint;

/**
 * 资源服务配置
 * @author zhuq
 *
 */
@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private AuthExceptionEntryPoint authExceptionEntryPoint;
	
    @Value("${spring.application.name:cbstp-service}")
    private static String RESOURCE_ID;

	@Value("${com.ossbar.is-cors:false}")
	private boolean isCors;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
        resources.authenticationEntryPoint(authExceptionEntryPoint);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	if(isCors) {
    		http.requestMatchers().antMatchers("/api/**").and().authorizeRequests().antMatchers("/api/**").authenticated()
                .and().cors().and().csrf().disable();
    	}else {
    		http.requestMatchers().antMatchers("/api/**").and().authorizeRequests().antMatchers("/api/**").authenticated();
    	}
    }
}
