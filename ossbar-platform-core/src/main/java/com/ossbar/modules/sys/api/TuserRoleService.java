package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;

import java.util.List;

/**
 * @author huj
 * @create 2022-08-25 9:56
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TuserRoleService {

    /**
     * 保存用户与角色关系
     * @author huj
     * @data 2019年5月6日
     * @param roleIdList
     * @param userIdList
     * @return
     */
    R saveOrUpdate(List<String> roleIdList, List<String> userIdList);

    /**
     * 先删除，再保存用户与角色关系
     * @param roleIdList
     * @param userIdList
     * @return
     */
    R saveOrUpdateForRole(List<String> roleIdList, List<String> userIdList);

    /**
     * 根据用户ID，获取角色ID列表
     * @author huj
     * @data 2019年5月6日
     * @param userId
     * @return
     */
    List<String> selectRoleIdListByUserId(String userId);

    /**
     * 根据角色ID,查询用户ID列表
     * @author huj
     * @data 2019年5月6日
     * @param roleId
     * @return
     */
    List<String> selectUserIdListByRoleId(String roleId);

    /**
     * <p>根据用户ID,删除用户角色关系</p>
     * @author huj
     * @data 2019年5月6日
     * @param userId
     * @return
     */
    R delete(String userId);

    /**
     * 根据用户ID,批量删除用户角色关系
     * @author huj
     * @data 2019年5月6日
     * @param userIds
     * @return
     */
    R deleteBatch(String[] userIds);

}
