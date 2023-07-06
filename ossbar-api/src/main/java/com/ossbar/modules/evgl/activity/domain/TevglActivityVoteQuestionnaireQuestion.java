package com.ossbar.modules.evgl.activity.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;

import java.util.List;

/**
 * <p> Title: 活动-投票/问卷 -> 题目 </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public class TevglActivityVoteQuestionnaireQuestion extends BaseDomain<TevglActivityVoteQuestionnaireQuestion> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglActivityVoteQuestionnaireQuestion";
	public static final String ALIAS_QUESTION_ID = "题目ID";
	public static final String ALIAS_ACTIVITY_ID = "活动ID";
	public static final String ALIAS_QUESTION_NAME = "题目名称";
	public static final String ALIAS_QUESTION_TYPE = "题目类型(1单选2多选)";
	public static final String ALIAS_SORT_NUM = "排序号";
	public static final String ALIAS_QUESTION_ANSWER = "正确答案(存选项ID,多个逗号隔开)";
	public static final String ALIAS_STATE = "状态(Y有效N无效)";
	public static final String ALIAS_TIPS = "提示";
	

    /**
     * 题目ID       db_column: question_id 
     */	
 	//@NotNull(msg="题目ID不能为空")
 	//@MaxLength(value=32, msg="字段[题目ID]超出最大长度[32]")
	private String questionId;
    /**
     * 活动ID       db_column: activity_id 
     */	
 	//@NotNull(msg="活动ID不能为空")
 	//@MaxLength(value=32, msg="字段[活动ID]超出最大长度[32]")
	private String activityId;
    /**
     * 题目名称       db_column: question_name 
     */	
 	//@NotNull(msg="题目名称不能为空")
 	//@MaxLength(value=65535, msg="字段[题目名称]超出最大长度[65535]")
	private String questionName;
    /**
     * 题目类型(1单选2多选)       db_column: question_type 
     */	
 	//@NotNull(msg="题目类型(1单选2多选)不能为空")
 	//@MaxLength(value=2, msg="字段[题目类型(1单选2多选)]超出最大长度[2]")
	private String questionType;
    /**
     * 排序号       db_column: sort_num 
     */	
 	//@NotNull(msg="排序号不能为空")
 	//@MaxLength(value=10, msg="字段[排序号]超出最大长度[10]")
	private Integer sortNum;
    /**
     * 正确答案(存选项ID,多个逗号隔开)       db_column: question_answer 
     */	
 	//@NotNull(msg="正确答案(存选项ID,多个逗号隔开)不能为空")
 	//@MaxLength(value=1000, msg="字段[正确答案(存选项ID,多个逗号隔开)]超出最大长度[1000]")
	private String questionAnswer;
    /**
     * 状态(Y有效N无效)       db_column: state 
     */	
 	//@NotNull(msg="状态(Y有效N无效)不能为空")
 	//@MaxLength(value=2, msg="字段[状态(Y有效N无效)]超出最大长度[2]")
	private String state;
 	 /**
     * 提示       db_column: tips 
     */	
 	////@NotNull(msg="提示不能为空")
 	//@MaxLength(value=500, msg="字段[提示]超出最大长度[500]")
	private String tips;
	//columns END
 	
 	private List<TevglActivityVoteQuestionnaireQuestionOption> children;

	public TevglActivityVoteQuestionnaireQuestion(){
	}

	public TevglActivityVoteQuestionnaireQuestion(
		String questionId
	){
		this.questionId = questionId;
	}

	public void setQuestionId(String value) {
		this.questionId = value;
	}
	public String getQuestionId() {
		return this.questionId;
	}
	public void setActivityId(String value) {
		this.activityId = value;
	}
	public String getActivityId() {
		return this.activityId;
	}
	public void setQuestionName(String value) {
		this.questionName = value;
	}
	public String getQuestionName() {
		return this.questionName;
	}
	public void setQuestionType(String value) {
		this.questionType = value;
	}
	public String getQuestionType() {
		return this.questionType;
	}
	public void setSortNum(Integer value) {
		this.sortNum = value;
	}
	public Integer getSortNum() {
		return this.sortNum;
	}
	public void setQuestionAnswer(String value) {
		this.questionAnswer = value;
	}
	public String getQuestionAnswer() {
		return this.questionAnswer;
	}
	public void setState(String value) {
		this.state = value;
	}
	public String getState() {
		return this.state;
	}
	
	private TevglActivityVoteQuestionnaire tevglActivityVoteQuestionnaire;
	
	public void setTevglActivityVoteQuestionnaire(TevglActivityVoteQuestionnaire tevglActivityVoteQuestionnaire){
		this.tevglActivityVoteQuestionnaire = tevglActivityVoteQuestionnaire;
	}
	
	public TevglActivityVoteQuestionnaire getTevglActivityVoteQuestionnaire() {
		return tevglActivityVoteQuestionnaire;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public List<TevglActivityVoteQuestionnaireQuestionOption> getChildren() {
		return children;
	}

	public void setChildren(List<TevglActivityVoteQuestionnaireQuestionOption> children) {
		this.children = children;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
}

