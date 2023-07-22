package com.ossbar.modules.evgl.site.api;

import java.util.List;
import java.util.Map;

import com.ossbar.common.exception.OssbarException;
import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.modules.evgl.site.domain.TevglSiteVideoRelation;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglSiteVideoRelationService extends IBaseService<TevglSiteVideoRelation>{
	
	/**
	 * 批量新增
	 * @param list
	 * @throws OssbarException
	 */
	void insertBatch(List<TevglSiteVideoRelation> list) throws OssbarException;
	
	List<TevglSiteVideoRelation> selectListByMap(Map<String, Object> map);
	
}