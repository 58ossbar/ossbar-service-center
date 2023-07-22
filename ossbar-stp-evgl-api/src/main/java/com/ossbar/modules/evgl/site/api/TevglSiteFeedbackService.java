package com.ossbar.modules.evgl.site.api;

import java.util.Map;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.domain.TevglSiteFeedback;

/**
 * <p> Title: 意见反馈表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglSiteFeedbackService extends IBaseService<TevglSiteFeedback>{
	
	R queryFeedbackInfo(Map<String, Object> params);
	
}