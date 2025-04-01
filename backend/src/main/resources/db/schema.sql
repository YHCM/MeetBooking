DROP TABLE IF EXISTS users;

CREATE SEQUENCE root_seq START WITH 10001;      -- ROOT 用户的 ID，从 10001 开始
CREATE SEQUENCE admin_seq START WITH 20001;     -- ADMIN 用户的 ID，从 20001 开始
CREATE SEQUENCE staff_seq START WITH 30001;     -- STAFF 用户的 ID，从 30001 开始
CREATE SEQUENCE client_seq START WITH 40001;    -- CLIENT 用户的 ID，从 40001 开始

-- 用户表（包括超级管理员，管理员，员工，客户）
CREATE TABLE users (
                       user_id BIGINT PRIMARY KEY COMMENT '主键，用户 ID',
                       username VARCHAR(127) NOT NULL COMMENT '用户名，非空',
                       password VARCHAR(63) NOT NULL COMMENT '密码，非空',
                       role ENUM('ROOT', 'ADMIN', 'STAFF', 'CLIENT') NOT NULL COMMENT '用户的身份，非空',
                       company_name VARCHAR(127) COMMENT '公司名字，仅客户需要',
                       phone_number VARCHAR(31) COMMENT '电话号码',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);