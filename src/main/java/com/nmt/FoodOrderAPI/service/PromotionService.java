package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.PromotionDetailResponse;

import java.util.List;

public interface PromotionService {
    List<PromotionDetailResponse> getAllCurrentUserPromotion();
}
