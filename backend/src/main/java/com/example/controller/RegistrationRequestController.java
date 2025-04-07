package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.RegistrationRequest;
import com.example.model.Result;
import com.example.service.RegistrationRequestService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/requests")
public class RegistrationRequestController {
    private final RegistrationRequestService registrationRequestService;

    public RegistrationRequestController(RegistrationRequestService registrationRequestService) {
        this.registrationRequestService = registrationRequestService;
    }

    // 获取所有注册请求
    @Operation(summary = "获取所有注册请求")
    @GetMapping
    public Result<List<RegistrationRequest>> getAllRegistrationRequests() {
        List<RegistrationRequest> registrationRequestList = registrationRequestService.getAllRegistrationRequests();
        return Result.ok("所有注册请求", registrationRequestList);
    }
    
}
