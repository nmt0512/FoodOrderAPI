package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.config.security.UserDetailsServiceImpl;
import com.nmt.FoodOrderAPI.dto.PromotionDetailResponse;
import com.nmt.FoodOrderAPI.mapper.PromotionMappper;
import com.nmt.FoodOrderAPI.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final UserDetailsServiceImpl userDetailsService;
    private final PromotionMappper promotionMappper;

    @Override
    @Cacheable(value = "promotionCache")
    public List<PromotionDetailResponse> getAllCurrentUserPromotion() {
        return userDetailsService
                .getCurrentUser()
                .getPromotionList()
                .stream()
                .map(promotion -> {
                    PromotionDetailResponse promotionDetailResponse = promotionMappper.toPromotionDetailResponse(promotion);
                    promotionDetailResponse.setPromotionDetail("Giảm " + promotionDetailResponse.getPercentage() + "%");
                    return promotionDetailResponse;
                })
                .collect(Collectors.toList());
    }
}
