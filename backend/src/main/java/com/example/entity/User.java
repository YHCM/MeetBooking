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
}
