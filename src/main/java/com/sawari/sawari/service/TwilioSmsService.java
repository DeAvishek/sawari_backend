package com.sawari.sawari.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsService {
    @Value("${twilio.account.sid}")
    private String TwilioAccSid;

    @Value("${twilio.auth.token}")
    private String TwilioAccToken;

    @Value("${twilio.phone.number}")
    private String TwilioPhoneNumber;

    @PostConstruct
    public void init(){
        System.out.println("SID=" + TwilioAccSid);
        System.out.println("TOKEN=" + TwilioAccToken);
        Twilio.init(TwilioAccSid, TwilioAccToken);
    }

    public void SendOtpCode(String PhoneNumber,String OtpCode){
        Message.creator(
                new com.twilio.type.PhoneNumber(PhoneNumber), //this is to number
                new com.twilio.type.PhoneNumber(TwilioPhoneNumber), //this is from number
                "Your Sawari OTP is: " + OtpCode + ". Valid for 5 minutes."
        ).create();
    }

}
