package com.ossbar.modules.mgr.tch.controller;

import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.tch.api.TevglTchTeacherService;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.tch.dto.SaveTeacherDTO;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/api/tch/tevgltchteacher")
public class TevglTchTeacherController {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglTchTeacherController.class);
	@Reference(version = "1.0.0")
	private TevglTchTeacherService tevglTchTeacherService;
	@Autowired
	private UploadFileUtils uploadFileUtils;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@GetMapping("/query")
	@PreAuthorize("hasAuthority('tch:tevgltchteacher:query')")
	@SysLog("查询列表(返回List<Bean>)")
	public R query(@RequestParam Map<String, Object> params) {
		return tevglTchTeacherService.query(params);
	}
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@GetMapping("/queryForMap")
	@PreAuthorize("hasAuthority('tch:tevgltchteacher:query')")
	@SysLog("查询列表(返回List<Map<String, Object>)")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		return tevglTchTeacherService.queryForMap(params);
	}

	/**
	 * 查看明细
	 */
	@GetMapping("/view/{id}")
	@PreAuthorize("hasAuthority('tch:tevgltchteacher:view')")
	@SysLog("查看明细")
	public R view(@PathVariable("id") String id) {
		return tevglTchTeacherService.view(id);
	}
	
	/**
	 * 执行数据新增
	 * 
	 */
	@PostMapping("/saveorupdate")
	@PreAuthorize("hasAuthority('tch:tevgltchteacher:add') or hasAuthority('tch:tevgltchteacher:edit')")
	@SysLog("执行数据新增")
	public R saveOrUpdate(@RequestBody SaveTeacherDTO dto) {
		if(StrUtils.isEmpty(dto.getTeacherId())) { //新增
			return tevglTchTeacherService.saveTeacherInfo(dto);
		} else {
			return tevglTchTeacherService.updateTeacherInfo(dto);
		}
	}
	
	/**
	 * 单条删除
	 */
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('tch:tevgltchteacher:delete')")
	@SysLog("单条删除")
	public R delete(@PathVariable("id") String id) {
		return tevglTchTeacherService.delete(id);
	}
	
	/**
	 * 批量删除
	 */
	@PostMapping("/deletes")
	@PreAuthorize("hasAuthority('tch:tevgltchteacher:delete')")
	@SysLog("批量删除")
	public R deleteBatch(@RequestBody(required = true) String[] ids) {
		return tevglTchTeacherService.deleteBatch(ids);
	}
	
	/**
	 * <p>上传教师头像</p>
	 * @author huj
	 * @data 2019年7月25日
	 * @param picture
	 * @return
	 */
	@PostMapping("/uploadPic")
	public R uploadPic(@RequestPart(value = "file", required = false) MultipartFile picture) {
		return uploadFileUtils.uploadImage(picture, "/teacher-img", "5", 0, 0);
	}
	
	/**
	 * <p>更新状态或是否首页显示</p>
	 * @author huj
	 * @data 2019年7月28日
	 * @param tevglTchTeacher
	 * @return
	 */
	@PostMapping("/updateStatus")
	public R updateStatus(@RequestBody(required = false) TevglTchTeacher tevglTchTeacher) {
		return tevglTchTeacherService.updateStateOrShowIndex(tevglTchTeacher);
	}
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@GetMapping("/selectTeacherList")
	@PreAuthorize("hasAuthority('tch:tevgltchteacher:query')")
	@SysLog("查询列表(返回List<Bean>)")
	public R selectTeacherList(@RequestParam Map<String, Object> params) {
		return R.ok().put(Constant.R_DATA, tevglTchTeacherService.selectListByMapInnerJoinTraineeTable(params));
	}
}
