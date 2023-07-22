package com.ossbar.common.utils;

import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.ossbar.modules.sys.domain.TsysUserinfo;
import com.ossbar.utils.tool.SpringContextUtils;
import com.ossbar.utils.tool.StrUtils;

@Component
public class ServiceLoginUtil {

	/**
	 * <p>获取当前登陆用户</p>
	 * @author zhuq
	 * @data 2019年6月18日
	 * @return 返回当前登陆用户对象
	 */
	public TsysUserinfo getLoginUser() {
		TsysUserinfo userInfo = null;
		try {
			Object loginUtils = SpringContextUtils.getBean("loginUtils");
			Class<?> cls = Class.forName("com.ossbar.modules.core.common.utils.LoginUtils");
			Object sUserInfo = cls.getDeclaredMethod("getLoginUser").invoke(loginUtils);
			if (!StrUtils.isNull(sUserInfo)) {
				userInfo = (TsysUserinfo)sUserInfo;
			}
		} catch (Exception e) {
			String sUserInfo = RpcContext.getContext().getAttachment("userInfo");
			if(!StrUtils.isNull(sUserInfo)) {
				userInfo = JSONObject.parseObject(sUserInfo, TsysUserinfo.class);
			}
		}
		return userInfo;
	}
	
	/**
	 * <p>获取当前登陆用户ID</p>
	 * @author zhuq
	 * @data 2019年6月18日
	 * @return 返回当前登陆用户ID
	 */
	public String getLoginUserId() {
		String userId = null;
		try {
			Object loginUtils = SpringContextUtils.getBean("loginUtils");
			Class<?> cls = Class.forName("com.ossbar.modules.core.common.utils.LoginUtils");
			Object sUserId = cls.getDeclaredMethod("getLoginUserId").invoke(loginUtils);
			if (!StrUtils.isNull(sUserId)) {
				userId = sUserId.toString();
			}
		}catch (Exception e) {
			userId = RpcContext.getContext().getAttachment("userId");
		}
		return userId;
	}
}
