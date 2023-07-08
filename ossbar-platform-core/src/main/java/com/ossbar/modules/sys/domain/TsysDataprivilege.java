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

public class TsysDataprivilege extends BaseDomain<Object> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TsysDataprivilege";
	public static final String ALIAS_ROLE_ORGID = "roleOrgid";
	public static final String ALIAS_ORG_ID = "机构ID";
	public static final String ALIAS_ROLE_ID = "角色ID";
	

    /**
     * roleOrgid       db_column: ROLE_ORGID 
     */	
	private String roleOrgid;
    /**
     * 机构ID       db_column: ORG_ID 
     */	
	private String orgId;
    /**
     * 角色ID       db_column: ROLE_ID 
     */	
	private String roleId;
	//columns END

	public TsysDataprivilege(){
	}

	public TsysDataprivilege(
		String roleOrgid
	){
		this.roleOrgid = roleOrgid;
	}

	public void setRoleOrgid(String value) {
		this.roleOrgid = value;
	}
	
	public String getRoleOrgid() {
		return this.roleOrgid;
	}
	public void setOrgId(String value) {
		this.orgId = value;
	}
	
	public String getOrgId() {
		return this.orgId;
	}
	public void setRoleId(String value) {
		this.roleId = value;
	}
	
	public String getRoleId() {
		return this.roleId;
	}
	
	private TsysRole tsysRole;
	
	public void setTsysRole(TsysRole tsysRole){
		this.tsysRole = tsysRole;
	}
	
	public TsysRole getTsysRole() {
		return tsysRole;
	}
	
	private TsysOrg tsysOrg;
	
	public void setTsysOrg(TsysOrg tsysOrg){
		this.tsysOrg = tsysOrg;
	}
	
	public TsysOrg getTsysOrg() {
		return tsysOrg;
	}

}

