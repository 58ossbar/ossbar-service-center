package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;

import java.util.List;

/**
 * 数据权限
 * @author huj
 * @create 2022-08-17 11:11
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TsysDataprivilegeService {

    /**
     * 获取数据过滤的SQL
     * @param userId
     * @param tableAlias
     * @param point
     * @return
     */
    String getSQLFilter(String userId, String tableAlias, Object point);

    /**
     * <p>保存角色与数据权限关系</p>
     * @author huj
     * @data 2019年5月6日
     * @param roleId
     * @param orgIdList
     * @return
     */
    R saveOrUpdate(String roleId, List<String> orgIdList);

}
