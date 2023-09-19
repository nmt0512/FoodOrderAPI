package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.config.security.UserDetailsServiceImpl;
import com.nmt.FoodOrderAPI.dto.PromotionDetailResponse;
import com.nmt.FoodOrderAPI.entity.Promotion;
import com.nmt.FoodOrderAPI.mapper.PromotionMappper;
import com.nmt.FoodOrderAPI.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final UserDetailsServiceImpl userDetailsService;
    private final PromotionMappper promotionMappper;

    @Override
    public List<PromotionDetailResponse> getAllCurrentUserPromotion() {
        List<Promotion> promotionList = userDetailsService.getCurrentUser().getPromotionList();
        return promotionList
                .stream()
                .map(promotionMappper::toPromotionDetailResponse)
                .collect(Collectors.toList());
    }
}
