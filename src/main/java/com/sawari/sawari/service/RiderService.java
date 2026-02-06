package com.sawari.sawari.service;

import com.sawari.sawari.Repository.RiderRepository;
import com.sawari.sawari.entity.Rider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class RiderService {
    @Autowired
    private OtpGeneratorAndSenderService otpGeneratorAndSenderService;

    @Autowired
    private RiderRepository riderRepository;

    public void CreateRider(Rider rider){
        try{
            String RiderOtp = otpGeneratorAndSenderService.GenerateOtp();
            String RiderPhoneNumber = rider.getPhoneNumber();
            rider.setIsVerified(false);
            rider.setOtp(RiderOtp);
            rider.setOtpExpiredAt(LocalDateTime.now().plusMinutes(5));
            RiderPhoneNumber = "+91"+RiderPhoneNumber;
            //off this service due to running out of money in Twilio ...uncomment only when need
            //otpGeneratorAndSenderService.SendOtp(RiderPhoneNumber,RiderOtp);
            riderRepository.save(rider);
        }catch(Exception e){
            log.error(e.getMessage());
        }


    }
}
