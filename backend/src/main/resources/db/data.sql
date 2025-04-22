-- 主 data.sql 文件
-- 用户数据
RUNSCRIPT FROM 'classpath:/db/data/01_users.sql';

-- 注册请求数据
RUNSCRIPT FROM 'classpath:/db/data/02_registration_requests.sql';

-- 会议室数据
RUNSCRIPT FROM 'classpath:/db/data/03_meeting_rooms.sql';

-- 设备类型数据
RUNSCRIPT FROM 'classpath:/db/data/04_equipment.sql';

-- 会议室设备关系数据
RUNSCRIPT FROM 'classpath:/db/data/05_room_equipment.sql';

-- 会议室可用时间数据
RUNSCRIPT FROM 'classpath:/db/data/06_room_availability.sql';
