package com.example.constants.messages;

import java.util.Map;

import org.springframework.http.HttpStatus;

public final class MeetingRoomMessage {
    // 添加会议室的信息
    public static final Map<HttpStatus, String> CREATE_MESSAGES = Map.of(
        HttpStatus.CREATED, "会议室添加成功",
        HttpStatus.CONFLICT, "会议室名称已存在",
        HttpStatus.INTERNAL_SERVER_ERROR, "会议室添加失败"
    );

    // 删除会议室的信息
    public static final Map<HttpStatus, String> DELETE_MESSAGES = Map.of(
        HttpStatus.NO_CONTENT, "会议室删除成功",
        HttpStatus.NOT_FOUND, "会议室不存在",
        HttpStatus.INTERNAL_SERVER_ERROR, "会议室删除失败"
    );

    // 更新会议室信息
    public static final Map<HttpStatus, String> UPDATE_MESSAGES = Map.of(
        HttpStatus.OK, "会议室更新成功",
        HttpStatus.NOT_FOUND, "会议室不存在",
        HttpStatus.CONFLICT, "会议室名称已存在",
        HttpStatus.INTERNAL_SERVER_ERROR, "会议室更新失败"
    );

    // 改变会议室状态信息
    public static final Map<HttpStatus, String> CHANGE_STATUS_MESSAGES = Map.of(
        HttpStatus.OK, "会议室状态修改成功",
        HttpStatus.NOT_FOUND, "会议室不存在",
        HttpStatus.INTERNAL_SERVER_ERROR, "会议室修改状态失败"
    );
}
