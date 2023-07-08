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

public class TsysUserprivilege extends BaseDomain<Object> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TsysUserprivilege";
	public static final String ALIAS_USERPRVIID = "用户特权ID";
	public static final String ALIAS_MENU_ID = "资源ID";
	public static final String ALIAS_USERID = "用户ID";
	

    /**
     * 用户特权ID       db_column: USERPRVIID 
     */	
	private String userprviid;
    /**
     * 资源ID       db_column: MENU_ID 
     */	
	private String menuId;
    /**
     * 用户ID       db_column: USERID 
     */	
	private String userid;
	//columns END

	public TsysUserprivilege(){
	}

	public TsysUserprivilege(
		String userprviid
	){
		this.userprviid = userprviid;
	}

	public void setUserprviid(String value) {
		this.userprviid = value;
	}
	
	public String getUserprviid() {
		return this.userprviid;
	}
	public void setMenuId(String value) {
		this.menuId = value;
	}
	
	public String getMenuId() {
		return this.menuId;
	}
	public void setUserid(String value) {
		this.userid = value;
	}
	
	public String getUserid() {
		return this.userid;
	}
	
	private TsysResource tsysResource;
	
	public void setTsysResource(TsysResource tsysResource){
		this.tsysResource = tsysResource;
	}
	
	public TsysResource getTsysResource() {
		return tsysResource;
	}

}

