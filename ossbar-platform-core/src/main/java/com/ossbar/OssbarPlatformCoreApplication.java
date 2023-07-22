package com.ossbar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class OssbarPlatformCoreApplication extends SpringBootServletInitializer {

	/**
	 * 以jar包运行
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(OssbarPlatformCoreApplication.class, args);
		
	}

	/**
	 * 以war包运行
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OssbarPlatformCoreApplication.class);
		
	}

}
