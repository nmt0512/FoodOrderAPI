package com.nmt.FoodOrderAPI.controller;

import com.nmt.FoodOrderAPI.dto.BillResponse;
import com.nmt.FoodOrderAPI.dto.PrepaidRequest;
import com.nmt.FoodOrderAPI.dto.ResponseMessage;
import com.nmt.FoodOrderAPI.response.ResponseData;
import com.nmt.FoodOrderAPI.response.ResponseUtils;
import com.nmt.FoodOrderAPI.service.PendingPrepaidBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pending-prepaid")
@RequiredArgsConstructor
public class PendingPrepaidBillController {
    private final PendingPrepaidBillService pendingPrepaidBillService;

    @PostMapping
    public ResponseEntity<ResponseMessage> orderPendingPrepaidBill(@RequestBody PrepaidRequest prepaidRequest) {
        return ResponseEntity.ok(pendingPrepaidBillService.orderPendingPrepaidBill(prepaidRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseData<List<BillResponse>>> getAllPendingPrepaidBill() {
        return ResponseUtils.success(pendingPrepaidBillService.getAllPendingPrepaidBill());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> confirmReceivedPendingPrepaidBill(@PathVariable("id") Integer pendingPrepaidBillId) {
        return ResponseEntity.ok(pendingPrepaidBillService.confirmReceivedPendingPrepaidBill(pendingPrepaidBillId));
    }
}
