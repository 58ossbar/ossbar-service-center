package com.ossbar.modules.evgl.activity.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public class TevglActivitySigninInfo extends BaseDomain<TevglActivitySigninInfo> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglActivitySigninInfo";
	public static final String ALIAS_ACTIVITY_ID = "活动id";
	public static final String ALIAS_ACTIVITY_TYPE = "活动类型(固定标识8签到)";
	public static final String ALIAS_ACTIVITY_TITLE = "activityTitle";
	public static final String ALIAS_LIMIT_TIME = "时间范围(单位分钟)";
	public static final String ALIAS_SIGN_TYPE = "签到类型(1普通签到2手势签到3手工登记)";
	public static final String ALIAS_STATE = "状态(Y有效N无效)";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_EMPIRICAL_VALUE = "经验值(签到可获得的经验值)";
	public static final String ALIAS_NUMBER = "number";
	public static final String ALIAS_ACTIVITY_BEGIN_TIME = "活动开始时间";
	public static final String ALIAS_ACTIVITY_END_TIME = "活动结束时间";
	public static final String ALIAS_ACTIVITY_STATE = "活动状态(0未开始1进行中2已结束)";
	public static final String ALIAS_TRAINEE_NUM = "签到人数";
	public static final String ALIAS_IS_TRAINEES_VISIBLE = "学员是否可见";
	public static final String ALIAS_CHAPTER_ID = "所属章节";
	

    /**
     * 活动id       db_column: activity_id 
     */	
 	//@NotNull(msg="活动id不能为空")
 	//@MaxLength(value=32, msg="字段[活动id]超出最大长度[32]")
	private String activityId;
    /**
     * 活动类型(固定标识8签到)       db_column: activity_type 
     */	
 	//@NotNull(msg="活动类型(固定标识8签到)不能为空")
 	//@MaxLength(value=2, msg="字段[活动类型(固定标识8签到)]超出最大长度[2]")
	private String activityType;
    /**
     * activityTitle       db_column: activity_title 
     */	
 	//@NotNull(msg="activityTitle不能为空")
 	//@MaxLength(value=100, msg="字段[activityTitle]超出最大长度[100]")
	private String activityTitle;
    /**
     * 时间范围(单位分钟)       db_column: limit_time 
     */	
 	//@NotNull(msg="时间范围(单位分钟)不能为空")
 	//@MaxLength(value=10, msg="字段[时间范围(单位分钟)]超出最大长度[10]")
	private Integer limitTime;
    /**
     * 签到类型(1普通签到2手势签到3手工登记)       db_column: sign_type 
     */	
 	//@NotNull(msg="签到类型(1普通签到2手势签到3手工登记)不能为空")
 	//@MaxLength(value=2, msg="字段[签到类型(1普通签到2手势签到3手工登记)]超出最大长度[2]")
	private String signType;
    /**
     * 状态(Y有效N无效)       db_column: state 
     */	
 	//@NotNull(msg="状态(Y有效N无效)不能为空")
 	//@MaxLength(value=2, msg="字段[状态(Y有效N无效)]超出最大长度[2]")
	private String state;
    /**
     * 备注       db_column: remark 
     */	
 	//@NotNull(msg="备注不能为空")
 	//@MaxLength(value=1000, msg="字段[备注]超出最大长度[1000]")
	private String remark;
    /**
     * 经验值(签到可获得的经验值)       db_column: empirical_value 
     */	
 	//@NotNull(msg="经验值(签到可获得的经验值)不能为空")
 	//@MaxLength(value=10, msg="字段[经验值(签到可获得的经验值)]超出最大长度[10]")
	private Integer empiricalValue;
    /**
     * number       db_column: number 
     */	
 	//@NotNull(msg="number不能为空")
 	//@MaxLength(value=100, msg="字段[number]超出最大长度[100]")
	private String number;
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
     * 活动状态(0未开始1进行中2已结束)       db_column: activity_state 
     */	
 	//@NotNull(msg="活动状态(0未开始1进行中2已结束)不能为空")
 	//@MaxLength(value=20, msg="字段[活动状态(0未开始1进行中2已结束)]超出最大长度[20]")
	private String activityState;
 	private String activityStateActual; // 实际活动状态
    /**
     * 签到人数       db_column: trainee_num 
     */	
 	//@NotNull(msg="签到人数不能为空")
 	//@MaxLength(value=10, msg="字段[签到人数]超出最大长度[10]")
	private Integer traineeNum;
    /**
     * 学员是否可见       db_column: is_trainees_visible 
     */	
 	//@NotNull(msg="学员是否可见不能为空")
 	//@MaxLength(value=2, msg="字段[学员是否可见]超出最大长度[2]")
	private String isTraineesVisible;
 	/**
     * 所属章节       db_column: chapter_id 
     */	
 	//@NotNull(msg="所属章节不能为空")
 	//@MaxLength(value=32, msg="字段[所属章节]超出最大长度[32]")
	private String chapterId;
	//columns END

	public TevglActivitySigninInfo(){
	}

	public TevglActivitySigninInfo(
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
	public void setActivityType(String value) {
		this.activityType = value;
	}
	public String getActivityType() {
		return this.activityType;
	}
	public void setActivityTitle(String value) {
		this.activityTitle = value;
	}
	public String getActivityTitle() {
		return this.activityTitle;
	}
	public void setLimitTime(Integer value) {
		this.limitTime = value;
	}
	public Integer getLimitTime() {
		return this.limitTime;
	}
	public void setSignType(String value) {
		this.signType = value;
	}
	public String getSignType() {
		return this.signType;
	}
	public void setState(String value) {
		this.state = value;
	}
	public String getState() {
		return this.state;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	public String getRemark() {
		return this.remark;
	}
	public void setEmpiricalValue(Integer value) {
		this.empiricalValue = value;
	}
	public Integer getEmpiricalValue() {
		return this.empiricalValue;
	}
	public void setNumber(String value) {
		this.number = value;
	}
	public String getNumber() {
		return this.number;
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
	public void setActivityState(String value) {
		this.activityState = value;
	}
	public String getActivityState() {
		return this.activityState;
	}
	public void setTraineeNum(Integer value) {
		this.traineeNum = value;
	}
	public Integer getTraineeNum() {
		return this.traineeNum;
	}
	public void setIsTraineesVisible(String value) {
		this.isTraineesVisible = value;
	}
	public String getIsTraineesVisible() {
		return this.isTraineesVisible;
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
}

