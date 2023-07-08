package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.domain.TsysOrg;
import com.ossbar.modules.sys.dto.org.SaveOrgDTO;

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

    /**
     * 根据条件查询机构列表
     * @param map
     * @return
     */
    R queryByMap(Map<String, Object> map);

    /**
     * 新增
     * @param dto
     * @return
     */
    R save(SaveOrgDTO dto);

    /**
     * 新增
     * @param dto
     * @return
     */
    R update(SaveOrgDTO dto);

    /**
     * 修改
     * @param tsysOrg
     * @return
     */
    R update(TsysOrg tsysOrg);

    /**
     * 批量删除
     *
     * @param ids
     * @author huangwb
     * @date 2019-05-05 15:18
     */
    R deleteBatch(String[] ids);

    /**
     * 移动资源菜单操作
     *
     *
     * @param moveId   移动对象的id
     * @param targetId 目标对象的id
     * @param moveType 移动的类型
     * @return
     * @author huangwb
     * @date 2019-06-03 17:18
     */
    R drag(String moveId, String targetId, String moveType);

    /**
     * 点击上下按钮移动机构管理节点操作
     *
     * @param moveId   移动对象的id
     * @param targetId 目标对象的id
     * @return
     * @author huangwb
     * @date 2019-06-17 14:18
     */
    R clickDrag(String moveId, String targetId);
}
