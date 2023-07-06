package com.ossbar.modules.evgl.tch.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;

/**
 * <p> Title: 课堂小组</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public class TevglTchClassroomGroup extends BaseDomain<TevglTchClassroomGroup> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglTchClassroomGroup";
	public static final String ALIAS_GP_ID = "主键小组ID";
	public static final String ALIAS_CT_ID = "课堂ID";
	public static final String ALIAS_GROUP_NAME = "小组名称";
	public static final String ALIAS_SORT_NUM = "排序号";
	public static final String ALIAS_STATE = "状态(Y有效N无效)";
	

    /**
     * 主键小组ID       db_column: gp_id 
     */	
 	//@NotNull(msg="主键小组ID不能为空")
 	//@MaxLength(value=32, msg="字段[主键小组ID]超出最大长度[32]")
	private String gpId;
    /**
     * 课堂ID       db_column: ct_id 
     */	
 	//@NotNull(msg="课堂ID不能为空")
 	//@MaxLength(value=32, msg="字段[课堂ID]超出最大长度[32]")
	private String ctId;
    /**
     * 小组名称       db_column: group_name 
     */	
 	//@NotNull(msg="小组名称不能为空")
 	//@MaxLength(value=20, msg="字段[小组名称]超出最大长度[20]")
	private String groupName;
    /**
     * 排序号       db_column: sort_num 
     */	
 	//@NotNull(msg="排序号不能为空")
 	//@MaxLength(value=10, msg="字段[排序号]超出最大长度[10]")
	private Integer sortNum;
    /**
     * 状态(Y有效N无效)       db_column: state 
     */	
 	//@NotNull(msg="状态(Y有效N无效)不能为空")
 	//@MaxLength(value=2, msg="字段[状态(Y有效N无效)]超出最大长度[2]")
	private String state;
 	/**
     * 当前课堂小组的人数       db_column: number 
     */	
 	//@NotNull(msg="当前课堂小组的人数不能为空")
 	//@MaxLength(value=10, msg="字段[当前课堂小组的人数]超出最大长度[10]")
	private Integer number;
	//columns END

	public TevglTchClassroomGroup(){
	}

	public TevglTchClassroomGroup(
		String gpId
	){
		this.gpId = gpId;
	}

	public void setGpId(String value) {
		this.gpId = value;
	}
	public String getGpId() {
		return this.gpId;
	}
	public void setCtId(String value) {
		this.ctId = value;
	}
	public String getCtId() {
		return this.ctId;
	}
	public void setGroupName(String value) {
		this.groupName = value;
	}
	public String getGroupName() {
		return this.groupName;
	}
	public void setSortNum(Integer value) {
		this.sortNum = value;
	}
	public Integer getSortNum() {
		return this.sortNum;
	}
	public void setState(String value) {
		this.state = value;
	}
	public String getState() {
		return this.state;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}

	private TevglTchClassroom tevglTchClassroom;
	
	public void setTevglTchClassroom(TevglTchClassroom tevglTchClassroom){
		this.tevglTchClassroom = tevglTchClassroom;
	}
	
	public TevglTchClassroom getTevglTchClassroom() {
		return tevglTchClassroom;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}

