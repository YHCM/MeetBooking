package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.util.converter.RoleConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final RoleConverter roleConverter;

    public WebConfig(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        registry.addConverter(roleConverter);
    }
}
