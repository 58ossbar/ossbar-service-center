package com.ossbar.modules.evgl.site.persistence;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.site.domain.TevglSiteCheckinfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Mapper
public interface TevglSiteCheckinfoMapper extends BaseSqlMapper<TevglSiteCheckinfo> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
}