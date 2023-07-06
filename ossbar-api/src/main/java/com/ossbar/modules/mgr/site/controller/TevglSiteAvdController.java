package com.ossbar.modules.mgr.site.controller;

import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.api.TevglSiteAvdService;
import com.ossbar.modules.evgl.site.domain.TevglSiteAvd;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@RestController
@RequestMapping("/api/site/tevglsiteavd")
public class TevglSiteAvdController {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglSiteAvdController.class);
	@Reference(version = "1.0.0")
	private TevglSiteAvdService tevglSiteAvdService;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@GetMapping("/query")
	@PreAuthorize("hasAuthority('site:tevglsiteavd:query')")
	@SysLog("查询列表(返回List<Bean>)")
	public R query(@RequestParam Map<String, Object> params) {
		return tevglSiteAvdService.query(params);
	}
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@GetMapping("/queryForMap")
	@PreAuthorize("hasAuthority('site:tevglsiteavd:query')")
	@SysLog("查询列表(返回List<Map<String, Object>)")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		return tevglSiteAvdService.queryForMap(params);
	}

	/**
	 * 查看明细
	 */
	@GetMapping("/view/{id}")
	@PreAuthorize("hasAuthority('site:tevglsiteavd:view')")
	@SysLog("查看明细")
	public R view(@PathVariable("id") String id) {
		return tevglSiteAvdService.view(id);
	}
	
	/**
	 * 执行数据新增
	 * 
	 */
	@PostMapping("/saveorupdate")
	@PreAuthorize("hasAuthority('site:tevglsiteavd:add') or hasAuthority('site:tevglsiteavd:edit')")
	@SysLog("执行数据新增")
	public R saveOrUpdate(@RequestBody(required = false) TevglSiteAvd tevglSiteAvd, HttpServletRequest request) {
		String attachId = request.getParameter("attachId"); // pc端的图片附件关联id
		String attachIdForMobile = request.getParameter("attachIdForMobile");
		if(StrUtils.isEmpty(tevglSiteAvd.getAvdId())) { //新增
			return tevglSiteAvdService.save2(tevglSiteAvd, attachId, attachIdForMobile);
		} else {
			return tevglSiteAvdService.update2(tevglSiteAvd, attachId, attachIdForMobile);
		}
	}
	
	/**
	 * 单条删除
	 */
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('site:tevglsiteavd:delete')")
	@SysLog("单条删除")
	public R delete(@PathVariable("id") String id) {
		return tevglSiteAvdService.delete(id);
	}
	
	/**
	 * 批量删除
	 */
	@PostMapping("/deletes")
	@PreAuthorize("hasAuthority('site:tevglsiteavd:delete')")
	@SysLog("批量删除")
	public R deleteBatch(@RequestBody(required = true) String[] ids) {
		return tevglSiteAvdService.deleteBatch(ids);
	}
	
	/**
	 * <p>更新状态</p>
	 * @author huj
	 * @data 2019年7月28日
	 * @param tevglSiteAvd
	 * @return
	 */
	@PostMapping("/updateStatus")
	@PreAuthorize("hasAuthority('site:tevglsiteavd:edit')")
	public R updateStatus(@RequestBody(required = false) TevglSiteAvd tevglSiteAvd) {
		return tevglSiteAvdService.updateState(tevglSiteAvd);
	}
}
