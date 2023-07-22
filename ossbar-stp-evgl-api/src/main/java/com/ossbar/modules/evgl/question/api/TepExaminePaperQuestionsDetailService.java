package com.ossbar.modules.evgl.question.api;

import java.util.List;
import java.util.Map;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.modules.evgl.question.domain.TepExaminePaperQuestionsDetail;

/**
 * <p> Title: 试卷题目详情表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TepExaminePaperQuestionsDetailService extends IBaseService<TepExaminePaperQuestionsDetail> {
	
	
	/**
	 * <p>关联题目表查询试卷题目详情</p>  
	 * @author huj
	 * @data 2019年12月30日	
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectListMapLinkQuestionByMap(Map<String, Object> map);
	
	/**
	 * 查找这个题目被哪些试卷所使用了
	 * @param questionId
	 * @return
	 */
	List<String> findPaperIdListByQuestionId(String questionId);
}
