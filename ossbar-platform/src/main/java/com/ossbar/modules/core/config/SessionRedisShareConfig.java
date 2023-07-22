package com.ossbar.modules.core.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;

/**
 * 覆盖core工程中的配置，去除redis会话共享配置
 * @author zhuq
 *
 */
@Configuration
//@AutoConfigureBefore(WebMvcConfig.class)
public class SessionRedisShareConfig {

}
