package com.ossbar.modules.core.config.dubbo;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.modules.sys.domain.TsysUserinfo;

/**
 * dubbo过滤器，在调用微服务时增加隐式参数(userId)
 * @author zhuq
 *
 */
public class CbDubboUserTraceFilter implements Filter {

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    	Map<String, String> attachments = invocation.getAttachments();
        try {
        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			LinkedHashMap<String, ?> lhm = (LinkedHashMap)((LinkedHashMap)((LinkedHashMap)authentication.getPrincipal()).get("principal")).get("tsysUserinfo");
			TsysUserinfo tsysUserinfo = new TsysUserinfo();
			if(lhm == null || lhm.isEmpty()) {
				return null;
			}
			BeanUtils.populate(tsysUserinfo, lhm);
			attachments.put("userId",  tsysUserinfo.getUserId());
			attachments.put("userInfo", JSONObject.toJSONString(tsysUserinfo));
        }catch (Exception e) {
        	attachments.put("userId", null);
        	attachments.put("userInfo", null);
		}
        return invoker.invoke(invocation);
    }
    
}

