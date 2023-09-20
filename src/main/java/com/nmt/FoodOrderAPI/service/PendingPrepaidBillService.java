package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.BillResponse;
import com.nmt.FoodOrderAPI.dto.PrepaidRequest;
import com.nmt.FoodOrderAPI.dto.ResponseMessage;

import java.util.List;

public interface PendingPrepaidBillService {
    ResponseMessage createPendingPrepaidBill(PrepaidRequest prepaidRequest);

    List<BillResponse> getAllPendingPrepaidBill();

    ResponseMessage confirmReceivedPendingPrepaidBill(int pendingPrepaidBillId);
}
