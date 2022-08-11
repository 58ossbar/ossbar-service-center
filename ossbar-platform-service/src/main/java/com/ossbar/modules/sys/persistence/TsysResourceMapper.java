package com.ossbar.modules.sys.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.TsysResource;

/**
 * Title: 系统资源配置 Description: Copyright: Copyright (c) 2017
 * Company:ossbar.co.,ltd
 * 
 * @author ossbar.co.,ltd
 * @version 1.0
 */
@Mapper
public interface TsysResourceMapper extends BaseSqlMapper<TsysResource> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<TsysResource> selectListParentId(String parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<TsysResource> selectNotButtonList();
	
	/**
	 * 查询用户的权限列表
	 */
	List<TsysResource> selectUserList(String userId);
	
	/**
	 * 删除资源数据
	 */
	int deleteBatchRes(Object[] id);
	
	/**
	 * 角色对应的资源数据
	 */
	int deleteBatchRolPri(Object[] id);
	/**
	 * 查出所有资源，包括其他系统的个性化资源，统一做授权
	 * 
	 */
	List<TsysResource> selectAllListByMap(Map<String, Object> map);
	/**
	 * 查出所有子系统
	 * 
	 */
	List<TsysResource> selectSubSysList();
}
