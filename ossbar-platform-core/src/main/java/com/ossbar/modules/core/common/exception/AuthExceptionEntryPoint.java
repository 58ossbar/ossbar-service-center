package com.ossbar.modules.core.common.exception;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.ossbar.common.constants.ExecStatus;

/**
 * oauth2 token校验自定义异常处理器
 * @author zhuq
 *
 */
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint
{

	private Logger log = LoggerFactory.getLogger(getClass());
	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {
        Throwable cause = authException.getCause();
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
        	JSONObject result = new JSONObject();
            if(cause instanceof InvalidTokenException) {
            	result.put("code", ExecStatus.INVALID_TOKEN.getCode());
            	result.put("msg", ExecStatus.INVALID_TOKEN.getMsg());
                response.getWriter().write(result.toJSONString());
            }else{
            	result.put("code", ExecStatus.TOKEN_NOT_EXITS.getCode());
            	result.put("msg", ExecStatus.TOKEN_NOT_EXITS.getMsg());
                response.getWriter().write(result.toJSONString());
            }
        } catch (IOException e) {
        	log.error(e.getMessage(), e);
        }
    }
}
