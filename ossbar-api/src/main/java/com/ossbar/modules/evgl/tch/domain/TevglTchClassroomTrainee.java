package com.ossbar.modules.evgl.tch.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;

/**
 * <p> Title: 课堂成员</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public class TevglTchClassroomTrainee extends BaseDomain<TevglTchClassroomTrainee> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglTchClassroomTrainee";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_CT_ID = "课堂ID";
	public static final String ALIAS_TRAINEE_ID = "学员ID";
	public static final String ALIAS_CLASS_ID = "班级ID";
	public static final String ALIAS_JOIN_DATE = "加入时间";
	public static final String ALIAS_STATE = "state";
	

    /**
     * 主键       db_column: id 
     */	
 	//@NotNull(msg="主键不能为空")
 	//@MaxLength(value=32, msg="字段[主键]超出最大长度[32]")
	private String id;
    /**
     * 课堂ID       db_column: ct_id 
     */	
 	//@NotNull(msg="课堂ID不能为空")
 	//@MaxLength(value=32, msg="字段[课堂ID]超出最大长度[32]")
	private String ctId;
    /**
     * 学员ID       db_column: trainee_id 
     */	
 	//@NotNull(msg="学员ID不能为空")
 	//@MaxLength(value=32, msg="字段[学员ID]超出最大长度[32]")
	private String traineeId;
    /**
     * 班级ID       db_column: class_id 
     */	
 	//@NotNull(msg="班级ID不能为空")
 	//@MaxLength(value=32, msg="字段[班级ID]超出最大长度[32]")
	private String classId;
    /**
     * 加入时间       db_column: join_date 
     */	
 	//@NotNull(msg="加入时间不能为空")
 	//@MaxLength(value=10, msg="字段[加入时间]超出最大长度[10]")
	private String joinDate;
    /**
     * state       db_column: state 
     */	
 	//@NotNull(msg="state不能为空")
 	//@MaxLength(value=2, msg="字段[state]超出最大长度[2]")
	private String state;
	//columns END

	public TevglTchClassroomTrainee(){
	}

	public TevglTchClassroomTrainee(
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
	public void setTraineeId(String value) {
		this.traineeId = value;
	}
	public String getTraineeId() {
		return this.traineeId;
	}
	public void setClassId(String value) {
		this.classId = value;
	}
	public String getClassId() {
		return this.classId;
	}
	public void setJoinDate(String value) {
		this.joinDate = value;
	}
	public String getJoinDate() {
		return this.joinDate;
	}
	public void setState(String value) {
		this.state = value;
	}
	public String getState() {
		return this.state;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}

