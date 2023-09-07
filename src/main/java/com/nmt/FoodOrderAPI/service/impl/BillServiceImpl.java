package com.nmt.FoodOrderAPI.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.nmt.FoodOrderAPI.config.security.UserDetailsServiceImpl;
import com.nmt.FoodOrderAPI.dto.*;
import com.nmt.FoodOrderAPI.entity.*;
import com.nmt.FoodOrderAPI.enums.BillStatusCode;
import com.nmt.FoodOrderAPI.mapper.BillMapper;
import com.nmt.FoodOrderAPI.mapper.ProductMapper;
import com.nmt.FoodOrderAPI.mapper.PromotionMappper;
import com.nmt.FoodOrderAPI.mapper.UserMapper;
import com.nmt.FoodOrderAPI.repo.BillItemRepository;
import com.nmt.FoodOrderAPI.repo.BillRepository;
import com.nmt.FoodOrderAPI.repo.ProductRepository;
import com.nmt.FoodOrderAPI.repo.PromotionRepository;
import com.nmt.FoodOrderAPI.service.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    private final BillItemRepository billItemRepository;
    private final BillRepository billRepository;
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final BillMapper billMapper;
    private final PromotionMappper promotionMappper;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;
    private final SocketIOServer socketIOServer;


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
                .user(null)
                .staffName(userDetailsService.getCurrentUser().getFullname())
                .build();
        bill = billRepository.save(bill);

        for (BillItemRequest billItemRequest : billItemRequestList) {
            BillItem billItem = BillItem
                    .builder()
                    .product(productRepository
                            .findById(billItemRequest.getProductId())
                            .orElseThrow(NoSuchElementException::new)
                    )
                    .price(billItemRequest.getPrice())
                    .quantity(billItemRequest.getQuantity())
                    .bill(bill)
                    .build();
            billItemRepository.save(billItem);
        }

        BillResponse billResponse = billMapper.toBillResponse(bill);

        List<Promotion> applyingPromotionList = promotionRepository.findByApplyingPriceLessThanEqual(totalPrice);
        if (applyingPromotionList != null)
            billResponse.setGivenPromotionResponseList(applyingPromotionList.stream()
                    .map(promotionMappper::toPromotionResponse)
                    .collect(Collectors.toList()));
        else
            billResponse.setGivenPromotionResponseList(new ArrayList<>());
        return billResponse;
    }

    @Override
    @Transactional
    @CachePut(key = "#billRequest.id", value = "billDetailCache")
    public BillResponse changeBillStatus(BillRequest billRequest) {
        Bill bill = billRepository
                .findById(billRequest.getId())
                .orElseThrow(NoSuchElementException::new);
        bill.setStatus(billRequest.getStatus());
        bill.setTotalPrice(billRequest.getNewTotalPrice());
        if (bill.getUser() != null)
            bill.setStaffName(userDetailsService.getCurrentUser().getFullname());
        if (billRequest.getPromotionId() != null) {
            Promotion promotion = promotionRepository
                    .findById(billRequest.getPromotionId())
                    .orElseThrow(NoSuchElementException::new);
            bill.setPromotion(promotion);
        }
        bill = billRepository.save(bill);

        if (!Objects.equals(billRequest.getStatus(), BillStatusCode.CANCELLED.getCode())) {
            List<BillItem> billItemList = bill.getBillItemList();
            billItemList.forEach(billItem -> {
                Product product = billItem.getProduct();
                product.setQuantity(product.getQuantity() - billItem.getQuantity());
                productRepository.saveAndFlush(product);
            });
        }
        return toBillResponse(bill);
    }

    @Override
    @Transactional
    public ResponseMessage prepaidBill(PrepaidRequest prepaidRequest) {
        List<BillItemRequest> billItemRequestList = prepaidRequest.getBillItemRequestList();

        Promotion usedPromotion = prepaidRequest.getPromotionId() != null
                ?
                promotionRepository.findById(prepaidRequest.getPromotionId()).orElseThrow(NoSuchElementException::new)
                :
                null;

        Bill bill = Bill
                .builder()
                .time(new Timestamp(System.currentTimeMillis()))
                .status(BillStatusCode.PREPAID.getCode())
                .totalPrice(prepaidRequest.getTotalPrice())
                .user(userDetailsService.getCurrentUser())
                .promotion(usedPromotion)
                .build();
        bill = billRepository.save(bill);

        for (BillItemRequest billItemRequest : billItemRequestList) {
            BillItem billItem = BillItem
                    .builder()
                    .product(productRepository
                            .findById(billItemRequest.getProductId())
                            .orElseThrow(NoSuchElementException::new)
                    )
                    .price(billItemRequest.getPrice())
                    .quantity(billItemRequest.getQuantity())
                    .bill(bill)
                    .build();
            billItemRepository.save(billItem);
        }

        billItemRequestList.forEach(billItemRequest -> {
            Integer productId = billItemRequest.getProductId();
            Product product = productRepository
                    .findById(productId)
                    .orElseThrow(NoSuchElementException::new);
            product.setQuantity(product.getQuantity() - billItemRequest.getQuantity());
            productRepository.save(product);
        });

        ResponseMessage responseMessage = new ResponseMessage("Thành công");
        socketIOServer.getBroadcastOperations().sendEvent("bill", responseMessage);

        return responseMessage;
    }

    @Override
    public List<BillResponse> getAllBill(Integer page) {
        Page<Bill> billList = billRepository.findAll(PageRequest.of(page - 1, 10));
        return billList
                .stream()
                .map(billMapper::toBillResponse)
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
                .map(billMapper::toBillResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(key = "#billId", value = "billDetailCache")
    public BillResponse getBillDetail(Integer billId) {
        Bill bill = billRepository
                .findById(billId)
                .orElseThrow(NoSuchElementException::new);
        return toBillResponse(bill);
    }

    private BillResponse toBillResponse(Bill bill) {
        BillResponse billResponse = billMapper.toBillResponse(bill);
        billResponse.setBillItemResponseList(
                bill.getBillItemList().stream().map(billItem -> {
                    Product product = billItem.getProduct();
                    ProductResponse productResponse = productMapper.toProductResponse(product);
                    productResponse.setImageLinks(product.getImageList()
                            .stream()
                            .map(Image::getLink)
                            .collect(Collectors.toList()));
                    return BillItemResponse.builder()
                            .quantity(billItem.getQuantity())
                            .price(billItem.getPrice())
                            .product(productResponse)
                            .build();
                }).collect(Collectors.toList())
        );

        if (bill.getPromotion() != null)
            billResponse.setUsedPromotionResponse(promotionMappper.toPromotionResponse(bill.getPromotion()));

        if (bill.getUser() != null) {
            UserResponse userResponse = userMapper.toUserResponse(bill.getUser());
            billResponse.setUserResponse(userResponse);
        }
        return billResponse;
    }

}
