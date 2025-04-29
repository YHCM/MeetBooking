package com.example.controller;

import com.example.constants.messages.OrderMessage;
import com.example.entity.Order;
import com.example.model.NewOrderRequest;
import com.example.model.Result;
import com.example.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
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
    public Result<Boolean> createOrder(@RequestBody NewOrderRequest request, HttpSession httpSession) {
        HttpStatus createStatus = orderService.createOrder(Order.fromOrderRequest(request), httpSession);
        var statusMessages = OrderMessage.CREATE_MESSAGES;
        var message = statusMessages.getOrDefault(createStatus, "订单提交失败");
        return Result.create(createStatus, message, createStatus.is2xxSuccessful());
    }

    @Operation(summary = "支付订单")
    @PatchMapping("/{orderId}/pay")
    public Result<Boolean> payOrder(@PathVariable Long orderId, HttpSession httpSession) {
        HttpStatus payStatus = orderService.pay(orderId, httpSession);
        var statusMessages = OrderMessage.PAY_MESSAGES;
        var message = statusMessages.getOrDefault(payStatus, "订单支付失败");
        return Result.create(payStatus, message, payStatus.is2xxSuccessful());
    }

    @Operation(summary = "取消订单")
    @PatchMapping("/{orderId}/cancel")
    public Result<Boolean> cancelOrder(@PathVariable Long orderId, HttpSession httpSession) {
        HttpStatus cancelStatus = orderService.cancel(orderId, httpSession);
        var statusMessages = OrderMessage.CANCEL_MESSAGES;
        var message = statusMessages.getOrDefault(cancelStatus, "订单取消失败");
        return Result.create(cancelStatus, message, cancelStatus.is2xxSuccessful());
    }
}
