package com.sawari.sawari.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpGeneratorAndSenderService {
    @Autowired
    private TwilioSmsService twilioSmsService;
    //    Otp generator Function
    public String GenerateOtp(){
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    //   Otp sender Function
    public void SendOtp(String RiderPhoneNumber,String RiderOtp){
        twilioSmsService.SendOtpCode(RiderPhoneNumber,RiderOtp);
    }
}
