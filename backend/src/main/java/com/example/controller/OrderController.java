package com.example.controller;

import com.example.constants.messages.OrderMessage;
import com.example.entity.Order;
import com.example.model.Result;
import com.example.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "获取所有订单", description = """
            参数（可选）：userId: 根据根据用户ID获取订单
            """)
    @GetMapping
    public Result<List<Order>> getAllOrders(@RequestParam(value = "userId", required = false) Long userId) {
        if (userId != null)
            return Result.ok("订单信息", orderService.getOrderByUserId(userId));
        return Result.ok("所有订单", orderService.getAllOrders());
    }

    @Operation(summary = "根据ID获取订单")
    @GetMapping("/{orderId}")
    public Result<Order> getOrderById(@PathVariable Long orderId) {
        return Result.ok("订单信息", orderService.getOrderById(orderId));
    }

    @Operation(summary = "提交订单")
    @PostMapping
    public Result<Boolean> createOrder(@RequestBody Order order) {
        HttpStatus createStatus = orderService.createOrder(order);
        var statusMessages = OrderMessage.CREATE_MESSAGES;
        var message = statusMessages.getOrDefault(createStatus, "订单提交失败");
        return Result.create(createStatus, message, createStatus.is2xxSuccessful());
    }
}
