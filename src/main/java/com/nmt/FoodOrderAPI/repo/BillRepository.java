package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.entity.Bill;
import com.nmt.FoodOrderAPI.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {
    Page<Bill> findByStatus(Integer status, Pageable pageable);

    List<Bill> findByShipperAndStatus(User shipper, Integer status);
}
