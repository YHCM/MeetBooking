package com.example.mapper;

import java.util.List;

import com.example.entity.Equipment;
import com.example.entity.MeetingRoom;
import com.example.entity.RoomEquipment;

public interface RoomEquipmentMapper {
    // 获取所有关联
    List<RoomEquipment> selectAllRoomEquipment();

    // 增加一个关联
    int insertRoomEquipment(RoomEquipment roomEquipment);

    // 删除一个关联
    int deleteRoomEquipment(RoomEquipment roomEquipment);

    // 根据设备 ID 获取所有拥有此设备的会议室 ID
    List<Long> selectRoomIdsByEquipmentId(Long equipmentId);

    // 根据会议室 ID 获取此会议室拥有的所有设备 ID
    List<Long> selectEquipmentIdsByRoomId(Long roomId);

    // 根据设备 ID 获取所有拥有此设备的会议室
    List<MeetingRoom> selectRoomsByEquipmentId(Long equipmentId);

    // 根据会议室 ID 获取此会议室拥有的所有设备
    List<Equipment> selectEquipmentByRoomId(Long roomId);

    // 根据会议室 ID 获取此会议室拥有的所有设备，不获取设备描述
    List<Equipment> selectEquipmentByRoomIdWithoutDescription(Long roomId);

    // 查看关联是否存在
    boolean isRoomEquipmentExisted(Long roomId, Long equipmentId);
}
