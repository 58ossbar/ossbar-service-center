package com.ossbar.modules.evgl.question.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.question.domain.TepExaminePaperScore;

/**
 * <p>
 * Title:试卷成绩表
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
public interface TepExaminePaperScoreMapper extends BaseSqlMapper<TepExaminePaperScore> {

	// 根据条件查询记录，返回List<Map>
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);

	/**
	 * 
	 * @desc //TODO 关联问题表数据查询
	 * @author huangwb
	 * 
	 * @data 2019年3月4日 下午5:41:28
	 */
	List<Map<String, Object>> selectListMapLinkQuestionByMap(Map<String, Object> map);
	
	/**
	 * 关联问题表数据查询，返回List<Map>
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectSimpleListMap(Map<String, Object> map);
	
	/**
	 * 批量更新数据
	 * @param list
	 */
	void updateBatchByDuplicateKey(@Param("list") List<TepExaminePaperScore> list);
	
	/**
	 * 批量新增
	 * @param list
	 */
	void insertBatch(@Param("list") List<TepExaminePaperScore> list);
}