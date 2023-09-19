package com.nmt.FoodOrderAPI.controller;

import com.nmt.FoodOrderAPI.dto.*;
import com.nmt.FoodOrderAPI.response.ResponseData;
import com.nmt.FoodOrderAPI.response.ResponseUtils;
import com.nmt.FoodOrderAPI.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bill")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @PostMapping
    public ResponseEntity<ResponseData<BillResponse>> addBill(@RequestBody List<BillItemRequest> billItemRequestList) {
        return ResponseUtils.success(billService.addBill(billItemRequestList));
    }

    @PostMapping("/status")
    public ResponseEntity<ResponseMessage> confirmOrCancelBill(
            @RequestBody BillRequest billRequest,
            @RequestHeader(name = "Authorization", required = false) String token
    ) {
        return ResponseEntity.ok(billService.confirmOrCancelBill(token, billRequest));
    }

    @PostMapping("/prepaid")
    public ResponseEntity<ResponseMessage> prepaidBill(@RequestBody PrepaidRequest prepaidRequest) {
        return ResponseEntity.ok(billService.prepaidBill(prepaidRequest));
    }

    @PostMapping("/pending-prepaid")
    public ResponseEntity<ResponseMessage> orderPendingPrepaidBill(@RequestBody PrepaidRequest prepaidRequest) {
        return ResponseEntity.ok(billService.orderPendingPrepaidBill(prepaidRequest));
    }

    @GetMapping("/pending-prepaid/all")
    public ResponseEntity<ResponseData<List<BillResponse>>> getAllPendingPrepaidBill() {
        return ResponseUtils.success(billService.getAllPendingPrepaidBill());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseData<List<BillResponse>>> getAllBillOrByFilter(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam(name = "time", required = false) String orderBy
    ) {
        if (status == null && orderBy == null)
            return ResponseUtils.success(billService.getAllBill(page));
        else
            return ResponseUtils.success(billService.getBillByFilter(page, status, orderBy));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseData<BillResponse>> getBillDetail(@PathVariable("id") Integer billId) {
        return ResponseUtils.success(billService.getBillDetail(billId));
    }

}
