package com.example.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.entity.RegistrationRequest;
import com.example.entity.RequestStatus;
import com.example.entity.Role;
import com.example.mapper.RegistrationRequestMapper;
import com.example.model.UserInfo;
import com.example.util.security.crypto.password.PasswordEncoder;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationRequestService {
    private final RegistrationRequestMapper registrationRequestMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // 获取所有的注册请求
    public List<RegistrationRequest> getAllRegistrationRequests() {
        return registrationRequestMapper.selectAllRegistrationRequests();
    }

    // 根据ID获取注册请求
    public RegistrationRequest getRegistrationRequestById(Long requestId) {
        return registrationRequestMapper.selectRegistrationRequestById(requestId);
    }

    // 根据请求状态获取请求
    public List<RegistrationRequest> getRegistrationRequestsByStatus(RequestStatus requestStatus) {
        return registrationRequestMapper.selectRegistrationRequestsByStatus(requestStatus);
    }

    // 添加一个注册请求
    public HttpStatus addRegistrationRequest(RegistrationRequest registrationRequest) {
        String username = registrationRequest.getUsername();

        // 检查用户名是否存在
        if (userService.isUsernameExisted(username) || isUsernameExistedInPending(username)) {
            return HttpStatus.CONFLICT;   // 用户名存在了
        }

        // 加密密码
        String encrptyedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        registrationRequest.setPassword(encrptyedPassword);

        int rowsAffected = registrationRequestMapper.insertRegistrationRequest(registrationRequest);

        if (rowsAffected <= 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.OK;
        }
    }

    // 处理一个请求
    public HttpStatus processRegistrationRequest(Long requestId, RequestStatus requestStatus, HttpSession session) {
        // 获取用户ID
        Long userId = (Long) session.getAttribute("uid");

        // 获取管理员用户
        UserInfo user = userService.getUserInfoByUserId(userId);
        if (user == null) {
            // 用户不存在
            return HttpStatus.NOT_FOUND;
        }

        // 检查用户是否是管理员
        if (!user.getRole().equals(Role.ADMIN)) {
            // 用户不是管理员
            return HttpStatus.FORBIDDEN;
        }

        // 检查请求是否存在
        RegistrationRequest registrationRequest = registrationRequestMapper.selectRegistrationRequestById(requestId);
        if (registrationRequest == null) {
            // 请求不存在
            return HttpStatus.NOT_FOUND;
        }

        // 检查请求是否是 待处理 状态，只有 待处理 状态才可以被处理
        if (!registrationRequest.getRequestStatus().equals(RequestStatus.PENDING)) {
            // 请求不允许被修改
            return HttpStatus.CONFLICT;
        }

        // 检查修改后的状态是否还是 待处理
        if (requestStatus.equals(RequestStatus.PENDING)) {
            // 还是改为 待处理，不行
            return HttpStatus.BAD_REQUEST;
        }

        int rowsAffected = registrationRequestMapper.updateRequestStatus(requestId, requestStatus, userId);
        if (rowsAffected <= 0) {
            // 插入异常
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        if (!requestStatus.equals(RequestStatus.APPROVED)) {
            // 如果是拒绝，不进行任何操作
            return HttpStatus.OK;
        }

        // 同意之后，把注册请求添加到 users 表中
        boolean addStatus = userService.addUserFromRequest(registrationRequest);
        if (addStatus) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    // 判断用户名是否在 待处理 请求中存在
    public boolean isUsernameExistedInPending(String username) {
        List<RegistrationRequest> registrationRequestList = registrationRequestMapper.selectRegistrationRequestsByUserName(username);

        // 如果不为空，那么就是存在
        return !registrationRequestList.isEmpty();
    }
}
