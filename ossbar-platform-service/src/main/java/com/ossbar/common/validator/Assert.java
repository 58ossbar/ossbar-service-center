package com.ossbar.common.validator;

import org.apache.commons.lang.StringUtils;
import com.ossbar.common.exception.OssbarException;

/**
 * Title:数据校验
 * Copyright: Copyright (c) 2017
 * Company:ossbar.co.,ltd
 * 
 * @author ossbar.co.,ltd
 * @version 1.0
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new OssbarException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new OssbarException(message);
        }
    }
}
