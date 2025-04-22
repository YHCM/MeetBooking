package com.example.mapper;

import com.example.entity.Equipment;

import java.util.List;

public interface EquipmentMapper {
    // 获取所有设备
    List<Equipment> selectAllEquipment();

    // 根据ID获取设备
    Equipment selectEquipmentById(Long equipmentId);

    // 添加一个设备
    int insertEquipment(Equipment equipment);

    // 删除一个设备
    int deleteEquipment(Long equipmentId);

    // 更改一个设备
    int updateEquipment(Equipment equipment);
}
