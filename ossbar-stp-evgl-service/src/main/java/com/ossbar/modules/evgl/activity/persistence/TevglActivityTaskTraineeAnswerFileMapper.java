package com.ossbar.modules.evgl.activity.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.activity.domain.TevglActivityTaskTraineeAnswerFile;
import com.ossbar.modules.evgl.activity.vo.ActTaskTraineeAnswerFileVo;

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
public interface TevglActivityTaskTraineeAnswerFileMapper extends BaseSqlMapper<TevglActivityTaskTraineeAnswerFile> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	/**
	 * 批量更新
	 * @param list
	 */
	void updateBatchByDuplicateKey(@Param("list") List<TevglActivityTaskTraineeAnswerFile> list);

	/**
	 * 批量新增
	 * @param list
	 */
	void insertBatch(List<TevglActivityTaskTraineeAnswerFile> list);
	
	/**
	 * 根据条件查询记录
	 * @param map
	 * @return
	 */
	List<ActTaskTraineeAnswerFileVo> findTraineeAnswerList(Map<String, Object> map);
}