package com.nmt.FoodOrderAPI.controller;

import com.nmt.FoodOrderAPI.dto.ChangingPasswordRequest;
import com.nmt.FoodOrderAPI.dto.ResponseMessage;
import com.nmt.FoodOrderAPI.dto.UserRequest;
import com.nmt.FoodOrderAPI.dto.UserResponse;
import com.nmt.FoodOrderAPI.response.ResponseData;
import com.nmt.FoodOrderAPI.response.ResponseUtils;
import com.nmt.FoodOrderAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ResponseData<UserResponse>> getCurrentUserInfomation() {
        return ResponseUtils.success(userService.getUserInformation());
    }

    @PutMapping
    public ResponseEntity<ResponseData<UserResponse>> updateUserInformation(@RequestBody UserRequest userRequest) {
        return ResponseUtils.success(userService.updateUser(userRequest));
    }

    @PutMapping("/password")
    public ResponseEntity<ResponseMessage> changeUserPassword(@RequestBody ChangingPasswordRequest changingPasswordRequest) {
        return ResponseEntity.ok(userService.changeUserPassword(changingPasswordRequest));
    }
}
