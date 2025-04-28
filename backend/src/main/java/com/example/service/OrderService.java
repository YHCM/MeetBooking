package com.example.service;

import com.example.entity.Order;
import com.example.entity.OrderStatus;
import com.example.mapper.MeetingRoomMapper;
import com.example.mapper.OrderMapper;
import com.example.util.Util;
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
    public HttpStatus createOrder(Order order) {
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

    public HttpStatus pay(Order order) {
        return HttpStatus.NOT_IMPLEMENTED;
    }

    public boolean changeOrderStatus(Long orderId, OrderStatus status) {
        return orderMapper.updateOrderStatus(orderId, status) > 0;
    }
}
