package com.nmt.FoodOrderAPI.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillResponse implements Serializable {
    private Integer id;
    private Integer totalPrice;
    private String time;
    private String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserResponse customer;
}
