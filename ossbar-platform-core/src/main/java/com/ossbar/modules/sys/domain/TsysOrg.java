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

import com.ossbar.core.baseclass.domain.BaseDomain;

import java.util.ArrayList;
import java.util.List;


public class TsysOrg extends BaseDomain<Object> {
	private static final long serialVersionUID = 1L;

	// alias
	public static final String TABLE_ALIAS = "TsysOrg";
	public static final String ALIAS_ORG_ID = "机构ID";
	public static final String ALIAS_ORG_SN = "机构排序ID";
	public static final String ALIAS_ORG_NAME = "机构名称";
	public static final String ALIAS_ORG_CODE = "机构编号";
	public static final String ALIAS_ORG_XZQM = "行政区码";
	public static final String ALIAS_ORG_SHOWNAME = "机构显示名称";
	public static final String ALIAS_PARENT_ID = "父机构ID";
	public static final String ALIAS_LAYER = "层（阶次）";
	public static final String ALIAS_CREATE_DATE = "创建时间";
	public static final String ALIAS_CREATOR = "创建人";
	public static final String ALIAS_REMARK = "单位简介";
	public static final String ALIAS_ORG_TYPE = "机构类型:1、部门 2、公司";
	public static final String ALIAS_ADDR = "通讯地址";
	public static final String ALIAS_ZIP = "邮政编码";
	public static final String ALIAS_EMAIL = "电子邮箱";
	public static final String ALIAS_LEADER = "机构负责人";
	public static final String ALIAS_PHONE = "办公电话";
	public static final String ALIAS_FAX = "传真号码";
	public static final String ALIAS_STATE = "状态:1有效 2、停用";
	public static final String ALIAS_MOBILE = "负责人手机号码";
	public static final String ALIAS_JP = "简拼";
	public static final String ALIAS_QP = "全拼";
	public static final String ALIAS_ANCESTRY = "排序";
	public static final String ALIAS_COVER_PIC = "封面图";
	public static final String ALIAS_DESCRIPTION = "机构描述";
	public static final String ALIAS_COLLEGE_CODE = "学院代码";
	public static final String ALIAS_MAJOR_CODE = "专业代码";
	/**
	 * 机构ID db_column: ORG_ID
	 */
	private String orgId;
	/**
	 * 机构排序ID db_column: ORG_SN
	 */
	private Integer orgSn;
	/**
	 * 机构名称 db_column: ORG_NAME
	 */
	private String orgName;
	/**
	 * 机构编号 db_column: ORG_CODE
	 */
	private String orgCode;
	/**
	 * 行政区码 db_column: ORG_XZQM
	 */
	private String orgXzqm;
	/**
	 * 机构显示名称 db_column: ORG_SHOWNAME
	 */
	private String orgShowname;
	/**
	 * 父机构ID db_column: PARENT_ID
	 */
	private String parentId;

	/**
	 * 父机构name
	 */
	private String parentName;

	/**
	 * 层（阶次） db_column: LAYER
	 */
	private Integer layer;

	/**
	 * 创建人 db_column: CREATOR
	 */
	private String creator;
	/**
	 * 单位简介 db_column: REMARK
	 */
	private String remark;
	/**
	 * 机构类型:1、部门 2、公司 db_column: ORG_TYPE
	 */
	private String orgType;
	/**
	 * 通讯地址 db_column: ADDR
	 */
	private String addr;
	/**
	 * 邮政编码 db_column: ZIP
	 */
	private String zip;
	/**
	 * 电子邮箱 db_column: EMAIL
	 */
	private String email;
	/**
	 * 机构负责人 db_column: LEADER
	 */
	private String leader;
	/**
	 * 办公电话 db_column: PHONE
	 */
	private String phone;
	/**
	 * 传真号码 db_column: FAX
	 */
	private String fax;
	/**
	 * 状态:1有效 2、停用 db_column: STATE
	 */
	private String state;
	/**
	 * 负责人手机号码 db_column: MOBILE
	 */
	private String mobile;
	/**
	 * 简拼 db_column: JP
	 */
	private String jp;
	/**
	 * 全拼 db_column: QP
	 */
	private String qp;
	/**
	 * 排序 db_column: ANCESTRY
	 */
	private String ancestry;
	/**
	 * 封面图 db_column: cover_pic
	 */

	private String coverPic;
	/**
	 * 机构描述 db_column: description
	 */
	private String description;
	/**
	 * 学院代码 db_column: college_code
	 */
	private String collegeCode;
	/**
	 * 专业代码 db_column: major_code
	 */
	private String majorCode;
	/**
	 * 是否有子结点 true有无子结点 false有子节点
	 */
	private boolean isLeaf;

	// columns END

	private List<TsysOrg> children = new ArrayList<TsysOrg>();

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getCollegeCode() {
		return collegeCode;
	}

	public void setCollegeCode(String collegeCode) {
		this.collegeCode = collegeCode;
	}

	public String getMajorCode() {
		return majorCode;
	}

	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}

	public TsysOrg() {
	}

	public TsysOrg(String orgId) {
		this.orgId = orgId;
	}

	public void setOrgId(String value) {
		this.orgId = value;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgSn(Integer value) {
		this.orgSn = value;
	}

	public Integer getOrgSn() {
		return this.orgSn;
	}

	public void setOrgName(String value) {
		this.orgName = value;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgCode(String value) {
		this.orgCode = value;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgXzqm(String value) {
		this.orgXzqm = value;
	}

	public String getOrgXzqm() {
		return this.orgXzqm;
	}

	public void setOrgShowname(String value) {
		this.orgShowname = value;
	}

	public String getOrgShowname() {
		return this.orgShowname;
	}

	public void setParentId(String value) {
		this.parentId = value;
	}

	public String getParentId() {
		return this.parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public void setLayer(Integer value) {
		this.layer = value;
	}

	public Integer getLayer() {
		return this.layer;
	}

	public void setCreator(String value) {
		this.creator = value;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setRemark(String value) {
		this.remark = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setOrgType(String value) {
		this.orgType = value;
	}

	public String getOrgType() {
		return this.orgType;
	}

	public void setAddr(String value) {
		this.addr = value;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setZip(String value) {
		this.zip = value;
	}

	public String getZip() {
		return this.zip;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public String getEmail() {
		return this.email;
	}

	public void setLeader(String value) {
		this.leader = value;
	}

	public String getLeader() {
		return this.leader;
	}

	public void setPhone(String value) {
		this.phone = value;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setFax(String value) {
		this.fax = value;
	}

	public String getFax() {
		return this.fax;
	}

	public void setState(String value) {
		this.state = value;
	}

	public String getState() {
		return this.state;
	}

	public void setMobile(String value) {
		this.mobile = value;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setJp(String value) {
		this.jp = value;
	}

	public String getJp() {
		return this.jp;
	}

	public void setQp(String value) {
		this.qp = value;
	}

	public String getQp() {
		return this.qp;
	}

	public void setAncestry(String value) {
		this.ancestry = value;
	}

	public String getAncestry() {
		return this.ancestry;
	}

	public void setCoverPic(String value) {
		this.coverPic = value;
	}

	public String getCoverPic() {
		return this.coverPic;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public String getDescription() {
		return this.description;
	}

	public List<TsysOrg> getChildren() {
		return children;
	}

	public void setChildren(List<TsysOrg> children) {
		this.children = children;
	}

}
