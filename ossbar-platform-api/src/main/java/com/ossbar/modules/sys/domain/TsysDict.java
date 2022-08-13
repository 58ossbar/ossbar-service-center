package com.ossbar.modules.sys.domain;


//import org.hibernate.validator.constraints.NotBlank;
//import com.creatorblue.common.cbsecurity.validator.group.AddGroup;
//import com.creatorblue.common.cbsecurity.validator.group.UpdateGroup;
//import com.creatorblue.core.baseclass.domain.BaseDomain;

import com.ossbar.core.baseclass.domain.BaseDomain;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * </p>
 * <p>
 * Company:creatorblue.co.,ltd
 * </p>
 *
 * @author zhujw
 * @version 1.0
 */

public class TsysDict extends BaseDomain<Object> {
	private static final long serialVersionUID = 1L;

	// alias
	public static final String TABLE_ALIAS = "TsysDict";
	public static final String ALIAS_DICT_ID = "dictId";
	public static final String ALIAS_DICT_TYPE = "字典分类";
	public static final String ALIAS_DICT_NAME = "字典分类名称";
	public static final String ALIAS_DICT_VALUE = "字典值";
	public static final String ALIAS_DICT_CODE = "字典编码";
	public static final String ALIAS_REMARK = "字典描述";
	public static final String ALIAS_DICT_SORT = "1、平台内2、平台外";
	public static final String ALIAS_SORT_NUM = "排序号";
	public static final String ALIAS_PARENT_TYPE = "父分类";
	public static final String ALIAS_DISPLAY_SORT = "字典展现分类:下拉类型(select)树形(tree) 复选型(checkbox)单选radio";
	public static final String ALIAS_DICT_CLASS = "dictClass";
	public static final String ALIAS_MULTI_TYPE = "单选或多选：主要针对树形控件";
	public static final String ALIAS_ORG_ID = "所属机构";
	public static final String ALIAS_DISPLAYING = "是否显示";

	/**
	 * dictId db_column: DICT_ID
	 */
	private String dictId;
	// @NotBlank(message = "字典分类不能为空", groups = { AddGroup.class, UpdateGroup.class
	// })
	private String dictType;
	private String dictUrl;

	public String getDictUrl() {
		return dictUrl;
	}

	public void setDictUrl(String dictUrl) {
		this.dictUrl = dictUrl;
	}

	/**
	 * 字典分类名称 db_column: DICT_NAME
	 */
	// @NotBlank(message = "字典分类名称不能为空", groups = { AddGroup.class,
	// UpdateGroup.class })
	private String dictName;
	/**
	 * 字典值 db_column: DICT_VALUE
	 */
	// @NotBlank(message = "字典值不能为空", groups = { AddGroup.class, UpdateGroup.class
	// })
	private String dictValue;
	/**
	 * 字典编码 db_column: DICT_CODE
	 */
	// @NotBlank(message = "字典编码不能为空", groups = { AddGroup.class, UpdateGroup.class
	// })
	private String dictCode;
	/**
	 * 字典描述 db_column: REMARK
	 */
	private String remark;
	/**
	 * 1、平台内2、平台外 db_column: DICT_SORT
	 */
	private String dictSort;
	/**
	 * 排序号 db_column: SORT_NUM
	 */
	private Integer sortNum;
	/**
	 * 父分类 db_column: PARENT_TYPE
	 */
	private String parentType;
	/**
	 * 字典展现分类:下拉类型(select)树形(tree) 复选型(checkbox)单选radio db_column: DISPLAY_SORT
	 */
	private String displaySort;
	/**
	 * dictClass db_column: DICT_CLASS
	 */
	private Integer dictClass;
	/**
	 * 单选或多选：主要针对树形控件 db_column: MULTI_TYPE
	 */
	private String multiType;
	/**
	 * 所属机构 db_column: ORG_ID
	 */
	private String orgId;
	/**
	 * 是否显示 db_column: displaying
	 */
	private String displaying;

	/**
	 * 是否默认值 db_column: isdefault
	 */
	private String isdefault;
	// columns END
	private String orgName;
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	public TsysDict() {
	}

	public TsysDict(String dictId) {
		this.dictId = dictId;
	}

	public void setDictId(String value) {
		this.dictId = value;
	}

	public String getDictId() {
		return this.dictId;
	}

	public void setDictType(String value) {
		this.dictType = value;
	}

	public String getDictType() {
		return this.dictType;
	}

	public void setDictName(String value) {
		this.dictName = value;
	}

	public String getDictName() {
		return this.dictName;
	}

	public void setDictValue(String value) {
		this.dictValue = value;
	}

	public String getDictValue() {
		return this.dictValue;
	}

	public void setDictCode(String value) {
		this.dictCode = value;
	}

	public String getDictCode() {
		return this.dictCode;
	}

	public void setRemark(String value) {
		this.remark = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setDictSort(String value) {
		this.dictSort = value;
	}

	public String getDictSort() {
		return this.dictSort;
	}

	public void setSortNum(Integer value) {
		this.sortNum = value;
	}

	public Integer getSortNum() {
		return this.sortNum;
	}

	public void setParentType(String value) {
		this.parentType = value;
	}

	public String getParentType() {
		return this.parentType;
	}

	public void setDisplaySort(String value) {
		this.displaySort = value;
	}

	public String getDisplaySort() {
		return this.displaySort;
	}

	public void setDictClass(Integer value) {
		this.dictClass = value;
	}

	public Integer getDictClass() {
		return this.dictClass;
	}

	public void setMultiType(String value) {
		this.multiType = value;
	}

	public String getMultiType() {
		return this.multiType;
	}

	public void setOrgId(String value) {
		this.orgId = value;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setDisplaying(String value) {
		this.displaying = value;
	}

	public String getDisplaying() {
		return this.displaying;
	}

	public TsysDict clone() {
		TsysDict dict = new TsysDict();

		dict.setCreateTime(this.getCreateTime());
		dict.setCreateUserId(this.getCreateUserId());
		dict.setDelFlag(this.getDelFlag());
		dict.setDictClass(this.getDictClass());
		dict.setDictCode(this.getDictCode());
		dict.setDictId(this.getDictId());
		dict.setDictName(this.getDictName());
		dict.setDictSort(this.getDictSort());
		dict.setDictType(this.getDictType());
		dict.setDictValue(this.getDictValue());
		dict.setDisplaying(this.getDisplaying());
		dict.setDisplaySort(this.getDisplaySort());
		dict.setIsdefault(this.getIsdefault());
		dict.setMultiType(this.getMultiType());
		dict.setOrgId(this.getOrgId());
		dict.setParentType(this.getParentType());
		dict.setRemark(this.getRemark());
		dict.setSortNum(this.getSortNum());
		dict.setSqlMap(this.getSqlMap());
		dict.setUpdateTime(this.getUpdateTime());
		dict.setUpdateUserId(this.getUpdateUserId());
		dict.setDictUrl(this.getDictUrl());
		return dict;
	}

}
