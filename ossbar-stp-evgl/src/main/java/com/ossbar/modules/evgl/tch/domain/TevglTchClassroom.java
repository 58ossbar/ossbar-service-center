package com.ossbar.modules.evgl.tch.domain;

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

public class TevglTchClassroom extends BaseDomain<TevglTchClassroom> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglTchClassroom";
	public static final String ALIAS_CT_ID = "主键ID";
	public static final String ALIAS_MAJOR_ID = "职业课程路径ID";
	public static final String ALIAS_TEACHER_ID = "教师ID";
	public static final String ALIAS_CLASS_ID = "班级ID(上课班级)";
	public static final String ALIAS_PKG_ID = "教学包";
	public static final String ALIAS_IS_PUBLIC = "是否公开课(Y/N)";
	public static final String ALIAS_PIC = "课堂封面图";
	public static final String ALIAS_INVITATION_CODE = "邀请码";
	public static final String ALIAS_NAME = "课堂名称";
	public static final String ALIAS_INTRO = "简介";
	public static final String ALIAS_STUDY_NUM = "学习人数";
	public static final String ALIAS_STATE = "状态(Y有效N无效)";
	public static final String ALIAS_IS_CHECK = "加入班级是否审核(Y是N否)";
	public static final String ALIAS_IS_TOP = "是否置顶(Y/N)";
	public static final String ALIAS_QR_CODE = "二维码";
	public static final String ALIAS_COLLECT_NUM = "收藏数";
	public static final String ALIAS_LIKE_NUM = "点赞数";
	public static final String ALIAS_CLASSROOM_STATE = "课堂状态(1未开始2进行中3已结束)";
	public static final String ALIAS_TRAINEE_ID = "助教（也就是学员）";
	public static final String ALIAS_BEGIN_TIME = "课堂开始时间";
	public static final String ALIAS_END_TIME = "课堂结束时间";
	public static final String ALIAS_PLATFORM_AUDIT_STATUS = "平台审核状态（Y通过N不通过）";
	public static final String ALIAS_VIEW_NUM = "用于记录课堂详情页面的查看次数，当0次时，生成课堂默认的云盘快捷面板";
	public static final String ALIAS_IF_LIVE_LESSON = "是否为直播课（Y/N）";
	public static final String ALIAS_LINK_URL = "直播地址";

    /**
     * 主键ID       db_column: ct_id 
     */	
 	//@NotNull(msg="主键ID不能为空")
 	//@MaxLength(value=32, msg="字段[主键ID]超出最大长度[32]")
	private String ctId;
    /**
     * 职业课程路径ID       db_column: major_id 
     */	
 	////@NotNull(msg="职业课程路径ID不能为空")
 	//@MaxLength(value=32, msg="字段[职业课程路径ID]超出最大长度[32]")
	private String majorId;
    /**
     * 教师ID       db_column: teacher_id 
     */	
 	////@NotNull(msg="教师ID不能为空")
 	//@MaxLength(value=32, msg="字段[教师ID]超出最大长度[32]")
	private String teacherId;
    /**
     * 班级ID(上课班级)       db_column: class_id 
     */	
 	//@NotNull(msg="班级ID(上课班级)不能为空")
 	//@MaxLength(value=500, msg="字段[班级ID(上课班级)]超出最大长度[500]")
	private String classId;
    /**
     * 教学包ID       db_column: pkg_id 
     */	
 	////@NotNull(msg="教学包ID 不能为空")
 	//@MaxLength(value=32, msg="字段[教学包ID]超出最大长度[32]")
	private String pkgId;
    /**
     * 是否公开课(Y/N)      db_column: is_public 
     */	
 	////@NotNull(msg="是否公开课(Y/N)不能为空")
 	//@MaxLength(value=2, msg="字段[是否公开课(Y/N)]超出最大长度[2]")
	private String isPublic;
    /**
     * 课堂封面图       db_column: pic 
     */	
 	////@NotNull(msg="课堂封面图不能为空")
 	//@MaxLength(value=500, msg="字段[课堂封面图]超出最大长度[500]")
	private String pic;
    /**
     * 邀请码       db_column: invitation_code 
     */	
 	////@NotNull(msg="邀请码不能为空")
 	//@MaxLength(value=10, msg="字段[邀请码]超出最大长度[10]")
	private String invitationCode;
    /**
     * 课堂名称       db_column: name 
     */	
 	//@NotNull(msg="课堂名称不能为空")
 	//@MaxLength(value=100, msg="字段[课堂名称]超出最大长度[100]")
	private String name;
    /**
     * 简介       db_column: intro 
     */	
 	////@NotNull(msg="简介不能为空")
 	//@MaxLength(value=500, msg="字段[简介]超出最大长度[500]")
	private String intro;
    /**
     * 学习人数       db_column: study_num 
     */	
 	////@NotNull(msg="学习人数不能为空")
 	//@MaxLength(value=10, msg="字段[学习人数]超出最大长度[10]")
	private Integer studyNum;
    /**
     * 状态(Y有效N无效)       db_column: state 
     */	
 	////@NotNull(msg="状态(Y有效N无效)不能为空")
 	//@MaxLength(value=2, msg="字段[状态(Y有效N无效)]超出最大长度[2]")
	private String state;
 	/**
     * 助教（也就是学员）       db_column: trainee_id 
     */	
 	////@NotNull(msg="助教（也就是学员）不能为空")
 	//@MaxLength(value=32, msg="字段[助教（也就是学员）]超出最大长度[32]")
	private String traineeId;
 	/**
     * 课堂状态(1未开始2进行中3已结束)       db_column: classroom_state 
     */	
 	////@NotNull(msg="课堂状态(1未开始2进行中3已结束)不能为空")
 	//@MaxLength(value=2, msg="字段[课堂状态(1未开始2进行中3已结束)]超出最大长度[2]")
	private String classroomState;
    /**
     * 加入班级是否审核（Y是N否）       db_column: is_check 
     */	
 	////@NotNull(msg="加入班级是否审核（Y是N否）不能为空")
 	//@MaxLength(value=2, msg="字段[加入班级是否审核（Y是N否）]超出最大长度[2]")
	private String isCheck;
 	/**
     * 是否置顶(Y/N)       db_column: is_top 
     */	
 	////@NotNull(msg="是否置顶(Y/N)不能为空")
 	//@MaxLength(value=2, msg="字段[是否置顶(Y/N)]超出最大长度[2]")
	private String isTop;
 	/**
     * 二维码       db_column: qr_code 
     */	
 	////@NotNull(msg="二维码不能为空")
 	//@MaxLength(value=200, msg="字段[二维码]超出最大长度[200]")
	private String qrCode;
 	/**
     * 收藏数       db_column: collect_num 
     */	
 	////@NotNull(msg="收藏数不能为空")
 	//@MaxLength(value=10, msg="字段[收藏数]超出最大长度[10]")
	private Integer collectNum;
    /**
     * 点赞数       db_column: like_num 
     */	
 	////@NotNull(msg="点赞数不能为空")
 	//@MaxLength(value=10, msg="字段[点赞数]超出最大长度[10]")
	private Integer likeNum;
 	/**
     * 课堂开始时间       db_column: begin_time 
     */	
 	////@NotNull(msg="课堂开始时间不能为空")
 	//@MaxLength(value=20, msg="字段[课堂开始时间]超出最大长度[20]")
	private String beginTime;
    /**
     * 课堂结束时间       db_column: end_time 
     */	
 	////@NotNull(msg="课堂结束时间不能为空")
 	//@MaxLength(value=20, msg="字段[课堂结束时间]超出最大长度[20]")
	private String endTime;
 	/**
     * 平台审核状态（Y通过N不通过）       db_column: platform_audit_status 
     */	
 	////@NotNull(msg="平台审核状态（Y通过N不通过）不能为空")
 	//@MaxLength(value=2, msg="字段[平台审核状态（Y通过N不通过）]超出最大长度[2]")
	private String platformAuditStatus;
 	/**
     * 用于记录课堂详情页面的查看次数，当0次时，生成课堂默认的云盘快捷面板       db_column: view_num 
     */	
 	////@NotNull(msg="用于记录课堂详情页面的查看次数，当0次时，生成课堂默认的云盘快捷面板不能为空")
 	//@MaxLength(value=10, msg="字段[用于记录课堂详情页面的查看次数，当0次时，生成课堂默认的云盘快捷面板]超出最大长度[10]")
	private Integer viewNum;
 	/**
     * 是否为直播课（Y/N）       db_column: if_live_lesson 
     */	
 	////@NotNull(msg="是否为直播课（Y/N）不能为空")
 	//@MaxLength(value=2, msg="字段[是否为直播课（Y/N）]超出最大长度[2]")
	private String ifLiveLesson;
    /**
     * 直播地址       db_column: link_url 
     */	
 	////@NotNull(msg="直播地址不能为空")
 	//@MaxLength(value=500, msg="字段[直播地址]超出最大长度[500]")
	private String linkUrl;
 	/**
     * 接收者（课堂被移交给此人）       db_column: receiver_user_id 
     */	
 	////@NotNull(msg="接收者（课堂被移交给此人）")
 	//@MaxLength(value=500, msg="字段[接收者]超出最大长度[500]")
	private String receiverUserId;
	//columns END
 	
 	private String attachId;
 	
 	private Integer resourceNum; // 用于存储资源数
 	
 	/**
 	 * 课程，若未选教学包，则使用此课程生成一份系统创建的教学包
 	 */
 	private String subjectId;
 	private String pkgName;
 	
 	/**
 	 * 此课堂对应的上课的班级
 	 */
 	private String className;
 	
	//columns END

	public TevglTchClassroom(){
	}

	public TevglTchClassroom(
		String ctId
	){
		this.ctId = ctId;
	}

	public void setCtId(String value) {
		this.ctId = value;
	}
	public String getCtId() {
		return this.ctId;
	}
	public void setMajorId(String value) {
		this.majorId = value;
	}
	public String getMajorId() {
		return this.majorId;
	}
	public void setTeacherId(String value) {
		this.teacherId = value;
	}
	public String getTeacherId() {
		return this.teacherId;
	}
	public void setClassId(String value) {
		this.classId = value;
	}
	public String getClassId() {
		return this.classId;
	}
	
	public void setIsPublic(String value) {
		this.isPublic = value;
	}
	public String getIsPublic() {
		return this.isPublic;
	}
	public void setPic(String value) {
		this.pic = value;
	}
	public String getPic() {
		return this.pic;
	}
	public void setInvitationCode(String value) {
		this.invitationCode = value;
	}
	public String getInvitationCode() {
		return this.invitationCode;
	}
	public void setName(String value) {
		this.name = value;
	}
	public String getName() {
		return this.name;
	}
	public void setIntro(String value) {
		this.intro = value;
	}
	public String getIntro() {
		return this.intro;
	}
	public void setStudyNum(Integer value) {
		this.studyNum = value;
	}
	public Integer getStudyNum() {
		return this.studyNum;
	}
	public void setState(String value) {
		this.state = value;
	}
	public String getState() {
		return this.state;
	}
	
	private TevglTchClass tevglTchClass;
	
	public void setTevglTchClass(TevglTchClass tevglTchClass){
		this.tevglTchClass = tevglTchClass;
	}
	
	public TevglTchClass getTevglTchClass() {
		return tevglTchClass;
	}
	
	private TevglTchTeacher tevglTchTeacher; // 存储教师信息
	
	public void setTevglTchTeacher(TevglTchTeacher tevglTchTeacher){
		this.tevglTchTeacher = tevglTchTeacher;
	}
	
	public TevglTchTeacher getTevglTchTeacher() {
		return tevglTchTeacher;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public Integer getResourceNum() {
		return resourceNum;
	}

	public void setResourceNum(Integer resourceNum) {
		this.resourceNum = resourceNum;
	}

	public String getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(String traineeId) {
		this.traineeId = traineeId;
	}

	public String getClassroomState() {
		return classroomState;
	}

	public void setClassroomState(String classroomState) {
		this.classroomState = classroomState;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getPkgId() {
		return pkgId;
	}

	public void setPkgId(String pkgId) {
		this.pkgId = pkgId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}

	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getPlatformAuditStatus() {
		return platformAuditStatus;
	}

	public void setPlatformAuditStatus(String platformAuditStatus) {
		this.platformAuditStatus = platformAuditStatus;
	}

	public Integer getViewNum() {
		return viewNum;
	}

	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}

	public String getIfLiveLesson() {
		return ifLiveLesson;
	}

	public void setIfLiveLesson(String ifLiveLesson) {
		this.ifLiveLesson = ifLiveLesson;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getReceiverUserId() {
		return receiverUserId;
	}

	public void setReceiverUserId(String receiverUserId) {
		this.receiverUserId = receiverUserId;
	}
	
}

