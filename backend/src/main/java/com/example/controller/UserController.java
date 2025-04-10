package com.example.controller;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.model.Result;
import com.example.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 获取所有用户
    @Operation(summary = "获取所有用户")
    @GetMapping
    public Result<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return Result.ok("所有用户信息", userList);
    }

    // 获取指定类型的用户
    @Operation(summary = "获取指定角色的用户")
    @GetMapping("/role/{role}")
    public Result<List<User>> getUsersByRole(@PathVariable Role role) {
        List<User> userList = userService.getUsersByRole(role);
        return Result.ok(role + " 用户信息", userList);
    }

    // 通过用户名获取用户
    @Operation(summary = "通过用户名获取用户")
    @GetMapping("/username/{username}")
    public Result<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return Result.create(HttpStatus.NOT_FOUND, "用户不存在", null);
        } else {
            return Result.ok("用户信息", user);
        }
    }

    // 通过 ID 获取用户名
    @Operation(summary = "通过ID获取用户")
    @GetMapping("/{userId}")
    public Result<User> getUserByUserId(@PathVariable Long userId) {
        User user = userService.getUserByUserId(userId);
        if (user == null) {
            return Result.create(HttpStatus.NOT_FOUND, "用户不存在", null);
        } else {
            return Result.ok("用户信息", user);
        }
    }

    // 根据角色类型添加用户
    @Operation(summary = "根据角色类型添加用户")
    @PostMapping("/{role}")
    public Result<Boolean> createUser(@PathVariable Role role, @RequestBody User user) {
        HttpStatus addStatus = userService.addUserByRole(user, role);

        Map<HttpStatus, String> statusMessages = new HashMap<>();
        statusMessages.put(HttpStatus.OK, "用户创建成功");
        statusMessages.put(HttpStatus.CONFLICT, "用户名已存在");
        statusMessages.put(HttpStatus.INTERNAL_SERVER_ERROR, "用户添加失败");

        String message = statusMessages.getOrDefault(addStatus, "用户创建失败");

        return Result.create(addStatus, message, addStatus.is2xxSuccessful());
    }
}
