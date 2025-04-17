package com.example.model;

import java.time.LocalDateTime;

import com.example.entity.RequestStatus;

import lombok.Data;

@Data
public class RegistrationRequestInfo {
    private Long requestId;     // 请求 ID
    private String username;    // 用户名，非空
    private String password;    // 用户密码
    private String companyName; // 公司名称
    private String phoneNumber; // 电话号码
    private RequestStatus requestStatus;    // 申请请求状态
    private LocalDateTime createdAt;        // 请求创建时间
    private LocalDateTime processedAt;      // 请求被处理的时间
    private Long processedBy;               // 处理请求的管理员 ID
}
