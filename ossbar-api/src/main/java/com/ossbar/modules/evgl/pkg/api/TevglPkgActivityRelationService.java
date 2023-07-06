package com.ossbar.modules.evgl.pkg.api;

import com.ossbar.modules.evgl.pkg.domain.TevglPkgActivityRelation;

import java.util.List;
import java.util.Map;

/**
 * <p> Title: 教学包与活动关系表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglPkgActivityRelationService {
	
	List<Map<String, Object>> selectSimpleListMap(Map<String, Object> params);
	
	List<TevglPkgActivityRelation> selectListByMap(Map<String, Object> params);
	
}