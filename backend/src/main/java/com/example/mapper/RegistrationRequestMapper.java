package com.example.mapper;

import java.util.List;

import com.example.entity.RegistrationRequest;
import com.example.entity.RequestStatus;

public interface RegistrationRequestMapper {
    // 获取所有注册请求
    List<RegistrationRequest> selectAllRegistrationRequests();

    // 根据ID获取注册请求
    RegistrationRequest selRegistrationRequestById(Long requestId);

    // 根据 username 获取待处理的请求
    List<RegistrationRequest> selectRegistrationRequestsByUserName(String username);

    // 根据请求状态获取请求
    List<RegistrationRequest> selectRegistrationRequestsByStatus(RequestStatus RequestStatus);

    // 添加一个注册请求
    int insertRegistrationRequest(RegistrationRequest registrationRequest);

    // 处理一个注册请求
    int updateRequestStatus(Long requestId, RequestStatus requestStatus, Long adminId);
}
