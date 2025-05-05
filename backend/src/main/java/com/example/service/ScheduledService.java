package com.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledService {
    private final OrderService orderService;

    // 自动删除过期的订单(30分钟内未支付)
    @Scheduled(initialDelay = 3000, fixedDelay = 60 * 1000)
    public void deleteExpired() {
        var orderIds = orderService.selectExpiredOrderIds();
        for (var id : orderIds) {
            orderService.cancel(id);
        }
    }
}
