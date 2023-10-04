package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.FirebasePairRequest;
import com.nmt.FoodOrderAPI.dto.ResponseMessage;

public interface FirebaseUserDeviceService {
    ResponseMessage saveFirebaseKeyAndToken(FirebasePairRequest firebasePairRequest);
}
