package com.example.entity;

import lombok.Data;

// 登陆请求模型
@Data
public class LoginRequest {
    private String username;
    private String password;
}
