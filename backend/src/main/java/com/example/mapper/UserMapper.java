package com.example.mapper;

import com.example.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    // 获取所有用户
    List<User> selectAllUsers();

    // 插入 ROOT 用户
    void insertRootUser(User user);

    // 插入 ADMIN 用户
    void insertAdminUser(User user);

    // 插入 STAFF 用户
    void insertStaffUser(User user);

    // 插入 CLIENT 用户
    void insertClientUser(User user);
}
