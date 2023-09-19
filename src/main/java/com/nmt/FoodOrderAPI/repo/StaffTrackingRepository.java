package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.entity.StaffTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffTrackingRepository extends JpaRepository<StaffTracking, Integer> {
}
