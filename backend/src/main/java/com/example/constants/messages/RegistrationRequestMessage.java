package com.example.constants.messages;

import java.util.Map;

import org.springframework.http.HttpStatus;

public final class RegistrationRequestMessage {
    // 添加请求的信息
    public static final Map<HttpStatus, String> CREATE_MESSAGES = Map.of(
        HttpStatus.OK, "注册请求添加成功",
        HttpStatus.CONFLICT, "用户名已存在",
        HttpStatus.INTERNAL_SERVER_ERROR, "注册请求添加失败"
    );

    // 处理请求的信息
    public static final Map<HttpStatus, String> PROCESS_MESSAGES = Map.of(
        HttpStatus.OK, "处理成功",
        HttpStatus.BAD_REQUEST, "错误的状态修改",
        HttpStatus.FORBIDDEN, "权限不足",
        HttpStatus.NOT_FOUND, "管理员或请求不存在",
        HttpStatus.CONFLICT, "这个请求不允许被修改",
        HttpStatus.INTERNAL_SERVER_ERROR, "处理失败"
    );
}
