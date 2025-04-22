DROP TABLE IF EXISTS room_availability;

-- 订单表
CREATE TABLE `order` (
    order_id BIGINT NOT NULL COMMENT '订单 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    room_id BIGINT NOT NULL COMMENT '会议室 ID',
    schedule_date DATE NOT NULL COMMENT '日期',
    hours INT NOT NULL DEFAULT 0 COMMENT '用户要求的使用时间，24小时状态',
    price NUMERIC(10,2) NOT NULL COMMENT '价格',
    PRIMARY KEY (order_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (room_id) REFERENCES meeting_rooms(room_id),
    CHECK (hours >= 0 AND hours <= 0xFFFFFF) -- 确保只使用24位 (2^24-1)
);