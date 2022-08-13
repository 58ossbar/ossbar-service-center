package com.ossbar.platform.core.common.utils;


import com.ossbar.common.exception.CreatorblueException;

/**
 * 校验工具类
 * @author zhuq
 *
 */
public class ValidatorUtils {
	/**
	  *  校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws CreatorblueException  校验不通过，则报CreatorblueException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws CreatorblueException {
    	//todo
    }
}
