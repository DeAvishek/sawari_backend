package com.sawari.sawari.service;

import com.sawari.sawari.Repository.RiderRepository;
import com.sawari.sawari.entity.Rider;
import com.sawari.sawari.pojo.OtpPojo;
import com.sawari.sawari.utiils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
@Service
public class RiderService {
    @Autowired
    private OtpGeneratorAndSenderService otpGeneratorAndSenderService;

    @Autowired
    private JwtUtil jwtUtil;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();
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
            rider.setRole("RIDER");
            rider.setTrips(new ArrayList<>());
            riderRepository.save(rider);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }
    public String verifyOtp(OtpPojo otp,Integer rider){
        try{
            Rider ExitedRider = riderRepository.findById(rider)
                    .orElseThrow(()->new Exception("Rider not found"));
            if(!ExitedRider.getIsVerified() ){
                System.out.println("Rider otp "+ExitedRider.getOtp());
                System.out.println("Pojo otp "+otp.getOtp());
                if(otp.getOtp().equals(ExitedRider.getOtp())){
                    ExitedRider.setIsVerified(true);
                    ExitedRider.setOtp("");
                    riderRepository.save(ExitedRider);
                    return jwtUtil.GenerateJwtToken(ExitedRider.getUserName());
                }
                throw new Exception("Otp is not valid");
            }
            throw  new Exception("Otp has already been verified");
        }catch(Exception e){
            log.error(e.getMessage());
            return "";
        }

    }
}
