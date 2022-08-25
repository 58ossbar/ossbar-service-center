package com.ossbar.modules.sys.persistence;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.TuserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Title: 用户与角色对应关系 Description: Copyright: Copyright (c) 2017
 * Company:ossbar.co.,ltd
 * 
 * @author ossbar.co.,ltd
 * @version 1.0
 */
@Mapper
public interface TuserRoleMapper extends BaseSqlMapper<TuserRole> {

	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<String> selectRoleListByUserId(String userId);
	/**
	 * 根据角色ID，获取用户ID列表
	 */
	List<String> selectUserListByRoleId(String roleId);
	/**
	 * 根据角色ID删除角色与用户关系数据
	 */
	int deleteByRole(String roleId);

	/**
	 * 批量新增
	 * @param list
	 */
	void insertBatch(List<TuserRole> list);
}
