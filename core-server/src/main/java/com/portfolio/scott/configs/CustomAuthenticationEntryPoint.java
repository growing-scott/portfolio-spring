package com.portfolio.scott.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.scott.controllers.errors.ApiException;
import com.portfolio.scott.controllers.errors.ErrorCode;
import com.portfolio.scott.controllers.errors.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint  implements org.springframework.security.web.AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ApiException apiException = new ApiException(
                HttpStatus.UNAUTHORIZED,
                ErrorCode.UNAUTHORIZED_ERROR);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorResponse(apiException)));
    }
}
