package com.ossbar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * 服务端入口程序
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class CreatorbluePlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreatorbluePlatformApplication.class, args);
	}

}
