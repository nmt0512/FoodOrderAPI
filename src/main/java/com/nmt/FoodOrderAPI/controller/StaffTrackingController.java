package com.nmt.FoodOrderAPI.controller;

import com.nmt.FoodOrderAPI.dto.StaffTrackingStatisticResponse;
import com.nmt.FoodOrderAPI.response.ResponseData;
import com.nmt.FoodOrderAPI.response.ResponseUtils;
import com.nmt.FoodOrderAPI.service.StaffTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tracking")
@RequiredArgsConstructor
public class StaffTrackingController {
    private final StaffTrackingService staffTrackingService;

    @GetMapping("/{month}/{year}")
    public ResponseEntity<ResponseData<List<StaffTrackingStatisticResponse>>> getStaffTrackingStatistic(
            @PathVariable(name = "month") Integer month,
            @PathVariable(name = "year") Integer year
    ) {
        return ResponseUtils.success(staffTrackingService.getStaffTrackingStatistic(month, year));
    }

}
