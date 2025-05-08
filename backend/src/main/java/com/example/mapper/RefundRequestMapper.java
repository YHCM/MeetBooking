package com.example.mapper;

import java.util.List;

import com.example.entity.RefundRequest;
import com.example.entity.RequestStatus;

public interface RefundRequestMapper {
    // 获取所有退款请求
    List<RefundRequest> selectAllRefundRequests();

    // 根据 ID 获取退款请求
    RefundRequest selectRefundRequestById(Long refundId);

    // 根据 用户ID 获取退款请求
    List<RefundRequest> selectRefundRequestByUserId(Long userId);

    // 根据 订单ID 获取 处理中的 退款请求
    List<RefundRequest> selectPendingRefundRequestByOrderId(Long orderId);

    // 添加一个退款请求
    int insertRefundRequest(RefundRequest refundRequest);

    // 处理一个退款请求
    int updateRefundRequestStatus(Long refundId, RequestStatus requestStatus, Long processedBy);
}
