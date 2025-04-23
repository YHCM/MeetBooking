package com.example.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.mapper.UserMapper;
import com.example.model.ChangePasswordRequest;
import com.example.model.UserInfo;
import com.example.entity.RegistrationRequest;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.util.security.crypto.password.PasswordEncoder;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // 获取所有用户信息
    public List<UserInfo> getAllUsersInfo() {
        return userMapper.selectAllUsersInfo();
    }

    // 获取指定类型的用户信息
    public List<UserInfo> getUsersInfoByRole(Role role) {
        return userMapper.selectUsersInfoByRole(role);
    }

    // 通过用户名获取用户
    public User getUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    // 通过用户名获取用户信息
    public UserInfo getUserInfoByUsername(String username) {
        return userMapper.selectUserInfoByUsername(username);
    }

    // 通过 ID 获取用户
    public User getUserByUserId(Long userId) {
        return userMapper.selectUserByUserId(userId);
    }

    // 通过 ID 获取用户信息
    public UserInfo getUserInfoByUserId(Long userId) {
        return userMapper.selectUserInfoByUserId(userId);
    }

    // 根据角色类型添加用户
    public HttpStatus addUserByRole(User user, Role role) {
        // 判断用户名是否存在
        if (isUsernameExisted(user.getUsername())) {
            return HttpStatus.CONFLICT;   // 用户名已经存在
        }

        // 加密密码
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(role);

        int rowsAffected = userMapper.insertUser(user);
        if (rowsAffected <= 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.CREATED;
        }
    }

    // 将同意后的注册请求添加到 users 表
    public boolean addUserFromRequest(RegistrationRequest registrationRequest) {
        User user = registrationRequest.toUser();

        return userMapper.insertUser(user) > 0;
    }

    // 查看用户是否存在
    public boolean isUsernameExisted(String username) {
        UserInfo userInfo = getUserInfoByUsername(username);

        return userInfo != null;
    }

    // 检查用户是否登陆
    public boolean isUserLoggedIn(HttpSession session) {
        // 从 session 获取 id
        Long userId = (Long) session.getAttribute("uid");

        // 如果没有或者 id 对应的用户不存在，那么不算登陆
        return userId != null && getUserInfoByUserId(userId) != null;
    }

    // 通过 session 获取 user
    public User getUserBySession(HttpSession session) {
        Long userId = (Long) session.getAttribute("uid");

        return getUserByUserId(userId);
    }

    // 通过 session 获取 user info
    public UserInfo getUserInfoBySession(HttpSession session) {
        Long userId = (Long) session.getAttribute("uid");

        return getUserInfoByUserId(userId);
    }

    // 切换用户的状态（冻结/解冻）
    public HttpStatus toggleUserStatus(Long userId, HttpSession session) {
        // 获取要改变的用户信息
        UserInfo userInfo = getUserInfoByUserId(userId);

        if (userInfo == null) {
            return HttpStatus.NOT_FOUND;    // 需要修改的用户不存在
        }

        // 检查用户类型，只有 员工 和 客户 可以被 冻结/解冻
        if (userInfo.getRole().equals(Role.ROOT) || userInfo.getRole().equals(Role.ADMIN)) {
            // 此用户类型不允许 冻结/解冻
            return HttpStatus.CONFLICT;
        }

        // 获取管理员 ID
        Long adminId = (Long) session.getAttribute("uid");
        
        // 获取管理员用户信息
        UserInfo adminUserInfo = getUserInfoByUserId(adminId);
        if (adminUserInfo == null) {
            return HttpStatus.NOT_FOUND;    // 管理员不存在
        }

        // 检查管理员是否是管理员
        if (!adminUserInfo.getRole().equals(Role.ADMIN)) {
            return HttpStatus.FORBIDDEN;    // 不是管理员，权限不够
        }

        // 冻结/解冻 用户
        int rowsAffected = userMapper.toggleUserStatus(userId, adminId);
        if (rowsAffected <= 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR;    // 服务器异常
        } else {
            return HttpStatus.OK;   // 处理成功
        }
    }

    // 更新用户信息
    public HttpStatus updateUserInfo(UserInfo userInfo) {
        if (userInfo.getUserId() == null) {
            // 没有传入 ID
            return HttpStatus.BAD_REQUEST;
        }

        if (getUserByUserId(userInfo.getUserId()) == null) {
            // 用户不存在
            return HttpStatus.NOT_FOUND;
        }

        if (isUsernameExisted(userInfo.getUsername())) {
            // 用户名已存在
            return HttpStatus.CONFLICT;
        }

        // 更新用户信息
        int rowsAffected = userMapper.updateUerInfo(userInfo);
        if (rowsAffected <= 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR;    // 服务器异常
        } else {
            return HttpStatus.OK;
        }
    }

    // 修改用户密码
    public HttpStatus changeUserPassword(ChangePasswordRequest request) {
        Long userId = request.getUserId();
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();

        User user = getUserByUserId(userId);

        if (user == null) {
            // 用户不存在
            return HttpStatus.NOT_FOUND;
        }

        // 获取数据库存储的密码
        String storedPassword = user.getPassword();

        // 验证旧密码是否正确
        if (!passwordEncoder.matches(oldPassword, storedPassword)) {
            // 密码验证不通过
            return HttpStatus.UNAUTHORIZED;
        }

        // 修改密码
        String encryptedPassword = passwordEncoder.encode(newPassword);

        int rowsAffected = userMapper.changeUserPassword(userId, encryptedPassword);
        if (rowsAffected <= 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR;    // 服务器异常
        } else {
            return HttpStatus.OK;   // 修改密码成功
        }
    }
}
