package com.ossbar.modules.sys.controller;

import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysLogService;

/**
 * <p>操作日志</p>
 * <p>Title: TsysLogController.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: 湖南创蓝信息科技有限公司</p> 
 * @author huj
 * @date 2019年5月23日
 */
@RestController
@RequestMapping("/api/sys/log")
public class TsysLogController {

	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TsysLogController.class);
	@Reference(version = "1.0.0")
	private TsysLogService tsysLogService;
	
	/**
	 * <p>根据条件查询记录</p>
	 * @author huj
	 * @data 2019年5月20日
	 * @param params
	 * @return
	 */
	@GetMapping("/query")
	@PreAuthorize("hasAuthority('sys:tsyslog:query')")
	public R query(@RequestParam Map<String, Object> params) {
		params.put("page", params.get("pageNum"));
		params.put("limit", params.get("pageSize"));
		return tsysLogService.query(params);
	}
	
	/**
	 * <p>单条删除</p>
	 * @author huj
	 * @data 2019年5月20日
	 * @param ids
	 * @return
	 */
	@PostMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('sys:tsyslog:remove')")
	public R delete(@PathVariable("id") String id) {
		return tsysLogService.delete(id);
	}
	
	/**
	 * <p>批量删除</p>
	 * @author huj
	 * @data 2019年5月20日
	 * @param ids
	 * @return
	 */
	@PostMapping("/deletes")
	@PreAuthorize("hasAuthority('sys:tsyslog:remove')")
	public R deleteBatch(@RequestBody(required = true) String[] ids) {
		return tsysLogService.deleteBatch(ids);
	}
}
