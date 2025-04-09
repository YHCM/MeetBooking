package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.RegistrationRequest;
import com.example.entity.RequestStatus;
import com.example.model.Result;
import com.example.service.RegistrationRequestService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/requests")
public class RegistrationRequestController {
    private final RegistrationRequestService registrationRequestService;

    public RegistrationRequestController(RegistrationRequestService registrationRequestService) {
        this.registrationRequestService = registrationRequestService;
    }

    @Operation(summary = "获取所有注册请求")
    @GetMapping
    public Result<List<RegistrationRequest>> getAllRegistrationRequests() {
        List<RegistrationRequest> registrationRequestList = registrationRequestService.getAllRegistrationRequests();
        return Result.ok("所有注册请求", registrationRequestList);
    }

    @Operation(summary = "根据 ID 获取请求")
    @GetMapping("/{requestId}")
    public Result<RegistrationRequest> getRegistrationRequestById(@PathVariable Long requestId) {
        RegistrationRequest registrationRequest = registrationRequestService.getRegistrationRequestById(requestId);

        return Result.ok("注册请求信息", registrationRequest);
    }

    @Operation(summary = "根据请求状态获取请求")
    @GetMapping("/status/{requestStatus}")
    public Result<List<RegistrationRequest>> getRegistrationRequestsByStatus(@PathVariable RequestStatus requestStatus) {
        List<RegistrationRequest> registrationRequestsList = registrationRequestService.getRegistrationRequestsByStatus(requestStatus);

        return Result.ok(requestStatus + " 状态的所有请求", registrationRequestsList);
    }
    
    @Operation(summary = "添加一个注册请求")
    @PostMapping
    public Result<Boolean> addRegistrationRequest(@RequestBody RegistrationRequest registrationRequest) {
        boolean addStatus = registrationRequestService.addRegistrationRequest(registrationRequest);
        
        if (addStatus) {
            return Result.create(HttpStatus.CREATED, "注册请求添加成功", Boolean.TRUE);
        } else {
            return Result.create(HttpStatus.CONFLICT, "注册请求添加失败", Boolean.FALSE);
        }
    }

    @Operation(summary = "处理一个请求")
    @PutMapping("/{requestId}/status")
    // 这里 adminId 应该要通过会话获取，先暂时这样，之后改
    public Result<Boolean> processRegistrationRequest(@PathVariable Long requestId, @RequestBody RequestStatus requestStatus, @RequestParam Long adminId) {
        boolean processStatus = registrationRequestService.processRegistrationRequest(requestId, adminId, requestStatus);

        if (processStatus) {
            return Result.ok("处理成功", Boolean.TRUE);
        } else {
            return Result.create(HttpStatus.INTERNAL_SERVER_ERROR, "处理失败", Boolean.FALSE);
        }
    }

    /*
     * 400 Bad Request：请求的数据无效或格式错误。
     * 409 Conflict：数据冲突（如违反唯一性约束）。
     * 500 Internal Server Error：服务器内部错误，可能是数据库故障。
     * 403 Forbidden：权限不足，用户无权执行更新。
     * 404 Not Found：资源未找到，尝试更新一个不存在的记录。
     */
}
