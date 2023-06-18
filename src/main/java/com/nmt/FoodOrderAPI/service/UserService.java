package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.ChangingPasswordRequest;
import com.nmt.FoodOrderAPI.dto.UserRequest;
import com.nmt.FoodOrderAPI.dto.UserResponse;

public interface UserService {
    UserResponse getUserInformation();
    UserResponse addUser(UserRequest userRequest);
    void deleteCurrentUser();
    UserResponse updateUser(UserRequest userRequest);
    void changeUserPassword(ChangingPasswordRequest changingPasswordRequest);
}
