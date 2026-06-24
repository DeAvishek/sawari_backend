package com.sawari.sawari.forRider.controller;

import com.sawari.sawari.common.dto.OtpPojo;
import com.sawari.sawari.forRider.service.genral.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Rider")
public class OtpVerifyController {
    @Autowired
    private RiderService riderService;
    @PostMapping("/verify/{rider}")
    public ResponseEntity<?>validateOtp(@RequestBody OtpPojo otp, @PathVariable Integer rider){
        try{
            Map<String,String> response = new HashMap<>();
            response.put("bearer",riderService.verifyOtp(otp,rider));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
