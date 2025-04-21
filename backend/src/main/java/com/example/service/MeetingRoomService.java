package com.example.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.entity.MeetingRoom;
import com.example.mapper.MeetingRoomMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeetingRoomService {
    private final MeetingRoomMapper meetingRoomMapper;
    // private final RoomEquipmentService roomEquipmentService;

    // 获取所有会议室
    public List<MeetingRoom> getAllMeetingRooms() {
        return meetingRoomMapper.selectAllMeetingRooms();
    }

    // 根据 ID 获取会议室
    public MeetingRoom getMeetingRoomById(Long roomId) {
        return meetingRoomMapper.selectMeetingRoomById(roomId);
    }

    // 根据 ID 获取会议室
    public MeetingRoom getMeetingRoomByName(String roomName) {
        return meetingRoomMapper.selectMeetingRoomByName(roomName);
    }

    // 添加一个会议室
    public HttpStatus addMeetingRoom(MeetingRoom meetingRoom) {
        // 查看会议室名称是否存在
        boolean isNameExisted = isMeetingRoomExistedByName(null);

        if (isNameExisted) {
            return HttpStatus.CONFLICT;
        }

        int rowsAffected = meetingRoomMapper.insertMeetingRoom(meetingRoom);
        if (rowsAffected <= 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.CREATED;
        }
    }

    // 删除一个会议室
    public HttpStatus deleteMeetingRoom(Long roomId) {
        boolean isExisted = isMeetingRoomExistedById(roomId);

        if (!isExisted) {
            return HttpStatus.NOT_FOUND;
        }

        int rowsAffected = meetingRoomMapper.deleteMeetingRoom(roomId);
        if (rowsAffected <= 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.OK;
        }
    }

    // 查看会议室是否存在，通过 ID
    public boolean isMeetingRoomExistedById(Long roomId) {
        return getMeetingRoomById(roomId) != null;
    }


    // 查看会议室是否存在，通过会议室名称
    public boolean isMeetingRoomExistedByName(String roomName) {
        return getMeetingRoomByName(roomName) != null;
    }
}
