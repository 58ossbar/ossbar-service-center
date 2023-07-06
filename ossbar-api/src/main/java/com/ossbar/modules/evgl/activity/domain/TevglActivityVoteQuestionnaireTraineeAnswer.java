package com.ossbar.modules.evgl.activity.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;

/**
 * <p> Title: 活动之投票/问卷---学员作答信息表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public class TevglActivityVoteQuestionnaireTraineeAnswer extends BaseDomain<TevglActivityVoteQuestionnaireTraineeAnswer> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglActivityVoteQuestionnaireTraineeAnswer";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_CT_ID = "ctId";
	public static final String ALIAS_ACTIVITY_ID = "activityId";
	public static final String ALIAS_TRAINEE_ID = "traineeId";
	public static final String ALIAS_QUESTION_ID = "questionId";
	public static final String ALIAS_OPTION_ID = "optionId";
	public static final String ALIAS_CONTENT = "content";
	public static final String ALIAS_QUESTION_TYPE = "questionType";
	

    /**
     * id       db_column: id 
     */	
 	//@NotNull(msg="id不能为空")
 	//@MaxLength(value=32, msg="字段[id]超出最大长度[32]")
	private String id;
    /**
     * ctId       db_column: ct_id 
     */	
 	//@NotNull(msg="ctId不能为空")
 	//@MaxLength(value=32, msg="字段[ctId]超出最大长度[32]")
	private String ctId;
    /**
     * activityId       db_column: activity_id 
     */	
 	//@NotNull(msg="activityId不能为空")
 	//@MaxLength(value=32, msg="字段[activityId]超出最大长度[32]")
	private String activityId;
    /**
     * traineeId       db_column: trainee_id 
     */	
 	//@NotNull(msg="traineeId不能为空")
 	//@MaxLength(value=32, msg="字段[traineeId]超出最大长度[32]")
	private String traineeId;
    /**
     * questionId       db_column: question_id 
     */	
 	//@NotNull(msg="questionId不能为空")
 	//@MaxLength(value=32, msg="字段[questionId]超出最大长度[32]")
	private String questionId;
    /**
     * optionId       db_column: option_id 
     */	
 	//@NotNull(msg="optionId不能为空")
 	//@MaxLength(value=1000, msg="字段[optionId]超出最大长度[1000]")
	private String optionId;
    /**
     * content       db_column: content 
     */	
 	//@NotNull(msg="content不能为空")
 	//@MaxLength(value=65535, msg="字段[content]超出最大长度[65535]")
	private String content;
    /**
     * questionType       db_column: question_type 
     */	
 	//@NotNull(msg="questionType不能为空")
 	//@MaxLength(value=2, msg="字段[questionType]超出最大长度[2]")
	private String questionType;
	//columns END

	public TevglActivityVoteQuestionnaireTraineeAnswer(){
	}

	public TevglActivityVoteQuestionnaireTraineeAnswer(
		String id
	){
		this.id = id;
	}

	public void setId(String value) {
		this.id = value;
	}
	public String getId() {
		return this.id;
	}
	public void setCtId(String value) {
		this.ctId = value;
	}
	public String getCtId() {
		return this.ctId;
	}
	public void setActivityId(String value) {
		this.activityId = value;
	}
	public String getActivityId() {
		return this.activityId;
	}
	public void setTraineeId(String value) {
		this.traineeId = value;
	}
	public String getTraineeId() {
		return this.traineeId;
	}
	public void setQuestionId(String value) {
		this.questionId = value;
	}
	public String getQuestionId() {
		return this.questionId;
	}
	public void setOptionId(String value) {
		this.optionId = value;
	}
	public String getOptionId() {
		return this.optionId;
	}
	public void setContent(String value) {
		this.content = value;
	}
	public String getContent() {
		return this.content;
	}
	public void setQuestionType(String value) {
		this.questionType = value;
	}
	public String getQuestionType() {
		return this.questionType;
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
}

