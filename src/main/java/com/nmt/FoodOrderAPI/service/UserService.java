package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.ChangingPasswordRequest;
import com.nmt.FoodOrderAPI.dto.ResponseMessage;
import com.nmt.FoodOrderAPI.dto.UserRequest;
import com.nmt.FoodOrderAPI.dto.UserResponse;

public interface UserService {
    UserResponse getUserInformation();

    void addUser(UserRequest userRequest, Boolean isShipper);

    void deleteCurrentUser();

    UserResponse updateUser(UserRequest userRequest);

    ResponseMessage changeUserPassword(ChangingPasswordRequest changingPasswordRequest);
}
