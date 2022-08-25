package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;

import java.util.List;

/**
 * 用户与机构
 * @author huj
 * @create 2022-08-25 15:53
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TorgUserService {

    /**
     * 保存用户与机构的关系
     * @param userId
     * @param orgIdList
     * @return
     */
    R saveOrUpdate(String userId, List<String> orgIdList);

    /**
     * <p>根据用户ID查询记录</p>
     * @author huj
     * @data 2019年5月13日
     * @param userId
     * @return
     */
    List<String> selectListByUserId(String userId);

}
