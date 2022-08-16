package com.ossbar.modules.sys.vo.post;

import com.ossbar.common.validator.group.AddGroup;
import com.ossbar.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * @author huj
 * @create 2022-08-16 11:15
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class SavePostVO implements Serializable {

    /**
     * 岗位id
     */
    @NotNull(message = "修改必须指定id", groups = {UpdateGroup.class})
    @Null(message = "新增不能指定id", groups = {AddGroup.class})
    private String postId;

    /**
     * 岗位名称
     */
    @NotEmpty(message="岗位名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String postName;

    /**
     * 岗位类别:字典中定义       db_column: POST_TYPE
     */
    @NotEmpty(message="岗位类别不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String postType;

    /**
     * 岗位描述
     */
    private String remark;

    /**
     * 排序号
     */
    private Integer sort;

    public SavePostVO() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SavePostVO{" +
                "postId='" + postId + '\'' +
                ", postName='" + postName + '\'' +
                ", postType='" + postType + '\'' +
                ", remark='" + remark + '\'' +
                ", sort=" + sort +
                '}';
    }
}
