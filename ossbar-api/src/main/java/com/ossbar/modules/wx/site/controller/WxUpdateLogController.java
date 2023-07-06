package com.ossbar.modules.wx.site.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.api.TevglSiteUpdateLogService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 更新日志
 * @author huj
 *
 */
@RestController
@RequestMapping("/wx/updatelog-api")
public class WxUpdateLogController {

	@Reference(version = "1.0.0")
	private TevglSiteUpdateLogService tevglSiteUpdateLogService;
	
	/**
	 * 查询小程序更新日志
	 * @return
	 */
	@GetMapping("query")
	public R query() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "2"); // 1网站2小程序3APP
		map.put("sidx", "create_time");
		map.put("order", "desc");
		return tevglSiteUpdateLogService.query(map);
	}
	
}
