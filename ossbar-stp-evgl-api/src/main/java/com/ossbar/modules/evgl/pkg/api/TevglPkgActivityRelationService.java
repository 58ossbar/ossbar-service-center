package com.ossbar.modules.evgl.pkg.api;

import java.util.List;
import java.util.Map;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgActivityRelation;

/**
 * <p> Title: 教学包与活动关系表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglPkgActivityRelationService extends IBaseService<TevglPkgActivityRelation>{
	
	List<Map<String, Object>> selectSimpleListMap(Map<String, Object> params);
	
	List<TevglPkgActivityRelation> selectListByMap(Map<String, Object> params);
	
}