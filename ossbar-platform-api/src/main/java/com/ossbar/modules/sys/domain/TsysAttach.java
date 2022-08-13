package com.ossbar.modules.sys.domain;

//import org.hibernate.validator.constraints.NotBlank;
//import com.creatorblue.common.cbsecurity.validator.group.AddGroup;
//import com.creatorblue.common.cbsecurity.validator.group.UpdateGroup;

import com.ossbar.core.baseclass.domain.BaseDomain;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * </p>
 * <p>
 * Company:creatorblue.co.,ltd
 * </p>
 *
 * @author zhujw
 * @version 1.0
 */

public class TsysAttach extends BaseDomain<Object> {
	private static final long serialVersionUID = 1L;

	// alias
	public static final String TABLE_ALIAS = "TsysAttach";
	public static final String ALIAS_ATTACH_ID = "attachId";
	public static final String ALIAS_UPLOAD_MAN = "上传人";
	public static final String ALIAS_UPLOAD_TIME = "上传时间";
	public static final String ALIAS_FILE_URL = "文件路径";
	public static final String ALIAS_FILE_NAME = "文件名称";
	public static final String ALIAS_REMARK = "文件备注";
	public static final String ALIAS_SECOND_NAME = "文件别名";
	public static final String ALIAS_FILE_TYPE = "附件分类";
	public static final String ALIAS_PKID = "关联ID";
	public static final String ALIAS_FILE_SIZE = "文件大小";
	public static final String ALIAS_FILE_SUFFIX = "文件后缀";
	public static final String ALIAS_LJ_URL = "链接地址";
	public static final String ALIAS_FILE_NO = "文件编号";
	public static final String ALIAS_FILE_STATE = "文件状态";
	public static final String ALIAS_FILE_ORERNO = "文件排序号";

	/**
	 * attachId db_column: ATTACH_ID
	 */
	// @NotBlank(message="attachId不能为空", groups = {AddGroup.class,
	// UpdateGroup.class})
	private String attachId;

	/**
	 * 文件路径 db_column: FILE_URL
	 */
	// @NotBlank(message="文件路径不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String fileUrl;
	/**
	 * 文件名称 db_column: FILE_NAME
	 */
	// @NotBlank(message="文件名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String fileName;
	/**
	 * 文件备注 db_column: REMARK
	 */
	// @NotBlank(message="文件备注不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String remark;
	/**
	 * 文件别名 db_column: SECOND_NAME
	 */
	// @NotBlank(message="文件别名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String secondName;
	/**
	 * 附件分类 db_column: FILE_TYPE
	 */
	// @NotBlank(message="附件分类不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String fileType;
	/**
	 * 关联ID db_column: PKID
	 */
	// @NotBlank(message="关联ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String pkid;
	/**
	 * 文件大小 db_column: FILE_SIZE
	 */
	// @NotBlank(message="文件大小不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String fileSize;
	/**
	 * 文件后缀 db_column: FILE_SUFFIX
	 */
	// @NotBlank(message="文件后缀不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String fileSuffix;
	/**
	 * 链接地址 db_column: LJ_URL
	 */
	// @NotBlank(message="链接地址不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String ljUrl;
	/**
	 * 文件编号 db_column: FILE_NO
	 */
	// @NotBlank(message="文件编号不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String fileNo;
	/**
	 * 文件状态 db_column: FILE_STATE
	 */
	// @NotBlank(message="文件状态不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String fileState;
	/**
	 * 文件排序号 db_column: FILE_ORERNO
	 */
	// @NotBlank(message="文件排序号不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer fileOrerno;
	// columns END

	public TsysAttach() {
	}

	public TsysAttach(String attachId) {
		this.attachId = attachId;
	}

	public void setAttachId(String value) {
		this.attachId = value;
	}

	public String getAttachId() {
		return this.attachId;
	}

	public void setFileUrl(String value) {
		this.fileUrl = value;
	}

	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileName(String value) {
		this.fileName = value;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setRemark(String value) {
		this.remark = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setSecondName(String value) {
		this.secondName = value;
	}

	public String getSecondName() {
		return this.secondName;
	}

	public void setFileType(String value) {
		this.fileType = value;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setPkid(String value) {
		this.pkid = value;
	}

	public String getPkid() {
		return this.pkid;
	}

	public void setFileSize(String value) {
		this.fileSize = value;
	}

	public String getFileSize() {
		return this.fileSize;
	}

	public void setFileSuffix(String value) {
		this.fileSuffix = value;
	}

	public String getFileSuffix() {
		return this.fileSuffix;
	}

	public void setLjUrl(String value) {
		this.ljUrl = value;
	}

	public String getLjUrl() {
		return this.ljUrl;
	}

	public void setFileNo(String value) {
		this.fileNo = value;
	}

	public String getFileNo() {
		return this.fileNo;
	}

	public void setFileState(String value) {
		this.fileState = value;
	}

	public String getFileState() {
		return this.fileState;
	}

	public void setFileOrerno(Integer value) {
		this.fileOrerno = value;
	}

	public Integer getFileOrerno() {
		return this.fileOrerno;
	}

}
