package com.example.mapper;

import java.util.List;

import com.example.entity.MeetingRoom;

public interface MeetingRoomMapper {
    // 获取所有的会议室
    List<MeetingRoom> selectAllMeetingRooms();

    // 根据 ID 获取会议室
    MeetingRoom selectMeetingRoomById(Long roomId);

    // 根据会议室名称获取会议室
    MeetingRoom selectMeetingRoomByName(String roomName);

    // 添加一个会议室
    int insertMeetingRoom(MeetingRoom meetingRoom);

    // 更新会议室信息
    int updateMeetingRoom(Long roomId, MeetingRoom meetingRoom);

    // 修改会议室状态
    int updateMeetingRoomStatus(Long roomId);

    // 删除会议室
    int deleteMeetingRoom(Long roomId);
}
