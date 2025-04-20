DROP TABLE IF EXISTS meeting_rooms;

-- 会议室表
CREATE TABLE meeting_rooms (
    room_id BIGINT COMMENT '主键，会议室 ID',
    room_name VARCHAR(127) UNIQUE NOT NULL COMMENT '会议室名称，唯一，非空',
    room_type ENUM('CLASSROOM', 'ROUND_TABLE') NOT NULL COMMENT '会议室类型，非空',
    capacity INT NOT NULL COMMENT '会议室容量',
    base_price NUMERIC(10, 2) NOT NULL COMMENT '每小时基本租用价格',
    room_status BOOLEAN NOT NULL DEFAULT TRUE COMMENT '会议室状态，可用和不可用，非空',
    created_at TIMESTAMP DEFAULT CURRENT_TIME COMMENT '会议室添加时间',
    PRIMARY KEY (room_id)
);
