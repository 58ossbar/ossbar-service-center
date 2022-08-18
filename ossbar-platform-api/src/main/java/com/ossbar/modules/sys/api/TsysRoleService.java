package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.domain.TsysRole;

import java.util.Map;

/**
 * @author huj
 * @create 2022-08-17 11:08
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TsysRoleService {

    /**
     * 根据条件分页查询记录
     * @author huj
     * @data 2019年5月5日
     * @param map
     * @return
     */
    R query(Map<String, Object> map);

    /**
     * 根据角色id查询记录
     * @param roleId
     * @return
     */
    TsysRole selectObjectByRoleId(String roleId);


}
