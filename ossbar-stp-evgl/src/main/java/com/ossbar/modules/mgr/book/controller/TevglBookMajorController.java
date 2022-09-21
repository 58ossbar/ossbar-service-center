package com.ossbar.modules.mgr.book.controller;

import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.book.api.TevglBookMajorService;
import com.ossbar.modules.evgl.book.domain.TevglBookMajor;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p> Title: 职业课程</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@RestController
@RequestMapping("/api/book/tevglbookmajor")
public class TevglBookMajorController {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglBookMajorController.class);
	@Reference(version = "1.0.0")
	private TevglBookMajorService tevglBookMajorService;
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@GetMapping("/query")
	@PreAuthorize("hasAuthority('book:tevglbookmajor:query')")
	@SysLog("查询列表(返回List<Bean>)")
	public R query(@RequestParam Map<String, Object> params) {
		return tevglBookMajorService.query(params);
	}
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@GetMapping("/queryForMap")
	@PreAuthorize("hasAuthority('book:tevglbookmajor:query')")
	@SysLog("查询列表(返回List<Map<String, Object>)")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		return tevglBookMajorService.queryForMap(params);
	}

	/**
	 * 查看明细
	 */
	@GetMapping("/view/{id}")
	@PreAuthorize("hasAuthority('book:tevglbookmajor:view')")
	@SysLog("查看明细")
	public R view(@PathVariable("id") String id) {
		return tevglBookMajorService.viewForMgr(id);
	}
	
	/**
	 * 执行数据新增
	 * 
	 */
	@PostMapping("/saveorupdate")
	@PreAuthorize("hasAuthority('book:tevglbookmajor:add') or hasAuthority('book:tevglbookmajor:edit')")
	@SysLog("执行数据新增")
	public R saveOrUpdate(@RequestBody(required = false) TevglBookMajor tevglBookMajor) {
		if(StrUtils.isEmpty(tevglBookMajor.getMajorId())) { //新增
			return tevglBookMajorService.save(tevglBookMajor);
		} else {
			return tevglBookMajorService.update(tevglBookMajor);
		}
	}
	
	/**
	 * 单条删除
	 */
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('book:tevglbookmajor:delete')")
	@SysLog("单条删除")
	public R delete(@PathVariable("id") String id) {
		return tevglBookMajorService.delete(id);
	}
	
	/**
	 * 批量删除
	 */
	@PostMapping("/deletes")
	@PreAuthorize("hasAuthority('book:tevglbookmajor:delete')")
	@SysLog("批量删除")
	public R deleteBatch(@RequestBody(required = true) String[] ids) {
		return tevglBookMajorService.deleteBatch(ids);
	}
	
	/**
	 * 获取职业课程路径树
	 */
	@GetMapping("/queryForTree")
	@PreAuthorize("hasAuthority('book:tevglbookmajor:query')")
	@SysLog("获取职业课程路径树")
	public R queryForTree(@RequestParam(required = false) Map<String, Object> map) {
		return tevglBookMajorService.queryForTree(map);
	}
	
	/**
	 * 获取职业课程路径+班级树
	 */
	@GetMapping("/queryMajorClassTreeData")
	@SysLog("获取职业课程路径+班级树")
	public R queryMajorClassTreeData(@RequestParam(required = false, value = "name") String name) {
		return R.ok().put(Constant.R_DATA, tevglBookMajorService.queryMajorClassTreeData(name));
	}
	
	/**
	 * 获取职业课程路径+课程树
	 */
	@GetMapping("/queryMajorSubjectTreeData")
	@SysLog("获取职业课程路径+课程树")
	public R queryMajorSubjectTreeData(@RequestParam(required = false, value = "name") String name) {
		return R.ok().put(Constant.R_DATA, tevglBookMajorService.queryMajorSubjectTreeData(name));
	}
	
}
