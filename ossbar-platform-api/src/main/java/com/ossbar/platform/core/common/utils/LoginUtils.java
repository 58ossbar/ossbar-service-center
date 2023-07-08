package com.ossbar.platform.core.common.utils;

import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletRequest;

import com.ossbar.modules.sys.domain.TsysUserinfo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LoginUtils {

	@Autowired
	private HttpServletRequest request;
	
	/**
	 * <p>获取当前登陆用户</p>
	 * @author huj
	 * @data 2019年5月16日
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TsysUserinfo getLoginUser(HttpServletRequest request) {
		/*HttpSession session = request.getSession();
		Object obj = session.getAttribute(prefix + Global.REDIS_LOGIN_USER);
		if(!StrUtils.isNull(obj)) {
			return (TsysUserinfo)obj;
		}
		return null;
		*/
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			LinkedHashMap<String, ?> lhm = (LinkedHashMap)((LinkedHashMap)((LinkedHashMap)authentication.getPrincipal()).get("principal")).get("tsysUserinfo");
			TsysUserinfo tsysUserinfo = new TsysUserinfo();
			if(lhm == null || lhm.isEmpty()) {
				return null;
			}
			BeanUtils.populate(tsysUserinfo, lhm);
			return tsysUserinfo;
		}catch (Exception e) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String string = mapper.writeValueAsString(authentication.getPrincipal());
				JSONObject obj = JSONObject.parseObject(string);
				return mapper.readValue(obj.getString("tsysUserinfo"), TsysUserinfo.class);
			} catch (Exception e1) {
				return null;
			}
		}
	}
	
	public TsysUserinfo getLoginUser() {
		return getLoginUser(request);
	}
	
	/**
	 * <p>获取当前登陆用户ID</p>
	 * @author huj
	 * @data 2019年5月15日
	 * @return 返回当前登陆用户ID
	 */
	public String getLoginUserId() {
		TsysUserinfo user = getLoginUser(request);
		if (user != null) {
			return user.getUserId();
		}
		return null;
	}
}
