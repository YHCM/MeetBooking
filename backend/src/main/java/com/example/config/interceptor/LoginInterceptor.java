package com.example.config.interceptor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        HttpSession session = request.getSession();

        if (session.getAttribute("uid") == null) {
            // 如果没有获取到 uid，则没有登陆，拦截
            // 返回 json 响应
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\": 401, \"msg\": \"未登录\", \"data\": false}");
            return false;
        }

        return true;
    }
}
