
package com.ossbar.modules.sys.domain;


//import org.hibernate.validator.constraints.NotBlank;
//import com.creatorblue.common.cbsecurity.validator.group.AddGroup;
//import com.creatorblue.common.cbsecurity.validator.group.UpdateGroup;

import com.ossbar.core.baseclass.domain.BaseDomain;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhujw
 * @version 1.0
 */

public class TsysParameter extends BaseDomain<Object> {
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "TsysParameter";
	public static final String ALIAS_PARAID = "paraid";
	public static final String ALIAS_ISDEFAULT = "是否默认";
	public static final String ALIAS_PARANAME = "参数名称";
	public static final String ALIAS_PARANO = "参数值";
	public static final String ALIAS_PARA_KEY = "参数key";
	public static final String ALIAS_PARA_TYPE = "参数标识符";
	public static final String ALIAS_PARAORDER = "排序号";
	public static final String ALIAS_REMARK = "描述";
	public static final String ALIAS_DISPLAYSORT = "显示方式：下拉(select)、复选(checkbox)、单选(radio)";
	

    /**
     * paraid       db_column: PARAID 
     */	
	private String paraid;
    /**
     * 是否默认       db_column: ISDEFAULT 
     */	
	private String isdefault;
    /**
     * 参数名称       db_column: PARANAME 
     */
	//@NotBlank(message="参数名不能为空" , groups = {AddGroup.class, UpdateGroup.class})
	private String paraname;
    /**
     * 参数值       db_column: PARANO 
     */	
	//@NotBlank(message="参数值不能为空" , groups = {AddGroup.class, UpdateGroup.class})
	private String parano;
    /**
     * 参数key      db_column: PARA_KEY 
     */
	//@NotBlank(message="参数Key不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String paraKey;
    /**
     * 参数标识符       db_column: PARA_TYPE 
     */	
	//@NotBlank(message="参数标识符不能为空" , groups = {AddGroup.class, UpdateGroup.class})
	private String paraType;
    /**
     * 排序号       db_column: PARAORDER 
     */	
	private Integer paraorder;
    /**
     * 描述       db_column: REMARK 
     */	
	private String remark;
    /**
     * 显示方式：下拉(select)、复选(checkbox)、单选(radio)
     */	
	private String displaysort;
	//columns END

	public TsysParameter(){
	}

	public TsysParameter(
		String paraid
	){
		this.paraid = paraid;
	}

	public void setParaid(String value) {
		this.paraid = value;
	}
	
	public String getParaid() {
		return this.paraid;
	}
	public void setIsdefault(String value) {
		this.isdefault = value;
	}
	
	public String getIsdefault() {
		return this.isdefault;
	}
	public void setParaname(String value) {
		this.paraname = value;
	}
	
	public String getParaname() {
		return this.paraname;
	}
	public void setParano(String value) {
		this.parano = value;
	}
	
	public String getParano() {
		return this.parano;
	}
	public void setParaKey(String value) {
		this.paraKey = value;
	}
	
	public String getParaKey() {
		return this.paraKey;
	}
	public void setParaType(String value) {
		this.paraType = value;
	}
	
	public String getParaType() {
		return this.paraType;
	}
	public void setParaorder(Integer value) {
		this.paraorder = value;
	}
	
	public Integer getParaorder() {
		return this.paraorder;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setDisplaysort(String value) {
		this.displaysort = value;
	}
	
	public String getDisplaysort() {
		return this.displaysort;
	}
}

