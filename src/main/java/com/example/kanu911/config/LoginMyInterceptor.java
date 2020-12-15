package com.example.kanu911.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginMyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String authorization = request.getHeader("Authorization");
        if(authorization == null) {
            response.sendRedirect("/admin/login");
            return false;
        }
        return true;
    }
}
