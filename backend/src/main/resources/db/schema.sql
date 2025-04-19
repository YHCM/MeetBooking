-- 主 schema.sql 文件
-- ID 序列
RUNSCRIPT FROM 'classpath:/db/schema/01_sequences.sql';

-- 用户表
RUNSCRIPT FROM 'classpath:/db/schema/02_users.sql';

-- 注册请求表
RUNSCRIPT FROM 'classpath:/db/schema/03_registration_requests.sql';
