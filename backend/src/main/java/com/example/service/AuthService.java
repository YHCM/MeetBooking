package com.example.service;

import com.example.model.Result;
import org.springframework.http.HttpStatus;
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

    public Result<Boolean> login(String username, String password) {
        // 通过用户名获取用户
        User user = userService.getUserByUsername(username).getData();

        if (user == null) {
            return Result.create(HttpStatus.NOT_FOUND, "用户不存在", Boolean.FALSE);   // 用户不存在
        }

        // 获取数据库存储的密码
        String storedPassword = user.getPassword();

        // 验证
        boolean flag = passwordEncoder.matches(password, storedPassword);
        if (flag) {
            return Result.ok("登陆成功", Boolean.TRUE);
        } else {
            return Result.create(HttpStatus.UNAUTHORIZED, "登陆失败", Boolean.FALSE);
        }
    }
}
