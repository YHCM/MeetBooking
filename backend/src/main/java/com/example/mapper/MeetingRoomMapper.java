package com.example.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.entity.MeetingRoom;
import com.example.entity.RoomType;
import com.example.model.MeetingRoomInfo;

public interface MeetingRoomMapper {
    // 获取所有的会议室
    List<MeetingRoom> selectAllMeetingRooms();

    // 获取所有可用的会议室
    List<MeetingRoom> selectAvailableMeetingRooms();

    // 根据 ID 获取会议室
    MeetingRoom selectMeetingRoomById(Long roomId);

    // 获取所有会议室详细信息
    List<MeetingRoomInfo> selectAllMeetingRoomInfos();

    // 根据 ID 获取会议室详细信息
    MeetingRoomInfo selectMeetingRoomInfoById(Long roomId);

    // 根据 ID 列表获取会议室详细信息列表
    List<MeetingRoomInfo> selectMeetingRoomInfosByIds(List<Long> roomIds);

    // 根据会议室名称获取会议室
    MeetingRoom selectMeetingRoomByName(String roomName);

    // 添加一个会议室
    int insertMeetingRoom(MeetingRoom meetingRoom);

    // 更新会议室信息
    int updateMeetingRoom(MeetingRoom meetingRoom);

    // 修改会议室状态
    int changeMeetingRoomStatus(Long roomId);

    // 获取会议室价格
    BigDecimal getTotalPriceById(Long roomId);

    // 筛选出符合条件的会议室 ID
    List<MeetingRoomInfo> searchMeetingRooms(RoomType roomType, LocalDate date, Integer attendance, Integer timeMask);
}
