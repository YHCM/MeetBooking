package com.example.service;

import com.example.entity.Order;
import com.example.entity.OrderStatus;
import com.example.mapper.MeetingRoomMapper;
import com.example.mapper.OrderMapper;
import com.example.util.Util;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final MeetingRoomMapper meetingRoomMapper;
    private final RoomAvailabilityService roomAvailabilityService;
    private final UserService userService;
    private final Util util;

    public List<Order> getAllOrders() {
        return orderMapper.selectAllOrders();
    }

    public Order getOrderById(Long orderId) {
        return orderMapper.selectOrderById(orderId);
    }

    public List<Order> getOrderByUserId(Long userId) {
        return orderMapper.selectOrderByUserId(userId);
    }

    @Transactional
    public HttpStatus createOrder(Order order, HttpSession httpSession) {
        var user = userService.getUserBySession(httpSession);
        order.setUserId(user.getUserId());

        var room = meetingRoomMapper.selectMeetingRoomInfoById(order.getRoomId());
        if (room == null)
            return HttpStatus.NOT_FOUND;

        // 判断时间段是否可用
        var hourStatus = roomAvailabilityService.getRoomAvailability(order.getRoomId(), order.getBookingDate());
        if (hourStatus == null || order.getStartHour() >= order.getEndHour())
            return HttpStatus.BAD_REQUEST;

        int mask = util.calculateTimeMask(order.getStartHour(), order.getEndHour());
        if ((hourStatus & mask) != 0)
            return HttpStatus.CONFLICT;

        // 计算价格
        BigDecimal duration = BigDecimal.valueOf(order.getEndHour() - order.getStartHour());
        order.setPrice(room.getPrice().multiply(duration));

        // 锁定会议室
        roomAvailabilityService.setLocked(order.getRoomId(), order.getBookingDate(), order.getStartHour(), order.getEndHour());

        int rowsAffected = orderMapper.insertOrder(order);
        if (rowsAffected <= 0) return HttpStatus.INTERNAL_SERVER_ERROR;
        return HttpStatus.CREATED;
    }

    // 支付订单
    public HttpStatus pay(Long orderId, HttpSession httpSession) {
        var order = orderMapper.selectOrderById(orderId);
        if (order == null)
            return HttpStatus.NOT_FOUND;

        // 判断是不是支付自己的订单
        var user = userService.getUserBySession(httpSession);
        if (!user.getUserId().equals(order.getUserId()))
            return HttpStatus.FORBIDDEN;

        // 判断订单状态
        if (order.getStatus() != OrderStatus.PENDING) // 不是待处理状态
            return HttpStatus.BAD_REQUEST;

        int rowsAffected = orderMapper.updateOrderStatus(orderId, OrderStatus.COMPLETED);
        if (rowsAffected <= 0) return HttpStatus.INTERNAL_SERVER_ERROR;
        return HttpStatus.OK;
    }

    // 取消订单
    @Transactional
    public HttpStatus cancel(Long orderId, HttpSession httpSession) {
        var order = orderMapper.selectOrderById(orderId);
        if (order == null)
            return HttpStatus.NOT_FOUND;

        // 判断是不是取消自己的订单，或是由系统调用
        if (httpSession != null) {
            var user = userService.getUserBySession(httpSession);
            if (!user.getUserId().equals(order.getUserId()))
                return HttpStatus.FORBIDDEN;
        }

        // 判断订单状态
        if (order.getStatus() != OrderStatus.PENDING) // 不是待处理状态
            return HttpStatus.BAD_REQUEST;

        // 设置订单状态
        orderMapper.updateOrderStatus(orderId, OrderStatus.CANCELLED);
        // 释放会议室
        roomAvailabilityService.setAvailable(order.getRoomId(), order.getBookingDate(), order.getStartHour(), order.getEndHour());

        return HttpStatus.OK;
    }

    // 取消订单，系统调用
    @Transactional
    public HttpStatus cancel(Long orderId) {
        return cancel(orderId, null);
    }

}
