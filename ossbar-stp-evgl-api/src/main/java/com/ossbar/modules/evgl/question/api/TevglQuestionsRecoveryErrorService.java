package com.ossbar.modules.evgl.question.api;

import com.ossbar.common.exception.OssbarException;
import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.question.domain.TevglQuestionsRecoveryError;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglQuestionsRecoveryErrorService extends IBaseService<TevglQuestionsRecoveryError>{
	
	/**
	 * 新增
	 */
	public R save(TevglQuestionsRecoveryError tevglQuestionsRecoveryError) throws OssbarException;
	
	/**
	 * 根据题目id查询题目信息
	 * @param questionsId
	 * @return
	 */
	public R selectByCollectionMap(String questionsId);
}