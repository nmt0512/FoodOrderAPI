package com.nmt.FoodOrderAPI.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticResponse {
    @SerializedName("Time")
    private String time;

    @SerializedName("Revenue")
    private Integer revenue;
}
