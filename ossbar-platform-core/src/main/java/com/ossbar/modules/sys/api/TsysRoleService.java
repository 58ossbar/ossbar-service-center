package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.domain.TsysRole;
import com.ossbar.modules.sys.dto.role.SaveRoleAssignUserDTO;
import com.ossbar.modules.sys.dto.role.SaveRoleDTO;

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
     * 根据条件查询记录
     * @author huj
     * @data 2019年5月5日
     * @param map
     * @return
     */
    R queryAll(Map<String, Object> map);

    /**
     * 查看明细
     * @param roleId
     * @return
     */
    R view(String roleId);

    /**
     * 根据角色id查询记录
     * @param roleId
     * @return
     */
    TsysRole selectObjectByRoleId(String roleId);

    /**
     * 新增角色
     * @param role
     * @return
     */
    R save(SaveRoleDTO role);

    /**
     * 修改角色
     * @param role
     * @return
     */
    R update(SaveRoleDTO role);

    /**
     * 批量删除角色信息
     * @author huj
     * @data 2019年5月5日
     * @param roleIds
     * @return
     */
    R deleteBatch(String[] roleIds);

    /**
     * 分配用户，将角色分配
     * @author huj
     * @data 2019年5月5日
     * @param roleIds
     * @return
     */
    R setUser(String[] roleIds);

    /**
     * 保存-分配用户
     * @author huj
     * @data 2019年5月6日
     * @param role
     * @return
     */
    R saveRoleUser(SaveRoleAssignUserDTO role);
}
