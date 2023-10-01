package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.config.security.UserDetailsServiceImpl;
import com.nmt.FoodOrderAPI.dto.ChangingPasswordRequest;
import com.nmt.FoodOrderAPI.dto.ResponseMessage;
import com.nmt.FoodOrderAPI.dto.UserRequest;
import com.nmt.FoodOrderAPI.dto.UserResponse;
import com.nmt.FoodOrderAPI.entity.User;
import com.nmt.FoodOrderAPI.enums.UserRolesCode;
import com.nmt.FoodOrderAPI.exception.BaseException;
import com.nmt.FoodOrderAPI.exception.CommonErrorCode;
import com.nmt.FoodOrderAPI.mapper.UserMapper;
import com.nmt.FoodOrderAPI.repo.UserRepository;
import com.nmt.FoodOrderAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDetailsServiceImpl userDetailsService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getUserInformation() {
        return userMapper.toUserResponse(userDetailsService.getCurrentUser());
    }

    @Override
    public void addUser(UserRequest userRequest, Boolean isShipper) {
        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (isShipper)
            user.setRole(UserRolesCode.SHIPPER.getCode());
        else
            user.setRole(UserRolesCode.CUSTOMER.getCode());
        userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteCurrentUser() {
        userRepository.delete(userDetailsService.getCurrentUser());
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        User user = userMapper.toUpdatedUser(userDetailsService.getCurrentUser(), userRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public ResponseMessage changeUserPassword(ChangingPasswordRequest changingPasswordRequest) {
        User currentUser = userDetailsService.getCurrentUser();
        if (passwordEncoder.matches(changingPasswordRequest.getOldPassword(), currentUser.getPassword())) {
            String encodedNewPassword = passwordEncoder.encode(changingPasswordRequest.getNewPassword());
            currentUser.setPassword(encodedNewPassword);
            userRepository.save(currentUser);
            return new ResponseMessage("Thành công!");
        } else
            throw new BaseException(CommonErrorCode.OLD_PASSWORD_NOT_MATCH);
    }
}
