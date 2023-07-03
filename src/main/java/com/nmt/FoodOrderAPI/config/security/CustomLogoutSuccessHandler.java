package com.nmt.FoodOrderAPI.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nmt.FoodOrderAPI.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final ObjectMapper objectMapper;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        ResponseMessage responseMessage = new ResponseMessage("Thành công");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseMessage));
    }
}
