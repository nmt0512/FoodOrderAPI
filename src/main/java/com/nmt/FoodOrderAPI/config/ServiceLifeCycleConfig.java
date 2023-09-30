package com.nmt.FoodOrderAPI.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ScheduledExecutorService;

@RequiredArgsConstructor
public class ServiceLifeCycleConfig {
    private final SocketIOServer socketIOServer;
    private final ScheduledExecutorService scheduledExecutorService;

    @PostConstruct
    public void start() {
        socketIOServer.start();
    }

    @PreDestroy
    public void stop() {
        scheduledExecutorService.shutdown();
        socketIOServer.stop();
    }
}
