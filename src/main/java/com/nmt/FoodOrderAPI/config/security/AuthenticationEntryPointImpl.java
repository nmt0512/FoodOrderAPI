package com.nmt.FoodOrderAPI.config.security;

import com.nmt.FoodOrderAPI.enums.ResponseStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        log.info("REQUEST: {} >>> RESPONSE STATUS: {}", request.getRequestURI(), ResponseStatusCode.UNAUTHORIZED.getValue());
        SecurityResponseUtils.sendResponse(
                response,
                ResponseStatusCode.UNAUTHORIZED,
                "You are not authenticated to access this resource"
        );
    }
}
