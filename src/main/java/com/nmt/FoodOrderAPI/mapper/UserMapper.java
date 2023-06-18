package com.nmt.FoodOrderAPI.mapper;

import com.nmt.FoodOrderAPI.dto.UserRequest;
import com.nmt.FoodOrderAPI.dto.UserResponse;
import com.nmt.FoodOrderAPI.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
    User toUser(UserRequest userRequest);
}
