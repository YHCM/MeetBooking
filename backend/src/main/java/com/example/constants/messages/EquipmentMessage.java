package com.example.constants.messages;

import org.springframework.http.HttpStatus;

import java.util.Map;

public final class EquipmentMessage {
    public static final Map<HttpStatus, String> CREATE_MESSAGES = Map.of(
        HttpStatus.CREATED, "设备添加成功",
        HttpStatus.CONFLICT, "设备名称已存在",
        HttpStatus.INTERNAL_SERVER_ERROR, "设备添加失败"
    );

    public static final Map<HttpStatus, String> DELETE_MESSAGES = Map.of(
        HttpStatus.NO_CONTENT, "设备删除成功",
        HttpStatus.NOT_FOUND, "设备不存在",
        HttpStatus.INTERNAL_SERVER_ERROR, "设备删除失败"
    );

    public static final Map<HttpStatus, String> UPDATE_MESSAGES = Map.of(
        HttpStatus.OK, "设备更新成功",
        HttpStatus.NOT_FOUND, "设备不存在",
        HttpStatus.CONFLICT, "设备名称已存在",
        HttpStatus.INTERNAL_SERVER_ERROR, "设备更新失败"
    );

}
