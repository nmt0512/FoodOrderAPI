package com.nmt.FoodOrderAPI.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BillStatusCode {

    PENDING(1, "Pending Bill", "Đang chờ nhân viên hoàn thành đơn hàng"),
    COMPLETED(2, "Completed Bill", "Đã hoàn thành đơn hàng"),
    CANCELLED(3, "Cancelled Bill", "Đã hủy đơn hàng"),
    PREPAID(4, "Prepaid Bill", "Đã trả trước đơn hàng"),
    PAID_FOR_SHIPPING(5, "Paid for shipping Bill", "Đã thanh toán cho đơn hàng để ship");

    private final Integer code;
    private final String description;
    private final String message;

}
