package com.nmt.FoodOrderAPI.service.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.nmt.FoodOrderAPI.entity.FirebaseUserDevice;
import com.nmt.FoodOrderAPI.entity.User;
import com.nmt.FoodOrderAPI.repo.FirebaseUserDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseMessageCloudService {
    private final FirebaseUserDeviceRepository firebaseUserDeviceRepository;
    private final FirebaseMessaging firebaseMessaging;

    public void sendNotificationToShipperTopic(User shipper, String customerName) {
        try {
            FirebaseUserDevice firebaseUserDevice = firebaseUserDeviceRepository.findByUser(shipper);
            Notification notification = Notification.builder()
                    .setTitle("")
                    .setBody(customerName)
                    .build();
            Message message = Message.builder()
                    .setToken(firebaseUserDevice.getFirebaseToken())
                    .setNotification(notification)
                    .putData("customer", customerName)
                    .build();
            String response = firebaseMessaging.send(message);
            log.info("Sent a message ID: {}", response);
        } catch (FirebaseMessagingException exception) {
            log.error("Sending firebase message error");
        }
    }
}
