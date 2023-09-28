package com.nmt.FoodOrderAPI.service.impl;

import com.nmt.FoodOrderAPI.dto.StaffTrackingStatisticResponse;
import com.nmt.FoodOrderAPI.repo.StaffTrackingStatisticRepository;
import com.nmt.FoodOrderAPI.service.StaffTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffTrackingServiceImpl implements StaffTrackingService {
    private final StaffTrackingStatisticRepository staffTrackingStatisticRepository;

    @Override
    public List<StaffTrackingStatisticResponse> getStaffTrackingStatistic(int month, int year) {
        String monthOfYear = String.format("%02d/%04d", month, year);
        return staffTrackingStatisticRepository.getStaffTrackingStatisticByMonthOfYear(monthOfYear);
    }

    @Override
    public List<String> getAllMonthStaffTrackingStatistic() {
        return staffTrackingStatisticRepository.getAllMonthStaffTrackingStatistic();
    }
}
