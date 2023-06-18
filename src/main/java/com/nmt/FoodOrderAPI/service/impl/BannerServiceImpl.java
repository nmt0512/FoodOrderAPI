package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.repo.BannerRepository;
import com.nmt.FoodOrderAPI.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public List<String> findAllBannerLink() {
        return bannerRepository.findAllLink();
    }
}
