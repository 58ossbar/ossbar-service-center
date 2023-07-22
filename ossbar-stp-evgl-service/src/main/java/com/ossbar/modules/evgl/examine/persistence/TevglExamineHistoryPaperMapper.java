package com.ossbar.modules.evgl.examine.persistence;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.evgl.examine.domain.TevglExamineHistoryPaper;
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
public interface TevglExamineHistoryPaperMapper extends BaseSqlMapper<TevglExamineHistoryPaper> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
}