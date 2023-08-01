package com.nmt.FoodOrderAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionDetailResponse implements Serializable {
    private Integer id;
    private Integer percentage;
    private Integer applyingPrice;
    private String promotionDetail;
    private String image;
}
