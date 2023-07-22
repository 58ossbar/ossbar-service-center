package com.ossbar.modules.evgl.examine.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.examine.domain.TevglExaminePaperScore;

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
public interface TevglExaminePaperScoreMapper extends BaseSqlMapper<TevglExaminePaperScore> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);

	List<Map<String, Object>> selectSimpleListMap(Map<String, Object> params);
	
	/**
	 * 批量更新数据
	 * @param list
	 */
	void updateBatchByDuplicateKey(@Param("list") List<TevglExaminePaperScore> list);

	/**
	 * 批量新增
	 * @param list
	 */
	void insertBatch(@Param("list") List<TevglExaminePaperScore> list);
}