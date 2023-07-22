package com.ossbar.modules.sys.api;

import java.util.List;
import java.util.Map;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.domain.TsysSettings;

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
 * Company:ossbar.co.,ltd
 * </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TsysSettingsService extends IBaseService<TsysSettings> {

	/**
	 * 查询设置 settingType 系统设置或用户设置（必填） settingUserId 用户设置则必填
	 * 
	 * @param map
	 * @return
	 */
	R querySetting(Map<String, Object> map);

	/**
	 * 批量修改
	 * 
	 * @param settings
	 * @return
	 */
	public R updateBatchSettings(List<TsysSettings> settings);

	R selectObjectById(String id);
	
	List<TsysSettings> selectListByMap(Map<String, Object> map);

}