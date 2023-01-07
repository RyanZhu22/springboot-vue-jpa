package com.example.springboot_restful.common.error;

/**
 * 普通错误枚举类
 */
public enum CommonEnum implements BaseErrorInfoInterface {
    SUCCESS("200", "成功"),
    BODY_NOT_MATCH("400", "请求的数据格式不符"),
    SIGNATURE_NOT_MATCH("401", "请求的数字签名不匹配!"),
    OT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503", "服务器正忙，请稍后再试!"),
    NULLPOINTER_ERROR("1001","空指针异常");

    //错误码
    private String resultCode;

    //描述信息
    private String resultMsg;

    CommonEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}
