package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.RefundRequest;
import com.example.mapper.RefundRequestMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefundRequestService {
    private final RefundRequestMapper refundRequestMapper;

    // 获取所有退款请求
    public List<RefundRequest> getAllRefundRequests() {
        return refundRequestMapper.selectAllRefundRequests();
    }

    // 根据 ID 获取退款请求
    public RefundRequest getRefundRequestById(Long refundId) {
        return refundRequestMapper.selectRefundRequestById(refundId);
    }
}
