package com.nmt.FoodOrderAPI.mapper;

import com.nmt.FoodOrderAPI.dto.PromotionDetailResponse;
import com.nmt.FoodOrderAPI.dto.PromotionResponse;
import com.nmt.FoodOrderAPI.entity.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PromotionMappper {
    PromotionResponse toPromotionResponse(Promotion promotion);

    @Mapping(target = "promotionDetail", source = "percentage")
    PromotionDetailResponse toPromotionDetailResponse(Promotion promotion);

    default String toPromotionDetail(Integer percentage) {
        return "Giảm " + percentage + "%";
    }
}
