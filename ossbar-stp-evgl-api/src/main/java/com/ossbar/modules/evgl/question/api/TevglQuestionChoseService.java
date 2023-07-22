package com.ossbar.modules.evgl.question.api;

import java.util.List;
import java.util.Map;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.modules.evgl.question.domain.TevglQuestionChose;

/**
 * <p> Title: 题目选项</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglQuestionChoseService extends IBaseService<TevglQuestionChose>{
	
	/**
	 * <p>根据条件查询记录</p>  
	 * @author huj
	 * @data 2019年12月25日	
	 * @param map
	 * @return
	 */
	List<TevglQuestionChose> selectListByMap(Map<String, Object> map);
	
	/**
	 * <p>根据题目正确选项ID批量查询 </p>  
	 * @author huj
	 * @data 2019年12月30日	
	 * @param arrays
	 * @return
	 */
	List<TevglQuestionChose> selectBatchQuestionsChoseByReplyIds(String[] arrays);
	
	TevglQuestionChose selectObjectById(Object id);
	
}