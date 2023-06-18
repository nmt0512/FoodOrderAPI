package com.nmt.FoodOrderAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillItemResponse {
    private ProductResponse product;
    private Integer quantity;
    private Integer price;
}
