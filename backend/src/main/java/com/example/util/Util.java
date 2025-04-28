package com.example.util;

import org.springframework.stereotype.Component;

@Component
public class Util {
    // 计算时间掩码
    public Integer calculateTimeMask(Integer startTime, Integer endTime) {
        if (startTime >= endTime) {
            return 0;
        }

        int mask = (1 << (endTime - startTime)) - 1;
        return mask << startTime;
    }
}
