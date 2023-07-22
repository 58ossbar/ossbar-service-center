package com.ossbar.modules.evgl.activity.persistence;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.evgl.activity.domain.TevglActivityLive;
import com.ossbar.core.baseclass.persistence.BaseSqlMapper;

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
public interface TevglActivityLiveMapper extends BaseSqlMapper<TevglActivityLive> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	/**
	 * 获取最大排序号
	 * @param map
	 * @return
	 */
	Integer getMaxSortNum(Map<String, Object> map);
	
	/**
	 * 查询对象
	 * @param params
	 * @return
	 */
	TevglActivityLive selectObjectByIdAndPkgId(Map<String, Object> params);
}