package com.example.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
// import org.springframework.stereotype.Component;

// @Component
// 暂时没有用
public class EnumConverter<T extends Enum<T>> implements Converter<String, T> {
    private final Class<T> targetType;

    public EnumConverter(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public T convert(@NonNull String source) {
        return Enum.valueOf(targetType, source.toUpperCase());
    }
}
