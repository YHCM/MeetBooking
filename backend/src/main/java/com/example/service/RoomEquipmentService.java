package com.example.service;

import java.util.List;

import org.springframework.http.HttpStatus;
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
    public HttpStatus addRoomEquipment(RoomEquipment roomEquipment) {
        // 检查关联是否存在
        boolean isExisted = roomEquipmentMapper.isRoomEquipmentExisted(roomEquipment.getRoomId(), roomEquipment.getEquipmentId());

        if (isExisted) {
            // 如果存在，返回冲突
            return HttpStatus.CONFLICT;
        }

        int rowsAffected = roomEquipmentMapper.insertRoomEquipment(roomEquipment);
        if (rowsAffected <= 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.CREATED;
        }
    }

    // 删除一个关联
    public HttpStatus deleteRoomEquipment(RoomEquipment roomEquipment) {
        boolean isExisted = roomEquipmentMapper.isRoomEquipmentExisted(roomEquipment.getRoomId(), roomEquipment.getEquipmentId());

        if (!isExisted) {
            // 如果不存在，返回未找到
            return HttpStatus.NOT_FOUND;
        }


        int rowsAffected = roomEquipmentMapper.deleteRoomEquipment(roomEquipment);
        if (rowsAffected <= 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.NO_CONTENT;
        }
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
