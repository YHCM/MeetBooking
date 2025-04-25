DELETE FROM room_availability;

-- 插入包括今天的 60 天数据
INSERT INTO room_availability (room_id, schedule_date)
WITH RECURSIVE date_series(date, day_count) AS (
    SELECT CURRENT_DATE AS date, 1 AS day_count
    UNION ALL
    SELECT DATEADD('DAY', 1, date), day_count + 1
    FROM date_series
    WHERE day_count < 60
)
SELECT mr.room_id, ds.date
FROM meeting_rooms mr
CROSS JOIN date_series ds