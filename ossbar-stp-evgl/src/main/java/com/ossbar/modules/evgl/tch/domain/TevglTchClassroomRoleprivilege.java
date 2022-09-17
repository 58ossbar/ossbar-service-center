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

public class TevglTchClassroomRoleprivilege extends BaseDomain<TevglTchClassroomRoleprivilege> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglTchClassroomRoleprivilege";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_CT_ID = "ctId";
	public static final String ALIAS_ROLE_ID = "roleId";
	public static final String ALIAS_MENU_ID = "menuId";
	

    /**
     * id       db_column: id 
     */	
 	//@NotNull(msg="id不能为空")
 	//@MaxLength(value=32, msg="字段[id]超出最大长度[32]")
	private String id;
    /**
     * ctId       db_column: ct_id 
     */	
 	//@NotNull(msg="ctId不能为空")
 	//@MaxLength(value=32, msg="字段[ctId]超出最大长度[32]")
	private String ctId;
    /**
     * roleId       db_column: role_id 
     */	
 	//@NotNull(msg="roleId不能为空")
 	//@MaxLength(value=32, msg="字段[roleId]超出最大长度[32]")
	private String roleId;
    /**
     * menuId       db_column: menu_id 
     */	
 	//@NotNull(msg="menuId不能为空")
 	//@MaxLength(value=32, msg="字段[menuId]超出最大长度[32]")
	private String menuId;
	//columns END

	public TevglTchClassroomRoleprivilege(){
	}

	public TevglTchClassroomRoleprivilege(
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
	public void setRoleId(String value) {
		this.roleId = value;
	}
	public String getRoleId() {
		return this.roleId;
	}
	public void setMenuId(String value) {
		this.menuId = value;
	}
	public String getMenuId() {
		return this.menuId;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}

