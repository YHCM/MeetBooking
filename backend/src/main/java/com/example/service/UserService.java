package com.example.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.mapper.UserMapper;
import com.example.entity.RegistrationRequest;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.util.security.crypto.password.PasswordEncoder;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // 获取所有用户
    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }

    // 获取指定类型的用户
    public List<User> getUsersByRole(Role role) {
        return userMapper.selectUsersByRole(role);
    }

    // 通过用户名获取用户
    public User getUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    // 通过 ID 获取用户
    public User getUserByUserId(Long userId) {
        return userMapper.selectUserByUserId(userId);
    }

    // 根据角色类型添加用户
    public HttpStatus addUserByRole(User user, Role role) {
        // 判断用户名是否存在
        if (isUsernameExisted(user.getUsername())) {
            return HttpStatus.CONFLICT;   // 用户名已经存在
        }

        // 加密密码
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(role);

        int rowsAffected = userMapper.insertUser(user);
        if (rowsAffected <= 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.OK;
        }
    }

    // 将同意后的注册请求添加到 users 表
    public boolean addUserFromRequest(RegistrationRequest registrationRequest) {
        User user = registrationRequest.toUser();

        return userMapper.insertUser(user) > 0;
    }

    // 查看用户是否存在
    public boolean isUsernameExisted(String username) {
        User user = getUserByUsername(username);

        return user != null;
    }

    // 检查用户是否登陆
    public boolean isUserLoggedIn(HttpSession session) {
        // 从 session 获取 id
        Long userId = (Long) session.getAttribute("uid");

        // 如果没有或者 id 对应的用户不存在，那么不算登陆
        return userId != null && getUserByUserId(userId) != null;
    }

    // 通过 session 获取 user
    public User getUserBySession(HttpSession session) {
        Long userId = (Long) session.getAttribute("uid");

        return getUserByUserId(userId);
    }
}
