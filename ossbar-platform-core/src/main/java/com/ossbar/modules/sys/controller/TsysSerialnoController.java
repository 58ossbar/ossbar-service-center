package com.ossbar.modules.sys.controller;

import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.core.common.cbsecurity.log.SysLog;
import com.ossbar.modules.core.common.utils.LoginUtils;
import com.ossbar.modules.sys.api.TsysSerialnoService;
import com.ossbar.modules.sys.domain.TsysSerialno;
import com.ossbar.modules.sys.domain.TsysUserinfo;

/**
 * <p>流水号管理</p>
 * <p>Title: TsysSerialnoController.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: 湖南创蓝信息科技有限公司</p> 
 * @author huj
 * @date 2019年5月20日
 */
@RestController
@RequestMapping("/api/sys/serialno")
public class TsysSerialnoController {

	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TsysSerialnoController.class);
	@Reference(version = "1.0.0")
	private TsysSerialnoService tsysSerialnoService;
	@Autowired
	private LoginUtils loginUtils;
	
	/**
	 * <p>查询列表(返回List<Bean>)</p>
	 * @author huj
	 * @data 2019年5月21日
	 * @param params
	 * @return
	 */
	@GetMapping("/query")
	@PreAuthorize("hasAuthority('sys:tsysserialno:query')")
	@SysLog("根据条件查询记录")
	public R query(@RequestParam Map<String, Object> params) {
		params.put("page", params.get("pageNum"));
		params.put("limit", params.get("pageSize"));
		return tsysSerialnoService.query(params);
	}
	
	/**
	 * <p>查看明细</p>
	 * @author huj
	 * @data 2019年5月21日
	 * @param id
	 * @return
	 */
	@GetMapping("/view/{id}")
	@PreAuthorize("hasAuthority('sys:tsysserialno:view')")
	@SysLog("查看明细")
	public R view(@PathVariable("id") String id) {
		return tsysSerialnoService.view(id);
	}
	
	/**
	 * <p>进入新增</p>
	 * @author huj
	 * @data 2019年5月21日
	 * @param params
	 * @return
	 */
	@GetMapping("/toAdd")
	@PreAuthorize("hasAuthority('sys:tsysserialno:add')")
	@SysLog("进入新增")
	public R add(@RequestParam Map<String, Object> params) {
		return tsysSerialnoService.add();
	}
	
	/**
	 * <p>进入修改</p>
	 * @author huj
	 * @data 2019年5月21日
	 * @param params
	 * @return
	 */
	@GetMapping("/toEdit")
	@PreAuthorize("hasAuthority('sys:tsysserialno:edit')")
	@SysLog("进入新增")
	public R edit(@RequestParam Map<String, Object> params) {
		return tsysSerialnoService.edit(params);
	}
	
	/**
	 * <p>执行数据新增</p>
	 * @author huj
	 * @data 2019年5月21日
	 * @param tsysSerialno
	 * @return
	 */
	@PostMapping("/save")
	public R save(@RequestBody(required = false) TsysSerialno tsysSerialno) {
		TsysUserinfo obj = loginUtils.getLoginUser();
		if (tsysSerialno.getSerialnoId() == null || "".equals(tsysSerialno.getSerialnoId())) {
			if(obj != null){
				tsysSerialno.setCreateUserId(obj.getCreateUserId());
			}
			return tsysSerialnoService.save(tsysSerialno);
		} else {
			if(obj != null){
				tsysSerialno.setUpdateUserId(obj.getCreateUserId());
			}
			return tsysSerialnoService.update(tsysSerialno);
		}
	}
	
	/**
	 * <p>单条删除</p>
	 * @author huj
	 * @data 2019年5月21日
	 * @param id
	 * @return
	 */
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('sys:tstudent:delete')")
	@SysLog("单条删除")
	public R delete(@PathVariable("id") String id) {
		return tsysSerialnoService.delete(id);
	}
	
	/**
	 * <p>批量删除</p>
	 * @author huj
	 * @data 2019年5月21日
	 * @param ids
	 * @return
	 */
	@PostMapping("/deletes")
	@PreAuthorize("hasAuthority('sys:tsysserialno:remove')")
	@SysLog("批量删除")
	public R deleteBatch(@RequestBody(required = false) String[] ids) {
		return tsysSerialnoService.deleteBatch(ids);
	}
}
