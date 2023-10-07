package com.nmt.FoodOrderAPI.service.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.nmt.FoodOrderAPI.entity.FirebaseUserDevice;
import com.nmt.FoodOrderAPI.entity.User;
import com.nmt.FoodOrderAPI.repo.FirebaseUserDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseCloudMessagingService {
    private final FirebaseUserDeviceRepository firebaseUserDeviceRepository;
    private final FirebaseMessaging firebaseMessaging;

    public void sendNotificationToShipper(User shipper, String customerName) {
        try {
            List<FirebaseUserDevice> firebaseUserDeviceList = firebaseUserDeviceRepository.findByUser(shipper);
            for (FirebaseUserDevice firebaseUserDevice : firebaseUserDeviceList) {
                Message message = Message.builder()
                        .setToken(firebaseUserDevice.getFirebaseToken())
                        .putData("customer", customerName)
                        .build();
                String response = firebaseMessaging.send(message);
                log.info("Sent a message ID: {}", response);
            }
        } catch (FirebaseMessagingException exception) {
            exception.printStackTrace();
            log.error("Sending firebase message error: " + exception.getMessagingErrorCode().name());
        }
    }

    public void sendNotificationToShipperTopic(String topicName, String customerName) {
        try {
            Message message = Message.builder()
                    .setTopic(topicName)
                    .putData("customer", customerName)
                    .build();
            String response = firebaseMessaging.send(message);
            log.info("Sent a message ID: {}", response);
        } catch (FirebaseMessagingException exception) {
            exception.printStackTrace();
            log.error("Sending firebase message error: " + exception.getMessagingErrorCode().name());
        }
    }
}
