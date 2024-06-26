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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pending-prepaid")
@RequiredArgsConstructor
public class PendingPrepaidBillController {
    private final PendingPrepaidBillService pendingPrepaidBillService;

    @PostMapping
    public ResponseEntity<ResponseData<BillResponse>> orderPendingPrepaidBill(@RequestBody @Valid PrepaidRequest prepaidRequest) {
        return ResponseUtils.success(pendingPrepaidBillService.createPendingPrepaidBill(prepaidRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseData<List<BillResponse>>> getAllPendingPrepaidBill() {
        return ResponseUtils.success(pendingPrepaidBillService.getAllPendingPrepaidBill());
    }

    @GetMapping("/received")
    public ResponseEntity<ResponseData<List<BillResponse>>> getAllReceivedPendingPrepaidBillByShipper() {
        return ResponseUtils.success(pendingPrepaidBillService.getAllReceivedCustomerPrepaidBillByShipper());
    }

    @PostMapping("/payment/{id}")
    public ResponseEntity<ResponseMessage> paymentPendingPrepaidBillByCustomer(@PathVariable("id") Integer pendingPrepaidBillId) {
        return ResponseEntity.ok(pendingPrepaidBillService.paymentPendingPrepaidBillByCustomer(pendingPrepaidBillId));
    }

    @PutMapping("/receive/{id}")
    public ResponseEntity<ResponseMessage> confirmReceivedPendingPrepaidBill(@PathVariable("id") Integer pendingPrepaidBillId) {
        return ResponseEntity.ok(pendingPrepaidBillService.confirmReceivedPendingPrepaidBill(pendingPrepaidBillId));
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<ResponseMessage> completeReceivedCustomerPrepaidBill(@PathVariable("id") Integer pendingPrepaidBillId) {
        return ResponseEntity.ok(pendingPrepaidBillService.completeCustomerPrepaidBillByShipper(pendingPrepaidBillId));
    }
}
