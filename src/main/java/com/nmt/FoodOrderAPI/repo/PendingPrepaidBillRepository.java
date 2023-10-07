package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.entity.PendingPrepaidBill;
import com.nmt.FoodOrderAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PendingPrepaidBillRepository extends JpaRepository<PendingPrepaidBill, Integer> {
    List<PendingPrepaidBill> findByShipperNullOrderByTimeDesc();

    Optional<PendingPrepaidBill> findByIdAndShipperNull(int pendingPrepaidBillId);

    Optional<PendingPrepaidBill> findByIdAndShipperNotNull(int pendingPrepaidBillId);

    List<PendingPrepaidBill> findByShipperAndIsCustomerPrepaidTrue(User shipper);
}
