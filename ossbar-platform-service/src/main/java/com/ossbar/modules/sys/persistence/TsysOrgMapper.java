package com.ossbar.modules.sys.persistence;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.sys.domain.TsysOrg;
import com.ossbar.core.baseclass.persistence.BaseSqlMapper;

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

	// 查询所有机构ID
	List<String> getAllOrgIds();

	String selectMaxCodeByParenId(String parentId);

	/**
	 * 查询子部门ID列表
	 * 
	 * @param parentId 上级部门ID
	 */
	List<String> selectOrgIdList(String parentId);

	/**
	 * 查询指定节点下的节点总数
	 * 
	 * @param parentId
	 * @return
	 */
	int selectCountParentId(String parentId);

	/**
	 * 查询指定节点下的最大排序号
	 * 
	 * @param parentId
	 * @return
	 */
	int selectMaxOrderSnByParentId(String parentId);

	/**
	 * 根据parentId 查询子元素
	 * 
	 * @param parentId
	 * @return
	 */
	List<TsysOrg> selectListByParentId(String parentId);
}