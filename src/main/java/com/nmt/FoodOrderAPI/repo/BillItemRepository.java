package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.entity.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemRepository extends JpaRepository<BillItem, Integer> {
}
