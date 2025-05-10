package com.example.entity;

import com.example.model.NewOrderRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long orderId;       // 订单 ID
    private Long userId;        // 用户 ID
    private String userName;        // 用户名
    private Long roomId;        // 会议室 ID
    private String roomName;    // 会议室名称
    private LocalDate bookingDate;   // 预定日期
    private BigDecimal price;   // 价格
    private Byte startHour;     // 开始时间
    private Byte endHour;       // 结束时间
    private LocalDateTime createdAt; // 创建时间
    private OrderStatus status; // 订单状态

    public static Order fromOrderRequest(NewOrderRequest request) {
        var order = new Order();
        order.setRoomId(request.getRoomId());
        order.setBookingDate(request.getBookingDate());
        order.setStartHour(request.getStartHour());
        order.setEndHour(request.getEndHour());
        return order;
    }
}
