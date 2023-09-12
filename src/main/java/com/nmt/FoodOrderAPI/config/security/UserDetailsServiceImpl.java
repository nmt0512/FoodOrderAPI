package com.nmt.FoodOrderAPI.config.security;

import com.nmt.FoodOrderAPI.entity.User;
import com.nmt.FoodOrderAPI.enums.UserRolesCode;
import com.nmt.FoodOrderAPI.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(
                        new SimpleGrantedAuthority(
                                Arrays.stream(UserRolesCode.values())
                                        .filter(userRolesCode -> Objects.equals(userRolesCode.getCode(), user.getRole()))
                                        .findAny()
                                        .map(UserRolesCode::getRole)
                                        .orElseThrow(() -> new RuntimeException("Invalid user role"))
                        )
                )
        );
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return userRepo.findByUsername(authentication.getName());
        }
        return null;
    }
}
