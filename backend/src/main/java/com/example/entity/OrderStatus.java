package com.example.entity;

// 订单状态
public enum OrderStatus {
    PENDING,        // 待处理(待支付，默认状态)
    CANCELLED,      // 已取消(超时未支付或主动取消)
    COMPLETED,      // 已完成(已支付并使用)
    REFUNDED       // 已退款(订单失效)
}