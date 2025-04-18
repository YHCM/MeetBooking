package com.example.model;

import java.time.LocalDateTime;

import com.example.entity.Role;

import lombok.Data;

@Data
public class UserInfo {
    private Long userId;
    private String username;
    private Role role;
    private String companyName;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private Boolean isActive;
    private LocalDateTime processedAt;
    private Long processedBy;
}
