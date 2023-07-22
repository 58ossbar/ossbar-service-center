package com.ossbar.modules.evgl.activity.api;

import com.ossbar.modules.evgl.activity.vo.ActivityTaskScoreVo;
import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.activity.domain.TevglActivityTaskScore;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglActivityTaskScoreService extends IBaseService<TevglActivityTaskScore>{
	
	/**
	 * 老师评分
	 * @param vo
	 * @param loginUserId
	 * @return
	 */
	R teachGiveScore(ActivityTaskScoreVo vo, String loginUserId);
	
}