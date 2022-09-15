package com.ossbar.modules.evgl.tch.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;
import com.ossbar.modules.evgl.book.domain.TevglBookMajor;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public class TevglTchClass extends BaseDomain<TevglTchClass> {
	private static final long serialVersionUID = 1L;

	//alias
	public static final String TABLE_ALIAS = "TevglTchClass";
	public static final String ALIAS_CLASS_ID = "班级标识ID";
	public static final String ALIAS_MAJOR_ID = "专业标识ID";
	public static final String ALIAS_ORG_ID = "机构ID";
	public static final String ALIAS_CLASS_NO = "班级编号";
	public static final String ALIAS_CLASS_NAME = "班级名称";
	public static final String ALIAS_CLASS_PIC = "班级缩略图";
	public static final String ALIAS_Y_PRICE = "原价格";
	public static final String ALIAS_YH_PRICE = "优惠价格";
	public static final String ALIAS_TG_PRICE = "团购价格";
	public static final String ALIAS_CLASS_TIME = "时长";
	public static final String ALIAS_CLASS_STATE = "班级状态：开放、授课、完成";
	public static final String ALIAS_EXPECT_TIME = "预计开班时间";
	public static final String ALIAS_ACCEPT_TIME = "实际开班时间";
	public static final String ALIAS_CUR_SUM = "当前报名人数";
	public static final String ALIAS_EXPECT_SUM = "计划开班人数";
	public static final String ALIAS_ACCEPT_SUM = "实际开班人数";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_TEACHING_ASSISTANT = "助教";


    /**
     * 班级标识ID       db_column: class_id
     */
	private String classId;
    /**
     * 专业标识ID       db_column: major_id
     */
	private String majorId;
    /**
     * 机构ID       db_column: ORG_ID
     */
	private String orgId;
    /**
     * 班级编号       db_column: class_no
     */
	private String classNo;
    /**
     * 班级名称       db_column: class_name
     */
	private String className;
    /**
     * 班级缩略图       db_column: class_pic
     */
	private String classPic;
	/**
     * 自定义封面       db_column: class_img
     */
	private String classImg;
    /**
     * 原价格       db_column: y_price
     */
	private java.math.BigDecimal yprice;
    /**
     * 优惠价格       db_column: yh_price
     */
	private java.math.BigDecimal yhPrice;
    /**
     * 团购价格       db_column: tg_price
     */
	private java.math.BigDecimal tgPrice;
    /**
     * 时长       db_column: class_time
     */
	private String classTime;
    /**
     * 班级状态：开放、授课、完成       db_column: class_state
     */
	private String classState;
	/**
     * 报名开始时间       db_column: registration_start_time
     */
	private String registrationStartTime;
    /**
     * 预计开班时间       db_column: expect_time
     */
	private String expectTime;
    /**
     * 实际开班时间       db_column: accept_time
     */
	private String acceptTime;
    /**
     * 当前报名人数       db_column: cur_sum
     */
	private Integer curSum;
    /**
     * 计划开班人数       db_column: expect_sum
     */
 	//@NotNull(msg="计划开班人数不能为空")
 	//@MaxLength(value=10, msg="字段[计划开班人数]超出最大长度[10]")
	private Integer expectSum;
    /**
     * 实际开班人数       db_column: accept_sum
     */
 	//@NotNull(msg="实际开班人数不能为空")
 	//@MaxLength(value=10, msg="字段[实际开班人数]超出最大长度[10]")
	private Integer acceptSum;
    /**
     * 备注       db_column: remark
     */
 	//@NotNull(msg="备注不能为空")
 	//@MaxLength(value=1000, msg="字段[备注]超出最大长度[1000]")
	private String remark;
    /**
     * 助教       db_column: teaching_assistant
     */
 	//@NotNull(msg="助教不能为空")
 	//@MaxLength(value=32, msg="字段[助教]超出最大长度[32]")
	private String teachingAssistant;
 	/**
     * 类型，空默认、1单科强化、2订单就业       db_column: type
     */
 	//@MaxLength(value=300, msg="字段[类型]超出最大长度[2]")
	private String type;

 	private String attachId; // 附件关联ID
 	private String majorName; // 用于职业路径名称的显示

 	/**
 	 * 教师名称
 	 */
 	private String teacherName;
 	private String teacherId;
 	/**
 	 * 教师头像
 	 */
 	private String teacherPic;

 	/**
 	 * 助教名称
 	 */
 	private String teachingAssistantName;
 	/**
 	 * 助教头像
 	 */
 	private String teachingAssistantPic;


	//columns END

	public TevglTchClass(){
	}

	public TevglTchClass(
		String classId
	){
		this.classId = classId;
	}

	public void setClassId(String value) {
		this.classId = value;
	}
	public String getClassId() {
		return this.classId;
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
	public void setClassNo(String value) {
		this.classNo = value;
	}
	public String getClassNo() {
		return this.classNo;
	}
	public void setClassName(String value) {
		this.className = value;
	}
	public String getClassName() {
		return this.className;
	}
	public void setClassPic(String value) {
		this.classPic = value;
	}
	public String getClassPic() {
		return this.classPic;
	}
	public void setYprice(java.math.BigDecimal value) {
		this.yprice = value;
	}
	public java.math.BigDecimal getYprice() {
		return this.yprice;
	}
	public void setYhPrice(java.math.BigDecimal value) {
		this.yhPrice = value;
	}
	public java.math.BigDecimal getYhPrice() {
		return this.yhPrice;
	}
	public void setTgPrice(java.math.BigDecimal value) {
		this.tgPrice = value;
	}
	public java.math.BigDecimal getTgPrice() {
		return this.tgPrice;
	}
	public void setClassTime(String value) {
		this.classTime = value;
	}
	public String getClassTime() {
		return this.classTime;
	}
	public void setClassState(String value) {
		this.classState = value;
	}
	public String getClassState() {
		return this.classState;
	}
	public void setExpectTime(String value) {
		this.expectTime = value;
	}
	public String getExpectTime() {
		return this.expectTime;
	}
	public void setAcceptTime(String value) {
		this.acceptTime = value;
	}
	public String getAcceptTime() {
		return this.acceptTime;
	}
	public void setCurSum(Integer value) {
		this.curSum = value;
	}
	public Integer getCurSum() {
		return this.curSum;
	}
	public void setExpectSum(Integer value) {
		this.expectSum = value;
	}
	public Integer getExpectSum() {
		return this.expectSum;
	}
	public void setAcceptSum(Integer value) {
		this.acceptSum = value;
	}
	public Integer getAcceptSum() {
		return this.acceptSum;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	public String getRemark() {
		return this.remark;
	}
	public void setTeachingAssistant(String value) {
		this.teachingAssistant = value;
	}
	public String getTeachingAssistant() {
		return this.teachingAssistant;
	}

	private TevglBookMajor tevglBookMajor;

	public void setTevglBookMajor(TevglBookMajor tevglBookMajor){
		this.tevglBookMajor = tevglBookMajor;
	}

	public TevglBookMajor getTevglBookMajor() {
		return tevglBookMajor;
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

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getTeachingAssistantName() {
		return teachingAssistantName;
	}

	public void setTeachingAssistantName(String teachingAssistantName) {
		this.teachingAssistantName = teachingAssistantName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherPic() {
		return teacherPic;
	}

	public void setTeacherPic(String teacherPic) {
		this.teacherPic = teacherPic;
	}

	public String getTeachingAssistantPic() {
		return teachingAssistantPic;
	}

	public void setTeachingAssistantPic(String teachingAssistantPic) {
		this.teachingAssistantPic = teachingAssistantPic;
	}

	public String getRegistrationStartTime() {
		return registrationStartTime;
	}

	public void setRegistrationStartTime(String registrationStartTime) {
		this.registrationStartTime = registrationStartTime;
	}

	public String getClassImg() {
		return classImg;
	}

	public void setClassImg(String classImg) {
		this.classImg = classImg;
	}
}

