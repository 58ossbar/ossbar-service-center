package com.ossbar.modules.sys.dto.dict;

import java.io.Serializable;

/**
 * @author huj
 * @create 2022-09-02 11:08
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class SaveDictTypeDTO implements Serializable {

    private String dictId;

    private String dictType;

    private String dictName;

    private String dictUrl;

    private String orgId;

    private String displaySort;

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
                ", orgId='" + orgId + '\'' +
                ", displaySort='" + displaySort + '\'' +
                ", displaying='" + displaying + '\'' +
                '}';
    }
}
