package com.nmt.FoodOrderAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder(value = {"id", "fullname", "revenue", "duration"})
public class StaffTrackingStatisticResponse {
    @SerializedName("Id")
    @JsonProperty("id")
    private Integer id;

    @SerializedName("Fullname")
    private String fullname;

    @SerializedName("TotalRevenue")
    @JsonProperty("revenue")
    private Integer totalRevenue;

    @SerializedName("TotalDuration")
    @JsonProperty("duration")
    private Float totalDuration;
}
