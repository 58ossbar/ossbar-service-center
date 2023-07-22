package com.ossbar.modules.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简易正则工具类
 * @author huj
 * @create 2021-12-30 17:42
 * @email hujun@ossbar.com
 */
public class RegularUtils {

    /**
     * 判断字符是不是整数
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern p = Pattern.compile("^[1-9]\\d*$");
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    /**
     * 手机格式验证
     *
     * @param mobile
     * @return
     */
    public boolean isMobile(String mobile) {
        Pattern p = Pattern.compile("^1[3456789]\\d{9}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

}
