package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.config.security.UserDetailsServiceImpl;
import com.nmt.FoodOrderAPI.config.utils.JwtUtils;
import com.nmt.FoodOrderAPI.dto.LoginRequest;
import com.nmt.FoodOrderAPI.dto.LoginResponse;
import com.nmt.FoodOrderAPI.entity.StaffTracking;
import com.nmt.FoodOrderAPI.enums.UserRolesCode;
import com.nmt.FoodOrderAPI.repo.StaffTrackingRepository;
import com.nmt.FoodOrderAPI.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final StaffTrackingRepository staffTrackingRepository;

    @Override
    public LoginResponse authenticateAndGenerateToken(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        int roleCode = userDetails
                .getAuthorities()
                .stream()
                .map(grantedAuthority -> UserRolesCode
                        .valueOf(grantedAuthority
                                .getAuthority()
                                .substring(5)
                        )
                        .getCode())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid user role"));
        String token = jwtUtils.generateToken(userDetails);

        if (roleCode == UserRolesCode.STAFF.getCode()) {
            StaffTracking staffTracking = new StaffTracking();
            staffTracking.setStaff(userDetailsService.getCurrentUser());
            staffTracking = staffTrackingRepository.save(staffTracking);
            token = jwtUtils.generateToken(userDetails, staffTracking.getId());
        }

        return new LoginResponse(token, roleCode);
    }
}
