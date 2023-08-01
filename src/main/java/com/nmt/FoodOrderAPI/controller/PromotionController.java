package com.nmt.FoodOrderAPI.controller;

import com.nmt.FoodOrderAPI.dto.PromotionDetailResponse;
import com.nmt.FoodOrderAPI.response.ResponseData;
import com.nmt.FoodOrderAPI.response.ResponseUtils;
import com.nmt.FoodOrderAPI.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/promotion")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @GetMapping
    public ResponseEntity<ResponseData<List<PromotionDetailResponse>>> getAllPromotion() {
        return ResponseUtils.success(promotionService.getAllCurrentUserPromotion());
    }
}
