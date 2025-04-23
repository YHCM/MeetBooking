package com.example.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Order {
    private Long orderId;
    private Long userId;
    private Long roomId;
    private LocalDate scheduleDate;
    private Integer hours;
    private BigDecimal price;
}
