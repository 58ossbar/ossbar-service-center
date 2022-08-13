package com.ossbar.platform.core.config;

import java.util.Iterator;
import java.util.Map;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;

/**
 * Title:Ehcache配置 Copyright: Copyright (c) 2017 Company:creatorblue.co.,ltd
 * 
 * @author creatorblue.co.,ltd
 * @version 1.0
 */
@Configuration
@EnableCaching // 标注启动缓存.
public class EhCacheConfig {

	/**
	 * EhCache的配置
	 */
	@Bean
	public EhCacheCacheManager cacheManager(EhCacheManagerFactoryBean bean) {
		CacheManager manager = bean.getObject();
		try{
			//读取子工程里的ehcache配置文件
			net.sf.ehcache.config.Configuration conf = EhCacheManagerUtils.parseConfiguration(
					new ClassPathResource ("ehcache-custom.xml")
					);
			//将子工程cache节点配置全部添加到platform的ehcache容器中
			Map<String,CacheConfiguration> caches = conf.getCacheConfigurations();
			Iterator<String> it = caches.keySet().iterator();
			while(it.hasNext()){
				Cache cache = new Cache(caches.get(it.next()));
				manager.addCache(cache);
			}
		}catch(Exception e){
		}
		return new EhCacheCacheManager(manager);
	}
	/*
	 * 据shared与否的设置, Spring分别通过CacheManager.create() 或new
	 * CacheManager()方式来创建一个ehcache基地.
	 *
	 * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
	 *
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        try {
        	cacheManagerFactoryBean.setConfigLocation (new ClassPathResource ("ehcache.xml"));
        	cacheManagerFactoryBean.setShared(true);
        	cacheManagerFactoryBean.isSingleton();
            return cacheManagerFactoryBean;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
	}
}
