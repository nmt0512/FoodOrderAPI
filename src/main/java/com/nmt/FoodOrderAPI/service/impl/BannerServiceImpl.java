package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.repo.BannerRepository;
import com.nmt.FoodOrderAPI.service.BannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.List;

@Service
//@EnableCaching
@Slf4j
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
//    private final CacheManager cacheManager;

    @Override
//    @Cacheable(value = "bannerCache")
    public List<String> findAllBannerLink() {
        return bannerRepository.findAllLink();
    }

//    @PreDestroy
//    protected void clearBannerCache() {
//        try {
//            Cache bannerCache = cacheManager.getCache("bannerCache");
//            if (bannerCache != null)
//                bannerCache.clear();
//        } catch (RedisConnectionFailureException redisConnectionFailureException) {
//            log.error("Connection to Redis cache failed");
//        }
//    }

}
