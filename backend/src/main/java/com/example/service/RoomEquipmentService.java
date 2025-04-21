package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Equipment;
import com.example.entity.MeetingRoom;
import com.example.entity.RoomEquipment;
import com.example.mapper.RoomEquipmentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomEquipmentService {
    private final RoomEquipmentMapper roomEquipmentMapper;

    // 获取所有关联
    public List<RoomEquipment> getAllRoomEquipment() {
        return roomEquipmentMapper.selectAllRoomEquipment();
    }

    // 增加一个关联
    public boolean addRoomEquipment(RoomEquipment roomEquipment) {
        return roomEquipmentMapper.insertRoomEquipment(roomEquipment) > 0;
    }

    // 删除一个关联
    public boolean deleteRoomEquipment(RoomEquipment roomEquipment) {
        return roomEquipmentMapper.deleteRoomEquipment(roomEquipment) > 0;
    }

    // 根据设备 ID 获取所有拥有此设备的会议室 ID
    public List<Long> getRoomIdsByEquipmentId(Long equipmentId) {
        return roomEquipmentMapper.selectRoomIdsByEquipmentId(equipmentId);
    }

    // 根据会议室 ID 获取此会议室拥有的所有设备 ID
    public List<Long> getEquipmentIdsByRoomId(Long roomId) {
        return roomEquipmentMapper.selecEquipmentIdsByRoomId(roomId);
    }

    // 根据设备 ID 获取所有拥有此设备的会议室
    public List<MeetingRoom> getRoomsByEquipmentId(Long equipmentId) {
        return roomEquipmentMapper.selecRoomsByEquipmentId(equipmentId);
    }

    // 根据会议室 ID 获取此会议室拥有的所有设备
    public List<Equipment> getEquipmentByRoomId(Long roomId) {
        return roomEquipmentMapper.selecEquipmentByRoomId(roomId);
    }
}
