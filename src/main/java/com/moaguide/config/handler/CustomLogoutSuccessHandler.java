package com.moaguide.config.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 로그아웃 성공 메시지 반환
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter writer = response.getWriter();
        writer.write("{\"message\": \"Logout successful!\"}");
        writer.flush();
    }
}