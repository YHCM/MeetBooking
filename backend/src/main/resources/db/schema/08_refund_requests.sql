DROP TABLE IF EXISTS refund_requests;

-- 客户退订申请表
CREATE TABLE refund_requests (
    refund_id BIGINT COMMENT '主键，退订 ID',
    order_id BIGINT NOT NULL COMMENT '订单 ID',
    request_status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING' COMMENT '申请状态，默认是待审批',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '退订请求的创建时间',
    processed_at TIMESTAMP DEFAULT NULL COMMENT '退订请求的处理时间（请求只能被处理一次）',
    processed_by BIGINT DEFAULT NULL COMMENT '处理请求的员工或管理员',
    refund_amount NUMERIC(10,2) NOT NULL COMMENT '实际退款金额，通过原价和时间计算',
    PRIMARY KEY (refund_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (processed_by) REFERENCES users(user_id) ON DELETE SET NULL
)