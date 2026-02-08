package com.sawari.sawari.controller;

import com.sawari.sawari.pojo.OtpPojo;
import com.sawari.sawari.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Rider")
public class OtpVerifyController {
    @Autowired
    private RiderService riderService;
    @PostMapping("/verify/{rider}")
    public String validateOtp(@RequestBody OtpPojo otp, @PathVariable Integer rider){
        return riderService.verifyOtp(otp,rider);
    }
}
