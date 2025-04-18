package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long userId;    // 用户 ID
    private String username;    // 用户名，姓名
    private String password;    // 用户密码
    private Role role;          // 用户身份
    private String companyName;     // 公司名称，仅客户需要
    private String phoneNumber;     // 电话号码
    private LocalDateTime createdAt;    // 创建时间
    private Boolean isActive;       // 用户是否被冻结
    private LocalDateTime processedAt;  // 最后被处理的时间（改变状态）
    private Long processedBy;       // 处理用户的管理员 ID
}
