package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.dto.StatisticResponse;
import com.nmt.FoodOrderAPI.repo.StatisticRepository;
import com.nmt.FoodOrderAPI.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private StatisticRepository statisticRepository;

    @Override
    public List<StatisticResponse> getMonthlyStatisticByYear(int year) {
        return statisticRepository.getMonthlyStatisticByYear(year);
    }

    @Override
    public List<Integer> getAllStatisticYear() {
        return statisticRepository.getAllStatisticYear();
    }

    @Override
    public StatisticResponse getRevenueByDay(String day) {
        return statisticRepository.getRevenueByDay(day);
    }

    @Override
    public StatisticResponse getTodayRevenue() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String today = currentDate.format(formatter);
        return statisticRepository.getRevenueByDay(today);
    }

}
