package com.ossbar.modules.evgl.site.persistence;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.evgl.site.domain.TevglSiteFeedback;
import com.ossbar.core.baseclass.persistence.BaseSqlMapper;

/**
 * <p> Title: 意见反馈</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Mapper
public interface TevglSiteFeedbackMapper extends BaseSqlMapper<TevglSiteFeedback> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
}