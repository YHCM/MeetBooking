package com.example.mapper;

import java.time.LocalDate;
import java.util.List;

public interface RoomAvailabilityMapper {
    // 获取可用状态
    Integer selectRoomAvailability(Long roomId, LocalDate scheduleDate);

    // 插入可用状态
    int insertRoomAvailability(Long roomId, LocalDate scheduleDate);

    // 批量插入可用状态
    int batchInsertRoomAvailability(Long roomId, List<LocalDate> scheduleDateList);

    // 删除可用状态
    int deleteRoomAvailability(Long roomId, LocalDate scheduleDate);

    // 修改每小时状态
    int changeRoomAvailability(Long roomId, LocalDate scheduleDate, Integer hourStatus);
}
