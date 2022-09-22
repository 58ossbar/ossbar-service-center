package com.ossbar.modules.mgr.tch.controller;

import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.tch.api.TevglTchClasstraineeService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClasstrainee;
import com.ossbar.modules.evgl.tch.dto.SaveClassTraineeDTO;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.evgl.trainee.vo.TraineeInfoVO;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
@RequestMapping("/api/tch/tevgltchclasstrainee")
public class TevglTchClasstraineeController {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglTchClasstraineeController.class);
	@Reference(version = "1.0.0")
	private TevglTchClasstraineeService tevglTchClasstraineeService;
	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@GetMapping("/query")
	@PreAuthorize("hasAuthority('tch:tevgltchclasstrainee:query')")
	@SysLog("查询列表(返回List<Bean>)")
	public R query(@RequestParam Map<String, Object> params) {
		return tevglTchClasstraineeService.query(params);
	}
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@GetMapping("/queryForMap")
	@PreAuthorize("hasAuthority('tch:tevgltchclasstrainee:query')")
	@SysLog("查询列表(返回List<Map<String, Object>)")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		return tevglTchClasstraineeService.queryForMap(params);
	}

	/**
	 * 查看明细
	 */
	@GetMapping("/view/{id}")
	@PreAuthorize("hasAuthority('tch:tevgltchclasstrainee:view')")
	@SysLog("查看明细")
	public R view(@PathVariable("id") String id) {
		return tevglTchClasstraineeService.view(id);
	}
	
	/**
	 * 执行数据新增
	 * 
	 */
	@PostMapping("/saveorupdate")
	@PreAuthorize("hasAuthority('tch:tevgltchclasstrainee:add') or hasAuthority('tch:tevgltchclasstrainee:edit')")
	@SysLog("执行数据新增")
	public R saveOrUpdate(@RequestBody(required = false) TevglTchClasstrainee tevglTchClasstrainee) {
		if(StrUtils.isEmpty(tevglTchClasstrainee.getCtId())) { //新增
			return tevglTchClasstraineeService.save(tevglTchClasstrainee);
		} else {
			return tevglTchClasstraineeService.update(tevglTchClasstrainee);
		}
	}
	
	/**
	 * 单条删除
	 */
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('tch:tevgltchclasstrainee:delete')")
	@SysLog("单条删除")
	public R delete(@PathVariable("id") String id) {
		return tevglTchClasstraineeService.delete(id);
	}
	
	/**
	 * 批量删除
	 */
	@PostMapping("/deletes")
	@PreAuthorize("hasAuthority('tch:tevgltchclasstrainee:delete')")
	@SysLog("批量删除")
	public R deleteBatch(@RequestBody(required = true) String[] ids) {
		return tevglTchClasstraineeService.deleteBatch(ids);
	}
	

	/**
	 * 导入excel
	 * @param request
	 * @param classId
	 * @return
	 */
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('tch:tevgltchclasstrainee:import')")
	//@SysLog("导入excel")
    public R importExcel(HttpServletRequest request, String classId) {
		return tevglTchClasstraineeService.importExcel(request, classId);
	}
	
	/**
	 * 导出
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/exportExcel")
	@PreAuthorize("hasAuthority('tch:tevgltchclasstrainee:export')")
	public R exportExcel(@RequestParam Map<String, Object> map) {
		log.debug("查询条件 {}", map);
		List<TraineeInfoVO> list = tevglTchClasstraineeService.selectClassTraineeListByMap(map);
		//ExcelUtil<TraineeInfoVO> objectExcelUtil = new ExcelUtil<>(TraineeInfoVO.class);
		//return objectExcelUtil.exportExcel(list, "班级成员数据");
		return null;
	}
	
	/**
	 * 修改班级成员部分信息
	 * @param request
	 * @param tevglTraineeInfo
	 * @return
	 */
	@RequestMapping(value = "/updateTrainee", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('tch:tevgltchclasstrainee:edit')")
	//@SysLog("修改班级成员部分信息")
    public R updateTrainee(HttpServletRequest request, @RequestBody TevglTraineeInfo tevglTraineeInfo) {
		return tevglTchClasstraineeService.updateTraineeForMgr(tevglTraineeInfo);
	}
	
	/**
	 * 根据条件，分页查询，不在任何班级里面的学员
	 * @param params
	 * @return
	 */
	@GetMapping("/findTraineeListNotInClassWithPage")
	@PreAuthorize("hasAuthority('tch:tevgltchclasstrainee:query')")
	@SysLog("根据条件，分页查询，不在任何班级里面的学员")
	public R findTraineeListNotInClass(@RequestParam Map<String, Object> params) {
		return tevglTraineeInfoService.findTraineeListNotInClassWithPage(params);
	}

	/**
	 * 批量选择新增班级成员
	 * @param dto
	 * @return
	 */
	@PostMapping("/saveClassTraineeBatch")
	@PreAuthorize("hasAuthority('tch:tevgltchclasstrainee:add') or hasAuthority('tch:tevgltchclasstrainee:edit')")
	@SysLog("批量选择新增班级成员")
	public R saveClassTraineeBatch(@RequestBody SaveClassTraineeDTO dto) {
		if (StrUtils.isEmpty(dto.getClassId())) {
			return R.error("请选择班级");
		}
		if (dto.getTraineeIdList() == null || dto.getTraineeIdList().isEmpty()) {
			return R.error("请选择学员");
		}
		return tevglTchClasstraineeService.saveClassTraineeBatch(dto.getClassId(), dto.getTraineeIdList());
	}
	
}
