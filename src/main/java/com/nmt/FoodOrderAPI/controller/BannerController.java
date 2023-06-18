package com.nmt.FoodOrderAPI.controller;

import com.nmt.FoodOrderAPI.response.ResponseData;
import com.nmt.FoodOrderAPI.response.ResponseUtils;
import com.nmt.FoodOrderAPI.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @GetMapping
    public ResponseEntity<ResponseData<List<String>>> getAllBannerLink() {
        return ResponseUtils.success(bannerService.findAllBannerLink());
    }
}
