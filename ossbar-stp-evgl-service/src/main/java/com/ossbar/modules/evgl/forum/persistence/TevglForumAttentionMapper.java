package com.ossbar.modules.evgl.forum.persistence;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.evgl.forum.domain.TevglForumAttention;
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
public interface TevglForumAttentionMapper extends BaseSqlMapper<TevglForumAttention> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	/**
	 * 展示关注列表
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryFollowList(Map<String, Object> map);
	
	/**
	 * 进查询关注的人（主键id）
	 * @param map
	 * @return
	 */
	List<String> queryFollowTraineeIdList(Map<String, Object> map);
	
}