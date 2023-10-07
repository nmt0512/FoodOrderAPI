package com.nmt.FoodOrderAPI.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrepaidRequest implements Serializable {

    @JsonProperty("billItemList")
    private List<BillItemRequest> billItemRequestList;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer promotionId;

    private String address;

    private Integer totalPrice;
}
