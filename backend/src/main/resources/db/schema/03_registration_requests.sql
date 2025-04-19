DROP TABLE IF EXISTS registration_requests;

-- 客户注册申请表
CREATE TABLE registration_requests (
    request_id BIGINT COMMENT '主键，申请 ID',
    username VARCHAR(127) NOT NULL COMMENT '用户名，非空',
    password VARCHAR(63) NOT NULL COMMENT '密码，非空',
    company_name VARCHAR(127) NOT NULL COMMENT '公司名称，非空',
    phone_number VARCHAR(31) NOT NULL COMMENT '电话号码，非空',
    request_status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING' COMMENT '申请状态，默认是待审批',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '申请请求的创建时间',
    processed_at TIMESTAMP DEFAULT NULL COMMENT '申请的最后更新时间（处理时间）',
    processed_by BIGINT DEFAULT NULL COMMENT '处理请求的管理员',
    PRIMARY KEY (request_id),
    FOREIGN KEY (processed_by) REFERENCES users(user_id) ON DELETE SET NULL
);
