package com.nmt.FoodOrderAPI.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.nmt.FoodOrderAPI.config.security.UserDetailsServiceImpl;
import com.nmt.FoodOrderAPI.dto.BillItemRequest;
import com.nmt.FoodOrderAPI.dto.BillResponse;
import com.nmt.FoodOrderAPI.dto.PrepaidRequest;
import com.nmt.FoodOrderAPI.dto.ResponseMessage;
import com.nmt.FoodOrderAPI.entity.*;
import com.nmt.FoodOrderAPI.enums.BillStatusCode;
import com.nmt.FoodOrderAPI.exception.BaseException;
import com.nmt.FoodOrderAPI.exception.CommonErrorCode;
import com.nmt.FoodOrderAPI.mapper.BillMapper;
import com.nmt.FoodOrderAPI.repo.BillRepository;
import com.nmt.FoodOrderAPI.repo.PendingPrepaidBillRepository;
import com.nmt.FoodOrderAPI.repo.ProductRepository;
import com.nmt.FoodOrderAPI.repo.PromotionRepository;
import com.nmt.FoodOrderAPI.service.PendingPrepaidBillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PendingPrepaidBillServiceImpl implements PendingPrepaidBillService {
    private final PendingPrepaidBillRepository pendingPrepaidBillRepository;
    private final BillRepository billRepository;
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final SocketIOServer socketIOServer;
    private final UserDetailsServiceImpl userDetailsService;
    private final ScheduledExecutorService scheduledExecutorService;
    private final BillMapper billMapper;
    private final Map<Integer, ScheduledFuture<?>> scheduledFutureMap;

    @Override
    @Transactional
    public BillResponse createPendingPrepaidBill(PrepaidRequest prepaidRequest) {
        List<BillItemRequest> billItemRequestList = prepaidRequest.getBillItemRequestList();

        Promotion usedPromotion = prepaidRequest.getPromotionId() != null
                ?
                promotionRepository
                        .findById(prepaidRequest.getPromotionId())
                        .orElseThrow(() -> new NoSuchElementException("No such promotion found"))
                :
                null;

        User customer = userDetailsService.getCurrentUser();

        PendingPrepaidBill pendingPrepaidBill = PendingPrepaidBill
                .builder()
                .time(new Timestamp(System.currentTimeMillis()))
                .totalPrice(prepaidRequest.getTotalPrice())
                .customer(customer)
                .promotion(usedPromotion)
                .build();

        List<PendingPrepaidBillItem> pendingPrepaidBillItemList = new ArrayList<>();
        for (BillItemRequest billItemRequest : billItemRequestList) {
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
        }

        pendingPrepaidBill.setPendingPrepaidBillItemList(pendingPrepaidBillItemList);
        pendingPrepaidBill = pendingPrepaidBillRepository.save(pendingPrepaidBill);

        BillResponse billResponse = billMapper.mapPendingPrepaidBillToBillResponse(pendingPrepaidBill);

        schedulePendingPrepaidBillTimeout(pendingPrepaidBill.getId(), 2, customer.getId(), false);
        socketIOServer.getBroadcastOperations().sendEvent("pendingPrepaidBill", 1);

        return billResponse;
    }

    @Override
    @Transactional
    public ResponseMessage confirmReceivedPendingPrepaidBill(int pendingPrepaidBillId) {
        PendingPrepaidBill pendingPrepaidBill = pendingPrepaidBillRepository
                .findById(pendingPrepaidBillId)
                .orElseThrow(() -> new NoSuchElementException("No pending prepaid bill found"));

        if (pendingPrepaidBill.getShipper() != null)
            throw new BaseException(CommonErrorCode.RECEIVE_PENDING_PREPAID_BILL_FAILED);

        User shipper = userDetailsService.getCurrentUser();
        pendingPrepaidBill.setShipper(shipper);
        pendingPrepaidBillRepository.save(pendingPrepaidBill);

        ScheduledFuture<?> scheduledFuture = scheduledFutureMap.get(pendingPrepaidBillId);
        if (scheduledFuture != null && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(false);
            schedulePendingPrepaidBillTimeout(pendingPrepaidBillId, 10, shipper.getId(), true);
            log.info(
                    "Task with PendingPrepaidBill ID {} is cancelled: {} and updated",
                    pendingPrepaidBillId,
                    scheduledFuture.isCancelled()
            );
        }

        socketIOServer.getBroadcastOperations().sendEvent(pendingPrepaidBill.getCustomer().getId().toString(), true);

        return new ResponseMessage("Đã nhận đơn hàng và đợi khách hàng thanh toán");
    }

    @Override
    @Transactional
    public ResponseMessage paymentPendingPrepaidBillByCustomer(int pendingPrepaidBillId) {
        PendingPrepaidBill pendingPrepaidBill = pendingPrepaidBillRepository
                .findById(pendingPrepaidBillId)
                .orElseThrow(() -> new NoSuchElementException("No such pending prepaid bill found"));

        Bill bill = billMapper.mapPendingPrepaidBillToBill(pendingPrepaidBill);
        bill.setStatus(BillStatusCode.PAID_FOR_SHIPPING.getCode());
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

        billRepository.save(bill);

        pendingPrepaidBillRepository.deleteById(pendingPrepaidBillId);

        ScheduledFuture<?> timeoutTaskScheduledFuture = scheduledFutureMap.get(pendingPrepaidBillId);
        if (timeoutTaskScheduledFuture != null && !timeoutTaskScheduledFuture.isDone()) {
            scheduledFutureMap.remove(pendingPrepaidBillId);
            timeoutTaskScheduledFuture.cancel(false);
            log.info(
                    "Task with PendingPrepaidBill ID {} is removed and cancelled: {}",
                    pendingPrepaidBillId,
                    timeoutTaskScheduledFuture.isCancelled()
            );
        }

        socketIOServer.getBroadcastOperations().sendEvent(pendingPrepaidBill.getShipper().getId().toString(), true);

        return new ResponseMessage(BillStatusCode.PAID_FOR_SHIPPING.getMessage());
    }

    @Override
    public List<BillResponse> getAllPendingPrepaidBill() {
        return pendingPrepaidBillRepository.findByShipperNullOrderByTimeDesc()
                .stream()
                .map(billMapper::mapPendingPrepaidBillToBillResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillResponse> getAllReceivedPendingPrepaidBillByShipper() {
        return pendingPrepaidBillRepository.findByShipper(userDetailsService.getCurrentUser())
                .stream()
                .map(billMapper::mapPendingPrepaidBillToBillResponse)
                .collect(Collectors.toList());
    }

    private void schedulePendingPrepaidBillTimeout(
            int pendingPrepaidBillId,
            int timeoutMinutes,
            Integer userId,
            boolean isShipper
    ) {
        boolean taskCondition = (isShipper && pendingPrepaidBillRepository
                .findByIdAndShipperNotNull(pendingPrepaidBillId)
                .isPresent()
        ) || (!isShipper && pendingPrepaidBillRepository
                .findByIdAndShipperNull(pendingPrepaidBillId)
                .isPresent()
        );
        if (taskCondition) {
            ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule(
                    () -> {
                        pendingPrepaidBillRepository.deleteById(pendingPrepaidBillId);
                        socketIOServer.getBroadcastOperations().sendEvent(userId.toString(), false);

                        log.info("Deleted pending prepaid bill ID: {}", pendingPrepaidBillId);
                    },
                    timeoutMinutes,
                    TimeUnit.MINUTES
            );
            scheduledFutureMap.put(pendingPrepaidBillId, scheduledFuture);
            log.info("Task with PendingPrepaidBill ID {} is scheduled", pendingPrepaidBillId);
        }
    }

}
