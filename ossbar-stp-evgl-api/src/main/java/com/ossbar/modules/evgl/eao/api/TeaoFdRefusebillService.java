package com.ossbar.modules.evgl.eao.api;

import java.util.Map;

import com.ossbar.core.baseclass.domain.R;

public interface TeaoFdRefusebillService {

	/**
	 * 根据条件查询退款记录
	 * @param params
	 * @return
	 */
	R query(Map<String, Object> params);
	
	/**
	 * 审核
	 * @param orderId
	 * @param type
	 * @param orderProceeds
	 * @param rbReason
	 * @return
	 */
	R saveorupdate(String orderId, String type, String orderProceeds, String rbReason);
}
