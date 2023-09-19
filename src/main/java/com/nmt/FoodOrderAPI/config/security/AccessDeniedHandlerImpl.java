package com.nmt.FoodOrderAPI.config.security;

import com.nmt.FoodOrderAPI.enums.ResponseStatusCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        SecurityResponseUtils.sendResponse(
                response,
                ResponseStatusCode.FORBIDDEN,
                "You are not authorized to access this resource"
        );
    }
}
