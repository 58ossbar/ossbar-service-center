package com.ossbar.modules.evgl.tch.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;

/**
 * <p> Title: 班级学员</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * //@author zhuq
 * //@version 1.0
 */

public class TevglTchClasstrainee extends BaseDomain<TevglTchClasstrainee> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglTchClasstrainee";
	public static final String ALIAS_CT_ID = "主键ID";
	public static final String ALIAS_CLASS_ID = "班级ID";
	public static final String ALIAS_TRAINEE_ID = "学员ID";
	

    /**
     * 主键ID       db_column: ct_id 
     */	
 	//@NotNull(msg="主键ID不能为空")
 	//@MaxLength(value=32, msg="字段[主键ID]超出最大长度[32]")
	private String ctId;
    /**
     * 班级ID       db_column: class_id 
     */	
 	//@NotNull(msg="班级ID不能为空")
 	//@MaxLength(value=32, msg="字段[班级ID]超出最大长度[32]")
	private String classId;
    /**
     * 学员ID       db_column: trainee_id 
     */	
 	//@NotNull(msg="学员ID不能为空")
 	//@MaxLength(value=32, msg="字段[学员ID]超出最大长度[32]")
	private String traineeId;
	//columns END

 	private String traineeName;
 	private String className;
 	
	public TevglTchClasstrainee(){
	}

	public TevglTchClasstrainee(
		String ctId
	){
		this.ctId = ctId;
	}

	public void setCtId(String value) {
		this.ctId = value;
	}
	public String getCtId() {
		return this.ctId;
	}
	public void setClassId(String value) {
		this.classId = value;
	}
	public String getClassId() {
		return this.classId;
	}
	public void setTraineeId(String value) {
		this.traineeId = value;
	}
	public String getTraineeId() {
		return this.traineeId;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public String getTraineeName() {
		return traineeName;
	}

	public void setTraineeName(String traineeName) {
		this.traineeName = traineeName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}

