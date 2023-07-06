package com.ossbar.modules.evgl.pkg.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;

/**
 * <p> Title: 教学包资源</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public class TevglPkgRes extends BaseDomain<TevglPkgRes> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglPkgRes";
	public static final String ALIAS_RES_ID = "资源主键ID";
	public static final String ALIAS_PKG_ID = "所属教学包";
	public static final String ALIAS_RESGROUP_ID = "所属资源分组";
	public static final String ALIAS_RES_TYPE = "资源类型(离线or在线)";
	public static final String ALIAS_RES_CONTENT = "资源内容";
	public static final String ALIAS_SORT_NUM = "排序号";
	public static final String ALIAS_RES_SAVE_URL = "资源保存路径";
	public static final String ALIAS_RES_DOWN_URL = "资源下载路径";
	public static final String ALIAS_RES_NAME = "资源名称";
	public static final String ALIAS_RES_SIZE = "资源大小";
	public static final String ALIAS_RES_SUFFIX = "资源文件后缀";
	public static final String ALIAS_STATE = "状态(Y有效N无效)";
	

    /**
     * 资源主键ID       db_column: res_id 
     */	
 	////@NotNull(msg="资源主键ID不能为空")
 	//@MaxLength(value=32, msg="字段[资源主键ID]超出最大长度[32]")
	private String resId;
    /**
     * 所属教学包       db_column: pkg_id 
     */	
 	//////@NotNull(msg="所属教学包不能为空")
 	//@MaxLength(value=32, msg="字段[所属教学包]超出最大长度[32]")
	private String pkgId;
    /**
     * 所属资源分组       db_column: resgroup_id 
     */	
 	////@NotNull(msg="所属资源分组不能为空")
 	//@MaxLength(value=32, msg="字段[所属资源分组]超出最大长度[32]")
	private String resgroupId;
    /**
     * 资源类型(离线or在线)       db_column: res_type 
     */	
 	//////@NotNull(msg="资源类型(离线or在线)不能为空")
 	//@MaxLength(value=32, msg="字段[资源类型(离线or在线)]超出最大长度[32]")
	private String resType;
    /**
     * 资源内容       db_column: res_content 
     */	
 	//////@NotNull(msg="资源内容不能为空")
 	//@MaxLength(value=2147483647, msg="字段[资源内容]超出最大长度[2147483647]")
	private String resContent;
    /**
     * 排序号       db_column: sort_num 
     */	
 	//////@NotNull(msg="排序号不能为空")
 	//@MaxLength(value=10, msg="字段[排序号]超出最大长度[10]")
	private Integer sortNum;
    /**
     * 资源保存路径       db_column: res_save_url 
     */	
 	//////@NotNull(msg="资源保存路径不能为空")
 	//@MaxLength(value=300, msg="字段[资源保存路径]超出最大长度[300]")
	private String resSaveUrl;
    /**
     * 资源下载路径       db_column: res_down_url 
     */	
 	//////@NotNull(msg="资源下载路径不能为空")
 	//@MaxLength(value=300, msg="字段[资源下载路径]超出最大长度[300]")
	private String resDownUrl;
    /**
     * 资源名称       db_column: res_name 
     */	
 	////@NotNull(msg="资源名称不能为空")
 	//@MaxLength(value=50, msg="字段[资源名称]超出最大长度[50]")
	private String resName;
    /**
     * 资源大小       db_column: res_size 
     */	
 	//////@NotNull(msg="资源大小不能为空")
 	//@MaxLength(value=10, msg="字段[资源大小]超出最大长度[10]")
	private Integer resSize;
    /**
     * 资源文件后缀       db_column: res_suffix 
     */	
 	//////@NotNull(msg="资源文件后缀不能为空")
 	//@MaxLength(value=10, msg="字段[资源文件后缀]超出最大长度[10]")
	private String resSuffix;
    /**
     * 状态(Y有效N无效)       db_column: state 
     */	
 	////@NotNull(msg="状态(Y有效N无效)不能为空")
 	//@MaxLength(value=2, msg="字段[状态(Y有效N无效)]超出最大长度[2]")
	private String state;
 	/**
     * 阅读量       db_column: view_num 
     */	
 	//////@NotNull(msg="阅读量不能为空")
 	//@MaxLength(value=11, msg="字段[资源大小]超出最大长度[11]")
	private Integer viewNum;
	//columns END

	public TevglPkgRes(){
	}

	public TevglPkgRes(
		String resId
	){
		this.resId = resId;
	}

	public void setResId(String value) {
		this.resId = value;
	}
	public String getResId() {
		return this.resId;
	}
	public void setPkgId(String value) {
		this.pkgId = value;
	}
	public String getPkgId() {
		return this.pkgId;
	}
	public void setResgroupId(String value) {
		this.resgroupId = value;
	}
	public String getResgroupId() {
		return this.resgroupId;
	}
	public void setResType(String value) {
		this.resType = value;
	}
	public String getResType() {
		return this.resType;
	}
	public void setResContent(String value) {
		this.resContent = value;
	}
	public String getResContent() {
		return this.resContent;
	}
	public void setSortNum(Integer value) {
		this.sortNum = value;
	}
	public Integer getSortNum() {
		return this.sortNum;
	}
	public void setResSaveUrl(String value) {
		this.resSaveUrl = value;
	}
	public String getResSaveUrl() {
		return this.resSaveUrl;
	}
	public void setResDownUrl(String value) {
		this.resDownUrl = value;
	}
	public String getResDownUrl() {
		return this.resDownUrl;
	}
	public void setResName(String value) {
		this.resName = value;
	}
	public String getResName() {
		return this.resName;
	}
	public void setResSize(Integer value) {
		this.resSize = value;
	}
	public Integer getResSize() {
		return this.resSize;
	}
	public void setResSuffix(String value) {
		this.resSuffix = value;
	}
	public String getResSuffix() {
		return this.resSuffix;
	}
	public void setState(String value) {
		this.state = value;
	}
	public String getState() {
		return this.state;
	}
	
	private TevglPkgResgroup tevglPkgResgroup;
	
	public void setTevglPkgResgroup(TevglPkgResgroup tevglPkgResgroup){
		this.tevglPkgResgroup = tevglPkgResgroup;
	}
	
	public TevglPkgResgroup getTevglPkgResgroup() {
		return tevglPkgResgroup;
	}
	
	private TevglPkgInfo tevglPkgInfo;
	
	public void setTevglPkgInfo(TevglPkgInfo tevglPkgInfo){
		this.tevglPkgInfo = tevglPkgInfo;
	}
	
	public TevglPkgInfo getTevglPkgInfo() {
		return tevglPkgInfo;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public Integer getViewNum() {
		return viewNum;
	}

	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}
}

