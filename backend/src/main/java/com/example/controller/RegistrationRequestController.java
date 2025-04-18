package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.constants.messages.RegistrationRequestMessage;
import com.example.entity.RegistrationRequest;
import com.example.entity.RequestStatus;
import com.example.model.RegistrationRequestInfo;
import com.example.model.Result;
import com.example.service.RegistrationRequestService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RegistrationRequestController {
    private final RegistrationRequestService registrationRequestService;

    @Operation(summary = "获取所有注册请求信息")
    @GetMapping
    public Result<List<RegistrationRequestInfo>> getAllRegistrationRequests() {
        List<RegistrationRequestInfo> registrationRequestInfoList = registrationRequestService.getAllRegistrationRequestsInfo();
        return Result.ok("所有注册请求", registrationRequestInfoList);
    }

    @Operation(summary = "根据 ID 获取请求")
    @GetMapping("/{requestId}")
    public Result<RegistrationRequestInfo> getRegistrationRequestById(@PathVariable Long requestId) {
        RegistrationRequestInfo registrationRequestInfo = registrationRequestService.getRegistrationRequestInfoById(requestId);

        return Result.ok("注册请求信息", registrationRequestInfo);
    }

    @Operation(summary = "根据请求状态获取请求信息")
    @GetMapping("/status/{requestStatus}")
    public Result<List<RegistrationRequestInfo>> getRegistrationRequestsByStatus(@PathVariable RequestStatus requestStatus) {
        List<RegistrationRequestInfo> registrationRequestsInfoList = registrationRequestService.getRegistrationRequestsInfoByStatus(requestStatus);

        return Result.ok(requestStatus + " 状态的所有请求", registrationRequestsInfoList);
    }
    
    @Operation(summary = "创建一个注册请求")
    @PostMapping
    public Result<Boolean> createRegistrationRequest(@RequestBody RegistrationRequest registrationRequest) {
        HttpStatus createStatus = registrationRequestService.addRegistrationRequest(registrationRequest);

        Map<HttpStatus, String> statusMessages = RegistrationRequestMessage.CREATE_MESSAGES;

        String message = statusMessages.getOrDefault(createStatus, "注册请求添加失败");
        
        return Result.create(createStatus, message, createStatus.is2xxSuccessful());
    }

    @Operation(summary = "处理一个请求")
    @PatchMapping("/{requestId}/{requestStatus}")
    public Result<Boolean> processRegistrationRequest(@PathVariable Long requestId, @PathVariable RequestStatus requestStatus, HttpSession session) {
        HttpStatus processStatus = registrationRequestService.processRegistrationRequest(requestId, requestStatus, session);

        Map<HttpStatus, String> statusMessages = RegistrationRequestMessage.PROCESS_MESSAGES;
        
        // 获取消息
        String message = statusMessages.getOrDefault(processStatus, "处理失败");

        return Result.create(processStatus, message, processStatus.is2xxSuccessful());
    }
}
