package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.entity.PendingPrepaidBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingPrepaidBillRepository extends JpaRepository<PendingPrepaidBill, Integer> {
}
