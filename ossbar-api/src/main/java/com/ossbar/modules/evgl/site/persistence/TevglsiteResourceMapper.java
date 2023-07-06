package com.ossbar.modules.evgl.site.persistence;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.site.vo.TsysResourceVo;
import com.ossbar.modules.sys.domain.TsysResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Title: 系统资源配置 Description: Copyright: Copyright (c) 2017
 * Company:creatorblue.co.,ltd
 * 
 * @author creatorblue.co.,ltd
 * @version 1.0
 */
@Mapper
public interface TevglsiteResourceMapper extends BaseSqlMapper<TsysResource> {

	/**
	 * 查出自有资源
	 * 
	 */
	List<TsysResource> selectSiteListByMap(Map<String, Object> map);
	/**
	 * 查出自有资源
	 * 
	 */
	List<TsysResource> selectSiteListParentId(String parentId);
	
	/**
	 * 查出自有资源
	 * 
	 */
	List<TsysResourceVo> selectSiteListVoByMap(Map<String, Object> map);
	/**
	 * 查出自有资源
	 * 
	 */
	List<TsysResourceVo> selectSiteListVoParentId(String parentId);
}
