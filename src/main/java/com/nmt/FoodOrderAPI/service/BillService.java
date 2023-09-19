package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.*;

import java.util.List;

public interface BillService {
    BillResponse addBill(List<BillItemRequest> billItemRequestList);

    ResponseMessage confirmOrCancelBill(String token, BillRequest billRequest);

    ResponseMessage prepaidBill(PrepaidRequest prepaidRequest);

    ResponseMessage orderPendingPrepaidBill(PrepaidRequest prepaidRequest);

    List<BillResponse> getAllPendingPrepaidBill();

    BillResponse getBillDetail(Integer billId);

    List<BillResponse> getAllBill(Integer page);

    List<BillResponse> getBillByFilter(Integer page, Integer status, String orderBy);
}
