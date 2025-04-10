package com.example.constants.messages;

import java.util.Map;

import org.springframework.http.HttpStatus;

// auth controller 的信息常量
public final class AuthMessage {
    // 登陆的信息
    public static final Map<HttpStatus, String> LOGIN_MESSAGES = Map.of(
        HttpStatus.OK, "登陆成功",
        HttpStatus.UNAUTHORIZED, "用户名或密码错误",
        HttpStatus.NOT_FOUND, "用户不存在"
    );

    // 注册的信息
    public static final Map<HttpStatus, String> REGISTER_MESSAGES = Map.of(
        HttpStatus.OK, "注册请求成功",
        HttpStatus.CONFLICT, "用户名已存在",
        HttpStatus.SERVICE_UNAVAILABLE, "注册失败"
    );
}
