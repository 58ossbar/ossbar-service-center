package com.ossbar.modules.evgl.tch.persistence;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomGroup;
import com.ossbar.core.baseclass.persistence.BaseSqlMapper;

/**
 * <p> Title: 课堂小组</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Mapper
public interface TevglTchClassroomGroupMapper extends BaseSqlMapper<TevglTchClassroomGroup> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	/**
	 * 获取排序号
	 * @param map
	 * @return
	 */
	Integer getMaxSortNum(Map<String, Object> map);
	
	/**
	 * 更新数量
	 * @param tevglTchClassroomGroup
	 */
	void plusNum(TevglTchClassroomGroup tevglTchClassroomGroup);
	
	/**
	 * 批量新增
	 * @param list
	 */
	void insertBatch(List<TevglTchClassroomGroup> list);
	
	/**
	 * 批量更新人数
	 * @param list
	 */
	void plusNumBatchByCaseWhen(List<TevglTchClassroomGroup> list);
}