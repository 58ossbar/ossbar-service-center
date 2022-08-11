package com.ossbar.common.exception;
/**
 * Title:自定义异常 Copyright: Copyright (c) 2017
 * Company:ossbar.co.,ltd
 * 
 * @author ossbar.co.,ltd
 * @version 1.0
 */
public class CreatorblueException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = 500;
    
    public CreatorblueException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public CreatorblueException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public CreatorblueException(int code,String msg) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public CreatorblueException(int code,String msg,  Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
