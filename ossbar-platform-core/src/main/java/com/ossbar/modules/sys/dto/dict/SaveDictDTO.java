package com.ossbar.modules.sys.dto.dict;

import com.ossbar.common.validator.group.AddGroup;
import com.ossbar.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotEmpty;

/**
 * @author huj
 * @create 2022-09-05 10:57
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class SaveDictDTO extends SaveDictTypeDTO {

    /**
     * 字典编码 db_column: DICT_CODE
     */
    @NotEmpty(message="字典编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String dictCode;

    /**
     * 字典值 db_column: DICT_VALUE
     */
    @NotEmpty(message="字典值不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String dictValue;

    /**
     * 父分类
     */
    @NotEmpty(message="父分类不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String parentType;

    /**
     * 排序号 db_column: SORT_NUM
     */
    private Integer sortNum;

    /**
     * 是否默认值 db_column: isdefault
     */
    private String isdefault;

    public SaveDictDTO() {
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    @Override
    public String toString() {
        return "SaveDictDTO{" +
                "dictCode='" + dictCode + '\'' +
                ", dictValue='" + dictValue + '\'' +
                ", parentType='" + parentType + '\'' +
                ", sortNum=" + sortNum +
                ", isdefault='" + isdefault + '\'' +
                '}';
    }
}
