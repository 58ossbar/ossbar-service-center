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

public class TrolePost extends BaseDomain<Object> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TrolePost";
	public static final String ALIAS_ROLE_POSTID = "rolePostid";
	public static final String ALIAS_POST_ID = "岗位ID";
	public static final String ALIAS_ROLE_ID = "角色ID";
	

    /**
     * rolePostid       db_column: ROLE_POSTID 
     */	
	private String rolePostid;
    /**
     * 岗位ID       db_column: POST_ID 
     */	
	private String postId;
    /**
     * 角色ID       db_column: ROLE_ID 
     */	
	private String roleId;
	//columns END

	public TrolePost(){
	}

	public TrolePost(
		String rolePostid
	){
		this.rolePostid = rolePostid;
	}

	public void setRolePostid(String value) {
		this.rolePostid = value;
	}
	
	public String getRolePostid() {
		return this.rolePostid;
	}
	public void setPostId(String value) {
		this.postId = value;
	}
	
	public String getPostId() {
		return this.postId;
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
	
	private TsysPost tsysPost;
	
	public void setTsysPost(TsysPost tsysPost){
		this.tsysPost = tsysPost;
	}
	
	public TsysPost getTsysPost() {
		return tsysPost;
	}

}

