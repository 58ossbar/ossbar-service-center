package com.ossbar.common.exception;


import com.ossbar.common.constants.ExecStatus;

/**
 * 表单重复提交异常类
 */
public class RepeatedSubmitFormException extends CreatorblueException{

    private static final long serialVersionUID = 5341547962077784610L;
    public RepeatedSubmitFormException(){
        this(ExecStatus.FORM_REP_REPEATED_SUBMIT);
    }

    public RepeatedSubmitFormException(String msg) {
        super(msg);
    }

    public RepeatedSubmitFormException(int code,String msg) {
       super(code,msg);
    }

    public RepeatedSubmitFormException(int code,String msg,Throwable e) {
        super(code,msg,e);
    }

    public RepeatedSubmitFormException(ExecStatus status){
        super(status.getCode(),status.getMsg());
    }
}
