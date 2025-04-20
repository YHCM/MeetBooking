DELETE FROM meeting_rooms;

-- 插入会议室数据
INSERT INTO meeting_rooms (room_id, room_name, room_type, capacity, base_price, room_status) VALUES 
(NEXT VALUE FOR room_seq, '1号会议室', 'CLASSROOM', 30, 150.00, TRUE),
(NEXT VALUE FOR room_seq, '2号会议室', 'CLASSROOM', 40, 180.00, TRUE),
(NEXT VALUE FOR room_seq, '3号会议室', 'ROUND_TABLE', 15, 250.00, TRUE),
(NEXT VALUE FOR room_seq, '4号会议室', 'ROUND_TABLE', 25, 320.00, TRUE),
(NEXT VALUE FOR room_seq, '5号会议室', 'CLASSROOM', 60, 220.00, FALSE),
(NEXT VALUE FOR room_seq, '6号会议室', 'CLASSROOM', 80, 280.00, TRUE),
(NEXT VALUE FOR room_seq, '7号会议室', 'ROUND_TABLE', 20, 300.00, TRUE),
(NEXT VALUE FOR room_seq, '8号会议室', 'CLASSROOM', 100, 350.00, TRUE),
(NEXT VALUE FOR room_seq, '9号会议室', 'CLASSROOM', 50, 200.00, FALSE),
(NEXT VALUE FOR room_seq, '10号会议室', 'ROUND_TABLE', 30, 280.00, TRUE);
