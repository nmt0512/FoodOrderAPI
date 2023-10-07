package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.entity.FirebaseUserDevice;
import com.nmt.FoodOrderAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FirebaseUserDeviceRepository extends JpaRepository<FirebaseUserDevice, Integer> {
    List<FirebaseUserDevice> findByUser(User user);
}
