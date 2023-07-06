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

public class TevglBookpkgTeam extends BaseDomain<TevglBookpkgTeam> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglBookpkgTeam";
	public static final String ALIAS_TEAM_ID = "资源共建主键ID";
	public static final String ALIAS_RES_TYPE = "资源类型(课程教材or教学包)";
	public static final String ALIAS_SUBJECT_ID = "课程教材ID";
	public static final String ALIAS_PGK_ID = "教学包ID";
	public static final String ALIAS_USER_ID = "共建人";
	public static final String ALIAS_IS_SUBEDITOR = "是否为副主编(Y/N)";
	

    /**
     * 资源共建主键ID       db_column: team_id 
     */	
 	//@NotNull(msg="资源共建主键ID不能为空")
 	//@MaxLength(value=32, msg="字段[资源共建主键ID]超出最大长度[32]")
	private String teamId;
    /**
     * 资源类型(课程教材or教学包)       db_column: res_type 
     */	
 	//@NotNull(msg="资源类型(课程教材or教学包)不能为空")
 	//@MaxLength(value=32, msg="字段[资源类型(课程教材or教学包)]超出最大长度[32]")
	private String resType;
    /**
     * 课程教材ID       db_column: subject_id 
     */	
 	//@NotNull(msg="课程教材ID不能为空")
 	//@MaxLength(value=32, msg="字段[课程教材ID]超出最大长度[32]")
	private String subjectId;
    /**
     * 教学包ID       db_column: pgk_id 
     */	
 	//@NotNull(msg="教学包ID不能为空")
 	//@MaxLength(value=32, msg="字段[教学包ID]超出最大长度[32]")
	private String pgkId;
    /**
     * 共建人       db_column: user_id 
     */	
 	//@NotNull(msg="共建人不能为空")
 	//@MaxLength(value=32, msg="字段[共建人]超出最大长度[32]")
	private String userId;
    /**
     * 是否为副主编(Y/N)       db_column: is_subeditor 
     */	
 	//@NotNull(msg="是否为副主编(Y/N)不能为空")
 	//@MaxLength(value=2, msg="字段[是否为副主编(Y/N)]超出最大长度[2]")
	private String isSubeditor;
	//columns END

	public TevglBookpkgTeam(){
	}

	public TevglBookpkgTeam(
		String teamId
	){
		this.teamId = teamId;
	}

	public void setTeamId(String value) {
		this.teamId = value;
	}
	public String getTeamId() {
		return this.teamId;
	}
	public void setResType(String value) {
		this.resType = value;
	}
	public String getResType() {
		return this.resType;
	}
	public void setSubjectId(String value) {
		this.subjectId = value;
	}
	public String getSubjectId() {
		return this.subjectId;
	}
	public void setPgkId(String value) {
		this.pgkId = value;
	}
	public String getPgkId() {
		return this.pgkId;
	}
	public void setUserId(String value) {
		this.userId = value;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setIsSubeditor(String value) {
		this.isSubeditor = value;
	}
	public String getIsSubeditor() {
		return this.isSubeditor;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}

