package com.nmt.FoodOrderAPI.config.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer())
                );
    }

//    @Bean
//    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
//        return builder -> builder
//                .withCacheConfiguration(
//                        "bannerCache",
//                        RedisCacheConfiguration
//                                .defaultCacheConfig()
//                                .entryTtl(Duration.ofMinutes(30))
//                )
//                .withCacheConfiguration(
//                        "allStatisticYearCache",
//                        RedisCacheConfiguration
//                                .defaultCacheConfig()
//                                .entryTtl(Duration.ofMinutes(30))
//                )
//                .withCacheConfiguration(
//                        "allMonthStaffTrackingStatisticCache",
//                        RedisCacheConfiguration
//                                .defaultCacheConfig()
//                                .entryTtl(Duration.ofMinutes(30))
//                );
//    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory(new RedisStandaloneConfiguration());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

}
