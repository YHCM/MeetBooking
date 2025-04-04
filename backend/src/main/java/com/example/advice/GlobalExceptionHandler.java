package com.example.advice;

import com.example.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // 处理所有异常
    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e) {
        return Result.create(HttpStatus.INTERNAL_SERVER_ERROR, "server error", null);
    }
}
