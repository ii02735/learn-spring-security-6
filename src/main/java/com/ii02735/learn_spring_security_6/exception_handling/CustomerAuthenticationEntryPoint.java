package com.ii02735.learn_spring_security_6.exception_handling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomerAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        final var jsonResponse = """
                {"path": "%s", "message": "%s"}
                """;
        if (authException instanceof UsernameNotFoundException || authException instanceof BadCredentialsException) {
            response.getWriter().write(jsonResponse.formatted(request.getRequestURI(), "Nom d'utilisateur ou mot de passe invalide"));
            return;
        }

        if (authException instanceof InsufficientAuthenticationException) {
            response.getWriter().write(jsonResponse.formatted(request.getRequestURI(), "Authentification requise pour accéder à cette ressource"));
            return;
        }

        response.getWriter().write(jsonResponse.formatted(request.getRequestURI(), authException.getMessage()));
    }
}
