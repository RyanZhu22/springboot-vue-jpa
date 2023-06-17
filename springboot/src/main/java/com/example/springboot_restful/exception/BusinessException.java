package com.example.springboot_restful.exception;

import com.example.springboot_restful.common.error.ResultEnum;

/**
 * custom exception
 */
public class BusinessException extends RuntimeException {
    private int code;

    private String errMsg;

    public BusinessException(ResultEnum resultEnum){
        this.code = resultEnum.getCode();
        this.errMsg = resultEnum.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
