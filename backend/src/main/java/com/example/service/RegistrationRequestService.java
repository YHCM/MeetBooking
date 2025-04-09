package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.RegistrationRequest;
import com.example.entity.RequestStatus;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.mapper.RegistrationRequestMapper;

@Service
public class RegistrationRequestService {
    private final RegistrationRequestMapper registrationRequestMapper;
    private final UserService userService;

    public RegistrationRequestService(RegistrationRequestMapper registrationRequestMapper, UserService userService) {
        this.registrationRequestMapper = registrationRequestMapper;
        this.userService = userService;
    }

    // 获取所有的注册请求
    public List<RegistrationRequest> getAllRegistrationRequests() {
        return registrationRequestMapper.selectAllRegistrationRequests();
    }

    // 根据ID获取注册请求
    public RegistrationRequest getRegistrationRequestById(Long requestId) {
        return registrationRequestMapper.selRegistrationRequestById(requestId);
    }

    // 根据请求状态获取请求
    public List<RegistrationRequest> getRegistrationRequestsByStatus(RequestStatus requestStatus) {
        return registrationRequestMapper.selectRegistrationRequestsByStatus(requestStatus);
    }

    // 添加一个注册请求
    public boolean addRegistrationRequest(RegistrationRequest registrationRequest) {
        String username = registrationRequest.getUsername();

        // 检查用户名是否存在
        if (userService.isUsernameExisted(username) || isUsernameExistedInPending(username)) {
            return false;   // 用户名存在了
        }

        return registrationRequestMapper.insertRegistrationRequest(registrationRequest) > 0;
    }

    // 处理一个请求
    public boolean processRegistrationRequest(Long requestId, Long adminId, RequestStatus requestStatus) {
        // 获取管理员用户
        User user = userService.getUserByUserId(adminId);
        if (user == null) {
            // 用户不存在
            return false;
        }

        // 检查用户是否是管理员
        if (!user.getRole().equals(Role.ADMIN)) {
            // 用户不是管理员
            return false;
        }

        // 检查请求是否存在
        RegistrationRequest registrationRequest = registrationRequestMapper.selRegistrationRequestById(requestId);
        if (registrationRequest == null) {
            // 请求不存在
            return false;
        }

        // 检查修改后的状态是否还是 待处理
        if (requestStatus.equals(RequestStatus.PENDING)) {
            // 还是改为 待处理，不行
            return false;
        }

        return registrationRequestMapper.updateRequestStatus(requestId, requestStatus, adminId) > 0;
    }

    // 判断用户名是否在 待处理 请求中存在
    public boolean isUsernameExistedInPending(String username) {
        List<RegistrationRequest> registrationRequestList = registrationRequestMapper.selectRegistrationRequestsByUserName(username);

        return registrationRequestList != null;
    }
}
