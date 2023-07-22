package com.ossbar.modules.evgl.empirical.api;

import com.ossbar.modules.evgl.empirical.domain.TevglEmpiricalLogVideo;
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

public interface TevglEmpiricalLogVideoService extends IBaseService<TevglEmpiricalLogVideo>{
	
	/**
	 * 查看视频时，记录并获得经验值
	 * @param ctId
	 * @param pkgId
	 * @param subjectId
	 * @param chapterId
	 * @param videoId
	 * @param loginUserId
	 * @return
	 */
	R viewVideo(String ctId, String pkgId, String subjectId, String chapterId, String videoId, String loginUserId);
	
}