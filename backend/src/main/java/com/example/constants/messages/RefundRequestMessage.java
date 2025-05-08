package com.example.constants.messages;

import org.springframework.http.HttpStatus;

import java.util.Map;

public final class RefundRequestMessage {
    public static final Map<HttpStatus, String> CREATE_MESSAGES = Map.of(
            HttpStatus.CREATED, "退款请求添加成功",
            HttpStatus.NOT_FOUND, "订单不存在",
            HttpStatus.CONFLICT, "该订单正在退款",
            HttpStatus.BAD_REQUEST, "订单未完成，不能退款",
            HttpStatus.NOT_ACCEPTABLE, "请提前24小时退款",
            HttpStatus.INTERNAL_SERVER_ERROR, "退款请求添加失败"
    );

    public static final Map<HttpStatus, String> UPDATE_MESSAGES = Map.of(
            HttpStatus.OK, "处理成功",
            HttpStatus.NOT_FOUND, "请求不存在",
            HttpStatus.BAD_REQUEST, "错误的状态修改",
            HttpStatus.FORBIDDEN, "权限不足",
            HttpStatus.INTERNAL_SERVER_ERROR, "处理失败"
    );
}
