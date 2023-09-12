package com.nmt.FoodOrderAPI.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRolesCode {
    CUSTOMER("ROLE_CUSTOMER", 0),
    STAFF("ROLE_STAFF", 1),
    SHIPPER("ROLE_SHIPPER", 2);

    private final String role;
    private final Integer code;
}
