package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.RefundRequest;
import com.example.model.Result;
import com.example.service.RefundRequestService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/refunds")
@RequiredArgsConstructor
public class RefundRequestController {
    private final RefundRequestService refundRequestService;

    @Operation(summary = "获取所有退款请求")
    @GetMapping
    public Result<List<RefundRequest>> getAllRefundRequests() {
        List<RefundRequest> refundRequests = refundRequestService.getAllRefundRequests();
        return Result.ok("所有退款请求", refundRequests);
    }

    @Operation(summary = "根据 ID 获取退款请求")
    @GetMapping("/{refundId}")
    public Result<RefundRequest> getRefundRequestById(@PathVariable Long refundId) {
        RefundRequest refundRequest = refundRequestService.getRefundRequestById(refundId);
        return Result.ok("退款请求信息", refundRequest);
    }
}
