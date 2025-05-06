package com.example.model;

import java.time.LocalDate;
import java.util.List;

import com.example.entity.RoomType;

import lombok.Data;

// 对会议室进行筛选
@Data
public class SearchRoomRequest {
    private RoomType roomType;      // 会议室类型
    private LocalDate date;         // 日期
    private Integer attendance;     // 参会人数
    private Byte startTime;      // 开始时间，整数
    private Byte endTime;        // 结束时间，整数
    private List<Long> requiredEquipments;        // 所需使用的设备的ID
}
