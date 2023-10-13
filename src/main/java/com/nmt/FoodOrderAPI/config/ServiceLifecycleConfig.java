package com.nmt.FoodOrderAPI.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.nmt.FoodOrderAPI.repo.FirebaseUserDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@RequiredArgsConstructor
public class ServiceLifecycleConfig {
    private final SocketIOServer socketIOServer;
    private final ScheduledExecutorService scheduledExecutorService;
    private final FirebaseUserDeviceRepository firebaseUserDeviceRepository;

    @PostConstruct
    public void start() {
        socketIOServer.start();
    }

    @PreDestroy
    public void stop() {
        scheduledExecutorService.shutdown();
        socketIOServer.stop();
        firebaseUserDeviceRepository.deleteAll();
    }
}
