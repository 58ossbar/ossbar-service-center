package com.ossbar.modules.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.util.UrlPathHelper;

@EnableOAuth2Sso
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${com.ossbar.anno-filter:}")
	private String[] annoFilter;
	@Value("${com.ossbar.is-cors:false}")
	private boolean isCors;
	@Bean
	HttpFirewall httpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowSemicolon(true);
		firewall.setAllowUrlEncodedPercent(true);
		firewall.setUnsafeAllowAnyHttpMethod(true);
		return firewall;
	}
	protected void configurePathMatch(PathMatchConfigurer configurer) {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		urlPathHelper.setRemoveSemicolonContent(false);
		configurer.setUrlPathHelper(urlPathHelper);
	}

	@Override
    public void configure(HttpSecurity http) throws Exception {
			//@formatter:off
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
