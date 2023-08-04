package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.BillItemRequest;
import com.nmt.FoodOrderAPI.dto.BillRequest;
import com.nmt.FoodOrderAPI.dto.BillResponse;
import com.nmt.FoodOrderAPI.dto.PrepaidRequest;

import java.util.List;

public interface BillService {
    BillResponse addBill(List<BillItemRequest> billItemRequestList);

    void changeBillStatus(BillRequest billRequest);

    void prepaidBill(PrepaidRequest prepaidRequest);

    BillResponse getBillDetail(Integer billId);

    List<BillResponse> getAllBill(Integer page);

    List<BillResponse> getBillByFilter(Integer page, Integer status, String orderBy);
}
