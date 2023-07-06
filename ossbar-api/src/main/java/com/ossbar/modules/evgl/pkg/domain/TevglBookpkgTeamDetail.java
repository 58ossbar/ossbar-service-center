package com.ossbar.modules.evgl.pkg.domain;

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

public class TevglBookpkgTeamDetail extends BaseDomain<TevglBookpkgTeamDetail> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglBookpkgTeamDetail";
	public static final String ALIAS_DETAIL_ID = "明细主键ID";
	public static final String ALIAS_TEAM_ID = "资源共建主键ID";
	public static final String ALIAS_CHAPTER_ID = "章节ID";
	

    /**
     * 明细主键ID       db_column: detail_id 
     */	
 	//@NotNull(msg="明细主键ID不能为空")
 	//@MaxLength(value=32, msg="字段[明细主键ID]超出最大长度[32]")
	private String detailId;
    /**
     * 资源共建主键ID       db_column: team_id 
     */	
 	//@NotNull(msg="资源共建主键ID不能为空")
 	//@MaxLength(value=32, msg="字段[资源共建主键ID]超出最大长度[32]")
	private String teamId;
    /**
     * 章节ID       db_column: chapter_id 
     */	
 	//@NotNull(msg="章节ID不能为空")
 	//@MaxLength(value=32, msg="字段[章节ID]超出最大长度[32]")
	private String chapterId;
 	/**
     * 共建人       db_column: user_id 
     */	
 	//@NotNull(msg="共建人不能为空")
 	//@MaxLength(value=32, msg="字段[共建人]超出最大长度[32]")
	private String userId;
	//columns END

	public TevglBookpkgTeamDetail(){
	}

	public TevglBookpkgTeamDetail(
		String detailId
	){
		this.detailId = detailId;
	}

	public void setDetailId(String value) {
		this.detailId = value;
	}
	public String getDetailId() {
		return this.detailId;
	}
	public void setTeamId(String value) {
		this.teamId = value;
	}
	public String getTeamId() {
		return this.teamId;
	}
	public void setChapterId(String value) {
		this.chapterId = value;
	}
	public String getChapterId() {
		return this.chapterId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}

