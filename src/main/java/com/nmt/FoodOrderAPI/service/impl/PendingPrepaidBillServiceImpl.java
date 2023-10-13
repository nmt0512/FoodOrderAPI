package com.nmt.FoodOrderAPI.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.nmt.FoodOrderAPI.config.security.UserDetailsServiceImpl;
import com.nmt.FoodOrderAPI.dto.*;
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
import com.nmt.FoodOrderAPI.service.email.EmailService;
import com.nmt.FoodOrderAPI.service.firebase.FirebaseCloudMessagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
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
    private final FirebaseCloudMessagingService firebaseCloudMessagingService;
    private final EmailService emailService;
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
                .address(prepaidRequest.getAddress())
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

        schedulePendingPrepaidBillTimeout(pendingPrepaidBill.getId(), 5, customer.getId(), false);

        CompletableFuture.runAsync(() -> {
            socketIOServer.getBroadcastOperations().sendEvent("pendingPrepaidBill", 1);
            firebaseCloudMessagingService.sendNotificationToShipperTopic("shipperTopic", customer.getFullname());
            log.info("Sent new bill notification to shipper");
        });

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
            schedulePendingPrepaidBillTimeout(pendingPrepaidBillId, 5, shipper.getId(), true);
            log.info(
                    "Task PendingPrepaidBill ID {} timeout is cancelled: {} and updated",
                    pendingPrepaidBillId,
                    scheduledFuture.isCancelled()
            );
        }

        socketIOServer.getBroadcastOperations().sendEvent(
                pendingPrepaidBill.getCustomer().getId().toString(),
                true,
                shipper.getId()
        );
        log.info("Sent received bill notification to customer");

        return new ResponseMessage("Đã nhận đơn hàng và đợi khách hàng thanh toán");
    }

    @Override
    @Transactional
    public ResponseMessage paymentPendingPrepaidBillByCustomer(int pendingPrepaidBillId) {
        PendingPrepaidBill pendingPrepaidBill = pendingPrepaidBillRepository
                .findById(pendingPrepaidBillId)
                .orElseThrow(() -> new NoSuchElementException("No such pending prepaid bill found"));
        pendingPrepaidBill.setIsCustomerPrepaid(true);
        pendingPrepaidBillRepository.save(pendingPrepaidBill);

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

        Bill savedBill = billRepository.save(bill);

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

        CompletableFuture.runAsync(() -> {
            socketIOServer.getBroadcastOperations().sendEvent(pendingPrepaidBill.getShipper().getId().toString(), true);
            socketIOServer.getBroadcastOperations().sendEvent("bill", 1);
            firebaseCloudMessagingService.sendNotificationToShipper(
                    savedBill.getShipper(),
                    savedBill.getCustomer().getFullname()
            );
            log.info("Sent payment notification to shipper and new bill to store");
        });

        return new ResponseMessage(BillStatusCode.PAID_FOR_SHIPPING.getMessage());
    }

    @Override
    @Transactional
    public ResponseMessage completeCustomerPrepaidBillByShipper(int pendingPrepaidBillId) {
        PendingPrepaidBill pendingPrepaidBill = pendingPrepaidBillRepository
                .findById(pendingPrepaidBillId)
                .orElseThrow(() -> new NoSuchElementException("No such pending prepaid bill found"));
        String customerEmail = pendingPrepaidBill.getCustomer().getEmail();

        if (customerEmail != null) {
            CompletedBillNotification completedBillNotification = mapPendingPrepaidBillToCompletedBillNotification(pendingPrepaidBill);
            CompletableFuture.runAsync(() -> {
                try {
                    emailService.sendCompletedBillNotificationEmail(customerEmail, completedBillNotification);
                } catch (MessagingException exception) {
                    log.error("Sending email to customer error");
                }
            });
        }

        pendingPrepaidBillRepository.deleteById(pendingPrepaidBillId);
        return new ResponseMessage("Đã hoàn thành đơn hàng");
    }

    @Override
    public List<BillResponse> getAllPendingPrepaidBill() {
        return pendingPrepaidBillRepository.findByShipperNullOrderByTimeDesc()
                .stream()
                .map(billMapper::mapPendingPrepaidBillToBillResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillResponse> getAllReceivedCustomerPrepaidBillByShipper() {
        return pendingPrepaidBillRepository.findByShipperAndIsCustomerPrepaidTrueOrderByTimeDesc(userDetailsService.getCurrentUser())
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

            String logInfo = timeoutMinutes > 1
                    ?
                    "Task PendingPrepaidBill ID {} with {} minutes timeout is scheduled"
                    :
                    "Task PendingPrepaidBill ID {} with {} minute timeout is scheduled";

            log.info(logInfo, pendingPrepaidBillId, timeoutMinutes);
        }
    }

    private CompletedBillNotification mapPendingPrepaidBillToCompletedBillNotification(PendingPrepaidBill pendingPrepaidBill) {
        List<CompletedBillItem> completedBillItemList = pendingPrepaidBill.getPendingPrepaidBillItemList()
                .stream()
                .map(pendingPrepaidBillItem -> {
                    Product product = pendingPrepaidBillItem.getProduct();
                    return CompletedBillItem.builder()
                            .name(product.getName())
                            .quantity(pendingPrepaidBillItem.getQuantity())
                            .price(pendingPrepaidBillItem.getPrice())
                            .image(product
                                    .getImageList()
                                    .get(0)
                                    .getLink()
                            )
                            .build();
                })
                .collect(Collectors.toList());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String completedTime = formatter.format(new Date());

        return CompletedBillNotification.builder()
                .pendingPrepaidBillId(pendingPrepaidBill.getId())
                .totalPrice(pendingPrepaidBill.getTotalPrice())
                .completedTime(completedTime)
                .completedBillItemList(completedBillItemList)
                .build();
    }

}
