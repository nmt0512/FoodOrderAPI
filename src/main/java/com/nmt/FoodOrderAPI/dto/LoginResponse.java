package com.nmt.FoodOrderAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginResponse {
    @JsonProperty("access-token")
    private final String token;

    @JsonProperty("role")
    private final Integer roleCode;
}