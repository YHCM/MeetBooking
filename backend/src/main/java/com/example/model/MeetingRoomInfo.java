package com.example.model;

import com.example.entity.Equipment;
import com.example.entity.RoomType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeetingRoomInfo {
    private Long roomId;    // 会议室 ID
    private String roomName;    // 会议室名称
    private RoomType roomType;  // 会议室类型
    private Integer capacity;   // 会议室容量
    private BigDecimal basePrice;   // 会议室基础价格
    private Boolean roomStatus;     // 会议室状态（可用 、 不可用）
    private LocalDateTime createdAt;    // 创建时间
    private List<Equipment> equipments; //会议室配置的设备
    private BigDecimal price; // 每小时租赁价格
}
