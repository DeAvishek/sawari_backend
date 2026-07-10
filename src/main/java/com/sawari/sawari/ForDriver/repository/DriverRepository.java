package com.sawari.sawari.ForDriver.repository;

import com.sawari.sawari.ForDriver.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Integer> {
    Driver findDriverByPhoneNumber(String phoneNumber);
    Driver findDriverByUserName(String userName);
}
