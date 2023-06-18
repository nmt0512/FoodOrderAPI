package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    List<Promotion> findByApplyingPriceLessThanEqual(int totalPrice);
}
