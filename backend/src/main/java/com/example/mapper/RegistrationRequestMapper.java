package com.example.mapper;

import java.util.List;

import com.example.entity.RegistrationRequest;
import com.example.entity.RequestStatus;
import com.example.model.RegistrationRequestInfo;

public interface RegistrationRequestMapper {
    // 获取所有注册请求信息
    List<RegistrationRequestInfo> selectAllRegistrationRequestsInfo();

    // 根据ID获取注册请求
    RegistrationRequest selectRegistrationRequestById(Long requestId);

    // 根据ID获取注册请求信息
    RegistrationRequestInfo selectRegistrationRequestInfoById(Long requestId);

    // 根据 username 获取待处理的请求信息
    List<RegistrationRequestInfo> selectRegistrationRequestsInfoByUserName(String username);

    // 根据请求状态获取请求信息
    List<RegistrationRequestInfo> selectRegistrationRequestsInfoByStatus(RequestStatus RequestStatus);

    // 添加一个注册请求
    int insertRegistrationRequest(RegistrationRequest registrationRequest);

    // 处理一个注册请求
    int updateRequestStatus(Long requestId, RequestStatus requestStatus, Long adminId);
}
