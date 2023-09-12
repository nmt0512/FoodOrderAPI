package com.nmt.FoodOrderAPI.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BillStatusCode {

    PENDING(1, "Pending Bill"),
    COMPLETED(2, "Completed Bill"),
    CANCELLED(3, "Cancelled Bill"),
    PREPAID(4, "Prepaid Bill"),
    PENDING_PREPAID(5, "Pending Prepaid Bill"),
    SHIP_RECEIVED_BILL(6, "Shipper received Bill");

    private final Integer code;
    private final String description;

}
