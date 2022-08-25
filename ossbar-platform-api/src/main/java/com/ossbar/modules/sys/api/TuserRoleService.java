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

}
