package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;

import java.util.List;

/**
 * 角色与菜单
 * @author huj
 * @create 2022-08-24 9:15
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TsysRoleprivilegeService {

    /**
     * <p>保存角色与菜单关系</p>
     * @author huj
     * @data 2019年5月6日
     * @param roleId
     * @param menuIdList
     */
    R saveOrUpdate(String roleId, List<String> menuIdList);

    /**
     * <p>根据角色ID,查询菜单ID列表</p>
     * @author huj
     * @data 2019年5月6日
     * @param roleId
     * @return
     */
    List<String> selectMenuListByRoleId(String roleId);

}
