package com.nmt.FoodOrderAPI.controller;

import com.nmt.FoodOrderAPI.dto.FirebasePairRequest;
import com.nmt.FoodOrderAPI.dto.ResponseMessage;
import com.nmt.FoodOrderAPI.service.FirebaseUserDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class FirebaseUserDeviceController {
    private final FirebaseUserDeviceService firebaseUserDeviceService;

    @PostMapping
    public ResponseEntity<ResponseMessage> saveFirebaseKeyAndToken(@RequestBody FirebasePairRequest firebasePairRequest) {
        return ResponseEntity.ok(firebaseUserDeviceService.saveFirebaseKeyAndToken(firebasePairRequest));
    }
}
