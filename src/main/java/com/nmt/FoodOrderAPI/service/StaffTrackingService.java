package com.nmt.FoodOrderAPI.service;

import com.nmt.FoodOrderAPI.dto.StaffTrackingStatisticResponse;

import java.util.List;

public interface StaffTrackingService {
    List<StaffTrackingStatisticResponse> getStaffTrackingStatistic(int month, int year);

    List<String> getAllMonthStaffTrackingStatistic();
}
