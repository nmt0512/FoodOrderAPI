package com.nmt.FoodOrderAPI.dto;

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
public class MonthlyStatisticResponse {
    @JsonProperty("total")
    private Integer totalRevenue;

    @JsonProperty("maxValue")
    private Integer maxMonthRevenue;

    @JsonProperty("values")
    private List<StatisticResponse> statisticResponses;
}
