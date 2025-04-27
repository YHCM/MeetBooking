package com.example.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.example.constants.messages.EquipmentMessage;
import com.example.entity.RoomEquipment;
import com.example.model.MeetingRoomInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.constants.messages.MeetingRoomMessage;
import com.example.entity.Equipment;
import com.example.entity.MeetingRoom;
import com.example.model.Result;
import com.example.model.SearchRoomRequest;
import com.example.service.MeetingRoomService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;

    @Operation(summary = "获取所有会议室")
    @GetMapping
    public Result<List<MeetingRoom>> getAllMeetingRooms() {
        List<MeetingRoom> meetingRoomList = meetingRoomService.getAllMeetingRooms();

        return Result.ok("所有会议室信息", meetingRoomList);
    }

    @Operation(summary = "获取所有会议室详细信息")
    @GetMapping("/info")
    public Result<List<MeetingRoomInfo>> getAllMeetingRoomInfos() {
        return Result.ok("所有会议室信息", meetingRoomService.getAllMeetingRoomInfos());
    }

    @Operation(summary = "根据 ID 获取会议室详细信息")
    @GetMapping("/{roomId}/info")
    public Result<MeetingRoomInfo> getMeetingRoomInfoById(@PathVariable Long roomId) {
        MeetingRoomInfo meetingRoomInfo = meetingRoomService.getMeetingRoomInfoById(roomId);

        if (meetingRoomInfo == null) {
            return Result.create(HttpStatus.NOT_FOUND, "会议室不存在", null);
        } else {
            return Result.ok("会议室详细信息", meetingRoomInfo);
        }
    }

    @Operation(summary = "根据 ID 列表获取会议室详细信息列表")
    @PostMapping("/info/batch")
    public Result<List<MeetingRoomInfo>> getMeetingRoomInfosByIds(@RequestBody Map<String, List<Long>> request) {
        // 这里用 Map 是为了不出现 json 反序列化失败的警告
        List<Long> roomIds = request.get("roomIds");
        List<MeetingRoomInfo> meetingRoomInfos = meetingRoomService.getMeetingRoomInfosByIds(roomIds);

        return Result.ok("批量查询会议室详细信息", meetingRoomInfos);
    }

    @Operation(summary = "获取所有可用会议室")
    @GetMapping("/available")
    public Result<List<MeetingRoom>> getAvailableMeetingRooms() {
        List<MeetingRoom> availableMeetingRoomList = meetingRoomService.getAvailableMeetingRooms();

        return Result.ok("所有可用", availableMeetingRoomList);
    }

    @Operation(summary = "根据 ID 获取会议室信息")
    @GetMapping("/{roomId}")
    public Result<MeetingRoom> getMeetingRoomById(@PathVariable Long roomId) {
        MeetingRoom meetingRoom = meetingRoomService.getMeetingRoomById(roomId);
        if (meetingRoom == null) {
            return Result.create(HttpStatus.NOT_FOUND, "会议室不存在", null);
        } else {
            return Result.ok("会议室信息", meetingRoom);
        }
    }

    @Operation(summary = "添加一个会议室")
    @PostMapping
    public Result<Boolean> createMeetingRoom(@RequestBody MeetingRoom meetingRoom) {
        HttpStatus createStatus = meetingRoomService.addMeetingRoom(meetingRoom);

        Map<HttpStatus, String> statusMessages = MeetingRoomMessage.CREATE_MESSAGES;

        String message = statusMessages.getOrDefault(createStatus, "会议室添加失败");

        return Result.create(createStatus, message, createStatus.is2xxSuccessful());
    }

    @Operation(summary = "更新一个会议室")
    @PutMapping
    public Result<Boolean> updateMeetingRoom(@RequestBody MeetingRoom meetingRoom) {
        HttpStatus updateStatus = meetingRoomService.updateMeetingRoom(meetingRoom);

        Map<HttpStatus, String> statusMessages = MeetingRoomMessage.UPDATE_MESSAGES;

        String message = statusMessages.getOrDefault(updateStatus, "会议室更新失败");

        return Result.create(updateStatus, message, updateStatus.is2xxSuccessful());
    }

    @Operation(summary = "改变会议室状态（可用/不可用）")
    @PatchMapping("{roomId}/status")
    public Result<Boolean> changeMeetingRoomStatus(@PathVariable Long roomId) {
        HttpStatus changeStatus = meetingRoomService.changeMeetingRoomStatus(roomId);

        Map<HttpStatus, String> statusMessages = MeetingRoomMessage.CHANGE_STATUS_MESSAGES;

        String message = statusMessages.getOrDefault(changeStatus, "会议室更新失败");

        return Result.create(changeStatus, message, changeStatus.is2xxSuccessful());
    }

    @Operation(summary = "获取会议室价格")
    @GetMapping("{roomId}/price")
    public Result<BigDecimal> getTotalPrice(@PathVariable Long roomId) {
        BigDecimal price = meetingRoomService.getTotalPriceById(roomId);

        if (price.equals(BigDecimal.valueOf(-1))) {
            return Result.create(HttpStatus.NOT_FOUND, "会议室不存在", null);
        }

        return Result.ok("会议室总价格", price);
    }

    @Operation(summary = "根据会议室 ID 获取拥有的设备")
    @GetMapping("/{roomId}/equipment")
    public Result<List<Equipment>> getEquipment(@PathVariable Long roomId) {
        List<Equipment> equipmentList = meetingRoomService.getEquipmentByRoomId(roomId);

        return Result.ok("会议室拥有的设备", equipmentList);
    }

    @Operation(summary = "会议室添加一个设备")
    @PostMapping("/equipment")
    public Result<Boolean> addEquipment(@RequestBody RoomEquipment roomEquipment) {
        var addStatus = meetingRoomService.addEquipment(roomEquipment);
        var statusMessages = EquipmentMessage.CREATE_MESSAGES;
        var message = statusMessages.getOrDefault(addStatus, "设备添加失败");

        return Result.create(addStatus, message, addStatus.is2xxSuccessful());
    }

    @Operation(summary = "会议室删除一个设备")
    @DeleteMapping("/equipment")
    public Result<Boolean> deleteEquipment(@RequestBody RoomEquipment roomEquipment) {
        var deleteStatus = meetingRoomService.deleteEquipment(roomEquipment);
        var statusMessages = EquipmentMessage.DELETE_MESSAGES;
        var message = statusMessages.getOrDefault(deleteStatus, "设备删除失败");

        return Result.create(deleteStatus, message, deleteStatus.is2xxSuccessful());
    }

    @Operation(summary = "搜索会议室，类型，日期，时间段，设备")
    @PostMapping("/search")
    public Result<List<MeetingRoomInfo>> searchMeetingRoomIds(@RequestBody SearchRoomRequest request) {
        var idList = meetingRoomService.searchAvailableRooms(request);
        return Result.ok("符合要求的会议室列表", idList);
    }
}
