package com.example.springboot_restful.common.handler;

import com.example.springboot_restful.common.error.BaseErrorInfoInterface;

/**
 * 自定义异常类
 */
public class MsgException extends RuntimeException {
    /**
     * 错误代码
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;

    public MsgException() {
    }

    public MsgException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public MsgException(String message,String errorCode, String errorMsg) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    //传入错误信息接口(如枚举类)
    public MsgException(BaseErrorInfoInterface baseErrorInfoInterface, Throwable cause){
        super(baseErrorInfoInterface.getResultCode(), cause);
        this.errorCode = baseErrorInfoInterface.getResultCode();
        this.errorMsg = baseErrorInfoInterface.getResultMsg();
    }

    public MsgException(Throwable cause, String errorCode, String errorMsg){
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    //set/get方法
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }
}
