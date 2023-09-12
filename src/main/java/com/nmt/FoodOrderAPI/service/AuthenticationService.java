package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.LoginRequest;
import com.nmt.FoodOrderAPI.dto.LoginResponse;

public interface AuthenticationService {
    LoginResponse authenticateAndGenerateToken(LoginRequest loginRequest);
}
