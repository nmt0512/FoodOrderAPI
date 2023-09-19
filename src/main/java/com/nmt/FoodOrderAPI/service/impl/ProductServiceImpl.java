package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.dto.ProductResponse;
import com.nmt.FoodOrderAPI.entity.Product;
import com.nmt.FoodOrderAPI.mapper.ProductMapper;
import com.nmt.FoodOrderAPI.repo.ProductRepository;
import com.nmt.FoodOrderAPI.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> getAllProduct() {
        List<Product> productList = productRepository.findByQuantityGreaterThan(0);
        return productMapper.toProductResponseList(productList);
    }

//    private String convertNameToCode(String name) {
//        String temp = Normalizer.normalize(name, Normalizer.Form.NFD);
//        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
//        return pattern.matcher(temp).replaceAll("").toLowerCase()
//                .replaceAll(" ", "-").replaceAll("đ", "d");
//    }
}
