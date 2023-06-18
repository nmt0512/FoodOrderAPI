package com.nmt.FoodOrderAPI.mapper;

import com.nmt.FoodOrderAPI.dto.PromotionResponse;
import com.nmt.FoodOrderAPI.entity.Promotion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PromotionMappper {
    PromotionResponse toPromotionResponse(Promotion promotion);
}
