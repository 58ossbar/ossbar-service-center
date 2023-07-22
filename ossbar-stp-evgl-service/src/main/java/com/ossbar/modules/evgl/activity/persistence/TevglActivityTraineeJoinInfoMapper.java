package com.ossbar.modules.evgl.activity.persistence;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.evgl.activity.domain.TevglActivityTraineeJoinInfo;
import com.ossbar.core.baseclass.persistence.BaseSqlMapper;

/**
 * <p> Title: 学员活动参与情况信息</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Mapper
public interface TevglActivityTraineeJoinInfoMapper extends BaseSqlMapper<TevglActivityTraineeJoinInfo> {
	
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
	void insertBatch(List<TevglActivityTraineeJoinInfo> list);
}