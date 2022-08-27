package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;

import java.util.List;

/**
 * @author huj
 * @create 2022-08-25 16:05
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TuserPostService {

    /**
     * 保存用户与岗位之间的关系
     * @author huj
     * @data 2019年5月29日
     * @param userId
     * @param postIds
     * @return
     */
    R saveOrUpdate(String userId, List<String> postIds);

    /**
     * 根据用户id，查询岗位id
     * @param userId
     * @return
     */
    List<String> selectPostIdListByUserId(String userId);
}
