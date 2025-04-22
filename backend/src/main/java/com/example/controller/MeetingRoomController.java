package com.example.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.constants.messages.MeetingRoomMessage;
import com.example.entity.MeetingRoom;
import com.example.model.Result;
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

    @Operation(summary = "删除一个会议室")
    @DeleteMapping("/{roomId}")
    public Result<Boolean> deleteMeetingRoom(@PathVariable Long roomId) {
        HttpStatus deleteStatus = meetingRoomService.deleteMeetingRoom(roomId);

        Map<HttpStatus, String> statusMessages = MeetingRoomMessage.CREATE_MESSAGES;

        String message = statusMessages.getOrDefault(deleteStatus, "会议室添加失败");

        return Result.create(deleteStatus, message, deleteStatus.is2xxSuccessful());
    }

    @Operation(summary = "更新一个会议室")
    @PutMapping("{roomId}")
    public Result<Boolean> updateMeetingRoom(@PathVariable Long roomId, @RequestBody MeetingRoom meetingRoom) {
        HttpStatus updateStatus = meetingRoomService.updateMeetingRoom(roomId, meetingRoom);

        Map<HttpStatus, String> statusMessages = MeetingRoomMessage.UPDATE_MESSAGES;

        String message = statusMessages.getOrDefault(updateStatus, "会议室更新失败");

        return Result.create(updateStatus, message, updateStatus.is2xxSuccessful());
    }

    @Operation(summary = "改变会议室状态（可用/不可用）")
    @PatchMapping("{roomId}")
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
}
