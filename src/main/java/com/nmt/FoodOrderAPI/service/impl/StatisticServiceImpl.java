package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.dto.MonthlyStatisticResponse;
import com.nmt.FoodOrderAPI.dto.StatisticResponse;
import com.nmt.FoodOrderAPI.repo.StatisticRepository;
import com.nmt.FoodOrderAPI.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;

    @Override
    public MonthlyStatisticResponse getMonthlyStatisticByYear(int year) {
        List<StatisticResponse> statisticResponses = statisticRepository.getMonthlyStatisticByYear(year);
        Integer maxMonthRevenue = statisticResponses
                .stream()
                .mapToInt(StatisticResponse::getRevenue)
                .max()
                .orElseThrow(() -> new NoSuchElementException("No statistic response found"));
        Integer totalRevenue = statisticResponses
                .stream()
                .mapToInt(StatisticResponse::getRevenue)
                .sum();
        return new MonthlyStatisticResponse(totalRevenue, maxMonthRevenue, statisticResponses);
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
