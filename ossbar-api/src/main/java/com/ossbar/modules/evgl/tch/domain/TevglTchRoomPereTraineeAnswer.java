package com.ossbar.modules.evgl.tch.domain;

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

public class TevglTchRoomPereTraineeAnswer extends BaseDomain<TevglTchRoomPereTraineeAnswer> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglTchRoomPereTraineeAnswer";
	public static final String ALIAS_TRAINEE_ANSWER_ID = "学员抢答id";
	public static final String ALIAS_CT_ID = "课堂id";
	public static final String ALIAS_TRAINEE_ID = "学员id";
	public static final String ALIAS_ANSER_ID = "抢答id";
	public static final String ALIAS_TRAINEE_EMPIRICAL_VALUE = "经验值";
	public static final String ALIAS_SORT_NUM = "排序号";
	

    /**
     * 学员抢答id       db_column: trainee_answer_id 
     */	
 	//@NotNull(msg="学员抢答id不能为空")
 	//@MaxLength(value=32, msg="字段[学员抢答id]超出最大长度[32]")
	private String traineeAnswerId;
    /**
     * 课堂id       db_column: ct_id 
     */	
 	//@NotNull(msg="课堂id不能为空")
 	//@MaxLength(value=32, msg="字段[课堂id]超出最大长度[32]")
	private String ctId;
    /**
     * 学员id       db_column: trainee_id 
     */	
 	//@NotNull(msg="学员id不能为空")
 	//@MaxLength(value=32, msg="字段[学员id]超出最大长度[32]")
	private String traineeId;
    /**
     * 抢答id       db_column: anser_id 
     */	
 	//@NotNull(msg="抢答id不能为空")
 	//@MaxLength(value=32, msg="字段[抢答id]超出最大长度[32]")
	private String anserId;
    /**
     * 经验值       db_column: trainee_empirical_value 
     */	
 	//@NotNull(msg="经验值不能为空")
 	//@MaxLength(value=10, msg="字段[经验值]超出最大长度[10]")
	private Integer traineeEmpiricalValue;
    /**
     * 排序号       db_column: sort_num 
     */	
 	//@NotNull(msg="排序号不能为空")
 	//@MaxLength(value=10, msg="字段[排序号]超出最大长度[10]")
	private Integer sortNum;
	//columns END
 	
 	private Integer empiricalValue;

	public TevglTchRoomPereTraineeAnswer(){
	}

	public TevglTchRoomPereTraineeAnswer(
		String traineeAnswerId
	){
		this.traineeAnswerId = traineeAnswerId;
	}

	public void setTraineeAnswerId(String value) {
		this.traineeAnswerId = value;
	}
	public String getTraineeAnswerId() {
		return this.traineeAnswerId;
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
	public void setAnserId(String value) {
		this.anserId = value;
	}
	public String getAnserId() {
		return this.anserId;
	}
	public void setTraineeEmpiricalValue(Integer value) {
		this.traineeEmpiricalValue = value;
	}
	public Integer getTraineeEmpiricalValue() {
		return this.traineeEmpiricalValue;
	}
	public void setSortNum(Integer value) {
		this.sortNum = value;
	}
	public Integer getSortNum() {
		return this.sortNum;
	}
	public Integer getEmpiricalValue() {
		return empiricalValue;
	}

	public void setEmpiricalValue(Integer empiricalValue) {
		this.empiricalValue = empiricalValue;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}

