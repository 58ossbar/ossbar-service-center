package com.ossbar.modules.wx.site.controller;

import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.api.TevglSiteNewsService;
import com.ossbar.modules.evgl.site.api.TevglSiteResourceextService;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/wx/news-api")
public class WxNewsController {


	@Reference(version = "1.0.0")
	private TevglSiteResourceextService tevglSiteResourceextService;
	@Reference(version = "1.0.0")
	private TevglSiteNewsService tevglSiteNewsService;
	
	/**
	 * <p>获取站点栏目</p>
	 * @author huj
	 * @data 2019年7月15日
	 * @param map
	 * @return
	 */
	@GetMapping("/querySite")
	@SysLog("获取全部站点栏目")
	public R querySite(@RequestParam Map<String, Object> map) {
		String menuId = null;
		if (StrUtils.isNull(map.get("menuId")) || "undefined".equals(menuId) || "null".equals(menuId)) {
			menuId = "e517523468cf41bab3d77b7aa3fa4065";
		} else {
			menuId = map.get("menuId").toString();
		}
		return R.ok().put(Constant.R_DATA, tevglSiteResourceextService.querySite(menuId));
	}
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@GetMapping("/query")
	@SysLog("查询列表(返回List<Bean>)")
	public R query(@RequestParam Map<String, Object> params) {
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
		return tevglSiteNewsService.viewForWx(id);
	}
	
	
}
