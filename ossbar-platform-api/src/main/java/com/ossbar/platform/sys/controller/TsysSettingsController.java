package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.modules.sys.api.TsysSettingsService;
import com.ossbar.modules.sys.domain.TsysSettings;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2019
 * </p>
 * <p>
 * Company:creatorblue.co.,ltd
 * </p>
 *
 * @author zhuq
 * @version 1.0
 */

@RestController
@RequestMapping("/api/sys/settings")
public class TsysSettingsController {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TsysSettingsController.class);
	@Reference(version = "1.0.0")
	private TsysSettingsService tsysSettingsService;
	@Autowired
	private UploadFileUtils uploadFileUtils;
	@Reference(version = "1.0.0")
	private TsysAttachService tsysAttachService;

	/**
	 * 查询列表(返回List<Bean>)
	 * 
	 * @param params
	 * @return R
	 */
	@GetMapping("/query")
	@PreAuthorize("hasAuthority('sys:settings:query')")
	@SysLog("查询列表(返回List<Bean>)")
	public R query(@RequestParam Map<String, Object> params) {
		return tsysSettingsService.query(params);
	}

	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * 
	 * @param params
	 * @return R
	 */
	@GetMapping("/queryForMap")
	@PreAuthorize("hasAuthority('sys:settings:query')")
	@SysLog("查询列表(返回List<Map<String, Object>)")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		return tsysSettingsService.queryForMap(params);
	}

	/**
	 * 查看明细
	 */
	@GetMapping("/view/{id}")
	@PreAuthorize("hasAuthority('sys:settings:view')")
	@SysLog("查看明细")
	public R view(@PathVariable("id") String id) {
		return tsysSettingsService.view(id);
	}

	/**
	 * 执行数据修改
	 * 
	 */
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('sys:settings:edit')")
	@SysLog("执行数据修改")
	public R update(@RequestBody(required = false) TsysSettings tsysSettings) {
		return tsysSettingsService.update(tsysSettings);
	}

	/**
	 * 单条删除
	 */
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('sys:settings:delete')")
	@SysLog("单条删除")
	public R delete(@PathVariable("id") String id) {
		return tsysSettingsService.delete(id);
	}

	/**
	 * 批量删除
	 */
	@PostMapping("/deletes")
	@PreAuthorize("hasAuthority('sys:settings:delete')")
	@SysLog("批量删除")
	public R deleteBatch(@RequestParam(required = true) String[] ids) {
		return tsysSettingsService.deleteBatch(ids);
	}

	/**
	 * 批量修改
	 * 
	 * @param settings
	 * @return
	 */
	@PostMapping("/updateBatch")
	@PreAuthorize("hasAuthority('sys:settings:updatebatch')")
	@SysLog("批量修改")
	public R updateBatchSettings(@RequestBody(required = false) List<TsysSettings> settings) {
		return tsysSettingsService.updateBatchSettings(settings);
	}

	/**
	 * 设置图片上传
	 * 
	 * @param icon
	 * @return
	 */
	@PostMapping("/uploadIcon/{pkId}/{maxWidth}/{maxHeight}")
	@SysLog("设置的图片上传")
	public R uploadDictIcon(@RequestParam(name = "icon", required = true) MultipartFile icon,
			@PathVariable("pkId") String pkId, @PathVariable("maxWidth") int maxWidth,
			@PathVariable("maxHeight") int maxHeight) {
		R r = uploadFileUtils.uploadImage(icon, "/settings", "3", maxWidth, maxHeight);
		String attachId = ((Map<String, Object>) r.get(Constant.R_DATA)).get("attachId").toString();
		tsysAttachService.updateAttachForAdd(attachId, pkId, "3");
		TsysSettings selectObjectById = tsysSettingsService.selectObjectById(pkId);
		selectObjectById.setSettingValue(((Map<String, Object>) r.get("data")).get("imgNamePc").toString());
		tsysSettingsService.update(selectObjectById);
		return R.ok();
	}
}
