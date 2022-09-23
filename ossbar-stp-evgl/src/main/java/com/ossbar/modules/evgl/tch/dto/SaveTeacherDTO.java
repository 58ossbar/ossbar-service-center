package com.ossbar.modules.evgl.tch.dto;

import com.ossbar.common.validator.group.AddGroup;
import com.ossbar.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class SaveTeacherDTO implements Serializable {

    private java.lang.String teacherId;

    /**
     * 教师名称
     */
    @NotEmpty(message = "教师名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 50, message = "角色名称 长度不能超过50位", groups = {AddGroup.class, UpdateGroup.class})
    private java.lang.String teacherName;

    /**
     * 所属院校
     */
    @NotEmpty(message = "所属院校不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private java.lang.String orgId;

    /**
     * 所属岗位
     */
    private java.lang.String teacherPost;

    /**
     * 头像
     */
    @NotEmpty(message = "头像不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private java.lang.String teacherPic;

    /**
     * 账号名称(手机号码)
     */
    @NotEmpty(message = "账号名称(手机号码)不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 20, message = "账号名称 长度不能超过20位", groups = {AddGroup.class, UpdateGroup.class})
    private java.lang.String username;

    /**
     * 粉丝id
     */
    @NotEmpty(message = "关联粉丝id不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private java.lang.String traineeId;

    /**
     * 职业路径
     */
    @NotEmpty(message = "职业路径不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private java.lang.String majorId;

    /**
     * 首页显示 Y/N
     */
    @NotEmpty(message = "首页显示不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private java.lang.String showIndex;

    /**
     * 所在院系/二级部门
     */
    private java.lang.String orgIdDepartment;

    /**
     * 教师证件号
     */
    @NotEmpty(message = "证件号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 50, message = "证件号 长度不能超过50位", groups = {AddGroup.class, UpdateGroup.class})
    private java.lang.String teacherErtificateNumber;

    /**
     * 工号/学号
     */
    @NotEmpty(message = "工号/学号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 50, message = "证件号 长度不能超过50位", groups = {AddGroup.class, UpdateGroup.class})
    private java.lang.String jobNumber;

    /**
     * 性别
     */
    private java.lang.String sex;

    private Integer sortNum;

    /**
     * 简介
     */
    private java.lang.String teacherDesc;

    /**
     * 描述
     */
    private java.lang.String teacherRemark;

    private java.lang.String userId;


    /**
     * 附件id
     */
    private java.lang.String teacherPicAttachId;

    public SaveTeacherDTO() {
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getTeacherPost() {
        return teacherPost;
    }

    public void setTeacherPost(String teacherPost) {
        this.teacherPost = teacherPost;
    }

    public String getTeacherPic() {
        return teacherPic;
    }

    public void setTeacherPic(String teacherPic) {
        this.teacherPic = teacherPic;
    }

    public String getTeacherDesc() {
        return teacherDesc;
    }

    public void setTeacherDesc(String teacherDesc) {
        this.teacherDesc = teacherDesc;
    }

    public String getTeacherRemark() {
        return teacherRemark;
    }

    public void setTeacherRemark(String teacherRemark) {
        this.teacherRemark = teacherRemark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(String showIndex) {
        this.showIndex = showIndex;
    }

    public String getOrgIdDepartment() {
        return orgIdDepartment;
    }

    public void setOrgIdDepartment(String orgIdDepartment) {
        this.orgIdDepartment = orgIdDepartment;
    }

    public String getTeacherErtificateNumber() {
        return teacherErtificateNumber;
    }

    public void setTeacherErtificateNumber(String teacherErtificateNumber) {
        this.teacherErtificateNumber = teacherErtificateNumber;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTeacherPicAttachId() {
        return teacherPicAttachId;
    }

    public void setTeacherPicAttachId(String teacherPicAttachId) {
        this.teacherPicAttachId = teacherPicAttachId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String toString() {
        return "SaveTeacherDTO{" +
                "teacherId='" + teacherId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", teacherPost='" + teacherPost + '\'' +
                ", teacherPic='" + teacherPic + '\'' +
                ", teacherDesc='" + teacherDesc + '\'' +
                ", teacherRemark='" + teacherRemark + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", traineeId='" + traineeId + '\'' +
                ", majorId='" + majorId + '\'' +
                ", showIndex='" + showIndex + '\'' +
                ", orgIdDepartment='" + orgIdDepartment + '\'' +
                ", teacherErtificateNumber='" + teacherErtificateNumber + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", sex='" + sex + '\'' +
                ", teacherPicAttachId='" + teacherPicAttachId + '\'' +
                '}';
    }
}
