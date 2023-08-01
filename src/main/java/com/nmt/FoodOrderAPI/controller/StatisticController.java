package com.nmt.FoodOrderAPI.controller;

import com.nmt.FoodOrderAPI.dto.StatisticResponse;
import com.nmt.FoodOrderAPI.response.ResponseData;
import com.nmt.FoodOrderAPI.response.ResponseUtils;
import com.nmt.FoodOrderAPI.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistic")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping("/year/{year}")
    public ResponseEntity<ResponseData<List<StatisticResponse>>> getMonthlyStatisticByYear(
            @PathVariable(name = "year") Integer year) {
        return ResponseUtils.success(statisticService.getMonthlyStatisticByYear(year));
    }

    @GetMapping("/year")
    public ResponseEntity<ResponseData<List<Integer>>> getAllYearStatistic() {
        return ResponseUtils.success(statisticService.getAllStatisticYear());
    }

    @GetMapping("/day")
    public ResponseEntity<ResponseData<StatisticResponse>> getRevenueByDay(@RequestParam(name = "d") String day) {
        return ResponseUtils.success(statisticService.getRevenueByDay(day));
    }

    @GetMapping("/today")
    public ResponseEntity<ResponseData<StatisticResponse>> getTodayRevenue() {
        return ResponseUtils.success(statisticService.getTodayRevenue());
    }
}
