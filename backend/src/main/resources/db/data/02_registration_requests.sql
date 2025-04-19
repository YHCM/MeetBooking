DELETE FROM registration_requests;

-- 插入客户注册申请信息（初始密码是 123456）
INSERT INTO registration_requests (request_id, username, password, company_name, phone_number) VALUES
(NEXT VALUE FOR request_seq, 'client_request1', '$2a$10$eG20Zek6zZlWvQCcteUMO.qj6AeEdBUlpo6rNtenkRGZbPISHvINy', '1号公司', '11111'),
(NEXT VALUE FOR request_seq, 'client_request2', '$2a$10$aAGnXgPypiIKeIyjPMAIr.j0MrVS60KXW2nydp09lkhBxYGxtwFI.', '2号公司', '22222'),
(NEXT VALUE FOR request_seq, 'client_request3', '$2a$10$Nax34FdSSK2OD2AeCmpRau0LL1Cmza2UbZbgsDDacjaxhMtw7wuUW', '3号公司', '33333'),
(NEXT VALUE FOR request_seq, 'client_request4', '$2a$10$7a2dzQPpFztc8C74Cj/Xx.oMWZ8HxrTnPBxkeB9RTHVS6v8ZGWbYy', '4号公司', '44444'),
(NEXT VALUE FOR request_seq, 'client_request5', '$2a$10$HLBdQwh0ht21HbRUvPEqs.iHr0qBWlCTlEmEJRhSYR26rGBmF29Qu', '5号公司', '55555');
