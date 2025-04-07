DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS client_registration_requests;

DROP SEQUENCE IF EXISTS root_seq;
DROP SEQUENCE IF EXISTS admin_seq;
DROP SEQUENCE IF EXISTS staff_seq;
DROP SEQUENCE IF EXISTS client_seq;

DROP SEQUENCE IF EXISTS request_seq;

-- 用户 ID 序列
CREATE SEQUENCE root_seq START WITH 10001;      -- ROOT 用户的 ID，从 10001 开始
CREATE SEQUENCE admin_seq START WITH 20001;     -- ADMIN 用户的 ID，从 20001 开始
CREATE SEQUENCE staff_seq START WITH 30001;     -- STAFF 用户的 ID，从 30001 开始
CREATE SEQUENCE client_seq START WITH 40001;    -- CLIENT 用户的 ID，从 40001 开始

CREATE SEQUENCE request_seq START WITH 10001;   -- 客户注册的申请 ID 序列，从 10001 开始

-- 用户表（包括超级管理员，管理员，员工，客户）
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY COMMENT '主键，用户 ID',
    username VARCHAR(127) UNIQUE NOT NULL COMMENT '用户名，唯一，非空',
    password VARCHAR(63) NOT NULL COMMENT '密码，非空',
    role ENUM('ROOT', 'ADMIN', 'STAFF', 'CLIENT') NOT NULL COMMENT '用户的角色，非空',
    company_name VARCHAR(127) COMMENT '公司名称，仅客户需要',
    phone_number VARCHAR(31) COMMENT '电话号码，目前仅客户需要',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '用户添加的时间'
);

-- 客户注册申请表
CREATE TABLE client_registration_requests (
    request_id BIGINT PRIMARY KEY COMMENT '主键，申请 ID',
    username VARCHAR(127) UNIQUE NOT NULL COMMENT '用户名，唯一，非空',
    password VARCHAR(63) NOT NULL COMMENT '密码，非空',
    company_name VARCHAR(127) NOT NULL COMMENT '公司名称，非空',
    phone_number VARCHAR(31) NOT NULL COMMENT '电话号码，非空',
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING' COMMENT '申请状态，默认是待审批',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '申请请求的创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '申请的最后更新时间（处理时间）'
)