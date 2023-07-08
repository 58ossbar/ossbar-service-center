/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhujw
 * @version 1.0
 */

package com.ossbar.modules.sys.domain;
import com.ossbar.core.baseclass.domain.BaseDomain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class TsysPost extends BaseDomain<Object> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TsysPost";
	public static final String ALIAS_POST_ID = "岗位ID";
	public static final String ALIAS_POST_NAME = "岗位名称";
	public static final String ALIAS_REMARK = "岗位描述";
	public static final String ALIAS_POST_TYPE = "岗位类别:字典中定义";
	public static final String ALIAS_PARENT_POSTID = "父岗位ID";
	

    /**
     * 岗位ID       db_column: POST_ID 
     */	
	private String postId;
    /**
     * 岗位名称       db_column: POST_NAME 
     */	
	private String postName;
    /**
     * 岗位描述       db_column: REMARK 
     */	
	private String remark;
    /**
     * 岗位类别:字典中定义       db_column: POST_TYPE 
     */	
	private String postType;
    /**
     * 父岗位ID       db_column: PARENT_POSTID 
     */	
	private String parentPostid;
	
	private List<TsysPost> children;
	private String parentName; // 父岗位名称
	private Integer sort; // 排序号
	
	//columns END

	public TsysPost(){
	}

	public TsysPost(
		String postId
	){
		this.postId = postId;
	}

	public void setPostId(String value) {
		this.postId = value;
	}
	
	public String getPostId() {
		return this.postId;
	}
	public void setPostName(String value) {
		this.postName = value;
	}
	
	public String getPostName() {
		return this.postName;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setPostType(String value) {
		this.postType = value;
	}
	
	public String getPostType() {
		return this.postType;
	}
	public void setParentPostid(String value) {
		this.parentPostid = value;
	}
	
	public String getParentPostid() {
		return this.parentPostid;
	}
	
	@SuppressWarnings("rawtypes")
	private Set tuserPosts = new HashSet(0);
	public void setTuserPosts(Set<TuserPost> tuserPost){
		this.tuserPosts = tuserPost;
	}
	
	@SuppressWarnings("unchecked")
	public Set<TuserPost> getTuserPosts() {
		return tuserPosts;
	}
	
	@SuppressWarnings("rawtypes")
	private Set trolePosts = new HashSet(0);
	public void setTrolePosts(Set<TrolePost> trolePost){
		this.trolePosts = trolePost;
	}
	
	@SuppressWarnings("unchecked")
	public Set<TrolePost> getTrolePosts() {
		return trolePosts;
	}

	public List<TsysPost> getChildren() {
		return children;
	}

	public void setChildren(List<TsysPost> children) {
		this.children = children;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}

