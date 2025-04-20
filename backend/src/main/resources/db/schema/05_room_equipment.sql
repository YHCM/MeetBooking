DROP TABLE IF EXISTS room_equipment;

-- 会议室与设备类型关系表
CREATE TABLE room_equipment (
    room_id BIGINT COMMENT '会议室 ID',
    equipment_id BIGINT COMMENT '设备类型 ID',
    PRIMARY KEY (room_id, equipment_id),
    FOREIGN KEY (room_id) REFERENCES meeting_rooms(room_id),
    FOREIGN KEY (equipment_id) REFERENCES equipment_types(equipment_id)
);