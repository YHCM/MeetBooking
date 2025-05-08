package com.example.controller;

import java.util.List;
import java.util.Map;

import com.example.constants.messages.RefundRequestMessage;
import com.example.constants.messages.RegistrationRequestMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "提交退款请求")
    @PostMapping
    public Result<Boolean> addRefundRequest(@RequestBody RefundRequest refundRequest) {
        var createStatus = refundRequestService.addRefundRequest(refundRequest);
        var statusMessages = RefundRequestMessage.CREATE_MESSAGES;
        var message = statusMessages.getOrDefault(createStatus, "退款请求添加失败");
        return Result.create(createStatus, message, createStatus.is2xxSuccessful());
    }
}
