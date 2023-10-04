package com.nmt.FoodOrderAPI.mapper;

import com.nmt.FoodOrderAPI.dto.FirebasePairRequest;
import com.nmt.FoodOrderAPI.entity.FirebaseUserDevice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FirebaseUserDeviceMapper {
    FirebaseUserDevice toFirebaseUserDevice(FirebasePairRequest firebasePairRequest);
}
