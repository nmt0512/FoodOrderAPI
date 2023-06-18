package com.nmt.FoodOrderAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {
    private Integer id;
    private Integer status;
    private Integer newTotalPrice;
    @Nullable
    private Integer promotionId;
}
