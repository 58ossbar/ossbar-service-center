package com.ossbar.modules.evgl.web.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.medu.me.api.TmeduLiveService;

/**
 * 小程序直播接口
 * @author admin
 *
 */
@RestController
@RequestMapping("/live-api")
public class LiveController {

	@Reference(version = "1.0.0")
	private TmeduLiveService tmeduLiveService;
	
	/**
	 * <p>获取直播间列表</p>
	 * @author zhuq
	 * @data 2021年10月15日
	 * @param start 从第几条开始
	 * @param limit 拉取多少条
	 * @return
	 */
	@GetMapping("/liveinfo")
	public R listPkgLevel(Integer start, Integer limit) {
		return tmeduLiveService.getliveinfo(start, limit);
	}
}
