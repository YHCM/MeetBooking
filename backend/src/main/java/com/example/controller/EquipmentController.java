package com.example.controller;

import com.example.constants.messages.EquipmentMessage;
import com.example.entity.Equipment;
import com.example.model.Result;
import com.example.service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    @Operation(summary = "获取所有设备")
    @GetMapping
    public Result<List<Equipment>> getAllEquipment() {
        return Result.ok("所有设备信息", equipmentService.getAllEquipment());
    }

    @Operation(summary = "根据ID获取设备")
    @GetMapping("/{id}")
    public Result<Equipment> getEquipmentById(@PathVariable Long id) {
        var equipment = equipmentService.getEquipmentById(id);

        if (equipment == null)
            return Result.create(HttpStatus.NOT_FOUND, "设备不存在", null);
        return Result.ok("设备信息", equipment);
    }

    @Operation(summary = "添加一个设备")
    @PostMapping
    public Result<Boolean> addEquipment(@RequestBody Equipment equipment) {
        var addStatus = equipmentService.insertEquipment(equipment);
        var statusMessages = EquipmentMessage.CREATE_MESSAGES;
        var message = statusMessages.getOrDefault(addStatus, "设备添加失败");

        return Result.create(addStatus, message, addStatus.is2xxSuccessful());
    }

    @Operation(summary = "删除一个设备")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteEquipment(@PathVariable Long id) {
        var deleteStatus = equipmentService.deleteEquipment(id);
        var statusMessages = EquipmentMessage.DELETE_MESSAGES;
        var message = statusMessages.getOrDefault(deleteStatus, "设备删除失败");

        return Result.create(deleteStatus, message, deleteStatus.is2xxSuccessful());
    }

    @Operation(summary = "修改设备信息")
    @PutMapping
    public Result<Boolean> updateEquipment(@RequestBody Equipment equipment) {
        var httpStatus = equipmentService.updateEquipment(equipment);
        var statusMessages = EquipmentMessage.UPDATE_MESSAGES;
        var message = statusMessages.getOrDefault(httpStatus, "设备更新失败");

        return Result.create(httpStatus, message, httpStatus.is2xxSuccessful());
    }
}
