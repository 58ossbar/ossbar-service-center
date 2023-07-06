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

public class TevglActivitySigninTrainee extends BaseDomain<TevglActivitySigninTrainee> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglActivitySigninTrainee";
	public static final String ALIAS_ST_ID = "主键id";
	public static final String ALIAS_CT_ID = "课堂id";
	public static final String ALIAS_ACTIVITY_ID = "活动id";
	public static final String ALIAS_TRAINEE_ID = "学员id";
	public static final String ALIAS_SIGN_TIME = "签到时间";
	public static final String ALIAS_SIGN_TYPE = "签到类型(1普通签到2手势签到3手工登记)";
	public static final String ALIAS_FILE = "附件";
	public static final String ALIAS_NUMBER = "手势签到的数字顺序";
	public static final String ALIAS_SIGN_STATE = "签到状态1正常2迟到3早退4旷课5请假";
	public static final String ALIAS_AREA = "签到位置";
	public static final String ALIAS_AREA_TITLE = "areaTitle";
	

    /**
     * 主键id       db_column: st_id 
     */	
 	//@NotNull(msg="主键id不能为空")
 	//@MaxLength(value=32, msg="字段[主键id]超出最大长度[32]")
	private String stId;
    /**
     * 课堂id       db_column: ct_id 
     */	
 	//@NotNull(msg="课堂id不能为空")
 	//@MaxLength(value=32, msg="字段[课堂id]超出最大长度[32]")
	private String ctId;
    /**
     * 活动id       db_column: activity_id 
     */	
 	//@NotNull(msg="活动id不能为空")
 	//@MaxLength(value=32, msg="字段[活动id]超出最大长度[32]")
	private String activityId;
    /**
     * 学员id       db_column: trainee_id 
     */	
 	//@NotNull(msg="学员id不能为空")
 	//@MaxLength(value=32, msg="字段[学员id]超出最大长度[32]")
	private String traineeId;
    /**
     * 签到时间       db_column: sign_time 
     */	
 	//@NotNull(msg="签到时间不能为空")
 	//@MaxLength(value=19, msg="字段[签到时间]超出最大长度[19]")
	private String signTime;
    /**
     * 签到类型(1普通签到2手势签到3手工登记)       db_column: sign_type 
     */	
 	//@NotNull(msg="签到类型(1普通签到2手势签到3手工登记)不能为空")
 	//@MaxLength(value=2, msg="字段[签到类型(1普通签到2手势签到3手工登记)]超出最大长度[2]")
	private String signType;
    /**
     * 附件       db_column: file 
     */	
 	//@NotNull(msg="附件不能为空")
 	//@MaxLength(value=500, msg="字段[附件]超出最大长度[500]")
	private String file;
    /**
     * 手势签到的数字顺序       db_column: number 
     */	
 	//@NotNull(msg="手势签到的数字顺序不能为空")
 	//@MaxLength(value=100, msg="字段[手势签到的数字顺序]超出最大长度[100]")
	private String number;
    /**
     * 签到状态1正常2迟到3早退4旷课5请假       db_column: sign_state 
     */	
 	//@NotNull(msg="签到状态1正常2迟到3早退4旷课5请假不能为空")
 	//@MaxLength(value=2, msg="字段[签到状态1正常2迟到3早退4旷课5请假]超出最大长度[2]")
	private String signState;
 	/**
     * 签到具体位置       db_column: area 
     */	
 	////@NotNull(msg="签到位置不能为空")
 	//@MaxLength(value=500, msg="字段[签到位置]超出最大长度[500]")
	private String area;
 	/**
     * areaTitle       db_column: area_title 
     */	
 	//@NotNull(msg="areaTitle不能为空")
 	//@MaxLength(value=255, msg="字段[areaTitle]超出最大长度[255]")
	private String areaTitle;
	//columns END
 	
 	private String attachId;

	public TevglActivitySigninTrainee(){
	}

	public TevglActivitySigninTrainee(
		String stId
	){
		this.stId = stId;
	}

	public void setStId(String value) {
		this.stId = value;
	}
	public String getStId() {
		return this.stId;
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
	
	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public void setSignType(String value) {
		this.signType = value;
	}
	public String getSignType() {
		return this.signType;
	}
	public void setFile(String value) {
		this.file = value;
	}
	public String getFile() {
		return this.file;
	}
	public void setNumber(String value) {
		this.number = value;
	}
	public String getNumber() {
		return this.number;
	}
	public void setSignState(String value) {
		this.signState = value;
	}
	public String getSignState() {
		return this.signState;
	}
	
	private TevglActivitySigninInfo tevglActivitySigninInfo;
	
	public void setTevglActivitySigninInfo(TevglActivitySigninInfo tevglActivitySigninInfo){
		this.tevglActivitySigninInfo = tevglActivitySigninInfo;
	}
	
	public TevglActivitySigninInfo getTevglActivitySigninInfo() {
		return tevglActivitySigninInfo;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaTitle() {
		return areaTitle;
	}

	public void setAreaTitle(String areaTitle) {
		this.areaTitle = areaTitle;
	}
}

