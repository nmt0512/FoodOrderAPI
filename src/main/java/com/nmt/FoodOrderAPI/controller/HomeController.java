package com.nmt.FoodOrderAPI.controller;

import com.nmt.FoodOrderAPI.dto.ProductResponse;
import com.nmt.FoodOrderAPI.response.ResponseData;
import com.nmt.FoodOrderAPI.response.ResponseUtils;
import com.nmt.FoodOrderAPI.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ResponseData<List<ProductResponse>>> getAllProduct() {
        return ResponseUtils.success(productService.getAllProduct());
    }

}
