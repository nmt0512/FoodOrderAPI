package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.config.security.UserDetailsServiceImpl;
import com.nmt.FoodOrderAPI.dto.ChangingPasswordRequest;
import com.nmt.FoodOrderAPI.dto.UserRequest;
import com.nmt.FoodOrderAPI.dto.UserResponse;
import com.nmt.FoodOrderAPI.entity.User;
import com.nmt.FoodOrderAPI.exception.OldPasswordNotMatchException;
import com.nmt.FoodOrderAPI.mapper.UserMapper;
import com.nmt.FoodOrderAPI.repo.UserRepository;
import com.nmt.FoodOrderAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public UserResponse addUser(UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        user.setRole(true);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteCurrentUser() {
        userRepository.delete(userDetailsService.getCurrentUser());
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        User currentUser = userDetailsService.getCurrentUser();
        return userMapper.toUserResponse(userRepository.save(mapUserForUpdate(currentUser, userRequest)));
    }

    @Override
    public void changeUserPassword(ChangingPasswordRequest changingPasswordRequest) {
        User currentUser = userDetailsService.getCurrentUser();
        if (passwordEncoder.matches(changingPasswordRequest.getOldPassword(), currentUser.getPassword())) {
            String encodedNewPassword = passwordEncoder.encode(changingPasswordRequest.getNewPassword());
            currentUser.setPassword(encodedNewPassword);
            userRepository.save(currentUser);
        } else
            throw new OldPasswordNotMatchException("Old password request doesn't match with current password");
    }

    private User mapUserForUpdate(User currentUser, UserRequest userRequest) {
        currentUser.setFullname(userRequest.getFullname());
        currentUser.setPhone(userRequest.getPhone());
        currentUser.setGender(userRequest.getGender());
        if (userRequest.getBirthday() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate parsedBirthday = LocalDate.parse(userRequest.getBirthday(), formatter);
            currentUser.setBirthday(parsedBirthday);
        }
        return currentUser;
    }
}
