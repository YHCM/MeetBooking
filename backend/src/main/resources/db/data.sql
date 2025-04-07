DELETE FROM users;
DELETE FROM registration_requests;

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


-- 插入客户注册申请信息（之后记得改密码）
INSERT INTO registration_requests (request_id, username, password, company_name, phone_number) VALUES
(NEXT VALUE FOR request_seq, 'client_request1', '123456', '1号公司', '11111'),
(NEXT VALUE FOR request_seq, 'client_request2', '123456', '2号公司', '22222'),
(NEXT VALUE FOR request_seq, 'client_request3', '123456', '3号公司', '33333'),
(NEXT VALUE FOR request_seq, 'client_request4', '123456', '4号公司', '44444'),
(NEXT VALUE FOR request_seq, 'client_request5', '123456', '5号公司', '55555');
