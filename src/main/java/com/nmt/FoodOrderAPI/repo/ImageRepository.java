package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
