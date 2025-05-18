package com.example.controller.advice;

import com.example.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理所有异常
    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e) {
        String message = e.getClass().getName() + ": " + e.getMessage();
        log.error(message);
        return Result.create(HttpStatus.INTERNAL_SERVER_ERROR, "server error", null);
    }
}
