package com.ossbar.modules.mgr.trainee.controller;

import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/trainee/tevgltraineeinfo")
public class TevglTraineeInfoController {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglTraineeInfoController.class);
	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@GetMapping("/query")
	@PreAuthorize("hasAuthority('trainee:tevgltraineeinfo:query')")
	@SysLog("查询列表(返回List<Bean>)")
	public R query(@RequestParam Map<String, Object> params) {
		return tevglTraineeInfoService.query(params);
	}
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@GetMapping("/queryForMap")
	@PreAuthorize("hasAuthority('trainee:tevgltraineeinfo:query')")
	@SysLog("查询列表(返回List<Map<String, Object>)")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		return tevglTraineeInfoService.queryForMap(params);
	}

	/**
	 * 查看明细
	 */
	@GetMapping("/view/{id}")
	@PreAuthorize("hasAuthority('trainee:tevgltraineeinfo:view')")
	@SysLog("查看明细")
	public R view(@PathVariable("id") String id) {
		return tevglTraineeInfoService.view(id);
	}
	
	/**
	 * 执行数据新增
	 * 
	 */
	@PostMapping("/saveorupdate")
	@PreAuthorize("hasAuthority('trainee:tevgltraineeinfo:add') or hasAuthority('trainee:tevgltraineeinfo:edit')")
	@SysLog("执行数据新增")
	public R saveOrUpdate(@RequestBody(required = false) TevglTraineeInfo tevglTraineeInfo) {
		if(StrUtils.isEmpty(tevglTraineeInfo.getTraineeId())) { //新增
			return tevglTraineeInfoService.save(tevglTraineeInfo);
		} else {
			return tevglTraineeInfoService.update(tevglTraineeInfo);
		}
	}
	
	/**
	 * 单条删除
	 */
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('trainee:tevgltraineeinfo:delete')")
	@SysLog("单条删除")
	public R delete(@PathVariable("id") String id) {
		return tevglTraineeInfoService.delete(id);
	}
	
	/**
	 * 批量删除
	 */
	@PostMapping("/deletes")
	@PreAuthorize("hasAuthority('trainee:tevgltraineeinfo:delete')")
	@SysLog("批量删除")
	public R deleteBatch(@RequestBody(required = true) String[] ids) {
		return tevglTraineeInfoService.deleteBatch(ids);
	}
	
	/**
	 * 学员下拉列表
	 * @param params
	 * @return
	 */
	@GetMapping("/listTrainee")
	public R listTrainee(@RequestParam Map<String, Object> params) {
		return tevglTraineeInfoService.listTrainee(params);
	}
	
}
