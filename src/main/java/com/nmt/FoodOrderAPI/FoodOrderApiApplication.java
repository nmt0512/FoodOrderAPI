package com.nmt.FoodOrderAPI;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class FoodOrderApiApplication {
    private final SocketIOServer socketIOServer;

    public static void main(String[] args) {
        SpringApplication.run(FoodOrderApiApplication.class, args);
    }

    @PostConstruct
    public void start() {
        socketIOServer.start();
    }

    @PreDestroy
    public void stop() {
        socketIOServer.stop();
    }

}
