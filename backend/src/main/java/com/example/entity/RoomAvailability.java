package com.example.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RoomAvailability {
    private Long roomId;    // 会议室 ID
    private LocalDate scheduleDate; // 日期
    private Integer hourStatus; // 可用状态
}
