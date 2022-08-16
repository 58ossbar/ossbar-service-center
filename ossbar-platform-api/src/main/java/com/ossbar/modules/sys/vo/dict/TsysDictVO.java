package com.ossbar.modules.sys.vo.dict;

import java.io.Serializable;

/**
 * @author huj
 * @create 2022-08-16 15:57
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class TsysDictVO implements Serializable {

    private String dictId;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 字典图标
     */
    private String dictUrl;

    public TsysDictVO() {
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
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

    public String getDictUrl() {
        return dictUrl;
    }

    public void setDictUrl(String dictUrl) {
        this.dictUrl = dictUrl;
    }

    @Override
    public String toString() {
        return "TsysDictVO{" +
                "dictId='" + dictId + '\'' +
                ", dictCode='" + dictCode + '\'' +
                ", dictValue='" + dictValue + '\'' +
                ", dictUrl='" + dictUrl + '\'' +
                '}';
    }
}
