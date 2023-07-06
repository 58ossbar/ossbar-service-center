package com.ossbar.modules.evgl.activity.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;

/**
 * <p> Title: 活动-投票/问卷</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public class TevglActivityVoteQuestionnaire extends BaseDomain<TevglActivityVoteQuestionnaire> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglActivityVoteQuestionnaire";
	public static final String ALIAS_ACTIVITY_ID = "活动ID";
	public static final String ALIAS_RESGROUP_ID = "所属资源分组";
	public static final String ALIAS_ACTIVITY_TITLE = "活动标题";
	public static final String ALIAS_ACTIVITY_PIC = "活动封面";
	public static final String ALIAS_EMPIRICAL_VALUE = "empiricalValue";
	public static final String ALIAS_ACTIVITY_BEGIN_TIME = "活动开始时间";
	public static final String ALIAS_ACTIVITY_END_TIME = "活动结束时间";
	public static final String ALIAS_STATE = "状态(Y有效N无效)";
	public static final String ALIAS_ANSWER_NUMBER = "回答人数";
	public static final String ALIAS_QUESTION_NUMBER = "题目数量";
	public static final String ALIAS_ACTIVITY_STATE = "活动状态(0未开始1进行中2已结束)";
	public static final String ALIAS_IS_SHOW = "投票后立即显示结果(Y/N)";
	public static final String ALIAS_ACTIVITY_TYPE = "活动类型(固定值1投票/问卷)";
	public static final String ALIAS_CHAPTER_ID = "所属章节";
	

    /**
     * 活动ID       db_column: activity_id 
     */	
 	//@NotNull(msg="活动ID不能为空")
 	//@MaxLength(value=32, msg="字段[活动ID]超出最大长度[32]")
	private String activityId;
    /**
     * 所属资源分组       db_column: resgroup_id 
     */	
 	//@NotNull(msg="所属资源分组不能为空")
 	//@MaxLength(value=32, msg="字段[所属资源分组]超出最大长度[32]")
	private String resgroupId;
    /**
     * 活动标题       db_column: activity_title 
     */	
 	//@NotNull(msg="活动标题不能为空")
 	//@MaxLength(value=100, msg="字段[活动标题]超出最大长度[100]")
	private String activityTitle;
    /**
     * 活动封面       db_column: activity_pic 
     */	
 	//@NotNull(msg="活动封面不能为空")
 	//@MaxLength(value=500, msg="字段[活动封面]超出最大长度[500]")
	private String activityPic;
    /**
     * empiricalValue       db_column: empirical_value 
     */	
 	//@NotNull(msg="empiricalValue不能为空")
 	//@MaxLength(value=10, msg="字段[empiricalValue]超出最大长度[10]")
	private Integer empiricalValue;
    /**
     * 活动开始时间       db_column: activity_begin_time 
     */	
 	//@NotNull(msg="活动开始时间不能为空")
 	//@MaxLength(value=20, msg="字段[活动开始时间]超出最大长度[20]")
	private String activityBeginTime;
    /**
     * 活动结束时间       db_column: activity_end_time 
     */	
 	//@NotNull(msg="活动结束时间不能为空")
 	//@MaxLength(value=20, msg="字段[活动结束时间]超出最大长度[20]")
	private String activityEndTime;
    /**
     * 状态(Y有效N无效)       db_column: state 
     */	
 	//@NotNull(msg="状态(Y有效N无效)不能为空")
 	//@MaxLength(value=2, msg="字段[状态(Y有效N无效)]超出最大长度[2]")
	private String state;
    /**
     * 回答人数       db_column: answer_number 
     */	
 	//@NotNull(msg="回答人数不能为空")
 	//@MaxLength(value=10, msg="字段[回答人数]超出最大长度[10]")
	private Integer answerNumber;
    /**
     * 题目数量       db_column: question_number 
     */	
 	//@NotNull(msg="题目数量不能为空")
 	//@MaxLength(value=10, msg="字段[题目数量]超出最大长度[10]")
	private Integer questionNumber;
    /**
     * 活动状态(0未开始1进行中2已结束)       db_column: activity_state 
     */	
 	//@NotNull(msg="活动状态(0未开始1进行中2已结束)不能为空")
 	//@MaxLength(value=2, msg="字段[活动状态(0未开始1进行中2已结束)]超出最大长度[2]")
	private String activityState;
 	private String activityStateActual; // 实际活动状态
    /**
     * 投票后立即显示结果(Y/N)       db_column: is_show 
     */	
 	//@NotNull(msg="投票后立即显示结果(Y/N)不能为空")
 	//@MaxLength(value=2, msg="字段[投票后立即显示结果(Y/N)]超出最大长度[2]")
	private String isShow;
 	/**
     * 排序号       db_column: sort_num 
     */	
 	////@NotNull(msg="排序号不能为空")
 	//@MaxLength(value=10, msg="字段[排序号]超出最大长度[10]")
	private Integer sortNum;
    /**
     * 活动类型(固定值1投票/问卷)       db_column: activity_type 
     */	
 	//@NotNull(msg="活动类型(固定值1投票/问卷)不能为空")
 	//@MaxLength(value=2, msg="字段[活动类型(固定值1投票/问卷)]超出最大长度[2]")
	private String activityType;
 	/**
     * 用途(来源字典)       db_column: purpose 
     */	
 	////@NotNull(msg="用途(来源字典)不能为空")
 	//@MaxLength(value=2, msg="字段[用途(来源字典)]超出最大长度[2]")
	private String purpose;
 	/**
     * 所属章节       db_column: chapter_id 
     */	
 	//@NotNull(msg="所属章节不能为空")
 	//@MaxLength(value=32, msg="字段[所属章节]超出最大长度[32]")
	private String chapterId;
	//columns END
 	
 	private String oldActivityId;

	public TevglActivityVoteQuestionnaire(){
	}

	public TevglActivityVoteQuestionnaire(
		String activityId
	){
		this.activityId = activityId;
	}

	public void setActivityId(String value) {
		this.activityId = value;
	}
	public String getActivityId() {
		return this.activityId;
	}
	public void setResgroupId(String value) {
		this.resgroupId = value;
	}
	public String getResgroupId() {
		return this.resgroupId;
	}
	public void setActivityTitle(String value) {
		this.activityTitle = value;
	}
	public String getActivityTitle() {
		return this.activityTitle;
	}
	public void setActivityPic(String value) {
		this.activityPic = value;
	}
	public String getActivityPic() {
		return this.activityPic;
	}
	public void setEmpiricalValue(Integer value) {
		this.empiricalValue = value;
	}
	public Integer getEmpiricalValue() {
		return this.empiricalValue;
	}
	public void setActivityBeginTime(String value) {
		this.activityBeginTime = value;
	}
	public String getActivityBeginTime() {
		return this.activityBeginTime;
	}
	public void setActivityEndTime(String value) {
		this.activityEndTime = value;
	}
	public String getActivityEndTime() {
		return this.activityEndTime;
	}
	public void setState(String value) {
		this.state = value;
	}
	public String getState() {
		return this.state;
	}
	public void setAnswerNumber(Integer value) {
		this.answerNumber = value;
	}
	public Integer getAnswerNumber() {
		return this.answerNumber;
	}
	public void setQuestionNumber(Integer value) {
		this.questionNumber = value;
	}
	public Integer getQuestionNumber() {
		return this.questionNumber;
	}
	public void setActivityState(String value) {
		this.activityState = value;
	}
	public String getActivityState() {
		return this.activityState;
	}
	public void setIsShow(String value) {
		this.isShow = value;
	}
	public String getIsShow() {
		return this.isShow;
	}
	public void setActivityType(String value) {
		this.activityType = value;
	}
	public String getActivityType() {
		return this.activityType;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getChapterId() {
		return chapterId;
	}
	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}
	public String getActivityStateActual() {
		return activityStateActual;
	}
	public void setActivityStateActual(String activityStateActual) {
		this.activityStateActual = activityStateActual;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public String getOldActivityId() {
		return oldActivityId;
	}

	public void setOldActivityId(String oldActivityId) {
		this.oldActivityId = oldActivityId;
	}

}

