package com.example.service;

import java.time.LocalDate;
import java.util.List;

import com.example.util.Util;
import org.springframework.stereotype.Service;

import com.example.mapper.RoomAvailabilityMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomAvailabilityService {
    private final RoomAvailabilityMapper roomAvailabilityMapper;
    private final Util util;

    // 获取可用状态
    public Integer getRoomAvailability(Long roomId, LocalDate scheduleDate){
        return roomAvailabilityMapper.selectRoomAvailability(roomId, scheduleDate);
    }

    // 添加会议室可用记录
    public boolean addRoomAvailability(Long roomId, LocalDate scheduleDate) {
        return roomAvailabilityMapper.insertRoomAvailability(roomId, scheduleDate) > 0;
    }

    // 批量添加会议室可用记录
    public boolean batchaddRoomAvailability(Long roomId, List<LocalDate> scheduleDateList) {
        return roomAvailabilityMapper.batchInsertRoomAvailability(roomId, scheduleDateList) == scheduleDateList.size();
    }

    // 删除会议室可用记录
    public boolean deleteRoomAvailability(Long roomId, LocalDate scheduleDate) {
        return roomAvailabilityMapper.deleteRoomAvailability(roomId, scheduleDate) > 0;
    }

    // 设置 start（包括）时到end（不包括）时 为不可用状态
    public boolean setLocked(Long roomId, LocalDate scheduleDate, Byte start, Byte end) {
        int mask = util.calculateTimeMask(start, end);
        int oldStatus = roomAvailabilityMapper.selectRoomAvailability(roomId, scheduleDate);
        return roomAvailabilityMapper.changeRoomAvailability(roomId, scheduleDate, oldStatus | mask) > 0;
    }

    // 设置 start（包括）时到end（不包括）时 为可用状态
    public boolean setAvailable(Long roomId, LocalDate scheduleDate, Byte start, Byte end) {
        int mask = ~util.calculateTimeMask(start, end);
        int oldStatus = roomAvailabilityMapper.selectRoomAvailability(roomId, scheduleDate);
        return roomAvailabilityMapper.changeRoomAvailability(roomId, scheduleDate, oldStatus & mask) > 0;
    }
}
