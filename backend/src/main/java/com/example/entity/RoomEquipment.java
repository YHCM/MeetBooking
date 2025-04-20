package com.example.entity;

import lombok.Data;

@Data
public class RoomEquipment {
    private Long roomId;        // 会议室 ID
    private Long equipmentId;   // 设备 ID
}
