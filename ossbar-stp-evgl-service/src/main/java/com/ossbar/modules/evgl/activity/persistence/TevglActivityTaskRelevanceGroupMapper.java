package com.ossbar.modules.evgl.activity.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.activity.domain.TevglActivityTaskRelevanceGroup;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Mapper
public interface TevglActivityTaskRelevanceGroupMapper extends BaseSqlMapper<TevglActivityTaskRelevanceGroup> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	/**
	 *
	 * @param ctId
	 * @param activityId
	 * @return
	 */
	List<String> findGroupIdBy(@Param("ctId") String ctId, @Param("activityId") String activityId);
}