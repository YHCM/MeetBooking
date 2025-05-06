package com.example.mapper;

import java.util.List;

import com.example.entity.RefundRequest;

public interface RefundRequestMapper {
    // 获取所有退款请求
    List<RefundRequest> selectAllRefundRequests();

    // 根据 ID 获取退款请求
    RefundRequest selectRefundRequestById(Long refundId);

    // 添加一个退款请求
    int insertRefundRequest(RefundRequest refundRequest);
}
