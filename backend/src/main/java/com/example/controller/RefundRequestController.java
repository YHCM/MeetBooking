package com.example.controller;

import java.util.List;

import com.example.constants.messages.RefundRequestMessage;
import jakarta.servlet.http.HttpSession;
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

    @Operation(summary = "获取所有退款请求", description = """
            参数（可选）：userId: 根据根据用户ID获取订单
            """)
    @GetMapping
    public Result<List<RefundRequest>> getAllRefundRequests(@RequestParam(value = "userId", required = false) Long userId) {
        List<RefundRequest> refundRequests;
        if (userId != null)
            refundRequests = refundRequestService.getRefundRequestByUserId(userId);
        else
            refundRequests = refundRequestService.getAllRefundRequests();
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

    @Operation(summary = "批准退款请求")
    @PatchMapping("/{refundId}/approve")
    public Result<Boolean> approveRefundRequest(@PathVariable Long refundId, HttpSession httpSession) {
        var updateStatus = refundRequestService.approve(refundId, httpSession);
        var statusMessages = RefundRequestMessage.UPDATE_MESSAGES;
        var message = statusMessages.getOrDefault(updateStatus, "处理失败");
        return Result.create(updateStatus, message, updateStatus.is2xxSuccessful());
    }

    @Operation(summary = "拒绝退款请求")
    @PatchMapping("/{refundId}/reject")
    public Result<Boolean> rejectRefundRequest(@PathVariable Long refundId, HttpSession httpSession) {
        var updateStatus = refundRequestService.reject(refundId, httpSession);
        var statusMessages = RefundRequestMessage.UPDATE_MESSAGES;
        var message = statusMessages.getOrDefault(updateStatus, "处理失败");
        return Result.create(updateStatus, message, updateStatus.is2xxSuccessful());
    }
}
