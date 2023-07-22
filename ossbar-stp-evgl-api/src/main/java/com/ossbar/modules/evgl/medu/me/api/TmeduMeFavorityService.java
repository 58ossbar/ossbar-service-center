package com.ossbar.modules.evgl.medu.me.api;

import java.util.List;
import java.util.Map;

import com.ossbar.modules.evgl.medu.me.domain.TmeduMeFavority;
import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TmeduMeFavorityService extends IBaseService<TmeduMeFavority>{
	
	/**
	 * 根据条件查询记录
	 * @param map
	 * @return
	 */
	List<TmeduMeFavority> selectListByMap(Map<String, Object> map);
	
	/**
	 * 查询收藏的课堂数据
	 * @param map
	 * @return
	 */
	R selectClassroomList(Map<String, Object> map, String loginUserId);
	
	/**
	 * 查询收藏的视频
	 * @param map
	 * @param loginUserId
	 * @return
	 */
	R selectVideoList(Map<String, Object> map, String loginUserId);
	
	/**
	 * 查询收藏的题目
	 * @param map
	 * @param loginUserId
	 * @return
	 */
	R selectQuestionList(Map<String, Object> map, String loginUserId);
	
	/**
	 * 查询收藏的博客
	 * @param map
	 * @param loginUserId
	 * @return
	 */
	R selectBlogList(Map<String, Object> map, String loginUserId);
	
}