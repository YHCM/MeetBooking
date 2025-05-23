package com.example.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RegistrationRequest {
    private Long requestId;     // 请求 ID
    private String username;    // 用户名，非空
    private String password;    // 用户密码
    private String companyName; // 公司名称
    private String phoneNumber; // 电话号码
    private RequestStatus requestStatus;    // 申请请求状态
    private LocalDateTime createdAt;        // 请求创建时间
    private LocalDateTime processedAt;      // 请求被处理的时间
    private Long processedBy;               // 处理请求的管理员 ID

    // 转换为 user 的方法
    public User toUser() {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setRole(Role.CLIENT);
        user.setCompanyName(this.companyName);
        user.setPhoneNumber(this.phoneNumber);

        return user;
    }
}
