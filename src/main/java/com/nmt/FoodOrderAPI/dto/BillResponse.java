package com.nmt.FoodOrderAPI.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillResponse {
    private Integer id;
    private Integer totalPrice;
    private String time;
    private Integer status;
    private String staffName;

    @JsonProperty("givenPromotion")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PromotionResponse> givenPromotionResponseList;

    @JsonProperty("billItemList")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BillItemResponse> billItemResponseList;

    @JsonProperty("usedPromotion")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PromotionResponse usedPromotionResponse;

    @JsonProperty("user")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserResponse userResponse;
}
