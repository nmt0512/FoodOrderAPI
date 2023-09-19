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
    SHIPPER_RECEIVED(5, "Shipper received Bill", "Shipper đã nhận đơn hàng");

    private final Integer code;
    private final String description;
    private final String message;

}
