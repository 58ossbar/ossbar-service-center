package com.ossbar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OssbarSsoApplication extends SpringBootServletInitializer {

	/**
	 * 以jar包运行
	 */
	public static void main(String[] args) {
		SpringApplication.run(OssbarSsoApplication.class, args);
	}

	/**
	 * 以war包运行
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OssbarSsoApplication.class);
	}
	
}
