package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.BillResponse;
import com.nmt.FoodOrderAPI.dto.PrepaidRequest;
import com.nmt.FoodOrderAPI.dto.ResponseMessage;

import java.util.List;

public interface PendingPrepaidBillService {
    BillResponse createPendingPrepaidBill(PrepaidRequest prepaidRequest);

    ResponseMessage confirmReceivedPendingPrepaidBill(int pendingPrepaidBillId);

    ResponseMessage paymentPendingPrepaidBillByCustomer(int pendingPrepaidBillId);

    ResponseMessage completeCustomerPrepaidBillByShipper(int pendingPrepaidBillId);

    List<BillResponse> getAllPendingPrepaidBill();

    List<BillResponse> getAllReceivedCustomerPrepaidBillByShipper();
}
