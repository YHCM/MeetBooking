package com.example.controller;

import com.example.model.Result;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.RegistrationRequest;
import com.example.model.LoginRequest;
import com.example.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(
        summary = "用户登陆",
        description = "传入账号密码，进行登陆验证",
        responses = {
            @ApiResponse(responseCode = "200", description = "登陆成功", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "401", description = "用户名不存在或密码错误", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class)))
        }
    )
    @PostMapping("/login")
    public Result<Boolean> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        HttpStatus loginStatus = authService.login(loginRequest.getUsername(), loginRequest.getPassword(), session);

        Map<HttpStatus, String> statusMessages = new HashMap<>();
        statusMessages.put(HttpStatus.OK, "登陆成功");
        statusMessages.put(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
        statusMessages.put(HttpStatus.NOT_FOUND, "用户不存在");

        // 获取消息
        String message = statusMessages.getOrDefault(loginStatus, "登陆失败");

        return Result.create(loginStatus, message, loginStatus.is2xxSuccessful());
    }

    @Operation(summary = "用户注册，添加注册请求")
    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody RegistrationRequest registrationRequest) {
        HttpStatus registerStatus = authService.register(registrationRequest);

        Map<HttpStatus, String> statusMessages = new HashMap<>();
        statusMessages.put(HttpStatus.OK, "注册请求成功");
        statusMessages.put(HttpStatus.CONFLICT, "用户名已存在");
        statusMessages.put(HttpStatus.SERVICE_UNAVAILABLE, "注册失败");

        String message = statusMessages.getOrDefault(registerStatus, "注册失败");

        return Result.create(registerStatus, message, registerStatus.is2xxSuccessful());
    }
}
