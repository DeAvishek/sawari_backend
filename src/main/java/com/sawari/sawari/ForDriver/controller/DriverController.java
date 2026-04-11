package com.sawari.sawari.ForDriver.controller;

import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.ForDriver.service.DriverService;
import com.sawari.sawari.pojo.OtpPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
@Slf4j
public class DriverController {
    @Autowired
    private DriverService driverService;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody Driver driver){
        try{
            driverService.saveDriver(driver);
            return new ResponseEntity<>(driver, HttpStatus.CREATED);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> verifyOtp(@RequestBody OtpPojo otpPojo){
        try {
            //NEED TO RETURN BEARER SAME AS RIDER
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
