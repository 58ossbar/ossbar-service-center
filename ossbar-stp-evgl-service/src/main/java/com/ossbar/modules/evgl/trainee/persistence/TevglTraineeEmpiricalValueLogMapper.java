package com.ossbar.modules.evgl.trainee.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeEmpiricalValueLog;

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
public interface TevglTraineeEmpiricalValueLogMapper extends BaseSqlMapper<TevglTraineeEmpiricalValueLog> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	/**
	 * 批量新增
	 * @param list
	 */
	void insertBatch(List<TevglTraineeEmpiricalValueLog> list);
	
	/**
	 * 根据条件求和经验值
	 * @param params
	 * @return
	 */
	Integer sumEmpiricalValueByMap(Map<String, Object> params);
	
	List<Map<String, Object>> selectSimpleListMapByMap(Map<String, Object> map);
	
	void updateBatchByCaseWhen(List<TevglTraineeEmpiricalValueLog> list);
}