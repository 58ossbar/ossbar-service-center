package com.ossbar.modules.evgl.question.api;

import java.util.List;
import java.util.Map;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.modules.evgl.question.domain.TepExamineDynamicQuestionsOptions;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TepExamineDynamicQuestionsOptionsService extends IBaseService<TepExamineDynamicQuestionsOptions> {

	List<Map<String, Object>> selectListMapWithQuestionInfoByDyId(String dyId);
	
}
