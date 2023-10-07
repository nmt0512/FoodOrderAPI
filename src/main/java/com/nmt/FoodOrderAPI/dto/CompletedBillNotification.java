package com.nmt.FoodOrderAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletedBillNotification {
    private Integer pendingPrepaidBillId;
    private Integer totalPrice;
    private String completedTime;
    private List<CompletedBillItem> completedBillItemList;
}
