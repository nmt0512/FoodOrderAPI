package com.nmt.FoodOrderAPI.config.security;

import com.nmt.FoodOrderAPI.enums.ResponseStatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        SecurityResponseUtils.sendResponse(response, ResponseStatusCode.UNAUTHORIZED,
                "You are not authorized to access this resource");
    }
}
