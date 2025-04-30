package com.example.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewOrderRequest {
    private Long roomId;        // 会议室 ID
    private LocalDate bookingDate;   // 预定日期
    private Byte startHour;     // 开始时间
    private Byte endHour;       // 结束时间
}
