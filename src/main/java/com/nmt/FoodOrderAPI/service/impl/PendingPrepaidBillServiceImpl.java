package com.nmt.FoodOrderAPI.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.nmt.FoodOrderAPI.config.security.UserDetailsServiceImpl;
import com.nmt.FoodOrderAPI.dto.BillItemRequest;
import com.nmt.FoodOrderAPI.dto.BillResponse;
import com.nmt.FoodOrderAPI.dto.PrepaidRequest;
import com.nmt.FoodOrderAPI.dto.ResponseMessage;
import com.nmt.FoodOrderAPI.entity.*;
import com.nmt.FoodOrderAPI.enums.BillStatusCode;
import com.nmt.FoodOrderAPI.mapper.BillMapper;
import com.nmt.FoodOrderAPI.repo.BillRepository;
import com.nmt.FoodOrderAPI.repo.PendingPrepaidBillRepository;
import com.nmt.FoodOrderAPI.repo.ProductRepository;
import com.nmt.FoodOrderAPI.repo.PromotionRepository;
import com.nmt.FoodOrderAPI.service.PendingPrepaidBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PendingPrepaidBillServiceImpl implements PendingPrepaidBillService {
    private final PendingPrepaidBillRepository pendingPrepaidBillRepository;
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final BillRepository billRepository;
    private final SocketIOServer socketIOServer;
    private final UserDetailsServiceImpl userDetailsService;
    private final BillMapper billMapper;

    @Override
    @Transactional
    public ResponseMessage createPendingPrepaidBill(PrepaidRequest prepaidRequest) {
        List<BillItemRequest> billItemRequestList = prepaidRequest.getBillItemRequestList();

        Promotion usedPromotion = prepaidRequest.getPromotionId() != null
                ?
                promotionRepository
                        .findById(prepaidRequest.getPromotionId())
                        .orElseThrow(() -> new NoSuchElementException("No such promotion found"))
                :
                null;

        PendingPrepaidBill pendingPrepaidBill = PendingPrepaidBill
                .builder()
                .time(new Timestamp(System.currentTimeMillis()))
                .totalPrice(prepaidRequest.getTotalPrice())
                .customer(userDetailsService.getCurrentUser())
                .promotion(usedPromotion)
                .build();

        List<PendingPrepaidBillItem> pendingPrepaidBillItemList = new ArrayList<>();
        billItemRequestList.forEach(billItemRequest -> {
            PendingPrepaidBillItem pendingPrepaidBillItem = PendingPrepaidBillItem.builder()
                    .pendingPrepaidBill(pendingPrepaidBill)
                    .product(productRepository
                            .findById(billItemRequest.getProductId())
                            .orElseThrow(() -> new NoSuchElementException("No such product found"))
                    )
                    .price(billItemRequest.getPrice())
                    .quantity(billItemRequest.getQuantity())
                    .build();
            pendingPrepaidBillItemList.add(pendingPrepaidBillItem);
        });

        pendingPrepaidBill.setPendingPrepaidBillItemList(pendingPrepaidBillItemList);
        pendingPrepaidBillRepository.save(pendingPrepaidBill);

        ResponseMessage responseMessage = new ResponseMessage("Đã đặt hàng và đang tìm shipper");
        socketIOServer.getBroadcastOperations().sendEvent("pendingPrepaidBill", responseMessage);

        return responseMessage;
    }

    @Override
    public List<BillResponse> getAllPendingPrepaidBill() {
        return pendingPrepaidBillRepository.findAll()
                .stream()
                .map(billMapper::mapPendingPrepaidBillToBillResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResponseMessage confirmReceivedPendingPrepaidBill(int pendingPrepaidBillId) {
        PendingPrepaidBill pendingPrepaidBill = pendingPrepaidBillRepository
                .findById(pendingPrepaidBillId)
                .orElseThrow(() -> new NoSuchElementException("No pending prepaid bill found"));
        pendingPrepaidBill.setReceived(true);

        saveBillByPendingPrepaidBill(pendingPrepaidBillRepository.saveAndFlush(pendingPrepaidBill));

        pendingPrepaidBillRepository.deleteById(pendingPrepaidBillId);
        pendingPrepaidBillRepository.flush();

        ResponseMessage responseMessage = new ResponseMessage("Shipper đã nhận đơn hàng");
        socketIOServer.getBroadcastOperations().sendEvent("shipperReceivedBill", responseMessage);

        return responseMessage;
    }

    private void saveBillByPendingPrepaidBill(PendingPrepaidBill pendingPrepaidBill) {
        Bill bill = billMapper.mapPendingPrepaidBillToBill(pendingPrepaidBill);
        bill.setStatus(BillStatusCode.SHIPPER_RECEIVED.getCode());
        bill.setShipper(userDetailsService.getCurrentUser());

        List<BillItem> billItemList = pendingPrepaidBill
                .getPendingPrepaidBillItemList()
                .stream()
                .map(pendingPrepaidBillItem -> {
                    BillItem billItem = billMapper.mapPendingPrepaidBillItemToBillItem(pendingPrepaidBillItem);
                    billItem.setBill(bill);
                    return billItem;
                })
                .collect(Collectors.toList());

        bill.setBillItemList(billItemList);
        billRepository.saveAndFlush(bill);
    }
}
