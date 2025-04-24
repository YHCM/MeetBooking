package com.example.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.entity.Equipment;
import com.example.entity.MeetingRoom;
import com.example.mapper.MeetingRoomMapper;
import com.example.model.SearchRoomRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeetingRoomService {
    private final MeetingRoomMapper meetingRoomMapper;
    private final RoomEquipmentService roomEquipmentService;

    // 获取所有会议室
    public List<MeetingRoom> getAllMeetingRooms() {
        return meetingRoomMapper.selectAllMeetingRooms();
    }

    // 获取所有可用的会议室
    public List<MeetingRoom> getAvailableMeetingRooms() {
        return meetingRoomMapper.selectAvailableMeetingRooms();
    }

    // 根据 ID 获取会议室
    public MeetingRoom getMeetingRoomById(Long roomId) {
        return meetingRoomMapper.selectMeetingRoomById(roomId);
    }

    // 根据 name 获取会议室
    public MeetingRoom getMeetingRoomByName(String roomName) {
        return meetingRoomMapper.selectMeetingRoomByName(roomName);
    }

    // 添加一个会议室
    public HttpStatus addMeetingRoom(MeetingRoom meetingRoom) {
        // 查看会议室名称是否存在
        boolean isNameExisted = isMeetingRoomExistedByName(meetingRoom.getRoomName());

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

    // 更新一个会议室
    public HttpStatus updateMeetingRoom(MeetingRoom meetingRoom) {
        boolean isExisted = isMeetingRoomExistedById(meetingRoom.getRoomId());

        if (!isExisted) {
            return HttpStatus.NOT_FOUND;
        }

        int rowsAffected = meetingRoomMapper.updateMeetingRoom(meetingRoom);
        if (rowsAffected <= 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.OK;
        }
    }

    // 更新会议室状态（可用/不可用）
    public HttpStatus changeMeetingRoomStatus(Long roomId) {
        boolean isExisted = isMeetingRoomExistedById(roomId);

        if (!isExisted) {
            return HttpStatus.NOT_FOUND;
        }

        int rowsAffected = meetingRoomMapper.changeMeetingRoomStatus(roomId);
        if (rowsAffected <= 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.OK;
        }  
    }

    // 获取会议室价格，通过 ID
    public BigDecimal getTotalPriceById(Long roomId) {
        boolean isExisted = isMeetingRoomExistedById(roomId);

        // 会议室不存在，返回负数，之后 controller 返回 404 NOT_FOUND
        if (!isExisted) {
            return BigDecimal.valueOf(-1);
        }

        // 获取总价格
        BigDecimal totalPrice = meetingRoomMapper.getTotalPriceById(roomId);
        return totalPrice;
    }

    // 筛选会议室
    public List<Long> searchAvailableRoomIds(SearchRoomRequest request) {
        if (request.getStartTime() >= request.getEndTime()) {
            return List.of();   // 空的列表
        }

        Integer timeMask = calculateTimeMask(request.getStartTime(), request.getEndTime());
        return meetingRoomMapper.searchMeetingRoomIds(request.getRoomType(), request.getDate(), timeMask);
    }

    // 查看会议室是否存在，通过 ID
    public boolean isMeetingRoomExistedById(Long roomId) {
        return getMeetingRoomById(roomId) != null;
    }


    // 查看会议室是否存在，通过会议室名称
    public boolean isMeetingRoomExistedByName(String roomName) {
        return getMeetingRoomByName(roomName) != null;
    }

    // 获取会议室拥有的所有设备
    public List<Equipment> getEquipmentByRoomId(Long roomId) {
        return roomEquipmentService.getEquipmentByRoomId(roomId);
    }

    // 计算时间掩码
    private Integer calculateTimeMask(Integer startTime, Integer endTime) {
        if (startTime >= endTime) {
            return 0xFFFFFF;
        }

        int mask = (1 << (endTime - startTime)) - 1;
        return mask << startTime;
    }
}
