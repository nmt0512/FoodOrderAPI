package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.StatisticResponse;

import java.util.List;

public interface StatisticService {
    List<StatisticResponse> getMonthlyStatisticByYear(int year);
    List<Integer> getAllStatisticYear();
    StatisticResponse getRevenueByDay(String day);
    StatisticResponse getTodayRevenue();
}
