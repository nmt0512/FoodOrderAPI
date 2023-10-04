package com.nmt.FoodOrderAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FirebasePairRequest {
    private String deviceKey;
    private String firebaseToken;
}
