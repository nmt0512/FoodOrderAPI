package com.nmt.FoodOrderAPI.controller;

import com.nmt.FoodOrderAPI.dto.*;
import com.nmt.FoodOrderAPI.response.ResponseData;
import com.nmt.FoodOrderAPI.response.ResponseUtils;
import com.nmt.FoodOrderAPI.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<ResponseData<BillResponse>> addBill(@RequestBody List<BillItemRequest> billItemRequestList) {
        return ResponseUtils.success(billService.addBill(billItemRequestList));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseData<List<BillResponse>>> getAllBill(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam(name = "time", required = false) String orderBy) {
        if(status == null && orderBy == null)
            return ResponseUtils.success(billService.getAllBill(page));
        else
            return ResponseUtils.success(billService.getBillByFilter(page, status, orderBy));
    }

    @PostMapping("/status")
    public ResponseEntity<ResponseMessage> confirmBill(@RequestBody BillRequest billRequest) {
        billService.changeBillStatus(billRequest);
        if (billRequest.getStatus() == 3)
            return ResponseEntity.ok(new ResponseMessage("Đã hủy!"));
        return ResponseEntity.ok(new ResponseMessage("Thành công!"));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseData<BillResponse>> getBillDetail(@PathVariable("id") Integer billId) {
        return ResponseUtils.success(billService.getBillDetail(billId));
    }

}
