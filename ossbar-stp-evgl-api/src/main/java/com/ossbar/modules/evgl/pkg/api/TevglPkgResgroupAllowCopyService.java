package com.ossbar.modules.evgl.pkg.api;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgResgroupAllowCopy;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglPkgResgroupAllowCopyService extends IBaseService<TevglPkgResgroupAllowCopy>{
	
	/**
	 * 更新是否可复制资源内容（Y可复制N不可复制）
	 * @param ctId
	 * @param pkgId
	 * @param cpId
	 * @param value
	 * @param loginUserId
	 * @return
	 */
	R updateValue(String ctId, String pkgId, String cpId, String value, String loginUserId);
	
}