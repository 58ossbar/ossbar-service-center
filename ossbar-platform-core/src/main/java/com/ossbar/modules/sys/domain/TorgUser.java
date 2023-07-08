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

public class TorgUser extends BaseDomain<Object> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TorgUser";
	public static final String ALIAS_ORGUSER_ID = "orguserId";
	public static final String ALIAS_ORG_ID = "机构ID";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_IS_MAIN = "是否主机构（0：否，1：是）";
	

    /**
     * orguserId       db_column: orguser_id 
     */	
	private String orguserId;
    /**
     * 机构ID       db_column: ORG_ID 
     */	
	private String orgId;
    /**
     * 用户ID       db_column: USER_ID 
     */	
	private String userId;
    /**
     * 是否主机构（0：否，1：是）       db_column: IS_MAIN 
     */	
	private Integer isMain;
	//columns END

	public TorgUser(){
	}

	public TorgUser(
		String orguserId
	){
		this.orguserId = orguserId;
	}

	public void setOrguserId(String value) {
		this.orguserId = value;
	}
	
	public String getOrguserId() {
		return this.orguserId;
	}
	public void setOrgId(String value) {
		this.orgId = value;
	}
	
	public String getOrgId() {
		return this.orgId;
	}
	public void setUserId(String value) {
		this.userId = value;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setIsMain(Integer value) {
		this.isMain = value;
	}
	
	public Integer getIsMain() {
		return this.isMain;
	}
	
	private TsysOrg tsysOrg;
	
	public void setTsysOrg(TsysOrg tsysOrg){
		this.tsysOrg = tsysOrg;
	}
	
	public TsysOrg getTsysOrg() {
		return tsysOrg;
	}
	
	private TsysUserinfo tsysUserinfo;
	
	public void setTsysUserinfo(TsysUserinfo tsysUserinfo){
		this.tsysUserinfo = tsysUserinfo;
	}
	
	public TsysUserinfo getTsysUserinfo() {
		return tsysUserinfo;
	}

}

