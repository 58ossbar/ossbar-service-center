package com.ossbar.modules.evgl.site.api;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.domain.TevglSiteSysMsg;

/**
 * <p> Title: 系统消息</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglSiteSysMsgService extends IBaseService<TevglSiteSysMsg>{
	
	/**
	 * 保存
	 * @param tevglSiteSysMsg
	 * @return
	 */
	R saveMsg(TevglSiteSysMsg tevglSiteSysMsg);
	
	/**
	 * 查询发送给自己的系统消息
	 * @param params
	 * @param loginUserId
	 * @return
	 */
	R queryMyMsg(Map<String, Object> params, String loginUserId);
	
	/**
	 * 手动发送通知
	 * @param jsonObject
	 * @param loginUserId
	 * @return
	 */
	R sendText(JSONObject jsonObject, String loginUserId);
	
}