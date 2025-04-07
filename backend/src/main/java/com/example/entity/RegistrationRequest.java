package com.example.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RegistrationRequest {
    private Long requestId;     // 请求 ID
    private String username;    // 用户名，唯一，非空
    private String password;    // 用户密码
    private String companyName; // 公司名称
    private String phoneNumber; // 电话号码
    private RequestStatus requestStatus;    // 申请请求状态
    private LocalDateTime createdAt;        // 请求创建时间
    private LocalDateTime updatedAt;        // 请求最后更新时间
}
