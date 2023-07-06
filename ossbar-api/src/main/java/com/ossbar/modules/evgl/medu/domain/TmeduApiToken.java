package com.ossbar.modules.evgl.medu.domain;

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

public class TmeduApiToken extends BaseDomain<TmeduApiToken> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TmeduApiToken";
	public static final String ALIAS_TOKEN_ID = "tokenId";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_TOKEN = "token";
	public static final String ALIAS_EXPIRE_TIME = "过期时间";
	public static final String ALIAS_MOBILE = "手机号";
	public static final String ALIAS_SMS_CODE = "短信验证码";
	public static final String ALIAS_USER_TYPE = "用户类型，1、客户，2、系统用户";
	public static final String ALIAS_IMG_CODE = "图型验证码";
	public static final String ALIAS_OPENID = "微信用户唯一码";
	public static final String ALIAS_SESSION_KEY = "微信端会话";
	

    /**
     * tokenId       db_column: token_id 
     */	
 	//@NotBlank(message="tokenId不能为空", groups = {AddGroup.class, UpdateGroup.class})
	 private String tokenId;
    /**
     * userId       db_column: user_id 
     */	
 	//@NotBlank(message="userId不能为空", groups = {AddGroup.class, UpdateGroup.class})
	 private String userId;
    /**
     * token       db_column: token 
     */	
 	//@NotBlank(message="token不能为空", groups = {AddGroup.class, UpdateGroup.class})
	 private String token;
    /**
     * 过期时间       db_column: expire_time 
     */	
 	//@NotBlank(message="过期时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
	 private String expireTime;
    /**
     * 手机号       db_column: mobile 
     */	
 	//@NotBlank(message="手机号不能为空", groups = {AddGroup.class, UpdateGroup.class})
	 private String mobile;
    /**
     * 短信验证码       db_column: sms_code 
     */	
 	//@NotBlank(message="短信验证码不能为空", groups = {AddGroup.class, UpdateGroup.class})
	 private String smsCode;
    /**
     * 用户类型，1、客户，2、系统用户       db_column: user_type 
     */	
 	//@NotBlank(message="用户类型，1、客户，2、系统用户不能为空", groups = {AddGroup.class, UpdateGroup.class})
	 private String userType;
    /**
     * 图型验证码       db_column: img_code 
     */	
 	//@NotBlank(message="图型验证码不能为空", groups = {AddGroup.class, UpdateGroup.class})
	 private String imgCode;
    /**
     * 微信用户唯一码       db_column: openid 
     */	
 	//@NotBlank(message="微信用户唯一码不能为空", groups = {AddGroup.class, UpdateGroup.class})
	 private String openid;
    /**
     * 微信端会话       db_column: session_key 
     */	
 	//@NotBlank(message="微信端会话不能为空", groups = {AddGroup.class, UpdateGroup.class})
	 private String sessionKey;
	//columns END

	public TmeduApiToken(){
	}

	public TmeduApiToken(
		String tokenId
	){
		this.tokenId = tokenId;
	}

	public void setTokenId(String value) {
		this.tokenId = value;
	}
	public String getTokenId() {
		return this.tokenId;
	}
	public void setUserId(String value) {
		this.userId = value;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setToken(String value) {
		this.token = value;
	}
	public String getToken() {
		return this.token;
	}
	public void setExpireTime(String value) {
		this.expireTime = value;
	}
	public String getExpireTime() {
		return this.expireTime;
	}
	public void setMobile(String value) {
		this.mobile = value;
	}
	public String getMobile() {
		return this.mobile;
	}
	public void setSmsCode(String value) {
		this.smsCode = value;
	}
	public String getSmsCode() {
		return this.smsCode;
	}
	public void setUserType(String value) {
		this.userType = value;
	}
	public String getUserType() {
		return this.userType;
	}
	public void setImgCode(String value) {
		this.imgCode = value;
	}
	public String getImgCode() {
		return this.imgCode;
	}
	public void setOpenid(String value) {
		this.openid = value;
	}
	public String getOpenid() {
		return this.openid;
	}
	public void setSessionKey(String value) {
		this.sessionKey = value;
	}
	public String getSessionKey() {
		return this.sessionKey;
	}

}

