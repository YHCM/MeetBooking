package com.example.constants.messages;

import java.util.Map;

import org.springframework.http.HttpStatus;

public final class UserMessage {
    public static final Map<HttpStatus, String> CREATE_MESSAGES = Map.of(
        HttpStatus.OK, "用户创建成功",
        HttpStatus.CONFLICT, "用户名已存在",
        HttpStatus.INTERNAL_SERVER_ERROR, "用户添加失败"
    );
}
