package com.nmt.FoodOrderAPI;

import com.nmt.FoodOrderAPI.config.ServiceLifeCycleConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
@Import(ServiceLifeCycleConfig.class)
public class FoodOrderApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodOrderApiApplication.class, args);
    }
}
