package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.util.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean login(String username, String password) {
        // 通过用户名获取用户
        User user = userService.getUserByUsername(username);

        if (user == null) {
            return false;   // 用户不存在
        }

        // 获取数据库存储的密码
        String storedPassword = user.getPassword();

        // 验证
        if (passwordEncoder.matches(password, storedPassword)) {
            return true;    // 登陆成功
        } else {
            return false;   // 密码错误
        }
    }
}
