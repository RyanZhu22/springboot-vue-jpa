package com.example.springboot_restful.config;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.common.error.CommonEnum;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultBody handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors()
            .stream()
            .map(objectError -> objectError.getDefaultMessage())
            .collect(Collectors.joining(", "));
        return ResultBody.error(CommonEnum.PARAMS_ERROR.getResultCode(), errorMessage);
    }
}