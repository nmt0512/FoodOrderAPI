package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryCode(String categoryCode);
    List<Product> findByQuantityGreaterThan(int quantity);
}
