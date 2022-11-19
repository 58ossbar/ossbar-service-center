package com.ossbar.common.validator;

import com.ossbar.common.exception.CreatorblueException;
import org.apache.commons.lang3.StringUtils;

/**
 * Title:数据校验
 * Copyright: Copyright (c) 2017
 * Company:creatorblue.co.,ltd
 * 
 * @author creatorblue.co.,ltd
 * @version 1.0
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new CreatorblueException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new CreatorblueException(message);
        }
    }
}
