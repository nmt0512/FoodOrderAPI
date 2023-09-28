package com.nmt.FoodOrderAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {
    private Integer id;
    private String username;
    private Integer role;
    private Boolean gender;
    private Date birthday;
    private String fullname;
    private String email;
    private String phone;
}
