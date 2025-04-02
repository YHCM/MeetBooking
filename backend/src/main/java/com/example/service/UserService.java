package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.mapper.UserMapper;
import com.example.model.Role;
import com.example.model.User;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // 获取所有用户
    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    };

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

    // 添加用户
    private void addUserByRole(User user, Role role) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(role);
        userMapper.insertUser(user);
    }

    // 添加 Root 用户
    public void addRootUser(User user) {
        addUserByRole(user, Role.ROOT);
    }

    // 添加 Admin 用户
    public void addAdminUser(User user) {
        addUserByRole(user, Role.ROOT);
    }

    // 添加 Staff 用户
    public void addStaffUser(User user) {
        addUserByRole(user, Role.ROOT);
    }

    // 添加 Client 用户
    public void addClientUser(User user) {
        addUserByRole(user, Role.ROOT);
    }
}
