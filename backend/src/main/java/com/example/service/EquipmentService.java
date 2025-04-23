package com.example.service;

import com.example.entity.Equipment;
import com.example.mapper.EquipmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {
    private final EquipmentMapper equipmentMapper;

    // 获取所有设备
    public List<Equipment> selectAllEquipment() {
        return equipmentMapper.selectAllEquipment();
    }

    // 根据ID获取设备
    public Equipment selectEquipmentById(Long equipmentId) {
        return equipmentMapper.selectEquipmentById(equipmentId);
    }

    // 添加一个设备
    public HttpStatus insertEquipment(Equipment equipment) {
        int rowAffected = equipmentMapper.insertEquipment(equipment);
        if (rowAffected > 0) {
            return HttpStatus.CREATED;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    // 删除一个设备
    public HttpStatus deleteEquipment(Long equipmentId) {
        boolean exist = selectEquipmentById(equipmentId) != null;
        if (!exist)
            return HttpStatus.NOT_FOUND;

        int rowAffected = equipmentMapper.deleteEquipment(equipmentId);
        if (rowAffected > 0) {
            return HttpStatus.CREATED;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    // 修改设备信息
    public HttpStatus updateEquipment(Equipment equipment) {
        boolean exist = selectEquipmentById(equipment.getEquipmentId()) != null;
        if (!exist)
            return HttpStatus.NOT_FOUND;

        int rowAffected = equipmentMapper.updateEquipment(equipment);
        if (rowAffected > 0) {
            return HttpStatus.NO_CONTENT;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
