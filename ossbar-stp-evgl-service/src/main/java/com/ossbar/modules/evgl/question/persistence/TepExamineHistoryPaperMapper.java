package com.ossbar.modules.evgl.question.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.question.domain.TepExamineHistoryPaper;

/**
 * <p> Title: 学员答卷历史表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhujw
 * @version 1.0
 */

@Mapper
public interface TepExamineHistoryPaperMapper extends BaseSqlMapper<TepExamineHistoryPaper> {
	
	//根据条件查询记录，返回List<Map>
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectSimpleListMap(Map<String, Object> map);
	
	/**
	 * 根据条件查询历史信息以及成绩信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectScoreAndHistoryPaper(Map<String, Object> map);
}