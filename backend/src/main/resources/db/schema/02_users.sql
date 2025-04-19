DROP TABLE IF EXISTS users;

-- 用户表（包括超级管理员，管理员，员工，客户）
CREATE TABLE users (
    user_id BIGINT COMMENT '主键，用户 ID',
    username VARCHAR(127) UNIQUE NOT NULL COMMENT '用户名，唯一，非空',
    password VARCHAR(63) NOT NULL COMMENT '密码，非空',
    role ENUM('ROOT', 'ADMIN', 'STAFF', 'CLIENT') NOT NULL COMMENT '用户的角色，非空',
    company_name VARCHAR(127) COMMENT '公司名称，仅客户需要',
    phone_number VARCHAR(31) COMMENT '电话号码，目前仅客户需要',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '用户添加的时间',
    is_active BOOLEAN DEFAULT TRUE COMMENT '用户是否被冻结',
    processed_at TIMESTAMP DEFAULT NULL COMMENT '用户状态的最后更新时间，冻结和解冻）',
    processed_by BIGINT DEFAULT NULL COMMENT '处理用户的管理员',
    PRIMARY KEY (user_id),
    -- 客户的公司名称必填，其他用户不能填
    CONSTRAINT check_users_company_name CHECK (
        (role = 'CLIENT' AND company_name IS NOT NULL) OR
        (role IN ('ROOT', 'ADMIN', 'STAFF') AND company_name IS NULL)
    ),
    -- 只有员工和客户可以被冻结
    CONSTRAINT check_forzen_role CHECK (
        (role IN ('STAFF', 'CLIENT')) OR
        (role IN ('ROOT', 'ADMIN') AND is_active = TRUE)
    ),
    -- 外键约束
    FOREIGN KEY (processed_by) REFERENCES users(user_id) ON DELETE SET NULL
);
