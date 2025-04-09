package com.example.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.entity.Role;

@Component
public class RoleConverter implements Converter<String, Role> {
    @Override
    public Role convert(@NonNull String source) {
        return Role.valueOf(source.toUpperCase());
    }
}
