package com.example.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.example.entity.OrderStatus;
import com.example.entity.RegistrationRequest;
import com.example.mapper.OrderMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.entity.RefundRequest;
import com.example.mapper.RefundRequestMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefundRequestService {
    private final RefundRequestMapper refundRequestMapper;
    private final OrderMapper orderMapper;

    // 获取所有退款请求
    public List<RefundRequest> getAllRefundRequests() {
        return refundRequestMapper.selectAllRefundRequests();
    }

    // 根据 ID 获取退款请求
    public RefundRequest getRefundRequestById(Long refundId) {
        return refundRequestMapper.selectRefundRequestById(refundId);
    }

    public HttpStatus addRefundRequest(RefundRequest refundRequest) {
        var order = orderMapper.selectOrderById(refundRequest.getOrderId());
        if (order == null) return HttpStatus.NOT_FOUND;

        // 已完成的订单才能退款
        if (order.getStatus() != OrderStatus.COMPLETED) {
            return HttpStatus.BAD_REQUEST;
        }

        // 同一个订单只能存在一个待处理的取消请求
        var requests = refundRequestMapper.selectPendingRefundRequestByOrderId(refundRequest.getOrderId());
        if (!requests.isEmpty()) return HttpStatus.CONFLICT;

        // 计算价格
        var bookingTime = LocalDateTime.of(order.getBookingDate(), LocalTime.of(order.getStartHour(), 0));
        var duration = Duration.between(LocalDateTime.now(), bookingTime).toHours();

        if (duration >= 72)
            refundRequest.setRefundAmount(order.getPrice());
        else if (duration >= 48) {
            refundRequest.setRefundAmount(order.getPrice().multiply(BigDecimal.valueOf(0.75)));
        } else if (duration >= 24) {
            refundRequest.setRefundAmount(order.getPrice().multiply(BigDecimal.valueOf(0.25)));
        } else {
            return HttpStatus.NOT_ACCEPTABLE;
        }

        int rowsAffected = refundRequestMapper.insertRefundRequest(refundRequest);
        if (rowsAffected <= 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.CREATED;
        }
    }
}
