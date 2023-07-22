package com.ossbar.modules.evgl.site.api;

import java.util.Map;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.domain.TevglSiteVideo;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglSiteVideoService extends IBaseService<TevglSiteVideo>{
	
	/**
	 * 根据条件查询记录
	 * @param params
	 * @return
	 */
	R queryVideoList(Map<String, Object> params);
	
	/**
	 * 保存
	 * @param tevglSiteVideo
	 * @return
	 */
	R saveVideo(TevglSiteVideo tevglSiteVideo);
	
	/**
	 * 删除视频
	 * @param videoId
	 * @param loginUserId
	 * @return
	 */
	R deleteVideo(String videoId, String loginUserId);
	
	/**
	 * 查看详情
	 * @param videoId
	 * @return
	 */
	R viewVideo(String videoId);
	
	TevglSiteVideo selectObjectById(Object id);
}