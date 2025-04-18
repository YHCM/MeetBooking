package com.example.controller;

import com.example.constants.messages.UserMessage;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.model.Result;
import com.example.model.UserInfo;
import com.example.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "获取所有用户信息")
    @GetMapping
    public Result<List<UserInfo>> getAllUsers() {
        List<UserInfo> userInfoList = userService.getAllUsersInfo();
        return Result.ok("所有用户信息", userInfoList);
    }

    @Operation(summary = "获取指定角色的用户信息")
    @GetMapping("/role/{role}")
    public Result<List<UserInfo>> getUsersByRole(@PathVariable Role role) {
        List<UserInfo> userInfoList = userService.getUsersInfoByRole(role);
        return Result.ok(role + " 用户信息", userInfoList);
    }

    @Operation(summary = "通过用户名获取用户信息")
    @GetMapping("/username/{username}")
    public Result<UserInfo> getUserByUsername(@PathVariable String username) {
        UserInfo userInfo = userService.getUserInfoByUsername(username);
        if (userInfo == null) {
            return Result.create(HttpStatus.NOT_FOUND, "用户不存在", null);
        } else {
            return Result.ok("用户信息", userInfo);
        }
    }

    @Operation(summary = "通过ID获取用户信息")
    @GetMapping("/{userId}")
    public Result<UserInfo> getUserByUserId(@PathVariable Long userId) {
        UserInfo userInfo = userService.getUserInfoByUserId(userId);
        if (userInfo == null) {
            return Result.create(HttpStatus.NOT_FOUND, "用户不存在", null);
        } else {
            return Result.ok("用户信息", userInfo);
        }
    }

    @Operation(summary = "根据角色类型创建用户")
    @PostMapping("/{role}")
    public Result<Boolean> createUser(@PathVariable Role role, @RequestBody User user) {
        HttpStatus createStatus = userService.addUserByRole(user, role);

        Map<HttpStatus, String> statusMessages = UserMessage.CREATE_MESSAGES;

        String message = statusMessages.getOrDefault(createStatus, "用户创建失败");

        return Result.create(createStatus, message, createStatus.is2xxSuccessful());
    }

    @Operation(summary = "查看用户状态，是否登陆")
    @GetMapping("/status")
    public Result<Boolean> getUserStatus(HttpSession session) {
        boolean isLoggedIn = userService.isUserLoggedIn(session);

        if (isLoggedIn) {
            return Result.ok("用户已登陆", Boolean.TRUE);
        } else {
            return Result.create(HttpStatus.UNAUTHORIZED, "用户未登录", Boolean.FALSE);
        }
    }

    @Operation(summary = "通过 session 获取当前用户信息")
    @GetMapping("/current")
    public Result<UserInfo> getUserBySession(HttpSession session) {
        UserInfo currentUserInfo = userService.getUserInfoBySession(session);

        if (currentUserInfo == null) {
            return Result.create(HttpStatus.UNAUTHORIZED, "用户未登录", null);
        } else {
            return Result.ok("当前用户信息", currentUserInfo);
        }
    }

    @Operation(summary = "冻结 / 解冻用户")
    @PatchMapping("/{userId}/active")
    public Result<Boolean> toggleUserStatus(@PathVariable Long userId, HttpSession session) {
        HttpStatus toggleStatus = userService.toggleUserStatus(userId, session);

        Map<HttpStatus, String> statusMessages = UserMessage.TOGGLE_MESSAGES;

        // 获取消息
        String message = statusMessages.getOrDefault(toggleStatus, "处理失败");

        return Result.create(toggleStatus, message, toggleStatus.is2xxSuccessful());
    }
}
