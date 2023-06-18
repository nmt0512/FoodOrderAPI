package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Integer> {
    @Query(value = "SELECT Link FROM Banner", nativeQuery = true)
    List<String> findAllLink();
}
