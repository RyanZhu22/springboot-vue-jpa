package com.example.springboot_restful.common;

import com.example.springboot_restful.common.error.BaseErrorInfoInterface;
import com.example.springboot_restful.common.error.CommonEnum;
import lombok.Data;

@Data
public class ResultBody {
    /**
     * 响应码
     */
    private String code;
    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private Object result;

    public ResultBody() {
    }

    /**
     * 响应码与响应结果封装
     */
    public ResultBody(BaseErrorInfoInterface baseErrorInfoInterface) {
        this.code = baseErrorInfoInterface.getResultCode();
        this.message = baseErrorInfoInterface.getResultMsg();
    }

    /**
     * 成功
     * @param data 数据
     * @return xyz.changlu.util.ResultBody
     */
    public static ResultBody success(Object data){
        ResultBody resultBody = new ResultBody(CommonEnum.SUCCESS);
        resultBody.setResult(data);
        return resultBody;
    }

    /**
     * 错误
     * @param baseErrorInfoInterface 枚举类
     * @return xyz.changlu.util.ResultBody
     */
    public static ResultBody error(BaseErrorInfoInterface baseErrorInfoInterface){
        ResultBody resultBody = new ResultBody(baseErrorInfoInterface);
        resultBody.setResult(null);
        return resultBody;
    }

    /**
     * 错误
     * @param code 状态码
     * @param message 描述信息
     * @return xyz.changlu.util.ResultBody
     */
    public static ResultBody error(String code,String message){
        ResultBody resultBody = new ResultBody();
        resultBody.setCode(code);
        resultBody.setMessage(message);
        resultBody.setResult(null);
        return resultBody;
    }

}
