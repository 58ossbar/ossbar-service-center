package com.ossbar.modules.evgl.web.controller;

import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.api.TevglSiteNewsService;

/**
 * <p>新闻资讯</p>
 * <p>Title: NewsController.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: 湖南创蓝信息科技有限公司</p> 
 * @author huj
 * @date 2019年7月10日
 */
@RestController
@RequestMapping("news-api")
public class NewsController {

	@Reference(version = "1.0.0")
	private TevglSiteNewsService tevglSiteNewsService;
	
	/**
	 * <p>查询列表</p>
	 * @author huj
	 * @data 2019年7月10日
	 * @param params
	 * @return
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		params.put("scene", "0");
		params.put("status", "2"); // 状态1待审2已发布 3删除
		params.put("sidx", "create_time");
		params.put("order", "desc");
		return tevglSiteNewsService.query(params);
	}
	
	/**
	 * <p>查看新闻资讯明细</p>
	 * @author huj
	 * @data 2019年7月10日
	 * @param id
	 * @return
	 */
	@GetMapping("/view/{id}")
	public R view(@PathVariable("id") String id) {
		return tevglSiteNewsService.view(id);
	}
	
	/**
	 * <p>从字典获取新增资讯分类,如企业新闻,行业新闻等</p>
	 * @author huj
	 * @data 2019年7月16日
	 * @return
	 */
	@GetMapping("/getCategory")
	public R getCategory() {
		return tevglSiteNewsService.getCategory();
	}
}
