package com.ossbar.modules.evgl.trainee.vo;

import java.io.Serializable;

/**
 * 魅力信息
 * @author huj
 * @create 2022-09-15 14:38
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class TraineeCharmInfoVO implements Serializable {

    /**
     * 经验值
     */
    private Integer empiricalValue;

    /**
     * 累计开设的课堂数
     */
    private Integer cloudClassroomNum;

    /**
     * 累计学生数
     */
    private Integer studentNum;

    /**
     * 累计发布资源数
     */
    private Integer resourceNum;

    /**
     * 累计开展活动数
     */
    private Integer activityNum;

    /**
     * 个人总博客数
     */
    private Integer blogsNum;

    /**
     * 魅力值（等待公式计算）
     */
    private Integer charmValue;

    public TraineeCharmInfoVO() {
    }

    public Integer getEmpiricalValue() {
        return empiricalValue;
    }

    public void setEmpiricalValue(Integer empiricalValue) {
        this.empiricalValue = empiricalValue;
    }

    public Integer getCloudClassroomNum() {
        return cloudClassroomNum;
    }

    public void setCloudClassroomNum(Integer cloudClassroomNum) {
        this.cloudClassroomNum = cloudClassroomNum;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getResourceNum() {
        return resourceNum;
    }

    public void setResourceNum(Integer resourceNum) {
        this.resourceNum = resourceNum;
    }

    public Integer getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(Integer activityNum) {
        this.activityNum = activityNum;
    }

    public Integer getBlogsNum() {
        return blogsNum;
    }

    public void setBlogsNum(Integer blogsNum) {
        this.blogsNum = blogsNum;
    }

    public Integer getCharmValue() {
        return charmValue;
    }

    public void setCharmValue(Integer charmValue) {
        this.charmValue = charmValue;
    }

    @Override
    public String toString() {
        return "TraineeCharmInfoVO{" +
                "empiricalValue=" + empiricalValue +
                ", cloudClassroomNum=" + cloudClassroomNum +
                ", studentNum=" + studentNum +
                ", resourceNum=" + resourceNum +
                ", activityNum=" + activityNum +
                ", blogsNum=" + blogsNum +
                ", charmValue=" + charmValue +
                '}';
    }
}
