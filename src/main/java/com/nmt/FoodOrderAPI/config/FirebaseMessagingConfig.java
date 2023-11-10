package com.nmt.FoodOrderAPI.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class FirebaseMessagingConfig {
    @Value("${firebase.json.path}")
    private String firebaseJsonPath;

    @Bean
    @SneakyThrows
    public FirebaseMessaging firebaseMessaging() {
        InputStream serviceAccount = FirebaseMessagingConfig.class.getResourceAsStream(firebaseJsonPath);
        if (serviceAccount != null) {
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(googleCredentials)
                    .build();
            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options, "food-order");
            return FirebaseMessaging.getInstance(firebaseApp);
        }
        return null;
    }
}
