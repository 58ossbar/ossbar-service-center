package com.ossbar.modules.sys.persistence;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.TsysOrg;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * </p>
 * <p>
 * Company:ossbar.co.,ltd
 * </p>
 *
 * @author zhujw
 * @version 1.0
 */

@Mapper
public interface TsysOrgMapper extends BaseSqlMapper<TsysOrg> {

	/**
	 * 查询所有机构
	 */
	List<TsysOrg> getAllOrgs();
	//查询所有机构ID
	List<String> getAllOrgIds();

	String selectMaxCodeByParenId(String parentId);

	/**
	 * 查询子部门ID列表
	 * 
	 * @param parentId
	 * 上级部门ID
	 */
	List<String> selectOrgIdList(String parentId);
}