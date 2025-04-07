package com.example.mapper;

import com.example.entity.Role;
import com.example.entity.User;

import java.util.List;

public interface UserMapper {
    // 获取所有用户
    List<User> selectAllUsers();

    // 获取指定类型的用户
    List<User> selectUsersByRole(Role role);

    // 通过用户名获取用户
    User selectUserByUsername(String userName);

    // 通过 ID 获取用户
    User selectUserByUserId(Long userId);
    
    // 插入用户
    int insertUser(User user);
}
