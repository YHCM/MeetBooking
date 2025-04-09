package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.util.converter.RequestStatusConverter;
import com.example.util.converter.RoleConverter;
import com.example.util.interceptor.LoginInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final RoleConverter roleConverter;
    private final RequestStatusConverter requestStatusConverter;
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui/index.html", "/v3/api-docs", "/auth/login");
    }

    // 添加枚举类型的转换器
    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        registry.addConverter(roleConverter);
        registry.addConverter(requestStatusConverter);
    }
}
