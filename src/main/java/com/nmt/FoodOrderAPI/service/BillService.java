package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.BillItemRequest;
import com.nmt.FoodOrderAPI.dto.BillRequest;
import com.nmt.FoodOrderAPI.dto.BillResponse;

import java.util.List;

public interface BillService {
    BillResponse addBill(List<BillItemRequest> billItemRequestList);

    List<BillResponse> getAllBill(Integer page);

    List<BillResponse> getBillByFilter(Integer page, Integer status, String orderBy);

    void changeBillStatus(BillRequest billRequest);

    BillResponse getBillDetail(Integer billId);
}
