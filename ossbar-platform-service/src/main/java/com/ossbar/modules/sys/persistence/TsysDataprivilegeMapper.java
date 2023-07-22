package com.ossbar.modules.sys.persistence;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.sys.domain.TsysDataprivilege;
import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhujw
 * @version 1.0
 */

@Mapper
public interface TsysDataprivilegeMapper extends BaseSqlMapper<TsysDataprivilege> {

	/**
	 * 根据角色ID，获取机构ID列表
	 */
	List<String> selectOrgListByRoleId(String roleId);
	
	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<String> selectOrgListByRoleIds(String[] roleIds);

	/**
	 * 批量新增
	 * @param list
	 */
	void insertBatch(List<TsysDataprivilege> list);
}