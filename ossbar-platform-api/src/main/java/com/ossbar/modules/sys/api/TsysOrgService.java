package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;

import java.util.List;
import java.util.Map;

/**
 * @author huj
 * @create 2022-08-17 11:43
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TsysOrgService {

    /**
     * 获取子部门ID，用于数据过滤
     *
     * @param orgId
     * @author huangwb
     * @date 2019-05-05 15:18
     */
    List<String> getSubOrgIdList(String orgId);

    /**
     * 获取机构树形数据
     * @author huj
     * @data 2019年5月16日
     * @return
     */
    R getOrgTree(Map<String, Object> map);

}
