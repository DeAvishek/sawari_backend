package com.sawari.sawari.ForDriver.service;

import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.ForDriver.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;
    public void saveDriver(Driver requestBody){
        if(requestBody==null) throw new RuntimeException("Invalid Request");
        requestBody.setIsOnline(false);
        requestBody.setIsOnRide(false);
        requestBody.setIsVerified(false);
        driverRepository.save(requestBody);
    }
}
