package com.nmt.FoodOrderAPI.mapper;

import com.nmt.FoodOrderAPI.dto.UserRequest;
import com.nmt.FoodOrderAPI.dto.UserResponse;
import com.nmt.FoodOrderAPI.entity.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    UserResponse toUserResponse(User user);

    @Mapping(
            source = "birthday",
            target = "birthday",
            dateFormat = "dd/MM/yyyy",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    @Mapping(target = "gender", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "phone", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    User toUser(UserRequest userRequest);

    @Mapping(source = "birthday", target = "birthday", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toUpdatedUser(@MappingTarget User user, UserRequest userRequest);
}
