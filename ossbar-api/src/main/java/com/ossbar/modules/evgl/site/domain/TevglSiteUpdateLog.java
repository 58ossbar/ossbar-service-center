package com.ossbar.modules.evgl.site.domain;

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

public class TevglSiteUpdateLog extends BaseDomain<TevglSiteUpdateLog> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglSiteUpdateLog";
	public static final String ALIAS_LOG_ID = "主键id";
	public static final String ALIAS_TYPE = "类型(1网站2小程序3app)";
	public static final String ALIAS_VERSION = "版本";
	public static final String ALIAS_CONTENT = "内容";
	

    /**
     * 主键id       db_column: log_id 
     */	
 	//@NotNull(msg="主键id不能为空")
 	//@MaxLength(value=32, msg="字段[主键id]超出最大长度[32]")
	private String logId;
    /**
     * 类型(1网站2小程序3app)       db_column: type 
     */	
 	//@NotNull(msg="类型(1网站2小程序3app)不能为空")
 	//@MaxLength(value=2, msg="字段[类型(1网站2小程序3app)]超出最大长度[2]")
	private String type;
    /**
     * 版本       db_column: version 
     */	
 	//@NotNull(msg="版本不能为空")
 	//@MaxLength(value=50, msg="字段[版本]超出最大长度[50]")
	private String version;
    /**
     * 内容       db_column: content 
     */	
 	//@NotNull(msg="内容不能为空")
 	//@MaxLength(value=65535, msg="字段[内容]超出最大长度[65535]")
	private String content;
	//columns END

	public TevglSiteUpdateLog(){
	}

	public TevglSiteUpdateLog(
		String logId
	){
		this.logId = logId;
	}

	public void setLogId(String value) {
		this.logId = value;
	}
	public String getLogId() {
		return this.logId;
	}
	public void setType(String value) {
		this.type = value;
	}
	public String getType() {
		return this.type;
	}
	public void setVersion(String value) {
		this.version = value;
	}
	public String getVersion() {
		return this.version;
	}
	public void setContent(String value) {
		this.content = value;
	}
	public String getContent() {
		return this.content;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}

