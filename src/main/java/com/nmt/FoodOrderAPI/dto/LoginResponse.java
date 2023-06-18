package com.nmt.FoodOrderAPI.dto;

public class LoginResponse {
    private final String jwtToken;
    private Boolean isAdmin;

    public LoginResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public LoginResponse(String jwtToken, Boolean isAdmin) {
        this.jwtToken = jwtToken;
        this.isAdmin = isAdmin;
    }

    public String getToken() {
        return this.jwtToken;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }
}