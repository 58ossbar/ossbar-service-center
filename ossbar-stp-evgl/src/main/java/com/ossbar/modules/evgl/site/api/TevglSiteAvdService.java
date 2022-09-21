package com.ossbar.modules.evgl.site.api;


import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.domain.TevglSiteAvd;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglSiteAvdService extends IBaseService<TevglSiteAvd> {
	
	/**
	 * <p>新增</p>
	 * @author huj
	 * @data 2019年7月18日
	 * @param tevglSiteAvd
	 * @param attachId
	 * @param attachIdForMobile
	 * @return
	 */
	R save2(TevglSiteAvd tevglSiteAvd, String attachId, String attachIdForMobile);
	
	/**
	 * <p>修改</p>
	 * @author huj
	 * @data 2019年7月18日
	 * @param tevglSiteAvd
	 * @param attachId
	 * @param attachIdForMobile
	 * @return
	 */
	R update2(TevglSiteAvd tevglSiteAvd, String attachId, String attachIdForMobile);
	
	/**
	 * <p>更新状态</p>
	 * @author huj
	 * @data 2019年7月29日
	 * @param tevglSiteAvd
	 * @return
	 */
	R updateState(TevglSiteAvd tevglSiteAvd);
}