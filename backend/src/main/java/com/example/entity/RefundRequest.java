package com.example.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RefundRequest {
    private Long refundId;      // 退款请求 ID
    private Long orderId;       // 订单 ID
    private RequestStatus requestStatus;    // 请求状态
    private LocalDateTime createdAt;        // 退款请求创建时间
    private LocalDateTime processedAt;      // 退款请求处理时间
    private Long processedBy;   // 处理请求的员工或管理员
    private BigDecimal refundAmount;    // 实际的退款金额（根据原价和时间计算）
    private Order order;    // 订单
}
