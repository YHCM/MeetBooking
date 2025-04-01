package com.example.controller;

import com.example.mapper.UserMapper;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 获取所有用户
    @GetMapping
    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }

    // 插入 ROOT 用户
    @PostMapping("/root")
    public String insertRootUser(@RequestBody User user) {
        userMapper.insertRootUser(user);
        return "Root user inserted successfully.";
    }

    // 插入 ADMIN 用户
    @PostMapping("/admin")
    public String insertAdminUser(@RequestBody User user) {
        userMapper.insertAdminUser(user);
        return "Admin user inserted successfully.";
    }

    // 插入 STAFF 用户
    @PostMapping("/staff")
    public String insertStaffUser(@RequestBody User user) {
        userMapper.insertStaffUser(user);
        return "Staff user inserted successfully.";
    }

    // 插入 CLIENT 用户
    @PostMapping("/client")
    public String insertClientUser(@RequestBody User user) {
        userMapper.insertClientUser(user);
        return "Client user inserted successfully.";
    }
}
