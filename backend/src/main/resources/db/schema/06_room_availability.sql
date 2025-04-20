DROP TABLE IF EXISTS room_availability;

-- 会议室可用状态表
CREATE TABLE room_availability (
    room_id BIGINT NOT NULL COMMENT '会议室 ID',
    schedule_date DATE NOT NULL COMMENT '日期',
    hour_status INT NOT NULL DEFAULT 14680319 COMMENT '24小时状态(每位表示一小时,0 可用 1不可用)，8:00 - 21:00 可用',
    PRIMARY KEY (room_id, schedule_date),
    FOREIGN KEY (room_id) REFERENCES meeting_rooms(room_id),
    CHECK (hour_status >= 0 AND hour_status <= 16777215) -- 确保只使用24位 (2^24-1)
);