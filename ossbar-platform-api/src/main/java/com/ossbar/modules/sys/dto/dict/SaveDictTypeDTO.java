package com.ossbar.modules.sys.dto.dict;

import com.ossbar.common.validator.group.AddGroup;
import com.ossbar.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * @author huj
 * @create 2022-09-02 11:08
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class SaveDictTypeDTO implements Serializable {

    /**
     * 字典id
     */
    @NotNull(message = "修改必须指定id", groups = {UpdateGroup.class})
    @Null(message = "新增不能指定id，请不传或传null", groups = {AddGroup.class})
    private String dictId;

    /**
     * 字典分类编码
     */
    @NotEmpty(message="字典分类编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String dictType;

    /**
     * 字典分类名称
     */
    @NotEmpty(message="字典分类名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String dictName;

    /**
     * 字典图片
     */
    private String dictUrl;

    /**
     * 字典图片附件id
     */
    private String dictUrlAttachId;

    /**
     * 所属机构 db_column: ORG_ID
     */
    private String orgId;

    /**
     * 字典展现分类:下拉类型(select)树形(tree) 复选型(checkbox)单选radio
     */
    private String displaySort;

    /**
     * 是否显示 db_column: displaying
     */
    private String displaying;

    public SaveDictTypeDTO() {
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictUrl() {
        return dictUrl;
    }

    public void setDictUrl(String dictUrl) {
        this.dictUrl = dictUrl;
    }

    public String getDictUrlAttachId() {
        return dictUrlAttachId;
    }

    public void setDictUrlAttachId(String dictUrlAttachId) {
        this.dictUrlAttachId = dictUrlAttachId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDisplaySort() {
        return displaySort;
    }

    public void setDisplaySort(String displaySort) {
        this.displaySort = displaySort;
    }

    public String getDisplaying() {
        return displaying;
    }

    public void setDisplaying(String displaying) {
        this.displaying = displaying;
    }

    @Override
    public String toString() {
        return "SaveDictTypeDTO{" +
                "dictId='" + dictId + '\'' +
                ", dictType='" + dictType + '\'' +
                ", dictName='" + dictName + '\'' +
                ", dictUrl='" + dictUrl + '\'' +
                ", dictUrlAttachId='" + dictUrlAttachId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", displaySort='" + displaySort + '\'' +
                ", displaying='" + displaying + '\'' +
                '}';
    }
}
