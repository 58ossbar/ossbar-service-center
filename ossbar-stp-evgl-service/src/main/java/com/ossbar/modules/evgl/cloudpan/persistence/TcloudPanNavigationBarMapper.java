package com.ossbar.modules.evgl.cloudpan.persistence;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.evgl.cloudpan.domain.TcloudPanNavigationBar;
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
public interface TcloudPanNavigationBarMapper extends BaseSqlMapper<TcloudPanNavigationBar> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	/**
	 * 批量插入
	 * @param list
	 */
	void insertBatch(List<TcloudPanNavigationBar> list);
	
	/**
	 * 根据条件获取最大排序号
	 * @param map
	 * @return
	 */
	Integer getMaxSortNum(Map<String, Object> map);
	
	/**
	 * 根据条件查询记录
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> querNavBarList(Map<String, Object> map);
}