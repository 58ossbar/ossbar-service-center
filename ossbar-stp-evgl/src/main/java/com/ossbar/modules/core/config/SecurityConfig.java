package com.ossbar.modules.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * 覆盖core工程中的配置，避免与sso工程冲突
 * @author zhuq
 *
 */
@Configuration
public class SecurityConfig {


}
