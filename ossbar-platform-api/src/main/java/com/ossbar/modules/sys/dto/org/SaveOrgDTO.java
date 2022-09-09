package com.ossbar.modules.sys.dto.org;

import com.ossbar.common.validator.group.AddGroup;
import com.ossbar.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author huj
 * @create 2022-09-09 10:31
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class SaveOrgDTO implements Serializable {

    /**
     * 机构ID db_column: ORG_ID
     */
    @NotNull(message = "修改必须指定id", groups = {UpdateGroup.class})
    @Null(message = "新增不能指定id，请不传或传null", groups = {AddGroup.class})
    private String orgId;
    /**
     * 机构排序ID db_column: ORG_SN
     */
    private Integer orgSn;
    /**
     * 机构名称 db_column: ORG_NAME
     */
    @NotEmpty(message="机构名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 50, message = "机构名称 长度不能超过50位", groups = {AddGroup.class, UpdateGroup.class})
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
     * 封面图 db_column: cover_pic
     */
    private String coverPic;
    /**
     * 机构描述 db_column: description
     */
    private String description;

    public SaveOrgDTO() {
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Integer getOrgSn() {
        return orgSn;
    }

    public void setOrgSn(Integer orgSn) {
        this.orgSn = orgSn;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgXzqm() {
        return orgXzqm;
    }

    public void setOrgXzqm(String orgXzqm) {
        this.orgXzqm = orgXzqm;
    }

    public String getOrgShowname() {
        return orgShowname;
    }

    public void setOrgShowname(String orgShowname) {
        this.orgShowname = orgShowname;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
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

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    public String getQp() {
        return qp;
    }

    public void setQp(String qp) {
        this.qp = qp;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SaveOrgDTO{" +
                "orgId='" + orgId + '\'' +
                ", orgSn=" + orgSn +
                ", orgName='" + orgName + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", orgXzqm='" + orgXzqm + '\'' +
                ", orgShowname='" + orgShowname + '\'' +
                ", parentId='" + parentId + '\'' +
                ", remark='" + remark + '\'' +
                ", orgType='" + orgType + '\'' +
                ", addr='" + addr + '\'' +
                ", zip='" + zip + '\'' +
                ", email='" + email + '\'' +
                ", leader='" + leader + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                ", mobile='" + mobile + '\'' +
                ", jp='" + jp + '\'' +
                ", qp='" + qp + '\'' +
                ", coverPic='" + coverPic + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
