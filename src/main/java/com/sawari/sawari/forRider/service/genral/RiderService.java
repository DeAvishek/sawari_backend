package com.sawari.sawari.forRider.service.genral;

import com.sawari.sawari.forRider.Repository.RiderRepository;
import com.sawari.sawari.forRider.entity.Rider;
import com.sawari.sawari.common.dto.OtpPojo;
import com.sawari.sawari.common.dto.PhoneNumber;
import com.sawari.sawari.common.utiils.JwtUtil;
import com.sawari.sawari.forRider.service.twillio.OtpGeneratorAndSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
@Service
public class RiderService {
    @Autowired
    private OtpGeneratorAndSenderService otpGeneratorAndSenderService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RiderRepository riderRepository;

    public Rider CreateRider(Rider rider){
            String RiderOtp = otpGeneratorAndSenderService.GenerateOtp();
            String RiderPhoneNumber = rider.getPhoneNumber();
            rider.setIsVerified(false);
            rider.setOtp(RiderOtp);
            rider.setOtpExpiredAt(LocalDateTime.now().plusMinutes(5));
            RiderPhoneNumber = "+91"+RiderPhoneNumber;
            //off this service due to running out of money in Twilio ...uncomment only when need
            //otpGeneratorAndSenderService.SendOtp(RiderPhoneNumber,RiderOtp);
            rider.setRole("RIDER");
            rider.setTrips(new ArrayList<>());
            Rider savedRider = riderRepository.save(rider);
            if(savedRider == null){
                throw new RuntimeException("Something went wrong");
            }
           return savedRider;
    }
    public String verifyOtp(OtpPojo otp,Integer rider){
            Rider ExitedRider = riderRepository.findById(rider)
                    .orElseThrow(()->new RuntimeException("Rider not found"));

            if(ExitedRider.getIsVerified()){
                throw new RuntimeException("⚠ Rider is already verified");
            }
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime otpTime = ExitedRider.getOtpExpiredAt();

            if(now.isAfter(otpTime)){
                throw new RuntimeException("Otp has expired⌛");

            }
            if(!otp.getOtp().equals(ExitedRider.getOtp())){
                throw new RuntimeException("❌ Invalid OTP! please try again later");
            }
            ExitedRider.setIsVerified(true);
            ExitedRider.setOtp("");
            riderRepository.save(ExitedRider);
            log.info("otp verified successfully😍 "+LocalDateTime.now());
            return jwtUtil.GenerateJwtToken(ExitedRider.getUserName());
    }
    //login service for rider
    public Rider loginService(PhoneNumber phNo){
        if(phNo==null) throw new RuntimeException("phone number is null");
        Rider existedRider = riderRepository.findRiderByPhoneNumber(String .valueOf(phNo.getPhoneNumber()));
        if(existedRider==null){
            throw new RuntimeException("Rider not found");
        }
        if(!existedRider.getIsVerified()){
            throw new RuntimeException("Rider is Not verified");
        }
        existedRider.setOtp(otpGeneratorAndSenderService.GenerateOtp());
        riderRepository.save(existedRider);
        return existedRider;
    }
}
