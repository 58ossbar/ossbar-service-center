package com.ossbar.modules.evgl.pkg.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;
import com.ossbar.modules.evgl.book.domain.TevglBookChapter;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;

import java.util.List;

/**
 * <p> Title: 教学包</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public class TevglPkgInfo extends BaseDomain<TevglPkgInfo> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglPkgInfo";
	public static final String ALIAS_PKG_ID = "教学包主键ID";
	public static final String ALIAS_ORG_ID = "所属院校";
	public static final String ALIAS_PKG_NAME = "教学包名称";
	public static final String ALIAS_PKG_NO = "教学包编号";
	public static final String ALIAS_SUBJECT_ID = "关联课程id";
	public static final String ALIAS_PKG_KEY = "关键字标签";
	public static final String ALIAS_REF_PKG_ID = "教学包来源";
	public static final String ALIAS_PKG_DESC = "教学包简介(文本)";
	public static final String ALIAS_PKG_REMARK = "教学包详细描述(富文本)";
	public static final String ALIAS_PKG_LEVEL = "适用层次(来源字典：本科，高职，中职)";
	public static final String ALIAS_PKG_LIMIT = "使用限制(来源字典：授权，购买，免费)";
	public static final String ALIAS_DEPLOY_MAIN_TYPE = "发布方大类(来源字典：学校，个人，创蓝)";
	public static final String ALIAS_DEPLOY_SUB_TYPE = "发布方小类";
	public static final String ALIAS_DEPLOY_TIME = "发布时间";
	public static final String ALIAS_DISPLAY = "可见性(来源字典:私有or公有)";
	public static final String ALIAS_SORT_NUM = "排序号";
	public static final String ALIAS_SHOW_INDEX = "是否推荐到首页(Y是N否)";
	public static final String ALIAS_PKG_REF_COUNT = "引用数";
	public static final String ALIAS_PKG_RES_COUNT = "资源数";
	public static final String ALIAS_PKG_ACT_COUNT = "活动数";
	public static final String ALIAS_PKG_VERSION = "教学包版本";
	public static final String ALIAS_LAST_VERSION_PKG = "上一版本ID";
	public static final String ALIAS_VERSION_REMARK = "版本更新备注";
	public static final String ALIAS_STATE = "状态(Y已发布N未发布)";
	public static final String ALIAS_PKG_TRAINEE_NAME = "联系人姓名";
	public static final String ALIAS_PKG_TRAINEE_QQ = "联系人qq";
	public static final String ALIAS_PKG_TRAINEE_MOBILE = "联系人电话号码";
	public static final String ALIAS_PKG_TRAINEE_WX = "联系人微信";
	public static final String ALIAS_PKG_TRAINEE_EMAIL = "联系人邮箱";
	public static final String ALIAS_VIEW_NUM = "查阅数";
	public static final String ALIAS_RELEASE_STATUS = "发布状态(Y已发布N未发布)";
	public static final String ALIAS_PRIVATE_USE = "Y，私人使用，新增课堂时，采用了别人授权的教学包之后，在课堂里面重新发布一个教学包，这个教学包则只能自己用";
	public static final String ALIAS_BASE_SUBJECT = "最开始对应的课程";
	public static final String ALIAS_HAS_GENERATED_DEFAULT_FOLDER = "（Y/N）是否已经生成过默认文件夹了";
	
	

    /**
     * 教学包主键ID       db_column: pkg_id 
     */	
 	//@NotNull(msg="教学包主键ID不能为空")
 	//@MaxLength(value=32, msg="字段[教学包主键ID]超出最大长度[32]")
	private String pkgId;
    /**
     * 所属院校       db_column: org_id 
     */	
 	////@NotNull(msg="所属院校不能为空")
 	//@MaxLength(value=32, msg="字段[所属院校]超出最大长度[32]")
	private String orgId;
    /**
     * 教学包名称       db_column: pkg_name 
     */	
 	//@NotNull(msg="教学包名称不能为空")
 	//@MaxLength(value=50, msg="字段[教学包名称]超出最大长度[50]")
	private String pkgName;
    /**
     * 教学包编号       db_column: pkg_no 
     */	
 	////@NotNull(msg="教学包编号不能为空")
 	//@MaxLength(value=10, msg="字段[教学包编号]超出最大长度[10]")
	private String pkgNo;
    /**
     * 关联课程id       db_column: subject_id 
     */	
 	////@NotNull(msg="关联课程id不能为空")
 	//@MaxLength(value=32, msg="字段[关联课程id]超出最大长度[32]")
	private String subjectId;
    /**
     * 关键字标签       db_column: pkg_key 
     */	
 	////@NotNull(msg="关键字标签不能为空")
 	//@MaxLength(value=1000, msg="字段[关键字标签]超出最大长度[1000]")
	private String pkgKey;
    /**
     * 教学包来源       db_column: ref_pkg_id 
     */	
 	////@NotNull(msg="教学包来源不能为空")
 	//@MaxLength(value=32, msg="字段[教学包来源]超出最大长度[32]")
	private String refPkgId;
    /**
     * 教学包简介(文本)       db_column: pkg_desc 
     */	
 	////@NotNull(msg="教学包简介(文本)不能为空")
 	//@MaxLength(value=1000, msg="字段[教学包简介(文本)]超出最大长度[1000]")
	private String pkgDesc;
    /**
     * 教学包详细描述(富文本)       db_column: pkg_remark 
     */	
 	////@NotNull(msg="教学包详细描述(富文本)不能为空")
 	//@MaxLength(value=2147483647, msg="字段[教学包详细描述(富文本)]超出最大长度[2147483647]")
	private String pkgRemark;
    /**
     * 适用层次(来源字典：本科，高职，中职)       db_column: pkg_level 
     */	
 	////@NotNull(msg="适用层次(来源字典：本科，高职，中职)不能为空")
 	//@MaxLength(value=32, msg="字段[适用层次(来源字典：本科，高职，中职)]超出最大长度[32]")
	private String pkgLevel;
    /**
     * 使用限制(来源字典：授权，购买，免费)       db_column: pkg_limit 
     */	
 	////@NotNull(msg="使用限制(来源字典：授权，购买，免费)不能为空")
 	//@MaxLength(value=32, msg="字段[使用限制(来源字典：授权，购买，免费)]超出最大长度[32]")
	private String pkgLimit;
    /**
     * 发布方大类(来源字典：学校，个人，创蓝)       db_column: deploy_main_type 
     */	
 	//@NotNull(msg="发布方大类(来源字典：学校，个人，创蓝)不能为空")
 	//@MaxLength(value=32, msg="字段[发布方大类(来源字典：学校，个人，创蓝)]超出最大长度[32]")
	private String deployMainType;
    /**
     * 发布方小类       db_column: deploy_sub_type 
     */	
 	////@NotNull(msg="发布方小类不能为空")
 	//@MaxLength(value=50, msg="字段[发布方小类]超出最大长度[50]")
	private String deploySubType;
    /**
     * 发布时间       db_column: deploy_time 
     */	
 	////@NotNull(msg="发布时间不能为空")
 	//@MaxLength(value=20, msg="字段[发布时间]超出最大长度[20]")
	private String deployTime;
    /**
     * 可见性(来源字典:私有or公有)       db_column: display 
     */	
 	////@NotNull(msg="可见性(来源字典:私有or公有)不能为空")
 	//@MaxLength(value=32, msg="字段[可见性(来源字典:私有or公有)]超出最大长度[32]")
	private String display;
    /**
     * 排序号       db_column: sort_num 
     */	
 	//@NotNull(msg="排序号不能为空")
 	//@MaxLength(value=10, msg="字段[排序号]超出最大长度[10]")
	private Integer sortNum;
    /**
     * 是否推荐到首页(Y是N否)       db_column: show_index 
     */	
 	////@NotNull(msg="是否推荐到首页(Y是N否)不能为空")
 	//@MaxLength(value=2, msg="字段[是否推荐到首页(Y是N否)]超出最大长度[2]")
	private String showIndex;
    /**
     * 引用数       db_column: pkg_ref_count 
     */	
 	////@NotNull(msg="引用数不能为空")
 	//@MaxLength(value=10, msg="字段[引用数]超出最大长度[10]")
	private Integer pkgRefCount;
    /**
     * 资源数       db_column: pkg_res_count 
     */	
 	////@NotNull(msg="资源数不能为空")
 	//@MaxLength(value=10, msg="字段[资源数]超出最大长度[10]")
	private Integer pkgResCount;
    /**
     * 活动数       db_column: pkg_act_count 
     */	
 	////@NotNull(msg="活动数不能为空")
 	//@MaxLength(value=10, msg="字段[活动数]超出最大长度[10]")
	private Integer pkgActCount;
    /**
     * 教学包版本       db_column: pkg_version 
     */	
 	////@NotNull(msg="教学包版本不能为空")
 	//@MaxLength(value=10, msg="字段[教学包版本]超出最大长度[10]")
	private String pkgVersion;
    /**
     * 上一版本ID       db_column: last_version_pkg 
     */	
 	////@NotNull(msg="上一版本ID不能为空")
 	//@MaxLength(value=32, msg="字段[上一版本ID]超出最大长度[32]")
	private String lastVersionPkg;
    /**
     * 版本更新备注       db_column: version_remark 
     */	
 	////@NotNull(msg="版本更新备注不能为空")
 	//@MaxLength(value=2147483647, msg="字段[版本更新备注]超出最大长度[2147483647]")
	private String versionRemark;
    /**
     * 状态(Y已发布N未发布)       db_column: state 
     */	
 	//@NotNull(msg="状态(Y已发布N未发布)不能为空")
 	//@MaxLength(value=2, msg="字段[状态(Y已发布N未发布)]超出最大长度[2]")
	private String state;
 	
 	private String pkgLogo; // 教学包封面图
 	
 	/**
     * 联系人姓名       db_column: pkg_trainee_name 
     */	
 	//@NotNull(msg="联系人姓名不能为空")
 	//@MaxLength(value=50, msg="字段[联系人姓名]超出最大长度[50]")
	private String pkgTraineeName;
 	/**
     *联系人qq      db_column: pkg_trainee_qq 
     */	
 	//@MaxLength(value=20, msg="字段[联系人qq]超出最大长度[20]")
	private String pkgTraineeQq;
 	/**
     * 联系人电话号码       db_column: pkg_trainee_mobile 
     */	
 	//@MaxLength(value=20, msg="字段[联系人电话号码]超出最大长度[20]")
	private String pkgTraineeMobile;
 	/**
     *联系人微信       db_column: pkg_trainee_wx 
     */	
 	//@MaxLength(value=100, msg="字段[联系人微信]超出最大长度[100]")
	private String pkgTraineeWx;
 	/**
     * 联系人邮箱       db_column: pkg_trainee_email 
     */	
 	//@MaxLength(value=100, msg="字段[联系人邮箱]超出最大长度[100]")
	private String pkgTraineeEmail;
 	/**
     * 查阅数       db_column: view_num 
     */	
 	////@NotNull(msg="查阅数不能为空")
 	//@MaxLength(value=11, msg="字段[查阅数]超出最大长度[11]")
	private Integer viewNum;
 	/**
     * 发布状态(Y已发布N未发布)       db_column: release_status 
     */	
 	//@NotNull(msg="发布状态(Y已发布N未发布)不能为空")
 	//@MaxLength(value=2, msg="字段[发布状态(Y已发布N未发布)]超出最大长度[2]")
	private String releaseStatus;
 	/**
     * Y，私人使用，新增课堂时，采用了别人授权的教学包之后，在课堂里面重新发布一个教学包，这个教学包则只能自己用       db_column: private_use 
     */	
 	////@NotNull(msg="Y，私人使用，新增课堂时，采用了别人授权的教学包之后，在课堂里面重新发布一个教学包，这个教学包则只能自己用不能为空")
 	//@MaxLength(value=2, msg="字段[Y，私人使用，新增课堂时，采用了别人授权的教学包之后，在课堂里面重新发布一个教学包，这个教学包则只能自己用]超出最大长度[2]")
	private String privateUse;
 	/**
     * 最开始对应的课程       db_column: base_subject 
     */	
 	////@NotNull(msg="最开始对应的课程不能为空")
 	//@MaxLength(value=32, msg="字段[最开始对应的课程]超出最大长度[32]")
	private String baseSubject;
 	/**
     * （Y/N）是否已经生成过默认文件夹了       db_column: has_generated_default_folder 
     */	
 	////@NotNull(msg="（Y/N）是否已经生成过默认文件夹了不能为空")
 	//@MaxLength(value=2, msg="字段[（Y/N）是否已经生成过默认文件夹了]超出最大长度[2]")
	private String hasGeneratedDefaultFolder;
 	/**
	 * 教学包接收者       db_column: receiver_user_id
	 */
	////@NotNull(msg="教学包接收者")
	//@MaxLength(value=32, msg="教学包接收者")
	private String receiverUserId;
	//columns END
 	
 	private TevglBookSubject subject; // 教学包关联课程ID所查询出来的信息
 	private TevglPkgResgroup PkgResgroup; // 教学包的教学包资源目录
 	private List<TevglPkgResgroup> PkgResgroupList; // 教学包的教学包资源目录
 	
 	private TevglTraineeInfo traineeInfo; // 教学包的所属人
 	private List<TevglBookChapter>  tevglBookChapterList;
 	private String attachId; // 附件关联ID，用于图片上传
 	private String resIdList; // 用于存储多个资源Id
 	private String resgroupIdList; // 用于存储多个资源Id
 	
 	private String majorId; 
 	
 	
	public TevglPkgInfo(){
	}

	public TevglPkgInfo(
		String pkgId
	){
		this.pkgId = pkgId;
	}

	public String getResgroupIdList() {
		return resgroupIdList;
	}

	public void setResgroupIdList(String resgroupIdList) {
		this.resgroupIdList = resgroupIdList;
	}

	public void setPkgId(String value) {
		this.pkgId = value;
	}
	public String getPkgId() {
		return this.pkgId;
	}
	
	public String getPkgTraineeName() {
		return pkgTraineeName;
	}

	public void setPkgTraineeName(String pkgTraineeName) {
		this.pkgTraineeName = pkgTraineeName;
	}

	public String getPkgTraineeQq() {
		return pkgTraineeQq;
	}

	public void setPkgTraineeQq(String pkgTraineeQq) {
		this.pkgTraineeQq = pkgTraineeQq;
	}

	public String getPkgTraineeMobile() {
		return pkgTraineeMobile;
	}

	public void setPkgTraineeMobile(String pkgTraineeMobile) {
		this.pkgTraineeMobile = pkgTraineeMobile;
	}

	public String getPkgTraineeWx() {
		return pkgTraineeWx;
	}

	public void setPkgTraineeWx(String pkgTraineeWx) {
		this.pkgTraineeWx = pkgTraineeWx;
	}

	public String getPkgTraineeEmail() {
		return pkgTraineeEmail;
	}

	public void setPkgTraineeEmail(String pkgTraineeEmail) {
		this.pkgTraineeEmail = pkgTraineeEmail;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getResIdList() {
		return resIdList;
	}

	public void setResIdList(String resIdList) {
		this.resIdList = resIdList;
	}

	public void setOrgId(String value) {
		this.orgId = value;
	}
	public String getOrgId() {
		return this.orgId;
	}
	public void setPkgName(String value) {
		this.pkgName = value;
	}
	public String getPkgName() {
		return this.pkgName;
	}
	public void setPkgNo(String value) {
		this.pkgNo = value;
	}
	public String getPkgNo() {
		return this.pkgNo;
	}
	public void setSubjectId(String value) {
		this.subjectId = value;
	}
	public String getSubjectId() {
		return this.subjectId;
	}
	public void setPkgKey(String value) {
		this.pkgKey = value;
	}
	public String getPkgKey() {
		return this.pkgKey;
	}
	public void setRefPkgId(String value) {
		this.refPkgId = value;
	}
	public String getRefPkgId() {
		return this.refPkgId;
	}
	public void setPkgDesc(String value) {
		this.pkgDesc = value;
	}
	public String getPkgDesc() {
		return this.pkgDesc;
	}
	public void setPkgRemark(String value) {
		this.pkgRemark = value;
	}
	public String getPkgRemark() {
		return this.pkgRemark;
	}
	public void setPkgLevel(String value) {
		this.pkgLevel = value;
	}
	public String getPkgLevel() {
		return this.pkgLevel;
	}
	public void setPkgLimit(String value) {
		this.pkgLimit = value;
	}
	public String getPkgLimit() {
		return this.pkgLimit;
	}
	public void setDeployMainType(String value) {
		this.deployMainType = value;
	}
	public String getDeployMainType() {
		return this.deployMainType;
	}
	public void setDeploySubType(String value) {
		this.deploySubType = value;
	}
	public String getDeploySubType() {
		return this.deploySubType;
	}
	public void setDeployTime(String value) {
		this.deployTime = value;
	}
	public String getDeployTime() {
		return this.deployTime;
	}
	public void setDisplay(String value) {
		this.display = value;
	}
	public String getDisplay() {
		return this.display;
	}
	public void setSortNum(Integer value) {
		this.sortNum = value;
	}
	public Integer getSortNum() {
		return this.sortNum;
	}
	public void setShowIndex(String value) {
		this.showIndex = value;
	}
	public String getShowIndex() {
		return this.showIndex;
	}
	public void setPkgRefCount(Integer value) {
		this.pkgRefCount = value;
	}
	public Integer getPkgRefCount() {
		return this.pkgRefCount;
	}
	public void setPkgResCount(Integer value) {
		this.pkgResCount = value;
	}
	public Integer getPkgResCount() {
		return this.pkgResCount;
	}
	public void setPkgActCount(Integer value) {
		this.pkgActCount = value;
	}
	public Integer getPkgActCount() {
		return this.pkgActCount;
	}
	public void setPkgVersion(String value) {
		this.pkgVersion = value;
	}
	public String getPkgVersion() {
		return this.pkgVersion;
	}
	public void setLastVersionPkg(String value) {
		this.lastVersionPkg = value;
	}
	public String getLastVersionPkg() {
		return this.lastVersionPkg;
	}
	public void setVersionRemark(String value) {
		this.versionRemark = value;
	}
	public String getVersionRemark() {
		return this.versionRemark;
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

	public TevglBookSubject getSubject() {
		return subject;
	}

	public void setSubject(TevglBookSubject subject) {
		this.subject = subject;
	}

	public TevglPkgResgroup getPkgResgroup() {
		return PkgResgroup;
	}

	public void setPkgResgroup(TevglPkgResgroup pkgResgroup) {
		PkgResgroup = pkgResgroup;
	}

	public List<TevglPkgResgroup> getPkgResgroupList() {
		return PkgResgroupList;
	}

	public void setPkgResgroupList(List<TevglPkgResgroup> pkgResgroupList) {
		PkgResgroupList = pkgResgroupList;
	}

	public String getPkgLogo() {
		return pkgLogo;
	}

	public void setPkgLogo(String pkgLogo) {
		this.pkgLogo = pkgLogo;
	}

	public TevglTraineeInfo getTraineeInfo() {
		return traineeInfo;
	}

	public void setTraineeInfo(TevglTraineeInfo traineeInfo) {
		this.traineeInfo = traineeInfo;
	}

	public List<TevglBookChapter> getTevglBookChapterList() {
		return tevglBookChapterList;
	}

	public void setTevglBookChapterList(List<TevglBookChapter> tevglBookChapterList) {
		this.tevglBookChapterList = tevglBookChapterList;
	}

	public Integer getViewNum() {
		return viewNum;
	}

	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public String getPrivateUse() {
		return privateUse;
	}

	public void setPrivateUse(String privateUse) {
		this.privateUse = privateUse;
	}

	public String getBaseSubject() {
		return baseSubject;
	}

	public void setBaseSubject(String baseSubject) {
		this.baseSubject = baseSubject;
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public String getHasGeneratedDefaultFolder() {
		return hasGeneratedDefaultFolder;
	}

	public void setHasGeneratedDefaultFolder(String hasGeneratedDefaultFolder) {
		this.hasGeneratedDefaultFolder = hasGeneratedDefaultFolder;
	}

	public String getReceiverUserId() {
		return receiverUserId;
	}

	public void setReceiverUserId(String receiverUserId) {
		this.receiverUserId = receiverUserId;
	}
}

