package com.ossbar.modules.evgl.pkg.domain;

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

public class TevglPkgDefaultChapter extends BaseDomain<TevglPkgDefaultChapter> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglPkgDefaultChapter";
	public static final String ALIAS_SE_ID = "主键id";
	public static final String ALIAS_PKG_ID = "教学包id";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_SORT_NUM = "排序号";
	

    /**
     * 主键id       db_column: se_id 
     */	
 	//@NotNull(msg="主键id不能为空")
 	//@MaxLength(value=32, msg="字段[主键id]超出最大长度[32]")
	private String seId;
    /**
     * 教学包id       db_column: pkg_id 
     */	
 	//@NotNull(msg="教学包id不能为空")
 	//@MaxLength(value=32, msg="字段[教学包id]超出最大长度[32]")
	private String pkgId;
    /**
     * 名称       db_column: name 
     */	
 	//@NotNull(msg="名称不能为空")
 	//@MaxLength(value=20, msg="字段[名称]超出最大长度[20]")
	private String name;
    /**
     * 排序号       db_column: sort_num 
     */	
 	//@NotNull(msg="排序号不能为空")
 	//@MaxLength(value=10, msg="字段[排序号]超出最大长度[10]")
	private Integer sortNum;
	//columns END

	public TevglPkgDefaultChapter(){
	}

	public TevglPkgDefaultChapter(
		String seId
	){
		this.seId = seId;
	}

	public void setSeId(String value) {
		this.seId = value;
	}
	public String getSeId() {
		return this.seId;
	}
	public void setPkgId(String value) {
		this.pkgId = value;
	}
	public String getPkgId() {
		return this.pkgId;
	}
	public void setName(String value) {
		this.name = value;
	}
	public String getName() {
		return this.name;
	}
	public void setSortNum(Integer value) {
		this.sortNum = value;
	}
	public Integer getSortNum() {
		return this.sortNum;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}

