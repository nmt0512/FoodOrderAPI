package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.*;
import reactor.core.publisher.Flux;

import java.util.List;

public interface BillService {
    BillResponse addBill(List<BillItemRequest> billItemRequestList);

    BillResponse changeBillStatus(BillRequest billRequest);

    ResponseMessage prepaidBill(PrepaidRequest prepaidRequest);

    BillResponse getBillDetail(Integer billId);

    List<BillResponse> getAllBill(Integer page);

    Flux<BillResponse> getAllBillFlux();

    List<BillResponse> getBillByFilter(Integer page, Integer status, String orderBy);
}
