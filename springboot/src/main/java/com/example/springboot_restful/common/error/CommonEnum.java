package com.example.springboot_restful.common.error;

/**
 * common error enum class
 */
public enum CommonEnum implements BaseErrorInfoInterface {
    SUCCESS(200, "Success"),
    PARAMS_ERROR(40000, "Request parameter error"),
    SIGNATURE_NOT_MATCH(40100, "Digital signature doesn't match!"),
    NOT_FOUND(40400, "Resource not found!"),
    INTERNAL_SERVER_ERROR(50000, "Internal server error!"),
    SERVER_BUSY(50300, "Server is busy, please try again later!"),
    OTHER_SERVICE_ERROR(60000, "Other business exception"),
    NULLPOINTER_ERROR(100100,"Null pointer exception");


    private final int code;
    private final String message;

    CommonEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public int getResultCode() {
        return code;
    }

    @Override
    public String getResultMsg() {
        return message;
    }
}
