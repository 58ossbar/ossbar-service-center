package com.ossbar.modules.evgl.empirical.persistence;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.evgl.empirical.domain.TevglEmpiricalSetting;
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
public interface TevglEmpiricalSettingMapper extends BaseSqlMapper<TevglEmpiricalSetting> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	/**
	 * 根据条件查询能获得的经验值
	 * @param map
	 * @return
	 */
	Integer getEmpiricalValueByMap(Map<String, Object> map);
	
	/**
	 * 批量插入
	 * @param list
	 */
	void insertBatch(List<TevglEmpiricalSetting> list);
}