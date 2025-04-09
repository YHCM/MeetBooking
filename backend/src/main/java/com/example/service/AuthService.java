package com.example.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.util.security.crypto.password.PasswordEncoder;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public HttpStatus login(String username, String password, HttpSession session) {
        // 通过用户名获取用户
        User user = userService.getUserByUsername(username);

        if (user == null) {
            return HttpStatus.NOT_FOUND;   // 用户不存在
        }

        // 获取数据库存储的密码
        String storedPassword = user.getPassword();

        // 验证
        if (passwordEncoder.matches(password, storedPassword)) {
            session.setAttribute("uid", user.getUserId());
            return HttpStatus.OK;    // 登陆成功
        } else {
            return HttpStatus.UNAUTHORIZED;   // 密码错误
        }
    }
}
