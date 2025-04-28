DROP TABLE IF EXISTS orders;

-- 订单表
CREATE TABLE orders (
    order_id BIGINT NOT NULL COMMENT '订单 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    room_id BIGINT NOT NULL COMMENT '会议室 ID',
    booking_date DATE NOT NULL COMMENT '预定日期',
    start_hour TINYINT NOT NULL COMMENT '开始时间(8-20)',
    end_hour TINYINT NOT NULL COMMENT '结束时间(9-21)',
    price NUMERIC(10,2) NOT NULL COMMENT '价格',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
    status ENUM(
        'PENDING',      -- 待处理(待支付，默认状态)
        'CANCELLED',    -- 已取消(超时未支付或主动取消)
        'COMPLETED',    -- 已完成(已支付并使用)
        'REFUNDED'      -- 已退款(订单失效)
    ) DEFAULT 'PENDING' COMMENT '订单状态',
    PRIMARY KEY (order_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (room_id) REFERENCES meeting_rooms(room_id),
    CHECK (start_hour >= 8 AND start_hour <= 20),
    CHECK (end_hour >= 9 AND end_hour <= 21),
    CHECK (start_hour < end_hour)
);