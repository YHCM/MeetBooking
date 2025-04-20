DELETE FROM equipment_types;

-- 插入设备类型数据
INSERT INTO equipment_types (equipment_id, equipment_name, additional_price, description) VALUES 
(NEXT VALUE FOR equipment_seq, '激光笔', 10.00, '演示用激光指示器'),
(NEXT VALUE FOR equipment_seq, '茶水服务', 80.00, '包含茶水和点心'),
(NEXT VALUE FOR equipment_seq, '同声传译设备', 150.00, '多语言同声传译系统'),
(NEXT VALUE FOR equipment_seq, '电子投票系统', 120.00, '会议表决和投票设备'),
(NEXT VALUE FOR equipment_seq, 'LED显示屏', 200.00, '大型高清LED显示屏幕'),
(NEXT VALUE FOR equipment_seq, '智能控制系统', 90.00, '灯光、窗帘等智能控制'),
(NEXT VALUE FOR equipment_seq, '空气净化器', 40.00, '会议室专用空气净化设备'),
(NEXT VALUE FOR equipment_seq, '录像设备', 75.00, '会议全程录像服务');
