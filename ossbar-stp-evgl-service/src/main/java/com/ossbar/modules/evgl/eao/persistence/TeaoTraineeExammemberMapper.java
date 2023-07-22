package com.ossbar.modules.evgl.eao.persistence;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.evgl.eao.domain.TeaoTraineeExammember;
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
public interface TeaoTraineeExammemberMapper extends BaseSqlMapper<TeaoTraineeExammember> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	List<Map<String, Object>> selectListByMapForSp(Map<String, Object> map);
	
	List<Map<String, Object>> selectListByMapForExam(Map<String, Object> map);

	void deleteOther(Map<String, Object> map);
	
	int updateForState(Map<String, Object> map);
	
	List<TeaoTraineeExammember> selectListByMapForScore(Map<String, Object> map);
}