package com.example.service;

import java.util.List;

import com.example.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.mapper.UserMapper;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.util.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // 获取所有用户
    public Result<List<User>> getAllUsers() {
        List<User> userList = userMapper.selectAllUsers();
        return Result.ok("所有用户列表", userList);
    }

    // 获取指定类型的用户
    public Result<List<User>> getUsersByRole(Role role) {
        List<User> userList = userMapper.selectUsersByRole(role);
        return Result.ok(role + " 用户列表", userList);
    }

    // 通过用户名获取用户
    public Result<User> getUserByUsername(String username) {
        User user = userMapper.selectUserByUsername(username);
        return Result.ok(username + " 用户信息", user);
    }

    // 通过 ID 获取用户
    public Result<User> getUserByUserId(Long userId) {
        User user = userMapper.selectUserByUserId(userId);
        return Result.ok("用户信息", user);
    }

    // 根据角色类型添加用户
    public Result<Boolean> addUserByRole(User user, Role role) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(role);
        userMapper.insertUser(user);

        return Result.ok("添加 " + role + " 用户成功", Boolean.TRUE);
    }
}
