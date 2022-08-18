package com.ossbar.modules.sys.api;

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

}
