package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
