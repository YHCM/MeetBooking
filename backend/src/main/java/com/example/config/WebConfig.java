package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.config.converter.RequestStatusConverter;
import com.example.config.converter.RoleConverter;
import com.example.config.interceptor.LoginInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final RoleConverter roleConverter;
    private final RequestStatusConverter requestStatusConverter;
    private final LoginInterceptor loginInterceptor;

    // 添加拦截器
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/swagger-ui/**", "/v3/**", "/auth/**", "/users/status", "/users/current");
    }

    // 配置跨域请求
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                // .allowedOrigins("http://localhost:*")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
                // .allowCredentials(true);
    }

    // 添加枚举类型的转换器
    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        registry.addConverter(roleConverter);
        registry.addConverter(requestStatusConverter);
    }
}
