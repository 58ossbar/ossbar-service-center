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

public class TevglTchClassroomResource extends BaseDomain<TevglTchClassroomResource> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglTchClassroomResource";
	public static final String ALIAS_MENU_ID = "主键";
	public static final String ALIAS_PARENT_ID = "父菜单ID，一级菜单为0";
	public static final String ALIAS_NAME = "菜单名称";
	public static final String ALIAS_URL = "菜单URL";
	public static final String ALIAS_PERMS = "授权(多个用逗号分隔，如：user:list,user:create)";
	public static final String ALIAS_TYPE = "类型   0：目录   1：菜单   2：按钮";
	public static final String ALIAS_ICON = "菜单图标";
	public static final String ALIAS_ORDER_NUM = "排序号";
	public static final String ALIAS_REMARK = "备注";
	

    /**
     * 主键       db_column: menu_id 
     */	
 	//@NotNull(msg="主键不能为空")
 	//@MaxLength(value=32, msg="字段[主键]超出最大长度[32]")
	private String menuId;
    /**
     * 父菜单ID，一级菜单为0       db_column: parent_id 
     */	
 	//@NotNull(msg="父菜单ID，一级菜单为0不能为空")
 	//@MaxLength(value=32, msg="字段[父菜单ID，一级菜单为0]超出最大长度[32]")
	private String parentId;
    /**
     * 菜单名称       db_column: name 
     */	
 	//@NotNull(msg="菜单名称不能为空")
 	//@MaxLength(value=50, msg="字段[菜单名称]超出最大长度[50]")
	private String name;
    /**
     * 菜单URL       db_column: url 
     */	
 	//@NotNull(msg="菜单URL不能为空")
 	//@MaxLength(value=200, msg="字段[菜单URL]超出最大长度[200]")
	private String url;
    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)       db_column: perms 
     */	
 	//@NotNull(msg="授权(多个用逗号分隔，如：user:list,user:create)不能为空")
 	//@MaxLength(value=500, msg="字段[授权(多个用逗号分隔，如：user:list,user:create)]超出最大长度[500]")
	private String perms;
    /**
     * 类型   0：目录   1：菜单   2：按钮       db_column: type 
     */	
 	//@NotNull(msg="类型   0：目录   1：菜单   2：按钮不能为空")
 	//@MaxLength(value=10, msg="字段[类型   0：目录   1：菜单   2：按钮]超出最大长度[10]")
	private Integer type;
    /**
     * 菜单图标       db_column: icon 
     */	
 	//@NotNull(msg="菜单图标不能为空")
 	//@MaxLength(value=50, msg="字段[菜单图标]超出最大长度[50]")
	private String icon;
    /**
     * 排序号       db_column: order_num 
     */	
 	//@NotNull(msg="排序号不能为空")
 	//@MaxLength(value=10, msg="字段[排序号]超出最大长度[10]")
	private Integer orderNum;
    /**
     * 备注       db_column: remark 
     */	
 	//@NotNull(msg="备注不能为空")
 	//@MaxLength(value=500, msg="字段[备注]超出最大长度[500]")
	private String remark;
	//columns END

	public TevglTchClassroomResource(){
	}

	public TevglTchClassroomResource(
		String menuId
	){
		this.menuId = menuId;
	}

	public void setMenuId(String value) {
		this.menuId = value;
	}
	public String getMenuId() {
		return this.menuId;
	}
	public void setParentId(String value) {
		this.parentId = value;
	}
	public String getParentId() {
		return this.parentId;
	}
	public void setName(String value) {
		this.name = value;
	}
	public String getName() {
		return this.name;
	}
	public void setUrl(String value) {
		this.url = value;
	}
	public String getUrl() {
		return this.url;
	}
	public void setPerms(String value) {
		this.perms = value;
	}
	public String getPerms() {
		return this.perms;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	public Integer getType() {
		return this.type;
	}
	public void setIcon(String value) {
		this.icon = value;
	}
	public String getIcon() {
		return this.icon;
	}
	public void setOrderNum(Integer value) {
		this.orderNum = value;
	}
	public Integer getOrderNum() {
		return this.orderNum;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	public String getRemark() {
		return this.remark;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}

