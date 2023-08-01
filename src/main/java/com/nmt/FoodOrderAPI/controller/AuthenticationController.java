package com.nmt.FoodOrderAPI.controller;

import com.nmt.FoodOrderAPI.dto.LoginRequest;
import com.nmt.FoodOrderAPI.dto.LoginResponse;
import com.nmt.FoodOrderAPI.dto.UserRequest;
import com.nmt.FoodOrderAPI.response.ResponseData;
import com.nmt.FoodOrderAPI.response.ResponseUtils;
import com.nmt.FoodOrderAPI.service.AuthenticationService;
import com.nmt.FoodOrderAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseData<LoginResponse>> loginAndCreateJsonWebToken(@RequestBody LoginRequest loginRequest) {
        String token = authenticationService.authenticateAndGenerateToken(loginRequest);
        if (userDetailsService
                .loadUserByUsername(loginRequest.getUsername())
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
        )
            return ResponseUtils.success(new LoginResponse(token, true));
        return ResponseUtils.success(new LoginResponse(token, false));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData<LoginResponse>> registerUser(@RequestBody @Valid UserRequest userRequest) {
        userService.addUser(userRequest);
        return loginAndCreateJsonWebToken(new LoginRequest(userRequest.getUsername(), userRequest.getPassword()));
    }

}
