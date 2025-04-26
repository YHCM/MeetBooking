package com.example.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Order {
    private Long orderId;       // 订单 ID
    private Long userId;        // 用户 ID
    private Long roomId;        // 会议室 ID
    private LocalDate bookingDate;   // 预定日期
    private BigDecimal price;   // 价格
    private Byte startHour;     // 开始时间
    private Byte endHour;       // 结束时间
    private OrderStatus status; // 订单状态
}
