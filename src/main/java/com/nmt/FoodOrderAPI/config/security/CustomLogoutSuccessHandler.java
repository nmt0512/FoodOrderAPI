package com.nmt.FoodOrderAPI.config.security;

import com.nmt.FoodOrderAPI.enums.ResponseStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        SecurityResponseUtil.sendResponse(response, ResponseStatusCode.OK, "Logout successfully!");
    }
}
