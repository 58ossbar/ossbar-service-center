package com.ossbar.modules.job.task;

import com.ossbar.modules.sys.api.TsysUserinfoService;
import com.ossbar.modules.sys.domain.TsysUserinfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Title:测试定时任务(演示Demo，可删除)
 * testTask为spring bean的名称
 * Copyright: Copyright (c) 2017
 * Company:creatorblue.co.,ltd
 * 
 * @author creatorblue.co.,ltd
 * @version 1.0
 */
@Component("testTask")
public class TestTask {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TsysUserinfoService tsysUserinfoService;
	
	public void test(String params){
		logger.info("我是带参数的test方法，正在被执行，参数为：" + params);
		
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		TsysUserinfo user = tsysUserinfoService.selectObjectByUserId("1");
		logger.info(ToStringBuilder.reflectionToString(user));
		
	}
	
	
	public void test2(){
		logger.info("我是不带参数的test2方法，正在被执行");
	}
}
