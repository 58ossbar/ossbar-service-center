/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhujw
 * @version 1.0
 */

package com.ossbar.modules.sys.domain;


import com.ossbar.core.baseclass.domain.BaseDomain;

public class TsysGroup extends BaseDomain<Object> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TsysGroup";
	public static final String ALIAS_GROUP_ID = "组ID";
	public static final String ALIAS_GROUP_NAME = "组名称";
	public static final String ALIAS_REMARK = "组描述";
	public static final String ALIAS_GROUP_TYPE = "组类别";
	public static final String ALIAS_PARENT_GROUPID = "父组ID ";
	

    /**
     * 组ID       db_column: GROUP_ID 
     */	
	private String groupId;
    /**
     * 组名称       db_column: GROUP_NAME 
     */	
	private String groupName;
    /**
     * 组描述       db_column: REMARK 
     */	
	private String remark;
    /**
     * 组类别
                   db_column: GROUP_TYPE 
     */	
	private String groupType;
    /**
     * 父组ID        db_column: PARENT_GROUPID 
     */	
	private String parentGroupid;
	//columns END

	public TsysGroup(){
	}

	public TsysGroup(
		String groupId
	){
		this.groupId = groupId;
	}

	public void setGroupId(String value) {
		this.groupId = value;
	}
	
	public String getGroupId() {
		return this.groupId;
	}
	public void setGroupName(String value) {
		this.groupName = value;
	}
	
	public String getGroupName() {
		return this.groupName;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setGroupType(String value) {
		this.groupType = value;
	}
	
	public String getGroupType() {
		return this.groupType;
	}
	public void setParentGroupid(String value) {
		this.parentGroupid = value;
	}
	
	public String getParentGroupid() {
		return this.parentGroupid;
	}
}

