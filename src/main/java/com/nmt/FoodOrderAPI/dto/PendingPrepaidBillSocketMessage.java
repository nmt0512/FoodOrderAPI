package com.nmt.FoodOrderAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class PendingPrepaidBillSocketMessage implements Serializable {
    private final Boolean isTimeout;
    private final Integer pendingPrepaidBillId;
}
