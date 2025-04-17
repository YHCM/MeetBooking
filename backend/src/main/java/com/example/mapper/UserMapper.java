package com.example.mapper;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.model.UserInfo;

import java.util.List;

public interface UserMapper {
    // 获取所有用户信息
    List<UserInfo> selectAllUsersInfo();

    // 获取指定类型的用户信息
    List<UserInfo> selectUsersInfoByRole(Role role);

    // 通过用户名获取用户
    User selectUserByUsername(String userName);

    // 通过用户名获取用户信息
    UserInfo selectUserInfoByUsername(String userName);

    // 通过 ID 获取用户
    User selectUserByUserId(Long userId);
    
    // 通过 ID 获取用户信息
    UserInfo selectUserInfoByUserId(Long userId);

    // 插入用户
    int insertUser(User user);
}
