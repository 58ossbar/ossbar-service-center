package com.ossbar.modules.evgl.site.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.modules.evgl.site.domain.TevglSiteSeo;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglSiteSeoService extends IBaseService<TevglSiteSeo>{
	 
	/**
	 * <p>进入编辑界面时，需要的数据</p>
	 * @author huj
	 * @data 2019年7月18日
	 * @param request
	 * @param response
	 * @return
	 */
	TevglSiteSeo editSiteSeo(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * <p>查看明细</p>
	 * @author huj
	 * @data 2019年7月18日
	 * @param id
	 */
	TevglSiteSeo selectObjectByRelationId(String id);
	
	/**
	 * <p>删除</p>
	 * @author huj
	 * @data 2019年7月18日
	 * @param ids
	 * @param request
	 * @param response
	 */
	void deleteSeo(String[] ids, HttpServletRequest request, HttpServletResponse response); 	
	
}