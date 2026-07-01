package com.sawari.sawari.ForDriver.controller;
import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.ForDriver.service.general.DriverService;
import com.sawari.sawari.ForDriver.service.rateLimiter.RateLimiterService;
import com.sawari.sawari.common.dto.OtpPojo;
import com.sawari.sawari.common.dto.OtpSession;
import com.sawari.sawari.common.dto.PhoneNumber;
import com.sawari.sawari.common.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/Driver")
@Slf4j
public class DriverController {
    @Autowired
    private DriverService driverService;

    @Autowired
    private RateLimiterService rateLimiterService;

    @Autowired
    private CommonService commonService;


    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody Driver driver){
        try{
            String response = driverService.saveDriver(driver);
            // only create the driver when is not existed so should return a bearer --->right
            HashMap<String,String> result = new HashMap<>();
            String[]info = response.split("#");
            result.put("userId",info[0]);
            result.put("userName",info[1]);
            result.put("Bearer",info[2]);
            result.put("RefreshToken",info[3]);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/verify/{phNumber}")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpPojo otpPojo, @PathVariable String phNumber){
        try {
            HashMap<String,String>result=new HashMap<>();
            String response = commonService.VerifyOtpForDriver(otpPojo,phNumber);
            if(response.equals("")){
                result.put("Bearer",response);
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            }
            String[]info = response.split("#");
            result.put("userId",info[0]);
            result.put("userName",info[1]);
            result.put("Bearer",info[2]);
            result.put("RefreshToken",info[3]);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> LoginValidDriver(@RequestBody PhoneNumber phNo){
        try{
            if(!rateLimiterService.isRequestInsideThresholdForAuth()){
                return  new ResponseEntity<>("to many request",HttpStatus.TOO_MANY_REQUESTS);
            }
             System.out.println(phNo);
             OtpSession session = commonService.loginService(phNo);
             return new ResponseEntity<>(session, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/refresh-token/{refreshToken}")
    public ResponseEntity<?> refreshTokenGet(@PathVariable String refreshToken){
        log.info("Request route to refresh token Api with token "+refreshToken );
        HashMap<String,String> response=new HashMap<>();
        try{
            String result = commonService.refreshTokenServiceForDriver(refreshToken);
            String []info=result.split("#");
            response.put("RefreshToken",info[0]);
            response.put("Bearer",info[1]);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("Bearer",e.getMessage());
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
