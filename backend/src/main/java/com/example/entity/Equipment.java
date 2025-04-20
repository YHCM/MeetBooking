package com.example.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Equipment {
    private Long equipmentId;       // 设备类型 ID
    private String equipmentName;   // 设备名称
    private BigDecimal additionalPrice;     // 额外的价格
    private String description;     // 设备描述
}
