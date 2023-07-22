package com.ossbar.modules.evgl.pkg.api;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.pkg.domain.TevglBookpkgTeamDetail;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglBookpkgTeamDetailService extends IBaseService<TevglBookpkgTeamDetail>{
	
	/**
	 * 单独对某个人授权
	 * @param jsonObject
	 * @param loginUserId
	 * @return
	 */
	R authorizationAlone(JSONObject jsonObject, String loginUserId);
	
	/**
	 * 根据条件查询记录
	 * @param parmas
	 * @return
	 */
	List<TevglBookpkgTeamDetail> selectListByMap(Map<String, Object> params);
}