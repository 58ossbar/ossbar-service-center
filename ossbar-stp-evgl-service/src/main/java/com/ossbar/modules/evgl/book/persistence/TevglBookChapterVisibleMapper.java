package com.ossbar.modules.evgl.book.persistence;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.evgl.book.domain.TevglBookChapterVisible;
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
public interface TevglBookChapterVisibleMapper extends BaseSqlMapper<TevglBookChapterVisible> {
	
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
	void insertBatch(List<TevglBookChapterVisible> list);
	
	/**
	 * 批量更新可见
	 */
	void updateVisibleBatch(Map<String, Object> data);
}