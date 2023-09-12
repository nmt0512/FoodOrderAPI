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
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseData<LoginResponse>> loginAndCreateJsonWebToken(@RequestBody LoginRequest loginRequest) {
        return ResponseUtils.success(authenticationService.authenticateAndGenerateToken(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData<LoginResponse>> registerUser(@RequestBody @Valid UserRequest userRequest) {
        userService.addUser(userRequest);
        return loginAndCreateJsonWebToken(new LoginRequest(userRequest.getUsername(), userRequest.getPassword()));
    }

}
