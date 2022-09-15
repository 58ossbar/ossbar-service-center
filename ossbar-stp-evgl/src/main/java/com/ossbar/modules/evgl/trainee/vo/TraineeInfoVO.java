package com.ossbar.modules.evgl.trainee.vo;

import com.ossbar.modules.common.annotation.Excel;

import java.io.Serializable;

/**
 * @author huj
 * @create 2022-09-15 11:32
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class TraineeInfoVO implements Serializable {

    /**
     * 粉丝主键ID       db_column: trainee_id
     */
    private String traineeId;

    /**
     * 姓名       db_column: trainee_name
     */
    @Excel(name = "学员姓名", sort = 1)
    private String traineeName;

    /**
     * 昵称       db_column: nick_name
     */
    private String nickName;

    /**
     * 性别       db_column: trainee_sex
     */
    //@Excel(name = "性别", sort = 3, dictType = "sex")
    private String traineeSex;

    /**
     * 手机号码       db_column: mobile
     */
    @Excel(name = "手机号码", sort = 2)
    private String mobile;

    /**
     * 状态       db_column: trainee_state
     */
    private String traineeState;

    /**
     * 在校专业       db_column: major
     */
    @Excel(name = "所学专业", sort = 6)
    private String major;

    /**
     * 就职公司       db_column: company
     */
    private String company;

    /**
     * 就读院校       db_column: trainee_school
     */
    @Excel(name = "所在学校", sort = 5)
    private String traineeSchool;

    /**
     * 证件照       db_column: trainee_pic
     */
    private String traineePic;
    /**
     * 头像       db_column: trainee_head
     */
    private String traineeHead;
    /**
     * qq号码       db_column: trainee_qq
     */
    private String traineeQq;
    /**
     * 学历       db_column: trainee_education
     */
    private String traineeEducation;
    /**
     * 用户邮箱       db_column: email
     */
    @Excel(name = "邮箱", sort = 4)
    private String email;

    /**
     * 用户类型       db_column: trainee_type
     */
    private String traineeType;
    /**
     * 总经验值       db_column: empirical_value
     */
    private Integer empiricalValue;
    /**
     * 博客总数量       db_column: blogs_num
     */
    private Integer blogsNum;
    /**
     * 开设课堂总数量       db_column: classroom_num
     */
    private Integer classroomNum;
    /**
     * 工号/学号       db_column: job_number
     */
    @Excel(name = "学号", sort = 7)
    private String jobNumber;

    /**
     * 粉丝数       db_column: fans_num
     */
    private Integer fansNum;

    /**
     * 关注数       db_column: follow_num
     */
    private Integer followNum;

    /**
     * 博客背景图       db_column: blog_bg_pic
     */
    private String blogBgPic;
    /**
     * 博客个人描述       db_column: blog_remark
     */
    private String blogRemark;

    /**
     * 班级id
     */
    private String classId;

    /**
     * 班级名称
     */
    @Excel(name = "所属班级", sort = 8)
    private String className;

    private String token;

    private Boolean ifTeacher;

    public TraineeInfoVO() {
    }

    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    public String getTraineeName() {
        return traineeName;
    }

    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTraineeSex() {
        return traineeSex;
    }

    public void setTraineeSex(String traineeSex) {
        this.traineeSex = traineeSex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTraineeState() {
        return traineeState;
    }

    public void setTraineeState(String traineeState) {
        this.traineeState = traineeState;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTraineeSchool() {
        return traineeSchool;
    }

    public void setTraineeSchool(String traineeSchool) {
        this.traineeSchool = traineeSchool;
    }

    public String getTraineePic() {
        return traineePic;
    }

    public void setTraineePic(String traineePic) {
        this.traineePic = traineePic;
    }

    public String getTraineeHead() {
        return traineeHead;
    }

    public void setTraineeHead(String traineeHead) {
        this.traineeHead = traineeHead;
    }

    public String getTraineeQq() {
        return traineeQq;
    }

    public void setTraineeQq(String traineeQq) {
        this.traineeQq = traineeQq;
    }

    public String getTraineeEducation() {
        return traineeEducation;
    }

    public void setTraineeEducation(String traineeEducation) {
        this.traineeEducation = traineeEducation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTraineeType() {
        return traineeType;
    }

    public void setTraineeType(String traineeType) {
        this.traineeType = traineeType;
    }

    public Integer getEmpiricalValue() {
        return empiricalValue;
    }

    public void setEmpiricalValue(Integer empiricalValue) {
        this.empiricalValue = empiricalValue;
    }

    public Integer getBlogsNum() {
        return blogsNum;
    }

    public void setBlogsNum(Integer blogsNum) {
        this.blogsNum = blogsNum;
    }

    public Integer getClassroomNum() {
        return classroomNum;
    }

    public void setClassroomNum(Integer classroomNum) {
        this.classroomNum = classroomNum;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Integer getFansNum() {
        return fansNum;
    }

    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
    }

    public String getBlogBgPic() {
        return blogBgPic;
    }

    public void setBlogBgPic(String blogBgPic) {
        this.blogBgPic = blogBgPic;
    }

    public String getBlogRemark() {
        return blogRemark;
    }

    public void setBlogRemark(String blogRemark) {
        this.blogRemark = blogRemark;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getIfTeacher() {
        return ifTeacher;
    }

    public void setIfTeacher(Boolean ifTeacher) {
        this.ifTeacher = ifTeacher;
    }

    @Override
    public String toString() {
        return "TraineeInfoVO{" +
                "traineeId='" + traineeId + '\'' +
                ", traineeName='" + traineeName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", traineeSex='" + traineeSex + '\'' +
                ", mobile='" + mobile + '\'' +
                ", traineeState='" + traineeState + '\'' +
                ", major='" + major + '\'' +
                ", company='" + company + '\'' +
                ", traineeSchool='" + traineeSchool + '\'' +
                ", traineePic='" + traineePic + '\'' +
                ", traineeHead='" + traineeHead + '\'' +
                ", traineeQq='" + traineeQq + '\'' +
                ", traineeEducation='" + traineeEducation + '\'' +
                ", email='" + email + '\'' +
                ", traineeType='" + traineeType + '\'' +
                ", empiricalValue=" + empiricalValue +
                ", blogsNum=" + blogsNum +
                ", classroomNum=" + classroomNum +
                ", jobNumber='" + jobNumber + '\'' +
                ", fansNum=" + fansNum +
                ", followNum=" + followNum +
                ", blogBgPic='" + blogBgPic + '\'' +
                ", blogRemark='" + blogRemark + '\'' +
                ", classId='" + classId + '\'' +
                ", className='" + className + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
