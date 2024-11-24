package com.portfolio.scott.configs.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public JwtAuthenticationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        setJwtAuthentication(request);
        filterChain.doFilter(request, response);
    }

    private void setJwtAuthentication(HttpServletRequest request) {
        String authorizationValue = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationValue != null && authorizationValue.startsWith("Bearer ")) {
            String token = authorizationValue.substring(7);
            if (tokenProvider.validateToken(token)) {
                Authentication authentication = this.tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }
}
