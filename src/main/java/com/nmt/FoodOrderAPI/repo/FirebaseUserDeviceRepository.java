package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.entity.FirebaseUserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirebaseUserDeviceRepository extends JpaRepository<FirebaseUserDevice, Integer> {
}
