package com.ossbar.modules.core.common.utils;

import com.ossbar.common.exception.OssbarException;

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
     * @throws OssbarException  校验不通过，则报OssbarException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws OssbarException {
    	//todo
    }
}
