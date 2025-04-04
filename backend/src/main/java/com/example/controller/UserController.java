package com.example.controller;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 获取所有用户
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 获取指定类型的用户
    @GetMapping("/role/{role}")
    public List<User> getUsersByRole(@PathVariable Role role) {
        return userService.getUsersByRole(role);
    }

    // 通过用户名获取用户
    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    // 通过 ID 获取用户名
    @GetMapping("/{userId}")
    public User getUserByUserId(@PathVariable Long userId) {
        return userService.getUserByUserId(userId);
    }

    // 添加 ROOT 用户
    @PostMapping("/root")
    public String addRootUser(@RequestBody User user) {
        userService.addRootUser(user);
        return "Root user inserted successfully.";
    }

    // 添加 ADMIN 用户
    @PostMapping("/admin")
    public String addAdminUser(@RequestBody User user) {
        userService.addAdminUser(user);
        return "Admin user inserted successfully.";
    }

    // 添加 STAFF 用户
    @PostMapping("/staff")
    public String addStaffUser(@RequestBody User user) {
        userService.addStaffUser(user);
        return "Staff user inserted successfully.";
    }

    // 添加 CLIENT 用户
    @PostMapping("/client")
    public String addClientUser(@RequestBody User user) {
        userService.addClientUser(user);
        return "Client user inserted successfully.";
    }
}
