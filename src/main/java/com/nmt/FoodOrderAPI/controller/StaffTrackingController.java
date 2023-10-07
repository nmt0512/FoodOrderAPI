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

    @GetMapping("/{time}")
    public ResponseEntity<ResponseData<List<StaffTrackingStatisticResponse>>> getStaffTrackingStatistic(
            @PathVariable(name = "time") String time
    ) {
        return ResponseUtils.success(staffTrackingService.getStaffTrackingStatistic(time));
    }

    @GetMapping
    public ResponseEntity<ResponseData<List<String>>> getAllMonthStaffTrackingStatistic() {
        return ResponseUtils.success(staffTrackingService.getAllMonthStaffTrackingStatistic());
    }

}
