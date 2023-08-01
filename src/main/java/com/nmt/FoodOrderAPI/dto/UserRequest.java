package com.nmt.FoodOrderAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "Username can't be null")
    private String username;
    @NotBlank(message = "Password can't be null")
    private String password;
    @NotBlank(message = "Fullname can't be null")
    private String fullname;
    @Nullable
    private String phone;
    @Nullable
    private Boolean gender;
    @Nullable
    private String birthday;
}
