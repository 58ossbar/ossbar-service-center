package com.ossbar.modules.evgl.site.api;

import java.util.Map;

import com.ossbar.modules.evgl.site.domain.TevglSiteUpdateLog;
import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;

/**
 * <p> Title: 布道师各版本更新日志</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglSiteUpdateLogService extends IBaseService<TevglSiteUpdateLog>{
	
	/**
	 * 查询更新日志
	 * @param params
	 * @return
	 */
	R queryUpdateLog(Map<String, Object> params);
	
}