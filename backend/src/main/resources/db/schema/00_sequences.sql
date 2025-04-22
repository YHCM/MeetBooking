DROP SEQUENCE IF EXISTS root_seq;
DROP SEQUENCE IF EXISTS admin_seq;
DROP SEQUENCE IF EXISTS staff_seq;
DROP SEQUENCE IF EXISTS client_seq;
DROP SEQUENCE IF EXISTS request_seq;
DROP SEQUENCE IF EXISTS room_seq;
DROP SEQUENCE IF EXISTS equipment_seq;

-- 用户 ID 序列
CREATE SEQUENCE root_seq START WITH 10001;      -- ROOT 用户的 ID，从 10001 开始
CREATE SEQUENCE admin_seq START WITH 20001;     -- ADMIN 用户的 ID，从 20001 开始
CREATE SEQUENCE staff_seq START WITH 30001;     -- STAFF 用户的 ID，从 30001 开始
CREATE SEQUENCE client_seq START WITH 40001;    -- CLIENT 用户的 ID，从 40001 开始

-- 注册请求 ID 序列
CREATE SEQUENCE request_seq START WITH 10001;   -- 客户注册的申请 ID 序列，从 10001 开始

-- 会议室 ID 序列
CREATE SEQUENCE room_seq START WITH 10001;      -- 会议室 ID 序列，从 10001 开始

-- 设备类型 ID 序列
CREATE SEQUENCE equipment_seq START WITH 10001; -- 设备类型 ID 序列，从 10001 开始

-- 订单 ID 序列
CREATE SEQUENCE order_seq START WITH 10001; -- 设备类型 ID 序列，从 10001 开始