package com.nmt.FoodOrderAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private Boolean gender;
    private Date birthday;
    private String fullname;
    private String email;
    private String phone;
}
