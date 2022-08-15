package com.ossbar.common.utils;

import org.apache.dubbo.rpc.RpcContext;

/**
 * dubbo隐式参数工具类
 * 
 * @author zhuq
 *
 */
public class DubboUtil {

	private DubboUtil() {
	}

	public static String getUserId() {
		return getParamValue("userId");
	}

	public static String getParamValue(String param) {
		return RpcContext.getContext().getAttachment(param);
	}
}
