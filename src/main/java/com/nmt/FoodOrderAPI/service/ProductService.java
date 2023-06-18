package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProduct();
}
