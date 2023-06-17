package com.example.springboot_restful.common.error;

/**
 * 自定义枚举
 * 使用枚举统一管理错误信息
 */
public enum ResultEnum implements BaseErrorInfoInterface {
    UNKNOWN_ERROR(-100, "Unknown error"),
    NEED_LOGIN(-1, "Not logged in"),
    REPEAT_REGISTER(-2, "This user is registered"),
    USER_NOT_EXIST(-3, "The user does not exist"),
    NOT_EXIST(-7, "Database cannot query data by parameters"),
    PASSWORD_ERROR(-4, "Password error"),
    EMPTY_USERNAME(-5, "Username is empty"),
    EMPTY_PASSWORD(-6, "Password is empty"),
    SUCCESS(200, "success"),
    SYSTEM_ERROR(500,"Multiple clicks");

    private int code;

    private String msg;

    private ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public int getResultCode() {
        return code;
    }

    @Override
    public String getResultMsg() {
        return msg;
    }
}
