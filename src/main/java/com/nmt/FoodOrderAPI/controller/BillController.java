package com.nmt.FoodOrderAPI.controller;

import com.corundumstudio.socketio.SocketIOServer;
import com.nmt.FoodOrderAPI.dto.*;
import com.nmt.FoodOrderAPI.enums.BillStatusCode;
import com.nmt.FoodOrderAPI.response.ResponseData;
import com.nmt.FoodOrderAPI.response.ResponseUtils;
import com.nmt.FoodOrderAPI.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/bill")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;
    private final SocketIOServer socketIOServer;

    @PostMapping
    public ResponseEntity<ResponseData<BillResponse>> addBill(@RequestBody List<BillItemRequest> billItemRequestList) {
        return ResponseUtils.success(billService.addBill(billItemRequestList));
    }

    @PostMapping("/status")
    public ResponseEntity<ResponseMessage> confirmBill(@RequestBody BillRequest billRequest) {
        billService.changeBillStatus(billRequest);
        if (Objects.equals(billRequest.getStatus(), BillStatusCode.CANCELLED.getCode()))
            return ResponseEntity.ok(new ResponseMessage("Đã hủy!"));
        return ResponseEntity.ok(new ResponseMessage("Thành công!"));
    }

    @PostMapping("/prepaid")
    public ResponseEntity<ResponseMessage> prepaidBill(@RequestBody PrepaidRequest prepaidRequest) {
        billService.prepaidBill(prepaidRequest);
        ResponseMessage responseMessage = new ResponseMessage("Thành công");
        socketIOServer.getBroadcastOperations().sendEvent("bill", responseMessage);
        return ResponseEntity.ok(responseMessage);
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

//    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<BillResponse> getAllBillFlux() {
//        return billService.getAllBillFlux();
//    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseData<BillResponse>> getBillDetail(@PathVariable("id") Integer billId) {
        return ResponseUtils.success(billService.getBillDetail(billId));
    }

}
