package com.ossbar.modules.evgl.book.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;

import java.util.List;

/**
 * <p> Title: 专业课程（职业课程）关系</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public class TevglBookSubperiod extends BaseDomain<TevglBookSubperiod> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglBookSubperiod";
	public static final String ALIAS_SUBPERIOD_ID = "主键ID";
	public static final String ALIAS_MAJOR_ID = "专业ID";
	public static final String ALIAS_SUBJECT_ID = "课程ID";
	public static final String ALIAS_SORT_NUM = "排序号";
	public static final String ALIAS_TERM = "所属学期(来源字典)";
	public static final String ALIAS_SUBJECT_PROPERTY = "课程属性(选修or必修)";
	public static final String ALIAS_CLASS_HOUR = "课时";
	public static final String ALIAS_CLASS_SCORE = "学分";
	

    /**
     * 主键ID       db_column: subperiod_id 
     */	
 	//@NotNull(msg="主键ID不能为空")
 	//@MaxLength(value=32, msg="字段[主键ID]超出最大长度[32]")
	private String subperiodId;
    /**
     * 专业ID       db_column: major_id 
     */	
 	//@NotNull(msg="专业ID不能为空")
 	//@MaxLength(value=32, msg="字段[专业ID]超出最大长度[32]")
	private String majorId;
    /**
     * 课程ID       db_column: subject_id 
     */	
 	//@NotNull(msg="课程ID不能为空")
 	//@MaxLength(value=32, msg="字段[课程ID]超出最大长度[32]")
	private String subjectId;
    /**
     * 排序号       db_column: sort_num 
     */	
 	//@NotNull(msg="排序号不能为空")
 	//@MaxLength(value=10, msg="字段[排序号]超出最大长度[10]")
	private Integer sortNum;
    /**
     * 所属学期(来源字典)       db_column: term 
     */	
 	//@NotNull(msg="所属学期(来源字典)不能为空")
 	//@MaxLength(value=32, msg="字段[所属学期(来源字典)]超出最大长度[32]")
	private String term;
    /**
     * 课程属性(选修or必修)       db_column: subject_property 
     */	
 	//@NotNull(msg="课程属性(选修or必修)不能为空")
 	//@MaxLength(value=32, msg="字段[课程属性(选修or必修)]超出最大长度[32]")
	private String subjectProperty;
    /**
     * 课时       db_column: class_hour 
     */	
 	//@NotNull(msg="课时不能为空")
 	//@MaxLength(value=10, msg="字段[课时]超出最大长度[10]")
	private java.math.BigDecimal classHour;
    /**
     * 学分       db_column: class_score 
     */	
 	//@NotNull(msg="学分不能为空")
 	//@MaxLength(value=5, msg="字段[学分]超出最大长度[5]")
	private java.math.BigDecimal classScore;
 	
 	private List<TevglBookSubject> subjectList;
 	
	//columns END

	public TevglBookSubperiod(){
	}

	public TevglBookSubperiod(
		String subperiodId
	){
		this.subperiodId = subperiodId;
	}

	public void setSubperiodId(String value) {
		this.subperiodId = value;
	}
	public String getSubperiodId() {
		return this.subperiodId;
	}
	public void setMajorId(String value) {
		this.majorId = value;
	}
	public String getMajorId() {
		return this.majorId;
	}
	public void setSubjectId(String value) {
		this.subjectId = value;
	}
	public String getSubjectId() {
		return this.subjectId;
	}
	public void setSortNum(Integer value) {
		this.sortNum = value;
	}
	public Integer getSortNum() {
		return this.sortNum;
	}
	public void setTerm(String value) {
		this.term = value;
	}
	public String getTerm() {
		return this.term;
	}
	public void setSubjectProperty(String value) {
		this.subjectProperty = value;
	}
	public String getSubjectProperty() {
		return this.subjectProperty;
	}
	public void setClassHour(java.math.BigDecimal value) {
		this.classHour = value;
	}
	public java.math.BigDecimal getClassHour() {
		return this.classHour;
	}
	public void setClassScore(java.math.BigDecimal value) {
		this.classScore = value;
	}
	public java.math.BigDecimal getClassScore() {
		return this.classScore;
	}
	
	private TevglBookSubject tevglBookSubject;
	
	public void setTevglBookSubject(TevglBookSubject tevglBookSubject){
		this.tevglBookSubject = tevglBookSubject;
	}
	
	public TevglBookSubject getTevglBookSubject() {
		return tevglBookSubject;
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

	public List<TevglBookSubject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<TevglBookSubject> subjectList) {
		this.subjectList = subjectList;
	}
}

