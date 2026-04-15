package com.sawari.sawari.ForDriver.service;

import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.ForDriver.repository.DriverRepository;
import com.sawari.sawari.pojo.OtpPojo;
import com.sawari.sawari.utiils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private JwtUtil jwtUtil;
    public void saveDriver(Driver requestBody){
        if(requestBody==null) throw new RuntimeException("Invalid Request");
        requestBody.setOtp("123456"); //random hardcoded
        requestBody.setIsOnline(false);
        requestBody.setIsOnRide(false);
        requestBody.setIsVerified(false);
        requestBody.setOtpExpiredAt(LocalDateTime.now().plusMinutes(5)); //set otp expired at
        driverRepository.save(requestBody);
    }
    public String VerifyOtp(OtpPojo requestBody,Integer driverId){
        if(requestBody==null) throw new RuntimeException("Invalid Request");
        Driver existedDriver = driverRepository.findById(driverId).orElseThrow(()->new RuntimeException("Driver not found"));
        if(existedDriver.getIsVerified()){
            throw new RuntimeException("⚠ Driver is already verified");
        }
        if(LocalDateTime.now().isAfter(existedDriver.getOtpExpiredAt())){
            throw new RuntimeException("Otp has expired⌛");
        }
        if(!existedDriver.getOtp().equals(requestBody.getOtp())){
            throw new RuntimeException("❌ Invalid OTP! please try again later");
        }
        //if everything okay then navigate to next phase
        existedDriver.setIsVerified(true);
        existedDriver.setOtp("");
        driverRepository.save(existedDriver);
        return jwtUtil.GenerateJwtToken(existedDriver.getName());
    }
}
