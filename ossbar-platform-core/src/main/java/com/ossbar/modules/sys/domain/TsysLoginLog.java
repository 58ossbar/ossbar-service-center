package com.ossbar.modules.sys.domain;


//import org.hibernate.validator.constraints.NotBlank;
//import com.creatorblue.common.cbsecurity.validator.group.AddGroup;
//import com.creatorblue.common.cbsecurity.validator.group.UpdateGroup;
//import com.creatorblue.core.baseclass.domain.BaseDomain;

import com.ossbar.core.baseclass.domain.BaseDomain;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhujw
 * @version 1.0
 */

@SuppressWarnings("rawtypes")
public class TsysLoginLog extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TsysLoginLog";
	public static final String ALIAS_LOGINLOGID = "主键";
	public static final String ALIAS_LOGNAME = "日志名称";
	public static final String ALIAS_USERID = "管理员id";
	public static final String ALIAS_CREATETIME = "创建时间";
	public static final String ALIAS_SUCCEED = "是否执行成功";
	public static final String ALIAS_MESSAGE = "具体消息";
	public static final String ALIAS_IP = "登录ip";
	

    /**
     * 主键       db_column: loginlogid 
     */	
	//@NotBlank(message="主键不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String loginlogid;
    /**
     * 日志名称       db_column: logname 
     */	
	//@NotBlank(message="日志名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String logname;
    /**
     * 管理员id       db_column: userid 
     */	
	//@NotBlank(message="管理员id不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String userid;
    /**
     * 是否执行成功       db_column: succeed 
     */	
	//@NotBlank(message="是否执行成功不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String succeed;
    /**
     * 具体消息       db_column: message 
     */	
	//@NotBlank(message="具体消息不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String message;
    /**
     * 登录ip       db_column: ip 
     */	
	//@NotBlank(message="登录ip不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String ip;
	
	private String beginTime;
	private String endTime;
	//columns END

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public TsysLoginLog(){
	}

	public TsysLoginLog(
		String loginlogid
	){
		this.loginlogid = loginlogid;
	}

	public void setLoginlogid(String value) {
		this.loginlogid = value;
	}
	
	public String getLoginlogid() {
		return this.loginlogid;
	}
	public void setLogname(String value) {
		this.logname = value;
	}
	
	public String getLogname() {
		return this.logname;
	}
	public void setUserid(String value) {
		this.userid = value;
	}
	
	public String getUserid() {
		return this.userid;
	}
	public void setSucceed(String value) {
		this.succeed = value;
	}
	
	public String getSucceed() {
		return this.succeed;
	}
	public void setMessage(String value) {
		this.message = value;
	}
	
	public String getMessage() {
		return this.message;
	}
	public void setIp(String value) {
		this.ip = value;
	}
	
	public String getIp() {
		return this.ip;
	}

}

