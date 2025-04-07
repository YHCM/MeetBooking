package com.example.controller;

import com.example.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.LoginRequest;
import com.example.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
        summary = "用户登陆",
        description = "传入账号密码，进行登陆验证",
        responses = {
            @ApiResponse(responseCode = "200", description = "登陆成功", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "401", description = "用户名不存在或密码错误", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class)))
        }
    )
    @PostMapping("/login")
    public Result<Boolean> login(@RequestBody LoginRequest loginRequest) {
        boolean loginStatus = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (loginStatus) {
            return Result.ok("登陆成功", Boolean.TRUE);
        } else {
            return Result.create(HttpStatus.UNAUTHORIZED, "用户不存在或密码错误", Boolean.FALSE);
        }
    }
}
