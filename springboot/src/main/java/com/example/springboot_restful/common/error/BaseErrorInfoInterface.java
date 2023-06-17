package com.example.springboot_restful.common.error;

/**
 * 错误码接口规范
 */
public interface BaseErrorInfoInterface {
    /**
     * 得到错误码
     * @date 2021/07/22 17:04
     * @return java.lang.String
     */
    int getResultCode();

    /**
     * 得到错误信息
     * @date 2021/07/22 17:05
     * @return java.lang.String
     */
    String getResultMsg();

}
