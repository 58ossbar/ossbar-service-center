package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;

import java.util.Map;

/**
 * 字典管理
 * @author huj
 * @create 2022-08-16 15:46
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TsysDictService {

    /**
     * 获取字典表中parentType!=0的字典信息
     *
     * @return
     */
    R selectListByMapNotZero(Map<String, Object> map);

}
