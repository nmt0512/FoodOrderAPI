package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.dto.ProductResponse;
import com.nmt.FoodOrderAPI.entity.Image;
import com.nmt.FoodOrderAPI.entity.Product;
import com.nmt.FoodOrderAPI.mapper.ProductMapper;
import com.nmt.FoodOrderAPI.repo.ProductRepository;
import com.nmt.FoodOrderAPI.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> getAllProduct() {
        List<Product> productList = productRepository.findByQuantityGreaterThan(0);
        return toProductResponseList(productList);
    }

    private List<ProductResponse> toProductResponseList(List<Product> productList)
    {
        return productList.stream()
                .map(product -> {
                    List<String> imageLinks = product.getImageList().stream()
                            .map(Image::getLink)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    ProductResponse dto = productMapper.toProductResponse(product);
                    dto.setImageLinks(imageLinks);
                    return dto;
                })
                .collect(Collectors.toList());
    }

//    private String convertNameToCode(String name) {
//        String temp = Normalizer.normalize(name, Normalizer.Form.NFD);
//        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
//        return pattern.matcher(temp).replaceAll("").toLowerCase()
//                .replaceAll(" ", "-").replaceAll("đ", "d");
//    }
}
