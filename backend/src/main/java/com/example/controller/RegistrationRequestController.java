package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.RegistrationRequest;
import com.example.entity.RequestStatus;
import com.example.model.Result;
import com.example.service.RegistrationRequestService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RegistrationRequestController {
    private final RegistrationRequestService registrationRequestService;

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
    @PutMapping("/{requestId}/{requestStatus}")
    public Result<Boolean> processRegistrationRequest(@PathVariable Long requestId, @PathVariable RequestStatus requestStatus, HttpSession session) {
        HttpStatus processStatus = registrationRequestService.processRegistrationRequest(requestId, requestStatus, session);

        Map<HttpStatus, String> statusMessages = new HashMap<>();
        statusMessages.put(HttpStatus.OK, "处理成功");
        statusMessages.put(HttpStatus.BAD_REQUEST, "错误的状态修改");
        statusMessages.put(HttpStatus.FORBIDDEN, "权限不足");
        statusMessages.put(HttpStatus.NOT_FOUND, "管理员或请求不存在");
        statusMessages.put(HttpStatus.CONFLICT, "这个请求不允许被修改");
        statusMessages.put(HttpStatus.INTERNAL_SERVER_ERROR, "处理失败");
        
        // 获取消息
        String message = statusMessages.getOrDefault(processStatus, "处理失败");

        return Result.create(processStatus, message, processStatus.equals(HttpStatus.OK));
    }
}
