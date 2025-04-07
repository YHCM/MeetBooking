package com.example.service;

import java.util.List;

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
    public boolean addUserByRole(User user, Role role) {
        // 判断用户名是否存在
        if (isUserExisted(user.getUsername())) {
            return false;   // 用户名已经存在
        }

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(role);

        return userMapper.insertUser(user) > 0;
    }

    // 查看用户是否存在
    private boolean isUserExisted(String username) {
        User user = getUserByUsername(username);

        return user != null;
    }
}
