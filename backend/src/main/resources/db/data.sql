DELETE FROM users;

-- 插入 ROOT 用户（使用 root_seq）
INSERT INTO users (user_id, username, password, role)
VALUES (NEXT VALUE FOR root_seq, 'root_user', 'root_password', 'ROOT');


-- 插入 ADMIN 用户（使用 admin_seq）
INSERT INTO users (user_id, username, password, role)
VALUES (NEXT VALUE FOR admin_seq, 'admin_user', 'admin_password', 'ADMIN');


-- 插入 STAFF 用户（使用 staff_seq）
INSERT INTO users (user_id, username, password, role)
VALUES (NEXT VALUE FOR staff_seq, 'staff_user', 'staff_password', 'STAFF');


-- 插入 CLIENT 用户（使用 client_seq）
INSERT INTO users (user_id, username, password, role, company_name, phone_number)
VALUES (NEXT VALUE FOR client_seq, 'client_user', 'client_password', 'CLIENT', 'Test Company', '1234567890');
