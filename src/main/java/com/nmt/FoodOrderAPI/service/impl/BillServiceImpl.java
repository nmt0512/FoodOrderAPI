package com.nmt.FoodOrderAPI.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.nmt.FoodOrderAPI.config.security.UserDetailsServiceImpl;
import com.nmt.FoodOrderAPI.config.utils.JwtUtils;
import com.nmt.FoodOrderAPI.dto.*;
import com.nmt.FoodOrderAPI.entity.*;
import com.nmt.FoodOrderAPI.enums.BillStatusCode;
import com.nmt.FoodOrderAPI.mapper.BillMapper;
import com.nmt.FoodOrderAPI.mapper.PromotionMappper;
import com.nmt.FoodOrderAPI.repo.BillRepository;
import com.nmt.FoodOrderAPI.repo.ProductRepository;
import com.nmt.FoodOrderAPI.repo.PromotionRepository;
import com.nmt.FoodOrderAPI.repo.StaffTrackingRepository;
import com.nmt.FoodOrderAPI.service.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final StaffTrackingRepository staffTrackingRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final BillMapper billMapper;
    private final PromotionMappper promotionMappper;
    private final SocketIOServer socketIOServer;
    private final JwtUtils jwtUtils;


    @Override
    @Transactional
    public BillResponse addBill(List<BillItemRequest> billItemRequestList) {
        int totalPrice = 0;
        for (BillItemRequest item : billItemRequestList)
            totalPrice += item.getPrice();

        Bill bill = Bill
                .builder()
                .time(new Timestamp(System.currentTimeMillis()))
                .status(BillStatusCode.PENDING.getCode())
                .totalPrice(totalPrice)
                .customer(null)
                .staff(userDetailsService.getCurrentUser())
                .build();

        BillResponse billResponse = billMapper.mapBillToBillResponse(saveBillByBillItemRequestList(bill, billItemRequestList));

        List<Promotion> applyingPromotionList = promotionRepository.findByApplyingPriceLessThanEqual(totalPrice);
        if (applyingPromotionList != null)
            billResponse.setGivenPromotionResponseList(
                    applyingPromotionList.stream()
                            .map(promotionMappper::toPromotionResponse)
                            .collect(Collectors.toList())
            );
        else
            billResponse.setGivenPromotionResponseList(new ArrayList<>());
        return billResponse;
    }

    @Override
    @Transactional
    public ResponseMessage confirmOrCancelBill(String token, BillRequest billRequest) {
        Bill bill = billRepository
                .findById(billRequest.getId())
                .orElseThrow(() -> new NoSuchElementException("No such bill found"));
        bill.setStatus(billRequest.getStatus());
        bill.setTotalPrice(billRequest.getNewTotalPrice());
        if (bill.getCustomer() != null)
            bill.setStaff(userDetailsService.getCurrentUser());
        if (billRequest.getPromotionId() != null) {
            Promotion promotion = promotionRepository
                    .findById(billRequest.getPromotionId())
                    .orElseThrow(() -> new NoSuchElementException("No such promotion found"));
            bill.setPromotion(promotion);
        }
        bill = billRepository.save(bill);

        if (Objects.equals(billRequest.getStatus(), BillStatusCode.COMPLETED.getCode())) {
            List<BillItem> billItemList = bill.getBillItemList();
            billItemList.forEach(billItem -> {
                Product product = billItem.getProduct();
                product.setQuantity(product.getQuantity() - billItem.getQuantity());
                productRepository.saveAndFlush(product);
            });

            int trackingId = jwtUtils.getTokenStaffTrackingId(token.substring(7));
            StaffTracking staffTracking = staffTrackingRepository
                    .findById(trackingId)
                    .orElseThrow(() -> new NoSuchElementException("No such Staff Tracking found"));
            staffTracking.setRevenue(staffTracking.getRevenue() + billRequest.getNewTotalPrice());
            staffTrackingRepository.save(staffTracking);
        }

        if (Objects.equals(BillStatusCode.COMPLETED.getCode(), billRequest.getStatus()))
            return new ResponseMessage(BillStatusCode.COMPLETED.getMessage());
        return new ResponseMessage(BillStatusCode.CANCELLED.getMessage());
    }

    @Override
    @Transactional
    public ResponseMessage prepaidBill(PrepaidRequest prepaidRequest) {
        List<BillItemRequest> billItemRequestList = prepaidRequest.getBillItemRequestList();

        Promotion usedPromotion = prepaidRequest.getPromotionId() != null
                ?
                promotionRepository
                        .findById(prepaidRequest.getPromotionId())
                        .orElseThrow(() -> new NoSuchElementException("No such promotion found"))
                :
                null;

        Bill bill = Bill
                .builder()
                .time(new Timestamp(System.currentTimeMillis()))
                .status(BillStatusCode.PREPAID.getCode())
                .totalPrice(prepaidRequest.getTotalPrice())
                .customer(userDetailsService.getCurrentUser())
                .staff(null)
                .promotion(usedPromotion)
                .build();

        saveBillByBillItemRequestList(bill, billItemRequestList);

        billItemRequestList.forEach(billItemRequest -> {
            Integer productId = billItemRequest.getProductId();
            Product product = productRepository
                    .findById(productId)
                    .orElseThrow(() -> new NoSuchElementException("No such product found"));
            product.setQuantity(product.getQuantity() - billItemRequest.getQuantity());
            productRepository.save(product);
        });

        ResponseMessage responseMessage = new ResponseMessage(BillStatusCode.PREPAID.getMessage());

        socketIOServer.getBroadcastOperations().sendEvent("prepaidBill", responseMessage);

        return responseMessage;
    }

    @Override
    public List<BillResponse> getAllBill(Integer page) {
        Page<Bill> billList = billRepository.findAll(PageRequest.of(page - 1, 10));
        return billList
                .stream()
                .map(billMapper::mapBillToBillResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillResponse> getBillByFilter(Integer page, Integer status, String orderBy) {
        Page<Bill> billList;
        Pageable ascTimePageable = PageRequest.of(
                page - 1,
                10,
                Sort.by(Sort.Direction.ASC, "time"));
        Pageable descTimePageable = PageRequest.of(
                page - 1,
                10,
                Sort.by(Sort.Direction.DESC, "time"));

        if (status == null) {
            billList = orderBy.equals("oldest")
                    ?
                    billRepository.findAll(ascTimePageable)
                    :
                    billRepository.findAll(descTimePageable);
        } else if (orderBy == null) {
            billList = billRepository.findByStatus(status, PageRequest.of(
                    page - 1,
                    10)
            );
        } else {
            billList = orderBy.equals("oldest")
                    ?
                    billRepository.findByStatus(status, ascTimePageable)
                    :
                    billRepository.findByStatus(status, descTimePageable);
        }
        return billList
                .stream()
                .map(billMapper::mapBillToBillResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BillResponse getBillDetail(Integer billId) {
        Bill bill = billRepository
                .findById(billId)
                .orElseThrow(() -> new NoSuchElementException("No such bill found"));
        return billMapper.mapBillToBillResponse(bill);
    }

    private Bill saveBillByBillItemRequestList(Bill bill, List<BillItemRequest> billItemRequestList) {
        List<BillItem> billItemList = new ArrayList<>();
        billItemRequestList.forEach(billItemRequest -> {
            BillItem billItem = BillItem
                    .builder()
                    .product(productRepository
                            .findById(billItemRequest.getProductId())
                            .orElseThrow(() -> new NoSuchElementException("No such product found"))
                    )
                    .price(billItemRequest.getPrice())
                    .quantity(billItemRequest.getQuantity())
                    .bill(bill)
                    .build();
            billItemList.add(billItem);
        });

        bill.setBillItemList(billItemList);
        return billRepository.save(bill);
    }
}
