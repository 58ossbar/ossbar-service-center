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

//import javax.validation.constraints.Min;
//import org.hibernate.validator.constraints.NotBlank;
//import com.creatorblue.common.cbsecurity.validator.group.AddGroup;
//import com.creatorblue.common.cbsecurity.validator.group.UpdateGroup;


import com.ossbar.core.baseclass.domain.BaseDomain;

public class TsysSerialno extends BaseDomain<Object> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TsysSerialno";
	public static final String ALIAS_SERIALNO_ID = "serialnoId";
	public static final String ALIAS_SERIAL_NAME = "名称";
	public static final String ALIAS_SECOUND_NAME = "字段名";
	public static final String ALIAS_FORMULAR_REGX = "规则 {YYYY}{MM}{DD}-{NO}  NO:000001开始";
	public static final String ALIAS_CREATE_TYPE = "生成方式";
	public static final String ALIAS_SERIAL_LENGTH = "流水号长度";
	public static final String ALIAS_STEP = "步长";
	public static final String ALIAS_INIT_VALUE = "初始值";
	public static final String ALIAS_CURRENT_VALUE = "当前值";
	public static final String ALIAS_REMARK = "备注说明";
	public static final String ALIAS_TAB_NAME = "表名";
	public static final String ALIAS_SFBL = "是否补零";
	

    /**
     * serialnoId       db_column: SERIALNO_ID 
     */	
	private String serialnoId;
	private String id;
	
    /**
     * 名称       db_column: SERIAL_NAME 
     */	
	//@NotBlank(message = ALIAS_SERIAL_NAME +"不能为空",groups={AddGroup.class,UpdateGroup.class})
	private String serialName;
    /**
     * 字段名       db_column: SECOUND_NAME 
     */	
	//@NotBlank(message = ALIAS_SECOUND_NAME +"不能为空",groups={AddGroup.class,UpdateGroup.class})
	private String secoundName;
    /**
     * 规则 {yyyy}{MM}{DD}-{NO}  NO:000001开始       db_column: FORMULAR_REGX 
     */	
	//@NotBlank(message = "规则不能为空",groups={AddGroup.class,UpdateGroup.class})
	private String formularRegx;
    /**
     * 生成方式       db_column: CREATE_TYPE 
     */	
	private String createType;
    /**
     * 流水号长度       db_column: SERIAL_LENGTH 
     */	
	//@NotBlank(message = ALIAS_SERIAL_LENGTH +"不能为空",groups={AddGroup.class,UpdateGroup.class})
	//@Min(value=1,message=ALIAS_SERIAL_LENGTH + "必须大于{value}")
	private Integer serialLength;
    /**
     * 步长       db_column: STEP 
     */	
	//@NotBlank(message = ALIAS_STEP +"不能为空",groups={AddGroup.class,UpdateGroup.class})
	private Integer step;
    /**
     * 初始值       db_column: INIT_VALUE 
     */	
	//@NotBlank(message = ALIAS_INIT_VALUE +"不能为空",groups={AddGroup.class,UpdateGroup.class})
	private String initValue;
    /**
     * 当前值       db_column: CURRENT_VALUE 
     */	
	//@NotBlank(message = ALIAS_CURRENT_VALUE +"不能为空",groups={AddGroup.class,UpdateGroup.class})
	private String currentValue;
    /**
     * 备注说明       db_column: REMARK 
     */	
	private String remark;
    /**
     * 表名       db_column: TAB_NAME 
     */	
	//@NotBlank(message = ALIAS_TAB_NAME +"不能为空",groups={AddGroup.class,UpdateGroup.class})
	private String tabName;
    /**
     * 是否补零       db_column: SFBL 
     */	
	private String sfbl;
	//columns END

	public TsysSerialno(){
	}

	public TsysSerialno(
		String serialnoId
	){
		this.serialnoId = serialnoId;
	}

	public void setSerialnoId(String value) {
		this.serialnoId = value;
	}
	
	public String getSerialnoId() {
		return this.serialnoId;
	}
	public void setSerialName(String value) {
		this.serialName = value;
	}
	
	public String getSerialName() {
		return this.serialName;
	}
	public void setSecoundName(String value) {
		this.secoundName = value;
	}
	
	public String getSecoundName() {
		return this.secoundName;
	}
	public void setFormularRegx(String value) {
		this.formularRegx = value;
	}
	
	public String getFormularRegx() {
		return this.formularRegx;
	}
	public void setCreateType(String value) {
		this.createType = value;
	}
	
	public String getCreateType() {
		return this.createType;
	}
	public void setSerialLength(Integer value) {
		this.serialLength = value;
	}
	
	public Integer getSerialLength() {
		return this.serialLength;
	}
	public void setStep(Integer value) {
		this.step = value;
	}
	
	public Integer getStep() {
		return this.step;
	}
	public void setInitValue(String value) {
		this.initValue = value;
	}
	
	public String getInitValue() {
		return this.initValue;
	}
	public void setCurrentValue(String value) {
		this.currentValue = value;
	}
	
	public String getCurrentValue() {
		return this.currentValue;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setTabName(String value) {
		this.tabName = value;
	}
	
	public String getTabName() {
		return this.tabName;
	}
	public void setSfbl(String value) {
		this.sfbl = value;
	}
	
	public String getSfbl() {
		return this.sfbl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

