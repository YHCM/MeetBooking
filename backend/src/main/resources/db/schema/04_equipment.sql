DROP TABLE IF EXISTS equipment;

-- 设备表，会议室用
CREATE TABLE equipment (
    equipment_id BIGINT COMMENT '主键，设备类型 ID',
    equipment_name VARCHAR(127) UNIQUE NOT NULL COMMENT '设备名称',
    additional_price NUMERIC(10, 2) NOT NULL COMMENT '每小时额外租用价格',
    description VARCHAR(255) COMMENT '设备描述',
    PRIMARY KEY (equipment_id)
);