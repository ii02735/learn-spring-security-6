package com.ii02735.learn_spring_security_6.exception_handling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomerAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        final var jsonResponse = """
                {"path": "%s", "message": "%s"}
                """;

        response.getWriter().write(jsonResponse.formatted(request.getRequestURI(), accessDeniedException.getMessage()));
    }
}
