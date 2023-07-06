package com.ossbar.modules.evgl.forum.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public class TevglForumFriend extends BaseDomain<TevglForumFriend> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglForumFriend";
	public static final String ALIAS_FRIEND_ID = "主键ID";
	public static final String ALIAS_FRIEND_NAME = "版块名称";
	public static final String ALIAS_FRIEND_TYPE = "版块分类";
	public static final String ALIAS_FRIEND_SUMMARY = "版块简介";
	public static final String ALIAS_FRIEND_URL = "网络链接";
	public static final String ALIAS_FRIEND_LOGO = "版块缩略图";
	public static final String ALIAS_STATE = "状态(Y启用N禁用)";
	public static final String ALIAS_BLOCK_ID = "板块ID";
	public static final String ALIAS_SORT_NUM = "排序号";
	public static final String ALIAS_SHOW_INDEX = "是否博客首页显示(Y/N)";
	

    /**
     * 主键ID       db_column: friend_id 
     */	
 	//@NotNull(msg="主键ID不能为空")
 	//@MaxLength(value=32, msg="字段[主键ID]超出最大长度[32]")
	private String friendId;
    /**
     * 版块名称       db_column: friend_name 
     */	
 	//@NotNull(msg="版块名称不能为空")
 	//@MaxLength(value=50, msg="字段[版块名称]超出最大长度[50]")
	private String friendName;
    /**
     * 版块分类       db_column: friend_type 
     */	
 	////@NotNull(msg="版块分类不能为空")
 	//@MaxLength(value=32, msg="字段[版块分类]超出最大长度[32]")
	private String friendType;
 	private String friendTypeName;
    /**
     * 版块简介       db_column: friend_summary 
     */	
 	////@NotNull(msg="版块简介不能为空")
 	//@MaxLength(value=2000, msg="字段[版块简介]超出最大长度[2000]")
	private String friendSummary;
    /**
     * 网络链接       db_column: friend_url 
     */	
 	//@NotNull(msg="网络链接不能为空")
 	//@MaxLength(value=200, msg="字段[网络链接]超出最大长度[200]")
	private String friendUrl;
    /**
     * 版块缩略图       db_column: friend_logo 
     */	
 	////@NotNull(msg="版块缩略图不能为空")
 	//@MaxLength(value=1000, msg="字段[版块缩略图]超出最大长度[1000]")
	private String friendLogo;
 	private String friendLogoAttachId;
    /**
     * 状态(Y启用N禁用)       db_column: state 
     */	
 	////@NotNull(msg="状态(Y启用N禁用)不能为空")
 	//@MaxLength(value=5, msg="字段[状态(Y启用N禁用)]超出最大长度[5]")
	private String state;
    /**
     * 板块ID       db_column: block_id 
     */	
 	////@NotNull(msg="板块ID不能为空")
 	//@MaxLength(value=32, msg="字段[板块ID]超出最大长度[32]")
	private String blockId;
 	 /**
     * 排序号       db_column: sort_num 
     */	
 	////@NotNull(msg="排序号不能为空")
 	//@MaxLength(value=10, msg="字段[排序号]超出最大长度[10]")
	private Integer sortNum;
    /**
     * 是否博客首页显示(Y/N)       db_column: show_index 
     */	
 	////@NotNull(msg="是否博客首页显示(Y/N)不能为空")
 	//@MaxLength(value=2, msg="字段[是否博客首页显示(Y/N)]超出最大长度[2]")
	private String showIndex;
 	//columns END

	public TevglForumFriend(){
	}

	public TevglForumFriend(
		String friendId
	){
		this.friendId = friendId;
	}
	public void setFriendId(String value) {
		this.friendId = value;
	}
	public String getFriendId() {
		return this.friendId;
	}
	public void setFriendName(String value) {
		this.friendName = value;
	}
	public String getFriendName() {
		return this.friendName;
	}
	public void setFriendType(String value) {
		this.friendType = value;
	}
	public String getFriendType() {
		return this.friendType;
	}
	public void setFriendSummary(String value) {
		this.friendSummary = value;
	}
	public String getFriendSummary() {
		return this.friendSummary;
	}
	public void setFriendUrl(String value) {
		this.friendUrl = value;
	}
	public String getFriendUrl() {
		return this.friendUrl;
	}
	public void setFriendLogo(String value) {
		this.friendLogo = value;
	}
	public String getFriendLogo() {
		return this.friendLogo;
	}
	public void setState(String value) {
		this.state = value;
	}
	public String getState() {
		return this.state;
	}
	public void setBlockId(String value) {
		this.blockId = value;
	}
	public String getBlockId() {
		return this.blockId;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public String getFriendLogoAttachId() {
		return friendLogoAttachId;
	}

	public void setFriendLogoAttachId(String friendLogoAttachId) {
		this.friendLogoAttachId = friendLogoAttachId;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(String showIndex) {
		this.showIndex = showIndex;
	}

	public String getFriendTypeName() {
		return friendTypeName;
	}

	public void setFriendTypeName(String friendTypeName) {
		this.friendTypeName = friendTypeName;
	}

}

