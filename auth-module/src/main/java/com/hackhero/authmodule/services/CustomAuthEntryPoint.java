package com.hackhero.authmodule.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackhero.authmodule.utils.ExceptionUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        Throwable rootCause = ExceptionUtil.getRootCause(authException);
        log.error("Error in filter chain: {}", rootCause.getMessage(), rootCause);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        if (rootCause instanceof ExpiredJwtException) {

        }
        else {
            //TODO Хардкод
        }


        log.error("error response: {}", "");

        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(""));
    }
}

