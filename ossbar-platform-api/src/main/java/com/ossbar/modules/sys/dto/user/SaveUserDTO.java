package com.ossbar.modules.sys.dto.user;

import com.ossbar.common.validator.group.AddGroup;
import com.ossbar.common.validator.group.UpdateGroup;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author huj
 * @create 2022-08-25 15:19
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class SaveUserDTO implements Serializable {

    /**
     * 用户id
     */
    @NotNull(message = "修改必须指定id", groups = {UpdateGroup.class})
    @Null(message = "新增不能指定id，请不传或传null", groups = {AddGroup.class})
    private String userId;

    /**
     * 用户名，依此作为系统登录账号
     */
    @NotEmpty(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 20, message = "用户名长度不能超过20位", groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 用户真实姓名
     */
    @NotEmpty(message="用户真实姓名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 50, message = "用户真实姓名长度不能超过50位", groups = {AddGroup.class, UpdateGroup.class})
    private String userRealname;

    /**
     * 用户头像
     */
    private String userimg;

    /**
     * 附件id
     */
    private String userimgAttachId;

    /**
     * 手机号
     */
    @NotEmpty(message="手机号码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    //@Length(min = 11, max = 11, message = "手机号只能为11位")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String mobile;

    /**
     * 所属机构
     */
    @NotEmpty(message="所属机构不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 32, message = "所属机构长度不能超过32位", groups = {AddGroup.class, UpdateGroup.class})
    private String orgId;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证号码       db_column: user_card
     */
    private String userCard;

    /**
     * 邮政编码
     */
    private String zip;

    /**
     * 邮箱
     */
    @Email
    private String email;

    /**
     * 状态 0：禁用 1：正常
     */
    private String status;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 出生年月       db_column: birthday
     */
    private String birthday;

    /**
     * 家庭住址       db_column: native_place
     */
    private String nativePlace;

    /**
     * 民族       db_column: nation
     */
    private String nation;

    /**
     * 所属岗位
     */
    private String postId;

    /**
     * 所属岗位
     */
    private List<String> postIdList;

    /**
     * 所属副机构
     */
    private List<String> orgIdList;

    private List<String> roleIdList;

    public SaveUserDTO() {
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

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getUserimgAttachId() {
        return userimgAttachId;
    }

    public void setUserimgAttachId(String userimgAttachId) {
        this.userimgAttachId = userimgAttachId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public List<String> getPostIdList() {
        return postIdList;
    }

    public void setPostIdList(List<String> postIdList) {
        this.postIdList = postIdList;
    }

    public List<String> getOrgIdList() {
        return orgIdList;
    }

    public void setOrgIdList(List<String> orgIdList) {
        this.orgIdList = orgIdList;
    }

    public List<String> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }

    @Override
    public String toString() {
        return "SaveUserDTO{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", userRealname='" + userRealname + '\'' +
                ", userimg='" + userimg + '\'' +
                ", userimgAttachId='" + userimgAttachId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", orgId='" + orgId + '\'' +
                ", sex='" + sex + '\'' +
                ", userCard='" + userCard + '\'' +
                ", zip='" + zip + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", userType='" + userType + '\'' +
                ", birthday='" + birthday + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", nation='" + nation + '\'' +
                ", postId='" + postId + '\'' +
                ", postIdList=" + postIdList +
                ", orgIdList=" + orgIdList +
                ", roleIdList=" + roleIdList +
                '}';
    }
}
