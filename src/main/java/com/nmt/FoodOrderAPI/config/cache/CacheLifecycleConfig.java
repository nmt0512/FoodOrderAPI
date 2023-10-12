package com.nmt.FoodOrderAPI.config.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;

import javax.annotation.PreDestroy;
import java.util.Collection;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CacheLifecycleConfig {
    private final CacheManager cacheManager;

    @PreDestroy
    protected void clearBannerCache() {
        try {
            Collection<String> cacheNames = cacheManager.getCacheNames();
            cacheNames.forEach(cacheName -> {
                Cache cache = cacheManager.getCache(cacheName);
                if (cache != null) {
                    cache.clear();
                }
            });
            log.info("Cleared all caches {} in Redis", cacheNames);
        } catch (RedisConnectionFailureException redisConnectionFailureException) {
            log.error("Connection to Redis cache failed");
        }
    }
}
