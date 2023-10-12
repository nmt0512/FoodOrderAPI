package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.repo.BannerRepository;
import com.nmt.FoodOrderAPI.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;

    @Override
    @Cacheable(value = "bannerCache")
    public List<String> findAllBannerLink() {
        return bannerRepository.findAllLink();
    }
}
