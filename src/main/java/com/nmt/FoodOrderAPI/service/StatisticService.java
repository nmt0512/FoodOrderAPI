package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.MonthlyStatisticResponse;
import com.nmt.FoodOrderAPI.dto.StatisticResponse;

import java.util.List;

public interface StatisticService {
    MonthlyStatisticResponse getMonthlyStatisticByYear(int year);

    List<Integer> getAllStatisticYear();

    StatisticResponse getRevenueByDay(String day);

    StatisticResponse getTodayRevenue();
}
