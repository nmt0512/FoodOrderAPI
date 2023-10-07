package com.nmt.FoodOrderAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompletedBillItem {
    private String name;
    private Integer quantity;
    private Integer price;
    private String image;
}
