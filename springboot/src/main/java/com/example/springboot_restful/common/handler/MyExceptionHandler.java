package com.example.springboot_restful.common.handler;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.common.error.CommonEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕捉类
 * 出现异常，仍然先前端进行响应返回数据
 */
@RestControllerAdvice
public class MyExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    /**
     * 自定义的业务异常（自己向外抛出的异常）
     * @param ex 自定义抛出异常
     * @return xyz.changlu.util.ResultBody
     */
    @ExceptionHandler(value = MsgException.class)
    public ResultBody msgExceptionHandler(HttpServletRequest request, MsgException ex){
        logger.error("自定义的业务异常拦截处理，原因是：" + ex.getErrorMsg());
        return ResultBody.error(ex.getErrorCode(),ex.getErrorMsg());
    }

    /**
     * 空指针异常处理程序
     *
     * @param req 要求的事情
     * @param e   e
     * @return {@link ResultBody}
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResultBody exceptionHandler(HttpServletRequest req, NullPointerException e) {
        logger.error("空指针异常处理！原因是:", e.getMessage());
        return ResultBody.error(CommonEnum.NULLPOINTER_ERROR);
    }

    /**
     * 其他异常处理程序
     *
     * @param req 要求的事情
     * @param e   e
     * @return {@link ResultBody}
     */
    @ExceptionHandler(value = Exception.class)
    public ResultBody exceptionHandler(HttpServletRequest req, Exception e) {
        logger.error("其他异常处理程序！原因是:", e.getMessage());
        return ResultBody.error(CommonEnum.INTERNAL_SERVER_ERROR);
    }
}
