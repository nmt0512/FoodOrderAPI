package com.nmt.FoodOrderAPI;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class FoodOrderApiApplication {
    private final SocketIOServer socketIOServer;
    private final ScheduledExecutorService scheduledExecutorService;

    public static void main(String[] args) {
        SpringApplication.run(FoodOrderApiApplication.class, args);
    }

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
