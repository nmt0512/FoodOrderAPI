package com.nmt.FoodOrderAPI.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {

    @Value("${socket.io.hostname}")
    private String hostname;
    @Value("${socket.io.port}")
    private Integer port;
    @Value("${socket.io.timeout}")
    private Integer timeout;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(hostname);
        config.setPort(port);
        config.setPingTimeout(timeout);
        config.setUpgradeTimeout(timeout);
        config.setFirstDataTimeout(timeout);
        return new SocketIOServer(config);
    }

}
