package com.example.mapper;

import java.util.List;

import com.example.entity.RegistrationRequest;

public interface RegistrationRequestMapper {
    // 获取所有注册请求
    List<RegistrationRequest> selectAllRegistrationRequests();
}
