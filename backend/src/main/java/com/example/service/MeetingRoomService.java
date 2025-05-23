package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.entity.RoomEquipment;
import com.example.model.MeetingRoomInfo;
import com.example.util.Util;
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
    private final RoomAvailabilityService roomAvailabilityService;
    private final Util util;

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

    // 获取所有会议室详细信息
    public List<MeetingRoomInfo> getAllMeetingRoomInfos() {
        return meetingRoomMapper.selectAllMeetingRoomInfos();
    }

    // 根据 ID 获取会议室详细信息
    public MeetingRoomInfo getMeetingRoomInfoById(Long roomId) {
        return meetingRoomMapper.selectMeetingRoomInfoById(roomId);
    }

    // 根据 ID 列表获取会议室详细信息列表
    public List<MeetingRoomInfo> getMeetingRoomInfosByIds(List<Long> roomIds) {
        if (roomIds == null || roomIds.isEmpty()) {
            // 如果没有传，直接返回空列表，不去查询数据库
            return Collections.emptyList();
        }

        return meetingRoomMapper.selectMeetingRoomInfosByIds(roomIds);
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
        }

        // 添加可用状态
        boolean addStatus = addRoomAvailability(meetingRoom.getRoomId());
        if (addStatus) {
            return HttpStatus.CREATED;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
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
            return HttpStatus.NO_CONTENT;
        }
    }

    // 更新一个会议室
    public HttpStatus updateMeetingRoom(MeetingRoom meetingRoom) {
        boolean isExisted = isMeetingRoomExistedById(meetingRoom.getRoomId());

        if (!isExisted) {
            return HttpStatus.NOT_FOUND;
        }

        // 查看会议室名称是否存在
        boolean isNameExisted = isMeetingRoomExistedByName(meetingRoom.getRoomName(), meetingRoom.getRoomId());

        if (isNameExisted) {
            return HttpStatus.CONFLICT;
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
        return meetingRoomMapper.getTotalPriceById(roomId);
    }

    // 筛选会议室
    public List<MeetingRoomInfo> searchAvailableRooms(SearchRoomRequest request) {
        if (request.getStartTime() >= request.getEndTime()) {
            return List.of();   // 空的列表
        }

        Integer timeMask = util.calculateTimeMask(request.getStartTime(), request.getEndTime());
        return meetingRoomMapper.searchMeetingRooms(request.getRoomType(), request.getDate(), request.getAttendance(), timeMask)
                .stream().filter(room ->
                        new HashSet<>(room.getEquipmentIds()).containsAll(request.getRequiredEquipments()))
                .toList();
    }

    // 查看会议室是否存在，通过 ID
    public boolean isMeetingRoomExistedById(Long roomId) {
        return getMeetingRoomById(roomId) != null;
    }


    // 查看会议室是否存在，通过会议室名称
    public boolean isMeetingRoomExistedByName(String roomName) {
        return getMeetingRoomByName(roomName) != null;
    }

    // 查看会议室是否存在，通过会议室名称（排除某个ID的）
    public boolean isMeetingRoomExistedByName(String roomName, Long exclude) {
        var room0 = getMeetingRoomByName(roomName);
        if (room0 == null) return false;
        return !room0.getRoomId().equals(exclude);
    }

    // 获取会议室拥有的所有设备
    public List<Equipment> getEquipmentByRoomId(Long roomId) {
        return roomEquipmentService.getEquipmentByRoomId(roomId);
    }

    // 为会议室添加一个设备
    public HttpStatus addEquipment(RoomEquipment roomEquipment) {
        return roomEquipmentService.addRoomEquipment(roomEquipment);
    }

    // 为会议室删除一个设备
    public HttpStatus deleteEquipment(RoomEquipment roomEquipment) {
        return roomEquipmentService.deleteRoomEquipment(roomEquipment);
    }

    // 添加 60 天可用数据
    private boolean addRoomAvailability(Long roomId) {
        LocalDate today = LocalDate.now();
        List<LocalDate> scheduleDateList = IntStream.range(0, 60)
                .mapToObj(today::plusDays)
                .collect(Collectors.toList());

        return roomAvailabilityService.batchaddRoomAvailability(roomId, scheduleDateList);
    }
}
