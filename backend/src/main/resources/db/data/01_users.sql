DELETE FROM users;

-- 密码就是对应的角色小写
-- 插入 ROOT 用户（使用 root_seq）
INSERT INTO users (user_id, username, password, role)
VALUES (NEXT VALUE FOR root_seq, 'root', '$2a$10$uLbCvSxgSsTStJurm58Hber2jViqIjTwjFAhGP/IHwRBNJpvodjKa', 'ROOT');

-- 插入 ADMIN 用户（使用 admin_seq）
INSERT INTO users (user_id, username, password, role)
VALUES (NEXT VALUE FOR admin_seq, 'admin', '$2a$10$vd9BtirJh6F.vphjwPSEIOc13BUoNeoTTx3VMdQc5K4cq6zi1t1hG', 'ADMIN');

-- 插入 STAFF 用户（使用 staff_seq）
INSERT INTO users (user_id, username, password, role)
VALUES (NEXT VALUE FOR staff_seq, 'staff', '$2a$10$Tlfg/uzVpbyIihFwrdgkU.t3E6CeUIZD99BC5qh09r89yJtuFFgci', 'STAFF');

-- 插入 CLIENT 用户（使用 client_seq）
INSERT INTO users (user_id, username, password, role, company_name, phone_number)
VALUES (NEXT VALUE FOR client_seq, 'client', '$2a$10$EnTQaiXERPXNHdDMz9.B1ueWSN17zaIFzywglwpqQRGSe/UTWiLIW', 'CLIENT', 'company', '1234567890');
