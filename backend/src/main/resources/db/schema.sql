-- 主 schema.sql 文件
-- ID 序列
RUNSCRIPT FROM 'classpath:/db/schema/00_sequences.sql';

-- 用户表
RUNSCRIPT FROM 'classpath:/db/schema/01_users.sql';

-- 注册请求表
RUNSCRIPT FROM 'classpath:/db/schema/02_registration_requests.sql';

-- 会议室表
RUNSCRIPT FROM 'classpath:/db/schema/03_meeting_rooms.sql';

-- 设备类型表
RUNSCRIPT FROM 'classpath:/db/schema/04_equipment_types.sql';

-- 会议室设备关系表
RUNSCRIPT FROM 'classpath:/db/schema/05_room_equipment.sql';

-- 会议室对应日期使用情况表
RUNSCRIPT FROM 'classpath:/db/schema/06_room_availability.sql';
