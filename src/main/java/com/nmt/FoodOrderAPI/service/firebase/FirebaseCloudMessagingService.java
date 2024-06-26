package com.nmt.FoodOrderAPI.service.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.nmt.FoodOrderAPI.entity.FirebaseUserDevice;
import com.nmt.FoodOrderAPI.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseCloudMessagingService {
    private final FirebaseMessaging firebaseMessaging;

    public void sendNotificationToShipper(User shipper, String customerName) {
        List<FirebaseUserDevice> firebaseUserDeviceList = shipper.getFirebaseUserDeviceList();
        for (FirebaseUserDevice firebaseUserDevice : firebaseUserDeviceList) {
            try {
                Message message = Message.builder()
                        .setToken(firebaseUserDevice.getFirebaseToken())
                        .putData("customer", customerName)
                        .build();
                String response = firebaseMessaging.send(message);
                log.info("Sent a message ID: {} to device key: {}", response, firebaseUserDevice.getDeviceKey());
            } catch (FirebaseMessagingException exception) {
                log.error("Sending firebase message to a SHIPPER error: " + exception.getMessagingErrorCode().name());
            }
        }

    }

    public void sendNotificationToShipperTopic(String topicName, String customerName) {
        try {
            Message message = Message.builder()
                    .setTopic(topicName)
                    .putData("newOrder", customerName)
                    .build();
            String response = firebaseMessaging.send(message);
            log.info("Sent a message ID: {}", response);
        } catch (FirebaseMessagingException exception) {
            log.error("Sending firebase message to a TOPIC error: " + exception.getMessagingErrorCode().name());
        }
    }
}
