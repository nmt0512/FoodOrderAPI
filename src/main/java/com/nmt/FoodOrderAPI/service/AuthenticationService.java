package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.LoginRequest;

public interface AuthenticationService {
    String authenticateAndGenerateToken(LoginRequest loginRequest);
}
