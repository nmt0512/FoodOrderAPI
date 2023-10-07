package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.config.security.UserDetailsServiceImpl;
import com.nmt.FoodOrderAPI.dto.FirebasePairRequest;
import com.nmt.FoodOrderAPI.dto.ResponseMessage;
import com.nmt.FoodOrderAPI.entity.FirebaseUserDevice;
import com.nmt.FoodOrderAPI.mapper.FirebaseUserDeviceMapper;
import com.nmt.FoodOrderAPI.repo.FirebaseUserDeviceRepository;
import com.nmt.FoodOrderAPI.service.FirebaseUserDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseUserDeviceServiceImpl implements FirebaseUserDeviceService {
    private final FirebaseUserDeviceRepository firebaseUserDeviceRepository;
    private final FirebaseUserDeviceMapper firebaseUserDeviceMapper;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public ResponseMessage saveFirebaseKeyAndToken(FirebasePairRequest firebasePairRequest) {
        try {
            FirebaseUserDevice firebaseUserDevice = firebaseUserDeviceMapper.toFirebaseUserDevice(firebasePairRequest);
            firebaseUserDevice.setUser(userDetailsService.getCurrentUser());
            firebaseUserDeviceRepository.save(firebaseUserDevice);
            return new ResponseMessage("Thành công");
        } catch (DataIntegrityViolationException exception) {
            return new ResponseMessage("Thông tin device của người dùng đã tồn tại");
        }
    }
}
