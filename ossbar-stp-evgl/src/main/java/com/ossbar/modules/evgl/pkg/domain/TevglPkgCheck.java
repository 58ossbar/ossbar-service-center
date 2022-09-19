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

public class TevglPkgCheck extends BaseDomain<TevglPkgCheck> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglPkgCheck";
	public static final String ALIAS_PC_ID = "主键id";
	public static final String ALIAS_PKG_ID = "教学包id";
	public static final String ALIAS_CHECK_STATE = "审核状态（Y/N）";
	public static final String ALIAS_REASON = "不通过理由";
	

    /**
     * 主键id       db_column: pc_id 
     */	
 	//@NotNull(msg="主键id不能为空")
 	//@MaxLength(value=32, msg="字段[主键id]超出最大长度[32]")
	private String pcId;
    /**
     * 教学包id       db_column: pkg_id 
     */	
 	//@NotNull(msg="教学包id不能为空")
 	//@MaxLength(value=32, msg="字段[教学包id]超出最大长度[32]")
	private String pkgId;
    /**
     * 审核状态（Y/N）       db_column: check_state 
     */	
 	//@NotNull(msg="审核状态（Y/N）不能为空")
 	//@MaxLength(value=2, msg="字段[审核状态（Y/N）]超出最大长度[2]")
	private String checkState;
    /**
     * 不通过理由       db_column: reason 
     */	
 	//@NotNull(msg="不通过理由不能为空")
 	//@MaxLength(value=65535, msg="字段[不通过理由]超出最大长度[65535]")
	private String reason;
	//columns END

	public TevglPkgCheck(){
	}

	public TevglPkgCheck(
		String pcId
	){
		this.pcId = pcId;
	}

	public void setPcId(String value) {
		this.pcId = value;
	}
	public String getPcId() {
		return this.pcId;
	}
	public void setPkgId(String value) {
		this.pkgId = value;
	}
	public String getPkgId() {
		return this.pkgId;
	}
	public void setCheckState(String value) {
		this.checkState = value;
	}
	public String getCheckState() {
		return this.checkState;
	}
	public void setReason(String value) {
		this.reason = value;
	}
	public String getReason() {
		return this.reason;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}

