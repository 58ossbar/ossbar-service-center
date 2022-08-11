package com.ossbar.modules.sys.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.TsysRoleprivilege;

/**
 * Title: 角色和菜单对应 Description: Copyright: Copyright (c) 2017
 * Company:ossbar.co.,ltd
 * 
 * @author ossbar.co.,ltd
 * @version 1.0
 */
@Mapper
public interface TsysRoleprivilegeMapper extends BaseSqlMapper<TsysRoleprivilege> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<String> selectMenuListByRoleId(String roleId);
}
