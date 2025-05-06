package com.example.mapper;

import com.example.entity.Order;
import com.example.entity.OrderStatus;

import java.util.List;

public interface OrderMapper {
    // 获取所有订单
    List<Order> selectAllOrders();

    // 根据ID获取订单
    Order selectOrderById(Long orderId);

    // 根据用户ID获取订单
    List<Order> selectOrderByUserId(Long userId);

    // 获取已过期未支付订单的ID
    List<Long> selectExpiredOrderIds();

    // 添加订单
    int insertOrder(Order order);

    // 修改订单状态
    int updateOrderStatus(Long orderId, OrderStatus status);
}
