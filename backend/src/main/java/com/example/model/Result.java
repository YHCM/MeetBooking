package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> create(HttpStatus code, String message, T data) {
        return new Result<>(code.value(), message, data);
    }

    public static <T> Result<T> ok(String message, T data) {
        return new Result<>(HttpStatus.OK.value(), message, data);
    }
}
