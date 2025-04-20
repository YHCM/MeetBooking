package com.example.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MeetingRoom {
    private Long roomId;    // 会议室 ID
    private String roomName;    // 会议室名称
    private RoomType roomType;  // 会议室类型
    private Integer capacity;   // 会议室容量
    private BigDecimal basePrice;   // 会议室基础价格
    private Boolean roomStatus;     // 会议室状态（可用 、 不可用）
    private LocalDateTime createdAt;    // 创建时间
}
