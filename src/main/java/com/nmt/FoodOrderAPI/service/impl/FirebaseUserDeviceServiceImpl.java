package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.dto.FirebasePairRequest;
import com.nmt.FoodOrderAPI.dto.ResponseMessage;
import com.nmt.FoodOrderAPI.entity.FirebaseUserDevice;
import com.nmt.FoodOrderAPI.mapper.FirebaseUserDeviceMapper;
import com.nmt.FoodOrderAPI.repo.FirebaseUserDeviceRepository;
import com.nmt.FoodOrderAPI.service.FirebaseUserDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

@Service
@RequiredArgsConstructor
public class FirebaseUserDeviceServiceImpl implements FirebaseUserDeviceService {
    private final FirebaseUserDeviceRepository firebaseUserDeviceRepository;
    private final FirebaseUserDeviceMapper firebaseUserDeviceMapper;

    @Override
    public ResponseMessage saveFirebaseKeyAndToken(FirebasePairRequest firebasePairRequest) {
        try {
            FirebaseUserDevice firebaseUserDevice = firebaseUserDeviceMapper.toFirebaseUserDevice(firebasePairRequest);
            firebaseUserDeviceRepository.save(firebaseUserDevice);
            return new ResponseMessage("Thành công");
        } catch (ConstraintViolationException exception) {
            return new ResponseMessage("Thông tin firebase key của người dùng đã tồn tại");
        }
    }
}
