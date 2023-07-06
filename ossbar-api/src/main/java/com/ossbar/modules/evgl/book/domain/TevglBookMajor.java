package com.ossbar.modules.evgl.book.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;

import java.util.List;

/**
 * <p> Title: 职业课程</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * //@author zhuq
 * //@version 1.0
 */

public class TevglBookMajor extends BaseDomain<TevglBookMajor> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglBookMajor";
	public static final String ALIAS_MAJOR_ID = "专业方向主键ID";
	public static final String ALIAS_ORG_ID = "所属院校";
	public static final String ALIAS_MAJOR_NAME = "专业名称";
	public static final String ALIAS_MAJOR_LOGO = "专业logo图";
	public static final String ALIAS_MAJOR_DESC = "专业简介(文本)";
	public static final String ALIAS_MAJOR_REMARK = "专业详细描述(富文本)";
	public static final String ALIAS_MAJOR_TYPE = "专业类型(拓展字段)";
	public static final String ALIAS_PARENT_ID = "父级ID";
	public static final String ALIAS_LEVEL = "层级";
	public static final String ALIAS_SHOW_INDEX = "是否推荐到首页(Y是N否)";
	public static final String ALIAS_SORT_NUM = "排序号";
	public static final String ALIAS_STATE = "状态(Y有效N无效)";
	

    /**
     * 专业方向主键ID       db_column: major_id 
     */	
 	//@NotNull(msg="专业方向主键ID不能为空")
 	//@MaxLength(value=32, msg="字段[专业方向主键ID]超出最大长度[32]")
	private String majorId;
    /**
     * 所属院校       db_column: org_id 
     */	
 	//@NotNull(msg="所属院校不能为空")
 	//@MaxLength(value=32, msg="字段[所属院校]超出最大长度[32]")
	private String orgId;
    /**
     * 专业名称       db_column: major_name 
     */	
 	//@NotNull(msg="专业名称不能为空")
 	//@MaxLength(value=50, msg="字段[专业名称]超出最大长度[50]")
	private String majorName;
    /**
     * 专业logo图       db_column: major_logo 
     */	
 	////@NotNull(msg="专业logo图不能为空")
 	//@MaxLength(value=300, msg="字段[专业logo图]超出最大长度[300]")
	private String majorLogo;
    /**
     * 专业简介(文本)       db_column: major_desc 
     */	
 	////@NotNull(msg="专业简介(文本)不能为空")
 	//@MaxLength(value=500, msg="字段[专业简介(文本)]超出最大长度[500]")
	private String majorDesc;
    /**
     * 专业详细描述(富文本)       db_column: major_remark 
     */	
 	////@NotNull(msg="专业详细描述(富文本)不能为空")
 	//@MaxLength(value=2147483647, msg="字段[专业详细描述(富文本)]超出最大长度[2147483647]")
	private String majorRemark;
    /**
     * 专业类型(拓展字段)       db_column: major_type 
     */	
 	////@NotNull(msg="专业类型(拓展字段)不能为空")
 	//@MaxLength(value=32, msg="字段[专业类型(拓展字段)]超出最大长度[32]")
	private String majorType;
    /**
     * 父级ID       db_column: parent_id 
     */	
 	////@NotNull(msg="父级ID不能为空")
 	//@MaxLength(value=32, msg="字段[父级ID]超出最大长度[32]")
	private String parentId;
    /**
     * 层级       db_column: level 
     */	
 	////@NotNull(msg="层级不能为空")
 	//@MaxLength(value=2, msg="字段[层级]超出最大长度[2]")
	private String level;
 	/**
     * 版本号       db_column: major_version 
     */	
 	////@NotNull(msg="版本号不能为空")
 	//@MaxLength(value=10, msg="字段[版本号]超出最大长度[10]")
	private String majorVersion;
 	/**
     * 上一版本ID      db_column: last_version_id 
     */	
 	////@NotNull(msg="上一版本ID不能为空")
 	//@MaxLength(value=32, msg="字段[上一版本ID]超出最大长度[32]")
	private String lastVersionId;
    /**
     * 是否推荐到首页(Y是N否)       db_column: show_index 
     */	
 	////@NotNull(msg="是否推荐到首页(Y是N否)不能为空")
 	//@MaxLength(value=2, msg="字段[是否推荐到首页(Y是N否)]超出最大长度[2]")
	private String showIndex;
    /**
     * 排序号       db_column: sort_num 
     */	
 	//@NotNull(msg="排序号不能为空")
 	//@MaxLength(value=10, msg="字段[排序号]超出最大长度[10]")
	private Integer sortNum;
    /**
     * 状态(Y有效N无效)       db_column: state 
     */	
 	//@NotNull(msg="状态(Y有效N无效)不能为空")
 	//@MaxLength(value=2, msg="字段[状态(Y有效N无效)]超出最大长度[2]")
	private String state;
 	
 	private List<TevglBookSubject> subjectList; // 职业路径下开设的课程
 	private Integer subjectTotalSize; // 职业路径下开设的课程总数量
 	
 	private String hot; // 是否热门(Y是N否)
 	
	//columns END

	public TevglBookMajor(){
	}

	public TevglBookMajor(
		String majorId
	){
		this.majorId = majorId;
	}

	public void setMajorId(String value) {
		this.majorId = value;
	}
	public String getMajorId() {
		return this.majorId;
	}
	public void setOrgId(String value) {
		this.orgId = value;
	}
	public String getOrgId() {
		return this.orgId;
	}
	public void setMajorName(String value) {
		this.majorName = value;
	}
	public String getMajorName() {
		return this.majorName;
	}
	public void setMajorLogo(String value) {
		this.majorLogo = value;
	}
	public String getMajorLogo() {
		return this.majorLogo;
	}
	public void setMajorDesc(String value) {
		this.majorDesc = value;
	}
	public String getMajorDesc() {
		return this.majorDesc;
	}
	public void setMajorRemark(String value) {
		this.majorRemark = value;
	}
	public String getMajorRemark() {
		return this.majorRemark;
	}
	public void setMajorType(String value) {
		this.majorType = value;
	}
	public String getMajorType() {
		return this.majorType;
	}
	public void setParentId(String value) {
		this.parentId = value;
	}
	public String getParentId() {
		return this.parentId;
	}
	public void setLevel(String value) {
		this.level = value;
	}
	public String getLevel() {
		return this.level;
	}
	public void setShowIndex(String value) {
		this.showIndex = value;
	}
	public String getShowIndex() {
		return this.showIndex;
	}
	public void setSortNum(Integer value) {
		this.sortNum = value;
	}
	public Integer getSortNum() {
		return this.sortNum;
	}
	public void setState(String value) {
		this.state = value;
	}
	public String getState() {
		return this.state;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public List<TevglBookSubject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<TevglBookSubject> subjectList) {
		this.subjectList = subjectList;
	}

	public String getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(String majorVersion) {
		this.majorVersion = majorVersion;
	}

	public String getLastVersionId() {
		return lastVersionId;
	}

	public void setLastVersionId(String lastVersionId) {
		this.lastVersionId = lastVersionId;
	}

	public Integer getSubjectTotalSize() {
		return subjectTotalSize;
	}

	public void setSubjectTotalSize(Integer subjectTotalSize) {
		this.subjectTotalSize = subjectTotalSize;
	}

	public String getHot() {
		return hot;
	}

	public void setHot(String hot) {
		this.hot = hot;
	}

}

