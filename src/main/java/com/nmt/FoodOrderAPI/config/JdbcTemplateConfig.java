package com.nmt.FoodOrderAPI.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Objects;

@Configuration
public class JdbcTemplateConfig {
    @Autowired
    private Environment environment;

    private DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driverClassName")));
        ds.setUrl(environment.getProperty("spring.datasource.url"));
        ds.setUsername(environment.getProperty("spring.datasource.username"));
        ds.setPassword(environment.getProperty("spring.datasource.password"));
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(driverManagerDataSource());
    }
}
