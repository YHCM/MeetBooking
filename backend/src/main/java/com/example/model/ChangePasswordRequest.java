package com.example.model;

import lombok.Data;

// 修改密码请求模型
@Data
public class ChangePasswordRequest {
    Long userId;
    String oldPassword;     // 旧密码
    String newPassword;     // 新密码
}
