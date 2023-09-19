package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.entity.PendingPrepaidBillItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingPrepaidBillItemRepository extends JpaRepository<PendingPrepaidBillItem, Integer> {
}
