package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.dto.StaffTrackingStatisticResponse;
import com.nmt.FoodOrderAPI.repo.StaffTrackingStatisticRepository;
import com.nmt.FoodOrderAPI.service.StaffTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffTrackingServiceImpl implements StaffTrackingService {
    private final StaffTrackingStatisticRepository staffTrackingStatisticRepository;

    @Override
    public List<StaffTrackingStatisticResponse> getStaffTrackingStatistic(String time) {
        return staffTrackingStatisticRepository.getStaffTrackingStatisticByMonthOfYear(time);
    }

    @Override
    @Cacheable(value = "allMonthStaffTrackingStatisticCache")
    public List<String> getAllMonthStaffTrackingStatistic() {
        return staffTrackingStatisticRepository.getAllMonthStaffTrackingStatistic();
    }
}
