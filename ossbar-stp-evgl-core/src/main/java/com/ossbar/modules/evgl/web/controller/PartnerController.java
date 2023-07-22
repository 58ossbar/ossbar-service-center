package com.ossbar.modules.evgl.web.controller;

import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.api.TevglSitePartnerService;

/**
 * <p>校企合作、合作企业</p>
 * <p>Title: PartnerController.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: 湖南创蓝信息科技有限公司</p> 
 * @author huj
 * @date 2019年7月10日
 */
@RestController
@RequestMapping("/partner-api")
public class PartnerController {

	@Reference(version = "1.0.0")
	private TevglSitePartnerService tevglSitePartnerService;
	
	/**
	 * <p>查询列表</p>
	 * @author huj
	 * @data 2019年7月10日
	 * @param parmas
	 * @return
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> parmas) {
		parmas.put("state", "Y");
		parmas.put("sidx", "cooperation_time");
		parmas.put("order", "desc");
		return tevglSitePartnerService.query(parmas);
	}
	
	/**
	 * <p>查看明细</p>
	 * @author huj
	 * @data 2019年7月4日
	 * @param id
	 * @return
	 */
	@GetMapping("/view/{id}")
	public R view(@PathVariable("id") String id) {
		return tevglSitePartnerService.view(id);
	}
	
}
