package com.example.constants.messages;

import java.util.Map;

import org.springframework.http.HttpStatus;

public final class UserMessage {
    // 创建用户的消息
    public static final Map<HttpStatus, String> CREATE_MESSAGES = Map.of(
        HttpStatus.CREATED, "用户创建成功",
        HttpStatus.CONFLICT, "用户名已存在",
        HttpStatus.INTERNAL_SERVER_ERROR, "用户添加失败"
    );

    // 切换用户 冻结 / 解冻 的消息
    public static final Map<HttpStatus, String> TOGGLE_MESSAGES = Map.of(
        HttpStatus.OK, "处理成功",
        HttpStatus.FORBIDDEN, "权限不足",
        HttpStatus.NOT_FOUND, "管理员或用户不存在",
        HttpStatus.CONFLICT, "该用户不允许被冻结",
        HttpStatus.INTERNAL_SERVER_ERROR, "处理失败"
    );
}
