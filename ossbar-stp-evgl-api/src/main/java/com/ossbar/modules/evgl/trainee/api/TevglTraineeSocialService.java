package com.ossbar.modules.evgl.trainee.api;

import java.util.List;
import java.util.Map;
import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeSocial;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglTraineeSocialService extends IBaseService<TevglTraineeSocial>{
	
	public List<TevglTraineeSocial> queryByMap(Map<String, Object> params);
}