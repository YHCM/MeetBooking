package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mapper.RoomAvailabilityMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomAvailabilityService {
    private final RoomAvailabilityMapper roomAvailabilityMapper;

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
}
