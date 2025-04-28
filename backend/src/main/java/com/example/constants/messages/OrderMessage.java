package com.example.constants.messages;

import org.springframework.http.HttpStatus;

import java.util.Map;

public final class OrderMessage {
    public static final Map<HttpStatus, String> CREATE_MESSAGES = Map.of(
            HttpStatus.CREATED, "订单提交成功",
            HttpStatus.NOT_FOUND, "会议室不存在",
            HttpStatus.CONFLICT, "预订时间不可用",
            HttpStatus.BAD_REQUEST, "请求参数有误",
            HttpStatus.INTERNAL_SERVER_ERROR, "订单提交失败"
    );
}
