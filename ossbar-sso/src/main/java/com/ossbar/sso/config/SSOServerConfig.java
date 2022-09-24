package com.ossbar.sso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import javax.sql.DataSource;

/**
 * 认证服务配置
 * 
 * @author zhuq
 *
 */
@Configuration
@EnableAuthorizationServer
public class SSOServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Autowired
	private SSOUserDetailsService userDetailsService;

	@Autowired
	private DataSource dataSource;
	
	@Value("${com.creatorblue.redisFlag:true}")
	private boolean redisFlag;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 配置token获取和验证时的策略
		security.tokenKeyAccess("permitAll()")
				// 验证通过，返回token信息
				.checkTokenAccess("isAuthenticated()");
				// 允许客户端使用client_id和client_secret获取token
				//.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		/*
		 * clients.inMemory() .withClient("client")
		 * .redirectUris("http://localhost:8082/console",
		 * "http://localhost:8082/console/api/user",
		 * "http://localhost:8082/console/login") // secret密码配置从 Spring Security
		 * 5.0开始必须以 {加密方式}+加密后的密码 这种格式填写 .secret("{noop}secret") .scopes("all")
		 * .authorizedGrantTypes("authorization_code", "password", "refresh_token")
		 * .autoApprove(true);
		 */
		clients.jdbc(dataSource);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// 配置tokenStore，保存到redis缓存中
		DefaultAccessTokenConverter datc = new DefaultAccessTokenConverter();
		datc.setUserTokenConverter(new CbAuthenticationConverter());
		endpoints.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService)
				.accessTokenConverter(datc);
		if(redisFlag) {
			endpoints.tokenStore(new SSORedisTokenStore(redisConnectionFactory));
		}
	}

}
