package com.nmt.FoodOrderAPI.config;

import com.nmt.FoodOrderAPI.config.auditing.AuditorAwareImpl;
import com.nmt.FoodOrderAPI.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareProvider")
public class AuditorAwareConfig {

    @Bean
    public AuditorAware<User> auditorAwareProvider() {
        return new AuditorAwareImpl();
    }
}
