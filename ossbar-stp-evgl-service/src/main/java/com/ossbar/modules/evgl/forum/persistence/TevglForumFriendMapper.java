package com.ossbar.modules.evgl.forum.persistence;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.evgl.forum.domain.TevglForumFriend;
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
public interface TevglForumFriendMapper extends BaseSqlMapper<TevglForumFriend> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	/**
	 * 查找友情社区
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> friendshipCommunity(Map<String, Object> map);
	
	/**
	 * 获取最大排序号
	 * @param map
	 * @return
	 */
	Integer getMaxSortNum(Map<String, Object> map);
}